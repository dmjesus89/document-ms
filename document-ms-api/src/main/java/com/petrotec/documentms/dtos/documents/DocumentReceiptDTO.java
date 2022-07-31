package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Introspected
public class DocumentReceiptDTO {

    //default = 1
    private Integer reprintNumber = 1;

    @NotEmpty
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
