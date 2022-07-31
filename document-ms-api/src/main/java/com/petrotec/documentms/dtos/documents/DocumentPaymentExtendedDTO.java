package com.petrotec.documentms.dtos.documents;

import java.math.BigDecimal;
import java.util.Collection;

public class DocumentPaymentExtendedDTO {
    private BigDecimal amount;
    private String receiptData;
    private PaymentModeDTO paymentMode;
    private PaymentMovementDTO movement;
    private Collection<DocumentPaymentCardExtendedDTO> cards;

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


    public PaymentModeDTO getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentModeDTO paymentModeByPaymentModeId) {
        this.paymentMode = paymentModeByPaymentModeId;
    }

    public PaymentMovementDTO getMovement() {
        return movement;
    }

    public void setMovement(PaymentMovementDTO paymentMovementByPaymentMovementId) {
        this.movement = paymentMovementByPaymentMovementId;
    }

    public Collection<DocumentPaymentCardExtendedDTO> getCards() {
        return cards;
    }

    public void setCards(Collection<DocumentPaymentCardExtendedDTO> documentPaymentCardsById) {
        this.cards = documentPaymentCardsById;
    }
}
