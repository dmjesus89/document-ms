package com.petrotec.documentms.entities.documents;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "document_tax")
public class DocumentTax {
    private Long id;
    private short lineNumber;
    private byte isFixedRate;
    private BigDecimal rate;
    private BigDecimal amount;
    private Document document;
    private DocumentLine line;
    private TaxItem taxItem;

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
    @Column(name = "line")
    public short getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(short line) {
        this.lineNumber = line;
    }

    @Basic
    @Column(name = "is_fixed_rate")
    public byte getIsFixedRate() {
        return isFixedRate;
    }

    public void setIsFixedRate(byte isFixedRate) {
        this.isFixedRate = isFixedRate;
    }

    @Basic
    @Column(name = "rate")
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
    @JoinColumn(name = "document_line_id", referencedColumnName = "id", nullable = false)
    public DocumentLine getLine() {
        return line;
    }

    public void setLine(DocumentLine documentLineByDocumentLineId) {
        this.line = documentLineByDocumentLineId;
    }

    @ManyToOne
    @JoinColumn(name = "tax_item_id", referencedColumnName = "id", nullable = false)
    public TaxItem getTaxItem() {
        return taxItem;
    }

    public void setTaxItem(TaxItem taxItemByTaxItemId) {
        this.taxItem = taxItemByTaxItemId;
    }

}
