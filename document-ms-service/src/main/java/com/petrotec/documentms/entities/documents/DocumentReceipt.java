package com.petrotec.documentms.entities.documents;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "document_receipt")
public class DocumentReceipt {
    private Long documentId;
    private Integer reprintNumber;
    private String receipt;
    private Document document;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @Id
    @Column(name = "document_id")
    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    @Basic
    @Column(name = "reprint_number")
    public Integer getReprintNumber() {
        return reprintNumber;
    }

    public void setReprintNumber(Integer reprintNumber) {
        this.reprintNumber = reprintNumber;
    }

    @Basic
    @Column(name = "receipt")
    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }


    @OneToOne
    @JoinColumn
    @MapsId
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document documentByDocumentId) {
        this.document = documentByDocumentId;
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
}
