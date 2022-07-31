package com.petrotec.documentms.mappers.documents;


import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.documents.*;
import com.petrotec.documentms.entities.documents.*;
import com.petrotec.documentms.mappers.SiteMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330", uses = {SiteMapper.class, TranslateMapper.class})
public abstract class DocumentMapper {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentMapper.class);


    public abstract List<DocumentExtendedDTO> toDTO(List<Document> document, @Context String locale);

    public abstract List<DocumentReceiptExtraDataDTO> toExtraDataReceiptDTO(List<DocumentReceipt> documentTaxReceipt, @Context String locale);

    @Mapping(target = "serie.siteDevicePos.posNumber", source = "serie.siteDevicePos.number")
    @Mapping(target = "serie.siteDevicePos.description", source = "serie.siteDevicePos.description")
    @Mapping(target = "docType", source = "documentSaleType.code", qualifiedByName = "docTypeFromSaleType")
    @Mapping(target = "serie.siteDevicePos.site", ignore = true)
    @Mapping(target = "siteCode", source = "site.code")
    public abstract DocumentExtendedDTO toDTO(Document document, @Context String locale);

    @Mapping(target = "productDescription", source = "productDescription", qualifiedByName = "descriptionForLocaleFromMap")
    @Mapping(target = "productDescriptionLong", source = "productDescriptionLong", qualifiedByName = "descriptionForLocaleFromMap")
    @Mapping(target = "product.description", source = "product.description", qualifiedByName = "descriptionForLocaleFromMap")
    public abstract DocumentLineExtendedDTO toDTO(DocumentLine documentLine, @Context String locale);

    public abstract DocumentLineSuppliedDiffDTO toDTO(DocumentLineSuppliedDiff documentLineSuppliedDiff, @Context String locale);

    public abstract DocumentLineFuelDTO toDTO(DocumentLineFuel documentLineFuel, @Context String locale);


    public abstract DocumentPartyDTO toDTO(DocumentParty documentParty, @Context String locale);

    @Mapping(target = "siteDevicePos.description", source = "siteDevicePos.description")
    @Mapping(target = "siteDevicePos.site", ignore = true)
    public abstract DocumentSerieExtendedDTO toDTO(DocumentSerie documentSerie, @Context String locale);

    @Mapping(target = "description", source = "description", qualifiedByName = "descriptionForLocaleFromMap")
    public abstract DocumentTypeDTO toDTO(DocumentType documentType, @Context String locale);

    public abstract DocumentLegalAuthorityExtendedDTO toDTO(DocumentLegalAuthority documentLegalAuthority, @Context String locale);

    public abstract DocumentTaxExtendedDTO toDTO(DocumentTax documentTax, @Context String locale);

    public abstract DocumentReceiptDTO toDTO(DocumentReceipt documentTaxReceipt, @Context String locale);

    @Mapping(target = "documentCode", source = "document.code")
    public abstract DocumentReceiptExtraDataDTO toExtraDataDTO(DocumentReceipt documentTaxReceipt, @Context String locale);

    public abstract DocumentPrompt toDTO(DocumentPrompt documentPrompt, @Context String locale);

    @Mapping(target = "description", source = "description", qualifiedByName = "descriptionForLocaleFromMap")
    public abstract PromptDTO toDTO(Prompt prompt, @Context String locale);


    public abstract DocumentPaymentExtendedDTO toDTO(DocumentPayment documentPayment, @Context String locale);

    public abstract DocumentPaymentCardExtendedDTO toDTO(DocumentPaymentCard documentPayment, @Context String locale);

    @Mapping(target = "description", source = "description", qualifiedByName = "descriptionForLocaleFromMap")
    public abstract PaymentModeDTO toDTO(PaymentMode paymentMode, @Context String locale);

    @Mapping(target = "description", source = "description", qualifiedByName = "descriptionForLocaleFromMap")
    public abstract PaymentMovementDTO toDTO(PaymentMovement paymentMovement, @Context String locale);

    @Mapping(target = "description", source = "description", qualifiedByName = "descriptionForLocaleFromMap")
    public abstract CardSystemDTO toDTO(CardSystem paymentMovement, @Context String locale);


    @Mapping(target = "refDocumentCode", source = "referencedDocumentLine.document.code")
    @Mapping(target = "refDocumentLineCode", source = "referencedDocumentLine.code")
    @Mapping(target = "refDocumentLineNumber", source = "referencedDocumentLine.line")
    public abstract DocumentLineRefDTO toDTO(DocumentLineRef documentLineRef, @Context String locale);

    public Map<Integer, DocumentLineExtendedDTO> lineCollectionToDTO(Collection<DocumentLine> documentLineCollection, @Context String locale) {
        if (documentLineCollection == null) return null;
        return documentLineCollection.stream().collect(Collectors.toMap(DocumentLine::getLine, line -> toDTO(line, locale)));
    }

    public Map<String, List<DocumentPaymentExtendedDTO>> paymentCollectionToDTO(Collection<DocumentPayment> documentPaymentCollection, @Context String locale) {
        if (documentPaymentCollection == null) return null;

        Map<String, List<DocumentPaymentExtendedDTO>> paymentMap = new HashMap<>();

        for (DocumentPayment payment : documentPaymentCollection) {
            //Adiciona codigo de pagamento ao hashmap caso este nao exista e adiciona o novo elemento
            paymentMap.computeIfAbsent(payment.getPaymentMode().getCode(), k -> new ArrayList<DocumentPaymentExtendedDTO>()).add(toDTO(payment, locale));
        }

        return paymentMap;
    }

    public Map<String, List<DocumentTaxExtendedDTO>> taxCollectionToDTO(Collection<DocumentTax> documentTaxCollection, @Context String locale) {
        if (documentTaxCollection == null) return null;

        Map<String, List<DocumentTaxExtendedDTO>> paymentMap = new HashMap<>();

        for (DocumentTax tax : documentTaxCollection) {
            //Adiciona codigo de pagamento ao hashmap caso este nao exista e adiciona o novo elemento
            paymentMap.computeIfAbsent(tax.getTaxItem().getCode(), k -> new ArrayList<DocumentTaxExtendedDTO>()).add(toDTO(tax, locale));
        }

        return paymentMap;
    }

    @Named(value = "docTypeFromSaleType")
    public DocumentDTO.DocumentSaleTypeEnum getSaleTypeFromString(String code) {
        if (code == null)
            return DocumentDTO.DocumentSaleTypeEnum.SALE;
        try {
            return DocumentDTO.DocumentSaleTypeEnum.valueOf(code);
        } catch (Exception ex) {
            LOG.error("Error converting docType", ex);
            return DocumentDTO.DocumentSaleTypeEnum.SALE;
        }
    }

    @Named(value = "descriptionForLocaleFromMap")
    public String getDescriptionForLocale(Map<String, String> translationsMap, @Context String locale) {
        if (locale == null) {
            locale = "en-en";
        }

        try {
            return translationsMap.computeIfAbsent(locale, k -> translationsMap.computeIfAbsent("en-en", k2 -> "NA"));
        } catch (Exception ex) {
            return "NA";
        }
    }
}
