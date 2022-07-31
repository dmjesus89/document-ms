package com.petrotec.documentms.dtos.documents;


import java.time.LocalDateTime;
import java.util.Collection;

public class CardSystemDTO {
    private String code;
    private String description;
    private byte isEnabled;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Collection<DocumentPaymentCardExtendedDTO> paymentCards;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(byte isEnabled) {
        this.isEnabled = isEnabled;
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
