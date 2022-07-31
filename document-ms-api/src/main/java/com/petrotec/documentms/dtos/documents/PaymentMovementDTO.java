package com.petrotec.documentms.dtos.documents;

import java.time.LocalDateTime;

public class PaymentMovementDTO {
    private String code;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public enum PaymentMovementEnum {
        IN("IN"),
        OUT("OUT");

        private final String code;

        PaymentMovementEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

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
