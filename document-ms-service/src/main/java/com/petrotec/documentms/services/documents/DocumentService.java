package com.petrotec.documentms.services.documents;

import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.api.queue.MessageOperationEnum;
import com.petrotec.queue.annotations.MqttPublish;
import com.petrotec.queue.annotations.MqttPublisher;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.documents.*;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteDevicePos;
import com.petrotec.documentms.entities.documents.*;
import com.petrotec.documentms.mappers.documents.DocumentCreationMapper;
import com.petrotec.documentms.mappers.documents.DocumentMapper;
import com.petrotec.documentms.repositories.SiteDevicePosRepository;
import com.petrotec.documentms.repositories.SiteRepository;
import com.petrotec.documentms.repositories.documents.DocumentReceiptRepository;
import com.petrotec.documentms.repositories.documents.DocumentRepository;
import com.petrotec.documentms.repositories.documents.DocumentSerieRepository;
import com.petrotec.documentms.repositories.documents.DocumentTypeRepository;
import io.micronaut.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
@Transactional
@MqttPublisher(topicName = "pcs/document-ms/documents")
public class DocumentService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final DocumentCreationMapper documentCreationMapper;
    private final DocumentSerieRepository documentSerieRepository;
    private final DocumentReceiptRepository documentReceiptRepository;
    private final SiteRepository siteRepository;
    private final SiteDevicePosRepository siteDevicePosRepository;
    private final DocumentTypeRepository documentTypeRepository;


    public DocumentService(DocumentRepository documentRepository,
                           DocumentMapper documentMapper,
                           DocumentCreationMapper documentCreationMapper,
                           DocumentSerieRepository documentSerieRepository,
                           DocumentReceiptRepository documentReceiptRepository,
                           SiteRepository siteRepository,
                           SiteDevicePosRepository siteDevicePosRepository,
                           DocumentTypeRepository documentTypeRepository) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
        this.documentCreationMapper = documentCreationMapper;
        this.documentSerieRepository = documentSerieRepository;
        this.documentReceiptRepository = documentReceiptRepository;
        this.siteRepository = siteRepository;
        this.siteDevicePosRepository = siteDevicePosRepository;
        this.documentTypeRepository = documentTypeRepository;
    }

    public Document getDocumentEntity(String documentCode) {
        return documentRepository.findByCode(documentCode).orElseThrow(() -> {
            LOG.debug("No Document found for code [{}]", documentCode);
            return new EntityNotFoundException(String.format("No Document found for code [%s]", documentCode));
        });
    }

    public PageResponse<DocumentExtendedDTO> getDocuments(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);

        int limit = pageAndSorting.getLimit() + 1;

        /*Retrieve all with pagination and filtering*/
        List<Document> results = documentRepository
                .findAll(pageAndSorting.getOffset(), limit, pageAndSorting.getSorting(), filterQuery);

        /*Generate PageResponse from returned result and given pagination fields*/
        return PageResponse.from(documentMapper.toDTO(results, locale), pageAndSorting, () -> documentRepository.count(filterQuery));
    }

    public DocumentExtendedDTO getDocumentByCode(String documentCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        Document document = getDocumentEntity(documentCode);

        return documentMapper.toDTO(document, locale);
    }

    @MqttPublish(operationType = MessageOperationEnum.CREATED)
    public DocumentExtendedDTO integrateDocument(@Valid DocumentDTO documentDTO) {
        try {
            String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

            if (documentDTO != null && documentDTO.getCode() != null) {
                /*check if the document exists*/
                Optional<Document> doc = documentRepository.findByCode(documentDTO.getCode());
                if (doc.isPresent()) {
                    /*Return the saved document to allow POS or other third party to continue their operations*/

                    if (documentDTO.getLines().values().stream().filter(documentLineDTO -> documentLineDTO.getType() == SaleLineTypeEnum.PREPAID_FUEL_TRANSACTION).findAny().isPresent()) {
                        return documentMapper.toDTO(updateDocumentPrepaymentLine(documentDTO, doc.get()), locale);
                    }
                    return documentMapper.toDTO(doc.get(), locale);
                }
            }
        } catch (Exception ex) {
            LOG.error("ERROR while integrating document", ex);
            throw ex;
        }

        return buildDocument(documentDTO);
    }

    public Document updateDocumentPrepaymentLine(DocumentDTO documentDTO, Document document) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        Map<String, DocumentLineDTO> existingDocumentLines =
                documentDTO.getLines().values().stream().filter(documentLine -> documentLine.getType() == SaleLineTypeEnum.PREPAID_FUEL_TRANSACTION).collect(Collectors.toMap(n -> n.getCode(), n -> n));

        document.getLines().forEach(documentLine -> {
            DocumentLineDTO documentLineDTO = existingDocumentLines.get(documentLine.getCode());
            if (documentLineDTO != null) {
                documentLine.setQuantity(documentLineDTO.getQuantity());
                documentLine.setTotalAmount(documentLineDTO.getTotalAmount());
                DocumentLineFuel fuelLine = documentCreationMapper.toEntity(documentLineDTO.getFuelLine(), locale);
                if (documentLine.getFuelLine() != null) {
                    documentLine.getFuelLine().setNozzle(fuelLine.getNozzle());
                    documentLine.getFuelLine().setTransactionId(fuelLine.getTransactionId());
                } else {
                    documentLine.setFuelLine(fuelLine);
                }

            }
        });

        Document savedDoc = documentRepository.update(document);
        documentSerieRepository.getEntityManager().flush();
        documentSerieRepository.getEntityManager().refresh(savedDoc);
        return savedDoc;
    }


    public DocumentExtendedDTO buildDocument(DocumentDTO documentDTO) {
        try {
            String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
            String entityCode = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_ENTITY_CODE);
            String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);

            if (documentDTO.getParty() != null && documentDTO.getParty().getIsoCountryCode() != null && documentDTO.getParty().getIsoCountryCode().length() > 3) {
                documentDTO.getParty().setIsoCountryCode("PT");
            }
            Document document = documentCreationMapper.toEntity(documentDTO, locale);
            validateDocument(document);

            document.setEntityCode(entityCode);
            document.setEntityRankOrder(Short.valueOf(rankOrder));

            /*I need to add payment foregn key to each card beacuse jpa limitation on oneToMany, cascade does not work*/
            if (document.getPayments() != null) {
                document.getPayments().stream().filter(p -> p.getCards() != null).forEach(p -> p.getCards().forEach(c -> c.setPayment(p)));
            }

            validateDocumentSerie(document, documentDTO);
            Document savedDoc = documentRepository.save(document);

            documentSerieRepository.getEntityManager().flush();
            documentSerieRepository.getEntityManager().refresh(savedDoc);

            DocumentExtendedDTO savedDocumentExtendedDTO = documentMapper.toDTO(savedDoc, locale);

            return savedDocumentExtendedDTO;
        } catch (Exception ex) {
            LOG.error("ERROR while integrating document", ex);
            throw ex;
        }
    }

    private void validateDocument(Document document) {
        if (document.getSerie() != null) {
            //TODO
            LOG.error("Temos que validar se o legal authority tem o document number aqui");
            //if (StringUtils.isEmpty(document.getNumber()))
            //    throw new InvalidDataException(String.format("Documents sent with custom DocumentSerie must have field 'document.number' associated to them"));
        } else if (StringUtils.isEmpty(document.getNumber())) {
            LOG.debug(String.format("Field 'number' on Document will be overwritten"));
        }

        if (document.getLines() == null) {
            document.setNumberOfLines(0);
        } else {
            document.setNumberOfLines(document.getLines().size());
        }
    }

    private void validateDocumentSerie(Document document, DocumentDTO documentDTO) {
        if (document.getSerie() == null) {
            String siteCode = documentDTO.getSiteCode();
            String posDeviceCode = documentDTO.getPosCode();

            /*TODO - Must be changed by Serie configurations*/
            String defaultDocumentTypeCode = "FS";

            if (document.getPayments() != null) {
                /*TODO - There might be more than one DocumentType associated to the current sale. Will return Any!*/
                Optional<DocumentPayment> documentPaymentQuery = document.getPayments().stream().filter(p -> p.getPaymentMode().getDocumentType() != null).findAny();
                if (documentPaymentQuery.isPresent()) {
                    defaultDocumentTypeCode = documentPaymentQuery.get().getPaymentMode().getDocumentType().getCode();
                }
            }


            Optional<DocumentSerie> serieQuery = documentSerieRepository.findSerieForSale(siteCode, posDeviceCode, defaultDocumentTypeCode);
            if (serieQuery.isPresent()) {
                document.setSerie(serieQuery.get());
            } else {
                Site site = siteRepository.findByCode(siteCode).orElseThrow(() -> {
                    LOG.debug("No Site found for code [{}]", siteCode);
                    return new EntityNotFoundException(String.format("No Site found for code [%s]", siteCode));
                });
                SiteDevicePos pos = siteDevicePosRepository.findByCode(posDeviceCode).orElseThrow(() -> {
                    LOG.debug("No PosDevice found for code [{}]", posDeviceCode);
                    return new EntityNotFoundException(String.format("No PosDevice found for code [%s]", posDeviceCode));
                });
                String finalDefaultDocumentTypeCode = defaultDocumentTypeCode;
                DocumentType documentType = documentTypeRepository.findByCode(defaultDocumentTypeCode).orElseThrow(() -> {
                    LOG.debug("No DocumentType found for code [{}]", finalDefaultDocumentTypeCode);
                    return new EntityNotFoundException(String.format("No DocumentType found for code [%s]", finalDefaultDocumentTypeCode));
                });

                DocumentSerie serie = new DocumentSerie();
                serie.setLastDocumentNumber(BigInteger.ZERO);
                serie.setDocumentType(documentType);
                serie.setSite(site);
                serie.setSiteDevicePos(pos);
                serie.setEnabled(true);

                /*Save serie and associate saved serie to current document*/
                document.setSerie(serie);
            }

            /*Incrementar série existente e associar número à venda actual*/
            incrementSaleSerieNumber(document);
        }


    }

    private void incrementSaleSerieNumber(Document document) {
        BigInteger nextSaleNumber = document.getSerie().getLastDocumentNumber().add(BigInteger.ONE);
        document.getSerie().setLastDocumentNumber(nextSaleNumber);
        document.setNumber(nextSaleNumber.toString());
    }


    public PageResponse<DocumentReceiptExtraDataDTO> getDocumentReceipts(PageAndSorting pageAndSorting, Filter filterQuery) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);

        int limit = pageAndSorting.getLimit() + 1;

        /*Retrieve all with pagination and filtering*/
        List<DocumentReceipt> results = documentReceiptRepository
                .findAll(pageAndSorting.getOffset(), limit, pageAndSorting.getSorting(), filterQuery);

        /*Generate PageResponse from returned result and given pagination fields*/
        return PageResponse.from(documentMapper.toExtraDataReceiptDTO(results, locale), pageAndSorting, () -> documentRepository.count(filterQuery));
    }

    public DocumentExtendedDTO insertDocumentReceiptData(String documentCode, @Valid DocumentReceiptDTO receiptDTO) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        Document doc = getDocumentEntity(documentCode);

        if (doc.getReceipt() != null) {
            throw new EntityExistsException(String.format("Document with code[%s] has receipt data and Receipts can't be changed", documentCode));
        }

        DocumentReceipt documentReceipt = documentCreationMapper.toEntity(receiptDTO, locale);
        documentReceipt.setDocument(doc);
        doc.setReceipt(documentReceipt);

        Document savedDocument = documentRepository.save(doc);

        return documentMapper.toDTO(savedDocument, locale);
    }

    public DocumentReceiptExtraDataDTO increaseDocumentReceiptCopies(String documentCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        Document doc = getDocumentEntity(documentCode);
        if (doc.getReceipt() == null) {
            throw new EntityNotFoundException(String.format("Document with code[%s] has no receipt data", documentCode));
        }

        doc.getReceipt().setReprintNumber(doc.getReceipt().getReprintNumber() + 1);

        Document savedDocument = documentRepository.save(doc);

        return documentMapper.toExtraDataDTO(savedDocument.getReceipt(), locale);
    }

}
