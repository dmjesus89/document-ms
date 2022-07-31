package com.petrotec.documentms.entities.documents;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "document_line_fuel")
public class DocumentLineFuel {
    private Long documentLineId;
    private byte pump;
    private byte nozzle;
    private BigDecimal startTotalizer;
    private BigDecimal endTotalizer;
    private BigDecimal startStock;
    private BigDecimal endStock;
    private String transactionId;
    private DocumentLine documentLine;

    @Id
    @Column(name = "document_line_id")
    public Long getDocumentLineId() {
        return documentLineId;
    }

    public void setDocumentLineId(Long documentLineId) {
        this.documentLineId = documentLineId;
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


    @Basic
    @Column(name = "pump")
    public byte getPump() {
        return pump;
    }

    public void setPump(byte pump) {
        this.pump = pump;
    }

    @Basic
    @Column(name = "nozzle")
    public byte getNozzle() {
        return nozzle;
    }

    public void setNozzle(byte nozzle) {
        this.nozzle = nozzle;
    }

    @Basic
    @Column(name = "start_totalizer")
    public BigDecimal getStartTotalizer() {
        return startTotalizer;
    }

    public void setStartTotalizer(BigDecimal startTotalizer) {
        this.startTotalizer = startTotalizer;
    }

    @Basic
    @Column(name = "end_totalizer")
    public BigDecimal getEndTotalizer() {
        return endTotalizer;
    }

    public void setEndTotalizer(BigDecimal endTotalizer) {
        this.endTotalizer = endTotalizer;
    }

    @Basic
    @Column(name = "start_stock")
    public BigDecimal getStartStock() {
        return startStock;
    }

    public void setStartStock(BigDecimal startStock) {
        this.startStock = startStock;
    }

    @Basic
    @Column(name = "end_stock")
    public BigDecimal getEndStock() {
        return endStock;
    }

    public void setEndStock(BigDecimal endStock) {
        this.endStock = endStock;
    }

    @Basic
    @Column(name = "transaction_id")
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}
