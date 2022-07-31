package com.petrotec.documentms.dtos.documents;

public class DocumentLegalAuthorityExtendedDTO {
    private String hash;
    private String hashPrint;
    private Byte hashControl;
    private Short certificateNumber;
    private String invoiceNumber;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHashPrint() {
        return hashPrint;
    }

    public void setHashPrint(String hashPrint) {
        this.hashPrint = hashPrint;
    }

    public Byte getHashControl() {
        return hashControl;
    }

    public void setHashControl(Byte hashControl) {
        this.hashControl = hashControl;
    }

    public Short getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(Short certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

}
