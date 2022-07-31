package com.petrotec.documentms.entities.documents;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity(name = "document_payment")
public class DocumentPayment {

    private Long id;
    private BigDecimal amount;
    private String receiptData;
    private Document document;
    private PaymentMode paymentMode;
    private PaymentMovement movement;
    private Collection<DocumentPaymentCard> cards;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "receipt_data")
    public String getReceiptData() {
        return receiptData;
    }

    public void setReceiptData(String receiptData) {
        this.receiptData = receiptData;
    }

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document documentByDocumentId) {
        this.document = documentByDocumentId;
    }

    @ManyToOne
    @JoinColumn(name = "payment_mode_id", referencedColumnName = "id", nullable = false)
    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentModeByPaymentModeId) {
        this.paymentMode = paymentModeByPaymentModeId;
    }

    @ManyToOne
    @JoinColumn(name = "payment_movement_id", referencedColumnName = "id", nullable = false)
    public PaymentMovement getMovement() {
        return movement;
    }

    public void setMovement(PaymentMovement paymentMovementByPaymentMovementId) {
        this.movement = paymentMovementByPaymentMovementId;
    }

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    public Collection<DocumentPaymentCard> getCards() {
        return cards;
    }

    public void setCards(Collection<DocumentPaymentCard> documentPaymentCardsById) {
        this.cards = documentPaymentCardsById;
    }
}
