package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;

import javax.annotation.Nonnull;

@Introspected
public class DocumentLegalAuthorityDTO {
    @Nonnull
    private String hash;
    @Nonnull
    private String hashPrint;
    @Nonnull
    private Byte hashControl;
    @Nonnull
    private Short certificateNumber;
    @Nonnull
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
