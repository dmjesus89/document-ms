package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;

import java.math.BigDecimal;

@Introspected
public class DocumentTaxDTO {
    private byte isFixedRate;
    private BigDecimal rate;
    private BigDecimal amount;
    private String taxItemCode;


    public byte getIsFixedRate() {
        return isFixedRate;
    }

    public void setIsFixedRate(byte isFixedRate) {
        this.isFixedRate = isFixedRate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTaxItemCode() {
        return taxItemCode;
    }

    public void setTaxItemCode(String taxItemByTaxItemId) {
        this.taxItemCode = taxItemByTaxItemId;
    }
}
