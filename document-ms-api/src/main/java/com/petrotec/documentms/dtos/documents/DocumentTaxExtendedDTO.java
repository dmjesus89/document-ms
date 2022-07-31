package com.petrotec.documentms.dtos.documents;

import java.math.BigDecimal;

public class DocumentTaxExtendedDTO {
    private byte isFixedRate;
    private BigDecimal rate;
    private BigDecimal amount;
    private TaxItemDTO taxItem;


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

    public TaxItemDTO getTaxItem() {
        return taxItem;
    }

    public void setTaxItem(TaxItemDTO taxItemByTaxItemId) {
        this.taxItem = taxItemByTaxItemId;
    }
}
