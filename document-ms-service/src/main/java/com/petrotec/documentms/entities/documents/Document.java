package com.petrotec.documentms.entities.documents;

import com.petrotec.documentms.entities.Site;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity(name = "document")
public class Document implements Serializable {
    private Long id;
    private String code;
    private String entityCode;
    private short entityRankOrder;
    private String number;
    private LocalDateTime documentDate;
    private Integer numberOfLines;
    private BigDecimal netAmount;
    private BigDecimal taxAmount;
    private BigDecimal grossAmount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private String receiptData;
    private Integer posNumber;
    private Boolean stockMovement;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Site site;
    private DocumentSerie serie;
    private DocumentSaleType documentSaleType;
    private DocumentLegalAuthority legalAuthority;
    private Collection<DocumentLine> lines;
    private DocumentParty party;
    private Collection<DocumentPayment> payments;
    private Collection<DocumentPrompt> prompts;
    private DocumentReceipt receipt;
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
    @Column(name = "entity_code")
    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    @Basic
    @Column(name = "entity_rank_order")
    public short getEntityRankOrder() {
        return entityRankOrder;
    }

    public void setEntityRankOrder(short entityRankOrder) {
        this.entityRankOrder = entityRankOrder;
    }

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "document_date")
    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateTime documentDate) {
        this.documentDate = documentDate;
    }

    @Basic
    @Column(name = "number_of_lines")
    public Integer getNumberOfLines() {
        return numberOfLines;
    }

    public void setNumberOfLines(Integer numberOfLines) {
        this.numberOfLines = numberOfLines;
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
    @Column(name = "receipt_data")
    public String getReceiptData() {
        return receiptData;
    }

    public void setReceiptData(String receiptData) {
        this.receiptData = receiptData;
    }

    @Basic
    @Column(name = "pos_number")
    public Integer getPosNumber() {
        return posNumber;
    }

    public void setPosNumber(Integer posNumber) {
        this.posNumber = posNumber;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_serie_id", referencedColumnName = "id", nullable = false)
    public DocumentSerie getSerie() {
        return serie;
    }

    public void setSerie(DocumentSerie documentSerieByDocumentSerieId) {
        this.serie = documentSerieByDocumentSerieId;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_sale_type_id", referencedColumnName = "id")
    public DocumentSaleType getDocumentSaleType() {
        return documentSaleType;
    }

    public void setDocumentSaleType(DocumentSaleType documentSaleType) {
        this.documentSaleType = documentSaleType;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public DocumentLegalAuthority getLegalAuthority() {
        return legalAuthority;
    }

    public void setLegalAuthority(DocumentLegalAuthority documentLegalAuthorityById) {
        this.legalAuthority = documentLegalAuthorityById;
    }


    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    public Collection<DocumentLine> getLines() {
        return lines;
    }

    public void setLines(Collection<DocumentLine> documentLinesById) {
        this.lines = documentLinesById;
    }


    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public DocumentParty getParty() {
        return party;
    }

    public void setParty(DocumentParty documentPartyById) {
        this.party = documentPartyById;
    }

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    public Collection<DocumentPayment> getPayments() {
        return payments;
    }

    public void setPayments(Collection<DocumentPayment> documentPaymentsById) {
        this.payments = documentPaymentsById;
    }

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    public Collection<DocumentPrompt> getPrompts() {
        return prompts;
    }

    public void setPrompts(Collection<DocumentPrompt> documentPromptsById) {
        this.prompts = documentPromptsById;
    }


    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    public DocumentReceipt getReceipt() {
        return receipt;
    }

    public void setReceipt(DocumentReceipt documentReceiptById) {
        this.receipt = documentReceiptById;
    }

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    public Collection<DocumentTax> getTaxes() {
        return taxes;
    }

    public void setTaxes(Collection<DocumentTax> documentTaxesById) {
        this.taxes = documentTaxesById;
    }

    @Basic
    @Column(name = "is_stock_movement")
    public Boolean getStockMovement() {
        return stockMovement;
    }

    public void setStockMovement(Boolean stockMovement) {
        this.stockMovement = stockMovement;
    }
}
