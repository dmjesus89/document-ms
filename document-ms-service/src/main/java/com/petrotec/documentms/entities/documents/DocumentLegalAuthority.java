package com.petrotec.documentms.entities.documents;

import javax.persistence.*;

@Entity(name = "document_legal_authority")
public class DocumentLegalAuthority {
    private Long documentId;
    private String hash;
    private String hashPrint;
    private Byte hashControl;
    private Short certificateNumber;
    private String invoiceNumber;
    private Document document;

    @Id
    @Column(name = "document_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    @Basic
    @Column(name = "hash")
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name = "hash_print")
    public String getHashPrint() {
        return hashPrint;
    }

    public void setHashPrint(String hashPrint) {
        this.hashPrint = hashPrint;
    }

    @Basic
    @Column(name = "hash_control")
    public Byte getHashControl() {
        return hashControl;
    }

    public void setHashControl(Byte hashControl) {
        this.hashControl = hashControl;
    }

    @Basic
    @Column(name = "certificate_number")
    public Short getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(Short certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    @Basic
    @Column(name = "invoice_number")
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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
}
