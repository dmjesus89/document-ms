package com.petrotec.documentms.dtos.documents;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

public class DocumentLineExtendedDTO {
    private String code;
    private short line;
    private String productDescription;
    private String productDescriptionLong;
    private BigDecimal quantity;
    private BigDecimal unitPriceNet;
    private BigDecimal unitPriceGross;
    private BigDecimal netAmount;
    private BigDecimal taxAmount;
    private BigDecimal grossAmount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private BigDecimal costPriceNet;
    private BigDecimal costPriceGross;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private DocumentLineProductDTO product;
    private DocumentLineFuelDTO fuelLine;
    private DocumentLineRefDTO lineReference;
    private Collection<DocumentLineRefDTO> referencedBy;
    private DocumentLineSuppliedDiffDTO suppliedDiff;
    private Collection<DocumentTaxExtendedDTO> taxes;
    private LocalDateTime movementOn;

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

    public DocumentLineProductDTO getProduct() {
        return product;
    }

    public void setProduct(DocumentLineProductDTO productByProductId) {
        this.product = productByProductId;
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

    public Collection<DocumentLineRefDTO> getReferencedBy() {
        return referencedBy;
    }

    public void setReferencedBy(Collection<DocumentLineRefDTO> documentLineRefsById) {
        this.referencedBy = documentLineRefsById;
    }

    public DocumentLineSuppliedDiffDTO getSuppliedDiff() {
        return suppliedDiff;
    }

    public void setSuppliedDiff(DocumentLineSuppliedDiffDTO documentLineSuppliedDiffById) {
        this.suppliedDiff = documentLineSuppliedDiffById;
    }

    public Collection<DocumentTaxExtendedDTO> getTaxes() {
        return taxes;
    }

    public void setTaxes(Collection<DocumentTaxExtendedDTO> documentTaxesById) {
        this.taxes = documentTaxesById;
    }

    public LocalDateTime getMovementOn() {
        return movementOn;
    }

    public void setMovementOn(LocalDateTime movementOn) {
        this.movementOn = movementOn;
    }
}
