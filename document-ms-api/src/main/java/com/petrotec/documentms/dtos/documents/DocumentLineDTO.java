package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;

@Introspected
@Schema(description = "Document Line")
public class DocumentLineDTO {
    @Schema(description = "Document line unique identifier. If null system will generate an UUID", example = "")
    private String code;

    @Nonnull
    @Schema(description = "Document line number. Unique for the current document", example = "1")
    private short line;

    @NotEmpty
    @NotNull
    @Schema(description = "Unique product code", required = true, example = "DIESEL")
    private String productCode;

    @NotEmpty
    @NotNull
    @Schema(description = "Product Description. Not 0 unless product code is 0", required = true, example = "Product number 1")
    private String productDescription;

    @NotEmpty
    @NotNull
    @Schema(description = "Product Long Description. Not 0 unless product code is 0", required = true, example = "Product")
    private String productDescriptionLong;

    @Nonnull
    @Schema(description = "Product quantity", required = true, minimum = "0")
    private BigDecimal quantity;

    @Nonnull
    @Schema(description = "Product unit price (without applying discounts)", required = true, example = "10.000")
    private BigDecimal unitPriceNet;

    @Nonnull
    @Schema(description = "Product unit price gross  (without applying discounts)", required = true, minimum = "9.000")
    private BigDecimal unitPriceGross;

    @Nonnull
    @Schema(description = "Product net amount (without applying discounts)", required = true, example = "10.000")
    private BigDecimal netAmount;

    @Nonnull
    @Schema(description = "Product tax amount (without applying discounts)", required = true, example = "1.000")
    private BigDecimal taxAmount;

    @Nonnull
    @Schema(description = "Product gross amount (without applying discounts)", required = true, example = "9.000")
    private BigDecimal grossAmount;

    @Nonnull
    @Schema(description = "Product discount amount (without applying discounts)", required = false, example = "0.500", defaultValue = "0")
    private BigDecimal discountAmount;

    @Nonnull
    @Schema(description = "Product total amount (after applying discounts)", required = true, example = "9.500")
    private BigDecimal totalAmount;

    @Nonnull
    @Schema(description = "Product cost value (from supplier). Used to calculate margins", required = false, example = "7.50")
    private BigDecimal costPriceNet;

    @Nonnull
    @Schema(description = "Product cost value (from supplier). Used to calculate margins", required = false, example = "7.00")
    private BigDecimal costPriceGross;

    @Valid
    @Schema(description = "Referenced line. Used when the current line is a result of another document line, used for refund lines)", required = false)
    private DocumentLineRefDTO lineReference;

    @Valid
    @Schema(description = "Fuel line data. Mandatory when the line refers to a fuelling", required = false)
    private DocumentLineFuelDTO fuelLine;

    @Valid
    @Schema(description = "Difference between supplied data and payed data. Occurs when the customer presets or pre pays with one value, but the supplied value is different", required = false)
    private DocumentLineSuppliedDiffDTO suppliedDiff;

    @Valid
    @Schema(description = "Product total amount (after applying discounts)", required = false)
    private Collection<DocumentTaxDTO> taxes;

    @Schema(description = "Sale type", example = "PREPAID_FUEL_TRANSACTION")
    private SaleLineTypeEnum type;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public short getLine() {
        return line;
    }

    public void setLine(short line) {
        this.line = line;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductDescriptionLong() {
        return productDescriptionLong;
    }

    public void setProductDescriptionLong(String productDescriptionLong) {
        this.productDescriptionLong = productDescriptionLong;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPriceNet() {
        return unitPriceNet;
    }

    public void setUnitPriceNet(BigDecimal unitPriceNet) {
        this.unitPriceNet = unitPriceNet;
    }

    public BigDecimal getUnitPriceGross() {
        return unitPriceGross;
    }

    public void setUnitPriceGross(BigDecimal unitPriceGross) {
        this.unitPriceGross = unitPriceGross;
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

    public BigDecimal getCostPriceNet() {
        return costPriceNet;
    }

    public void setCostPriceNet(BigDecimal costPriceNet) {
        this.costPriceNet = costPriceNet;
    }

    public BigDecimal getCostPriceGross() {
        return costPriceGross;
    }

    public void setCostPriceGross(BigDecimal costPriceGross) {
        this.costPriceGross = costPriceGross;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productByProductId) {
        this.productCode = productByProductId;
    }

    public DocumentLineFuelDTO getFuelLine() {
        return fuelLine;
    }

    public void setFuelLine(DocumentLineFuelDTO documentLineFuelById) {
        this.fuelLine = documentLineFuelById;
    }

    public DocumentLineRefDTO getLineReference() {
        return lineReference;
    }

    public void setLineReference(DocumentLineRefDTO documentLineRefById) {
        this.lineReference = documentLineRefById;
    }

    public DocumentLineSuppliedDiffDTO getSuppliedDiff() {
        return suppliedDiff;
    }

    public void setSuppliedDiff(DocumentLineSuppliedDiffDTO documentLineSuppliedDiffById) {
        this.suppliedDiff = documentLineSuppliedDiffById;
    }

    public Collection<DocumentTaxDTO> getTaxes() {
        return taxes;
    }

    public void setTaxes(Collection<DocumentTaxDTO> documentTaxesById) {
        this.taxes = documentTaxesById;
    }

    public SaleLineTypeEnum getType() {
        return type;
    }

    public void setType(SaleLineTypeEnum type) {
        this.type = type;
    }
}
