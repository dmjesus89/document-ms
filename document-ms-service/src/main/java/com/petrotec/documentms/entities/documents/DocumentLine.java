package com.petrotec.documentms.entities.documents;

import com.petrotec.service.converters.JpaConverterJson;
import com.petrotec.documentms.entities.product.Product;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Entity(name = "document_line")
public class DocumentLine {
    private Long id;
    private String code;
    private Integer line;
    private Map<String,String> productDescription;
    private Map<String, String> productDescriptionLong;
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
    private Document document;
    private Product product;
    private DocumentLineFuel fuelLine;
    private DocumentLineRef lineReference;
    private Collection<DocumentLineRef> referencedBy;
    private DocumentLineSuppliedDiff suppliedDiff;
    private Collection<DocumentTax> taxes;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "line")
    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    @Convert(converter = JpaConverterJson.class)
    @Column(name = "product_description", columnDefinition = "json", nullable = false)
    public Map<String,String> getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(Map<String,String> productDescription) {
        this.productDescription = productDescription;
    }

    @Convert(converter = JpaConverterJson.class)
    @Column(name = "product_description_long", columnDefinition = "json", nullable = false)
    public Map<String,String> getProductDescriptionLong() {
        return productDescriptionLong;
    }

    public void setProductDescriptionLong(Map<String,String> productDescriptionLong) {
        this.productDescriptionLong = productDescriptionLong;
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
    @Column(name = "net_amount")
    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    @Basic
    @Column(name = "tax_amount")
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    @Basic
    @Column(name = "gross_amount")
    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    @Basic
    @Column(name = "discount_amount")
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Basic
    @Column(name = "total_amount")
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Basic
    @Column(name = "cost_price_net")
    public BigDecimal getCostPriceNet() {
        return costPriceNet;
    }

    public void setCostPriceNet(BigDecimal costPriceNet) {
        this.costPriceNet = costPriceNet;
    }

    @Basic
    @Column(name = "cost_price_gross")
    public BigDecimal getCostPriceGross() {
        return costPriceGross;
    }

    public void setCostPriceGross(BigDecimal costPriceGross) {
        this.costPriceGross = costPriceGross;
    }

    @Basic
    @Column(name = "created_on")
    @CreationTimestamp
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "updated_on")
    @UpdateTimestamp
    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }


    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document documentByDocumentId) {
        this.document = documentByDocumentId;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product productByProductId) {
        this.product = productByProductId;
    }

    @OneToOne(mappedBy = "documentLine", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public DocumentLineFuel getFuelLine() {
        return fuelLine;
    }

    public void setFuelLine(DocumentLineFuel documentLineFuelById) {
        this.fuelLine = documentLineFuelById;
    }

    @OneToOne(mappedBy = "documentLine", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public DocumentLineRef getLineReference() {
        return lineReference;
    }

    public void setLineReference(DocumentLineRef documentLineRefById) {
        this.lineReference = documentLineRefById;
    }

    @OneToMany(mappedBy = "referencedDocumentLine", fetch = FetchType.LAZY)
    public Collection<DocumentLineRef> getReferencedBy() {
        return referencedBy;
    }

    public void setReferencedBy(Collection<DocumentLineRef> documentLineRefsById) {
        this.referencedBy = documentLineRefsById;
    }

    @OneToOne(mappedBy = "documentLine", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    public DocumentLineSuppliedDiff getSuppliedDiff() {
        return suppliedDiff;
    }

    public void setSuppliedDiff(DocumentLineSuppliedDiff documentLineSuppliedDiffById) {
        this.suppliedDiff = documentLineSuppliedDiffById;
    }

    @OneToMany(mappedBy = "line")
    public Collection<DocumentTax> getTaxes() {
        return taxes;
    }

    public void setTaxes(Collection<DocumentTax> documentTaxesById) {
        this.taxes = documentTaxesById;
    }

}
