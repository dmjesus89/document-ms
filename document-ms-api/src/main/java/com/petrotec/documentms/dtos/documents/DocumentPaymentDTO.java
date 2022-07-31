package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Collection;

@Introspected
public class DocumentPaymentDTO {
    @Nonnull
    private BigDecimal amount;

    private String receiptData;

    @Nonnull
    private String paymentModeCode;

    @Nonnull
    private String movementCode;

    private Collection<DocumentPaymentCardDTO> cards;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReceiptData() {
        return receiptData;
    }

    public void setReceiptData(String receiptData) {
        this.receiptData = receiptData;
    }


    public String getPaymentModeCode() {
        return paymentModeCode;
    }

    public void setPaymentModeCode(String paymentModeByPaymentModeId) {
        this.paymentModeCode = paymentModeByPaymentModeId;
    }

    public String getMovementCode() {
        return movementCode;
    }

    public void setMovementCode(String paymentMovementByPaymentMovementId) {
        this.movementCode = paymentMovementByPaymentMovementId;
    }

    public Collection<DocumentPaymentCardDTO> getCards() {
        return cards;
    }

    public void setCards(Collection<DocumentPaymentCardDTO> documentPaymentCardsById) {
        this.cards = documentPaymentCardsById;
    }
}
