package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Introspected
@Schema(description = "Allows a document to be added to the system")
public class DocumentDTO {
    private DocumentSaleTypeEnum docType;
    @Nonnull
    @Schema(description = "Document Original date", required = true)
    private LocalDateTime documentDate;
    @Nonnull
    @Schema(description = "Document total net amount (without applying discounts)", required = true, example = "10.00")
    private BigDecimal netAmount;
    @Nonnull
    @Schema(description = "Document total tax amount", required = true, example = "1.00")
    private BigDecimal taxAmount;
    @Nonnull
    @Schema(description = "Document total gross amount", required = true, example = "9.00")
    private BigDecimal grossAmount;
    @Nonnull
    @Schema(description = "Document total discounts amount.", required = true, example = "0.50")
    private BigDecimal discountAmount;
    @Nonnull
    @Schema(description = "Document total amount. (after discounts)", required = true, example = "9.50")
    private BigDecimal totalAmount;
    @Nonnull
    @Schema(description = "Pos Device Code", required = true, example = "1")
    private String posCode;

    @Schema(description = "Device Pos Number")
    private Integer posNumber;

    @Nonnull
    @Schema(description = "Site unique Code", required = true, example = "P025")
    private String siteCode;
    @Nonnull
    @Valid
    @Schema(description = "Document Lines", required = true, example = "P025")
    private Map<Integer, DocumentLineDTO> lines;
    @Valid
    @Schema(description = "Document Series. For fiscal purposes. If null, system will generate series", required = false)
    private DocumentSeriesDTO series;
    @Valid
    @Schema(description = "Document Fiscal Authority data. For fiscal purposes", required = false)
    private DocumentLegalAuthorityDTO legalAuthority;
    @Valid
    @Schema(description = "Customer identification data", required = false)
    private DocumentPartyDTO party;
    //    @Valid TODO https://github.com/micronaut-projects/micronaut-core/issues/4590 voltar a colocar quando estiver resolvido
    @Schema(description = "Method of payment used", required = false)
    private Map<String, List<DocumentPaymentDTO>> payments;
    @Valid
    @Schema(description = "Customer inserted prompts", required = false)
    private Collection<DocumentPromptDTO> prompts;
    @Valid
    @Schema(description = "Printed receipt. Usually filled after completing the sale", required = false)
    private DocumentReceiptDTO receipt;
    //    @Valid TODO https://github.com/micronaut-projects/micronaut-core/issues/4590 voltar a colocar quando estiver resolvido
    private Map<String, List<DocumentTaxDTO>> taxes;
    @Schema(description = "Unique document identification. If null, system will create an UUID")
    private String code;
    @Schema(description = "Document number")
    private String number;
    @Schema(description = "Receipt Data")
    private String receiptData;
    private boolean stockMovement;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DocumentSaleTypeEnum getDocType() {
        return docType;
    }

    public void setDocType(DocumentSaleTypeEnum docType) {
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

    public DocumentSeriesDTO getSeries() {
        return series;
    }

    public void setSeries(DocumentSeriesDTO series) {
        this.series = series;
    }

    public DocumentLegalAuthorityDTO getLegalAuthority() {
        return legalAuthority;
    }

    public void setLegalAuthority(DocumentLegalAuthorityDTO documentLegalAuthorityById) {
        this.legalAuthority = documentLegalAuthorityById;
    }

    public Map<Integer, DocumentLineDTO> getLines() {
        return lines;
    }

    public void setLines(Map<Integer, DocumentLineDTO> documentLinesById) {
        this.lines = documentLinesById;
    }

    public DocumentPartyDTO getParty() {
        return party;
    }

    public void setParty(DocumentPartyDTO documentPartyById) {
        this.party = documentPartyById;
    }

    public Map<String, List<DocumentPaymentDTO>> getPayments() {
        return payments;
    }

    public void setPayments(Map<String, List<DocumentPaymentDTO>> documentPaymentsById) {
        this.payments = documentPaymentsById;
    }

    public Collection<DocumentPromptDTO> getPrompts() {
        return prompts;
    }

    public void setPrompts(Collection<DocumentPromptDTO> documentPromptsById) {
        this.prompts = documentPromptsById;
    }

    public DocumentReceiptDTO getReceipt() {
        return receipt;
    }

    public void setReceipt(DocumentReceiptDTO documentReceiptById) {
        this.receipt = documentReceiptById;
    }

    public Map<String, List<DocumentTaxDTO>> getTaxes() {
        return taxes;
    }

    public void setTaxes(Map<String, List<DocumentTaxDTO>> documentTaxesById) {
        this.taxes = documentTaxesById;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(@Nonnull String posCode) {
        this.posCode = posCode;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(@Nonnull String siteCode) {
        this.siteCode = siteCode;
    }

    public Integer getPosNumber() {
        return posNumber;
    }

    public void setPosNumber(Integer posNumber) {
        this.posNumber = posNumber;
    }

    public boolean isStockMovement() {
        return stockMovement;
    }

    public void setStockMovement(boolean stockMovement) {
        this.stockMovement = stockMovement;
    }

    public enum DocumentSaleTypeEnum {SALE, REFUND}
}
