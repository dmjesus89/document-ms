package com.petrotec.documentms.dtos.documents.search;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Introspected
@SuperBuilder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentHeaderDTO {
    private Long id;
    private LocalDateTime documentDate;
    private String siteCode;
    private String siteDescription;
    private String saleTypeCode;
    private String saleTypeDescription;
    private Integer posNumber;
    private String documentCode;
    private Integer documentNumberOfLines;
    private BigDecimal documentNetAmount;
    private BigDecimal documentTaxAmount;
    private BigDecimal documentGrossAmount;
    private BigDecimal documentDiscountAmount;
    private BigDecimal documentTotalAmount;
    private String documentInvoiceNumber;
    private String documentSerieHash;
    private String documentSerieHashPrint;
    private String documentSerieHashControl;
    private String documentSerieCertificateNumber;
    private String partyCode;
    private String partyName;
    private String partyVatin;
    private String partyStreet;
    private String partyPostalZipCode;
    private String partyCity;
    private String partyIsoCountryCode;
    private String documentTypeCode;
    private String documentTypePrefix;
    private String documentTypeDescription;
    private String documentSerieCode;
}
