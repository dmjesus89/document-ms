package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Introspected
public class DocumentExtendedDTO {
    private String code;
    private String siteCode;
    private String number;
    private DocumentDTO.DocumentSaleTypeEnum docType;
    private LocalDateTime documentDate;
    private short numberOfLines;
    private BigDecimal netAmount;
    private BigDecimal taxAmount;
    private BigDecimal grossAmount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private String receiptData;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private DocumentSerieExtendedDTO serie;
    private DocumentLegalAuthorityExtendedDTO legalAuthority;
    private Map<Integer, DocumentLineExtendedDTO> lines;
    private DocumentPartyDTO party;
    private Map<String, List<DocumentPaymentExtendedDTO>> payments;
    private Collection<DocumentPromptExtendedDTO> prompts;
    private DocumentReceiptDTO receipt;
    private Map<String, List<DocumentTaxExtendedDTO>> taxes;
    private boolean stockMovement;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public DocumentDTO.DocumentSaleTypeEnum getDocType() {
        return docType;
    }

    public void setDocType(DocumentDTO.DocumentSaleTypeEnum docType) {
        this.docType = docType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateTime documentDate) {
        this.documentDate = documentDate;
    }

    public short getNumberOfLines() {
        return numberOfLines;
    }

    public void setNumberOfLines(short numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getReceiptData() {
        return receiptData;
    }

    public void setReceiptData(String receiptData) {
        this.receiptData = receiptData;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public DocumentSerieExtendedDTO getSerie() {
        return serie;
    }

    public void setSerie(DocumentSerieExtendedDTO documentSerieByDocumentSerieId) {
        this.serie = documentSerieByDocumentSerieId;
    }

    public DocumentLegalAuthorityExtendedDTO getLegalAuthority() {
        return legalAuthority;
    }

    public void setLegalAuthority(DocumentLegalAuthorityExtendedDTO documentLegalAuthorityById) {
        this.legalAuthority = documentLegalAuthorityById;
    }

    public Map<Integer, DocumentLineExtendedDTO> getLines() {
        return lines;
    }

    public void setLines(Map<Integer, DocumentLineExtendedDTO> documentLinesById) {
        this.lines = documentLinesById;
    }

    public DocumentPartyDTO getParty() {
        return party;
    }

    public void setParty(DocumentPartyDTO documentPartyById) {
        this.party = documentPartyById;
    }

    public Map<String, List<DocumentPaymentExtendedDTO>> getPayments() {
        return payments;
    }

    public void setPayments(Map<String, List<DocumentPaymentExtendedDTO>> documentPaymentsById) {
        this.payments = documentPaymentsById;
    }

    public Collection<DocumentPromptExtendedDTO> getPrompts() {
        return prompts;
    }

    public void setPrompts(Collection<DocumentPromptExtendedDTO> documentPromptsById) {
        this.prompts = documentPromptsById;
    }

    public DocumentReceiptDTO getReceipt() {
        return receipt;
    }

    public void setReceipt(DocumentReceiptDTO documentReceiptById) {
        this.receipt = documentReceiptById;
    }

    public Map<String, List<DocumentTaxExtendedDTO>> getTaxes() {
        return taxes;
    }

    public void setTaxes(Map<String, List<DocumentTaxExtendedDTO>> documentTaxesById) {
        this.taxes = documentTaxesById;
    }

    public boolean isStockMovement() {
        return stockMovement;
    }

    public void setStockMovement(boolean stockMovement) {
        this.stockMovement = stockMovement;
    }

    @Override
    public String toString() {
        return "DocumentExtendedDTO{" +
                "code='" + code + '\'' +
                ", number='" + number + '\'' +
                ", documentDate=" + documentDate +
                ", numberOfLines=" + numberOfLines +
                ", netAmount=" + netAmount +
                ", taxAmount=" + taxAmount +
                ", grossAmount=" + grossAmount +
                ", discountAmount=" + discountAmount +
                ", totalAmount=" + totalAmount +
                ", receiptData='" + receiptData + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", serie=" + serie +
                ", legalAuthority=" + legalAuthority +
                ", lines=" + lines +
                ", party=" + party +
                ", payments=" + payments +
                ", prompts=" + prompts +
                ", receipt=" + receipt +
                ", taxes=" + taxes +
                '}';
    }
}
