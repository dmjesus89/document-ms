package com.petrotec.documentms.mappers.documents;

import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.documents.*;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.documents.*;
import com.petrotec.documentms.entities.product.Product;
import com.petrotec.documentms.mappers.SiteMapper;
import com.petrotec.documentms.repositories.SiteRepository;
import com.petrotec.documentms.repositories.documents.*;
import com.petrotec.documentms.repositories.product.ProductRepository;
import org.mapstruct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330", uses = {SiteMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class DocumentCreationMapper {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentCreationMapper.class);
    @Inject
    protected TranslateMapper translateMapper;
    @Inject
    protected PaymentModeRepository paymentModeRepository;
    @Inject
    protected PaymentMovementRepository paymentMovementRepository;
    @Inject
    protected DocumentTypeRepository documentTypeRepository;
    @Inject
    protected PromptRepository promptRepository;
    @Inject
    protected CardSystemRepository cardSystemRepository;
    @Inject
    protected ProductRepository productRepository;
    @Inject
    protected DocumentLineRepository documentLineRepository;
    @Inject
    protected DocumentSerieRepository documentSerieRepository;
    @Inject
    protected DocumentSaleTypeRepository documentSaleTypeRepository;
    @Inject
    protected SiteRepository siteRepository;

    public abstract List<Document> toEntity(List<DocumentDTO> document, @Context String locale);

    @Mapping(target = "serie", source = "document", qualifiedByName = "documentSerieToEntity")
    @Mapping(target = "documentSaleType", source = "docType", qualifiedByName = "documentSaleTypeToEntity")
    @Mapping(target = "site", source = "siteCode", qualifiedByName = "siteToEntity")
    @Mapping(target = "posNumber", source = "posNumber")
    public abstract Document toEntity(DocumentDTO document, @Context String locale);

    @Mapping(target = "productDescription", expression = "java(translateMapper.setDescription(documentLine.getProductDescription(), documentLineDTO.getProductDescription(), null, locale))")
    @Mapping(target = "productDescriptionLong", expression = "java(translateMapper.setDescription(documentLine.getProductDescriptionLong(), documentLineDTO.getProductDescriptionLong(), null, locale))")
    @Mapping(target = "product", source = "productCode")
    public abstract DocumentLine toEntity(DocumentLineDTO documentLineDTO, @Context String locale);


    public abstract DocumentLineFuel toEntity(DocumentLineFuelDTO documentLineFuelDTO, @Context String locale);

    public abstract DocumentLineSuppliedDiff toEntity(DocumentLineSuppliedDiffDTO documentLineSuppliedDiffDTO, @Context String locale);

    @Mapping(target = "isoCountryCode", source = "isoCountryCode", defaultExpression = "java(\"PT\")")
    public abstract DocumentParty toEntity(DocumentPartyDTO documentPartyDTO, @Context String locale);

    public abstract DocumentTax toEntity(DocumentTaxDTO documentTaxDTO, @Context String locale);

    public abstract DocumentReceipt toEntity(DocumentReceiptDTO documentReceiptDTO, @Context String locale);


    @Mapping(target = "movement", source = "movementCode")
    @Mapping(target = "paymentMode", source = "paymentModeCode")
    public abstract DocumentPayment toEntity(DocumentPaymentDTO documentPayment, @Context String locale);


    @Mapping(target = "documentType", source = "documentSeriesType")
    @Mapping(target = "site", source = "siteCode")
    @Mapping(target = "siteDevicePos", source = "posCode")
    public abstract DocumentSerie toEntity(DocumentSeriesDTO documentSeriesDTO, @Context String locale);

    public String getDocumentType(DocumentSeriesDTO.DocumentSeriesTypeEnum type) {
        return type.name();
    }

    public abstract DocumentLegalAuthority toEntity(DocumentLegalAuthorityDTO documentLegalAuthorityDTO, @Context String locale);

    @Mapping(target = "prompt", source = "promptCode")
    public abstract DocumentPrompt toEntity(DocumentPromptDTO documentPromptDTO, @Context String locale);

    @Mapping(target = "cardSystem", source = "cardSystemCode")
    public abstract DocumentPaymentCard toEntity(DocumentPaymentCardDTO documentPaymentCardDTO, @Context String locale);

    @Mapping(target = "referencedDocumentLine", source = "refDocumentLineCode")
    public abstract DocumentLineRef toDTO(DocumentLineRefDTO documentLineRef, @Context String locale);

    public Collection<DocumentLine> lineMapToEntity(Map<Integer, DocumentLineDTO> integerDocumentLineDTOMap, @Context String locale) {
        if (integerDocumentLineDTOMap == null) return null;
        return integerDocumentLineDTOMap.values().stream().map(lineDto -> toEntity(lineDto, locale)).collect(Collectors.toCollection(ArrayList::new));
    }

    public Collection<DocumentPayment> paymentCollectionToEntity(Map<String, List<DocumentPaymentDTO>> documentPaymentCollection, @Context String locale) {
        if (documentPaymentCollection == null) return null;

        ArrayList<DocumentPayment> payments = new ArrayList<>();
        for (List<DocumentPaymentDTO> paymentList : documentPaymentCollection.values()) {
            payments.addAll(paymentList.stream().map(payment -> toEntity(payment, locale)).collect(Collectors.toList()));
        }
        return payments;
    }

    public Collection<DocumentTax> taxMapToEntity(Map<String, List<DocumentTaxDTO>> documentTaxCollection, @Context String locale) {
        if (documentTaxCollection == null) return null;
        ArrayList<DocumentTax> taxes = new ArrayList<>();
        for (List<DocumentTaxDTO> taxDTOList : documentTaxCollection.values()) {
            taxes.addAll(taxDTOList.stream().map(documentTaxDTO -> toEntity(documentTaxDTO, locale)).collect(Collectors.toList()));
        }
        return taxes;
    }


    public DocumentType documentTypeToEntity(DocumentSeriesDTO.DocumentSeriesTypeEnum type) {
        return documentTypeRepository.findByCode(type.name()).orElseThrow(() -> new EntityNotFoundException(String.format("Error while parsing documentDTO. Could not find any document with code: [%s]", type.name())));
    }

    public PaymentMode paymentModeToEntity(String paymentModeCode) {
        return paymentModeRepository.findByCode(paymentModeCode).orElseThrow(() -> new EntityNotFoundException(String.format("Error while parsing documentDTO. Could not find any Payment with code: [%s]", paymentModeCode)));
    }

    public PaymentMovement paymentMovementToEntity(String paymentMovementCode) {
        return paymentMovementRepository.findByCode(paymentMovementCode).orElseThrow(() -> new EntityNotFoundException(String.format("Error while parsing documentDTO. Could not find any PaymentMovment with code: [%s]", paymentMovementCode)));
    }

    public Prompt promptToEntity(String promptCode) {
        return promptRepository.findByCode(promptCode).orElseThrow(() -> new EntityNotFoundException(String.format("Error while parsing documentDTO. Could not find any Prompt with code: [%s]", promptCode)));
    }

    public CardSystem cardSystemToEntity(String cardSystemCode) {
        return cardSystemRepository.findByCode(cardSystemCode).orElseThrow(() -> new EntityNotFoundException(String.format("Error while parsing documentDTO. Could not find any Card System with code: [%s]", cardSystemCode)));
    }

    public Product productToEntity(String productCode) {
        return productRepository.findByCode(productCode).orElseThrow(() -> new EntityNotFoundException(String.format("Error while parsing documentDTO. Could not find any Product with code: [%s]", productCode)));
    }

    public DocumentLine documentLineToEntity(String documentLineCode) {
        return documentLineRepository.findByCode(documentLineCode).orElseThrow(() -> new EntityNotFoundException(String.format("Error while parsing documentDTO. Could not find any Document Line with code: [%s]", documentLineCode)));
    }

    @Named("documentSaleTypeToEntity")
    public DocumentSaleType mapDocumentType(DocumentDTO.DocumentSaleTypeEnum documentType) {
        if (documentType == null)
            return documentSaleTypeRepository.findByCode("SALE").orElse(null);
        return documentSaleTypeRepository.findByCode(documentType.name()).orElse(null);
    }

    @Named("siteToEntity")
    public Site mapSite(String siteCode) {
        if (siteCode == null) return null;

        return siteRepository.findByCode(siteCode).orElse(null);
    }

    @Named("documentSerieToEntity")
    public DocumentSerie documentSerieToEntity(DocumentDTO document, @Context String locale) {
        //Document will be filled by the DocumentService
        if (document == null || document.getSeries() == null)
            return null;

        DocumentSeriesDTO documentSeriesDTO = document.getSeries();

        Optional<DocumentSerie> serieQuery = null;
        if (documentSeriesDTO.getCode() != null) {
            LOG.info("Document serie was sent to the could");
            serieQuery = documentSerieRepository.findByCode(documentSeriesDTO.getCode());
        }

        if (serieQuery == null || !serieQuery.isPresent()) {
            LOG.info("No document serie was found");
            if (documentSeriesDTO.getLastDocumentNumber() == null)
                documentSeriesDTO.setLastDocumentNumber(BigInteger.ZERO);

            documentSeriesDTO.setSiteCode(document.getSiteCode());
            documentSeriesDTO.setPosCode(document.getPosCode());

            return toEntity(documentSeriesDTO, locale);
        }

        LOG.info("Fetching document serie from db");
        DocumentSerie serie = serieQuery.get();

        if (documentSeriesDTO.getLastDocumentNumber() != null)
            serie.setLastDocumentNumber(documentSeriesDTO.getLastDocumentNumber());

        return serie;
    }

    @AfterMapping
    protected void afterConvertingDocument(@MappingTarget Document createdDocument) {
        if (createdDocument.getLines() != null) {
            for (DocumentLine line : createdDocument.getLines()) {
                line.setDocument(createdDocument);

                if (line.getLineReference() != null) line.getLineReference().setDocumentLine(line);
                if (line.getSuppliedDiff() != null) line.getSuppliedDiff().setDocumentLine(line);
                if (line.getFuelLine() != null) line.getFuelLine().setDocumentLine(line);
            }
        }

        if (createdDocument.getReceipt() != null) createdDocument.getReceipt().setDocument(createdDocument);

        if (createdDocument.getParty() != null) createdDocument.getParty().setDocument(createdDocument);

        if (createdDocument.getLegalAuthority() != null)
            createdDocument.getLegalAuthority().setDocument(createdDocument);


        if (createdDocument.getPrompts() != null)
            createdDocument.getPrompts().stream().forEach(prompt -> prompt.setDocument(createdDocument));

        if (createdDocument.getPayments() != null)
            createdDocument.getPayments().stream().forEach(payment -> payment.setDocument(createdDocument));

        if (createdDocument.getTaxes() != null) {

            //Taxes not supported :'(
            createdDocument.setTaxes(null);
            //createdDocument.getTaxes().stream().forEach(taxes -> taxes.setDocument(createdDocument));
        }
    }
}
