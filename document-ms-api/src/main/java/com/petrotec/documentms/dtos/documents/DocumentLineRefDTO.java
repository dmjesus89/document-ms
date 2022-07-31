package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;

import javax.annotation.Nonnull;

@Introspected
public class DocumentLineRefDTO {
    private String refDocumentCode;
    @Nonnull
    private String refDocumentLineCode;
    private String refDocumentLineNumber;

    public String getRefDocumentCode() {
        return refDocumentCode;
    }

    public void setRefDocumentCode(String refDocumentCode) {
        this.refDocumentCode = refDocumentCode;
    }

    public String getRefDocumentLineCode() {
        return refDocumentLineCode;
    }

    public void setRefDocumentLineCode(String refDocumentLineCode) {
        this.refDocumentLineCode = refDocumentLineCode;
    }

    public String getRefDocumentLineNumber() {
        return refDocumentLineNumber;
    }

    public void setRefDocumentLineNumber(String refDocumentLineNumber) {
        this.refDocumentLineNumber = refDocumentLineNumber;
    }
}
