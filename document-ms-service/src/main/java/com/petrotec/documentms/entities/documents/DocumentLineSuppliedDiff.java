package com.petrotec.documentms.entities.documents;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "document_line_supplied_diff")
public class DocumentLineSuppliedDiff {
    private Long documentLineId;
    private BigDecimal quantity;
    private BigDecimal unitPriceNet;
    private BigDecimal unitPriceGross;
    private BigDecimal totalNetAmount;
    private BigDecimal totalGrossAmount;
    private DocumentLine documentLine;

    @Id
    @Column(name = "document_line_id")
    public Long getDocumentLineId() {
        return documentLineId;
    }

    public void setDocumentLineId(Long documentLineId) {
        this.documentLineId = documentLineId;
    }

    @Basic
    @Column(name = "quantity")
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "unit_price_net")
    public BigDecimal getUnitPriceNet() {
        return unitPriceNet;
    }

    public void setUnitPriceNet(BigDecimal unitPriceNet) {
        this.unitPriceNet = unitPriceNet;
    }

    @Basic
    @Column(name = "unit_price_gross")
    public BigDecimal getUnitPriceGross() {
        return unitPriceGross;
    }

    public void setUnitPriceGross(BigDecimal unitPriceGross) {
        this.unitPriceGross = unitPriceGross;
    }

    @Basic
    @Column(name = "total_net_amount")
    public BigDecimal getTotalNetAmount() {
        return totalNetAmount;
    }

    public void setTotalNetAmount(BigDecimal totalNetAmount) {
        this.totalNetAmount = totalNetAmount;
    }

    @Basic
    @Column(name = "total_gross_amount")
    public BigDecimal getTotalGrossAmount() {
        return totalGrossAmount;
    }

    public void setTotalGrossAmount(BigDecimal totalGrossAmount) {
        this.totalGrossAmount = totalGrossAmount;
    }

    @OneToOne
    @JoinColumn(name = "document_line_id")
    @MapsId
    public DocumentLine getDocumentLine() {
        return documentLine;
    }

    public void setDocumentLine(DocumentLine documentLineByDocumentLineId) {
        this.documentLine = documentLineByDocumentLineId;
    }
}
