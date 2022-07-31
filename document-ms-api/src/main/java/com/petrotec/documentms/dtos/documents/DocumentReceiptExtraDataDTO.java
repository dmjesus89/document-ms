package com.petrotec.documentms.dtos.documents;

import java.time.LocalDateTime;

public class DocumentReceiptExtraDataDTO {
    private String documentCode;
    private Integer reprintNumber;
    private String receipt;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public Integer getReprintNumber() {
        return reprintNumber;
    }

    public void setReprintNumber(Integer reprintNumber) {
        this.reprintNumber = reprintNumber;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
