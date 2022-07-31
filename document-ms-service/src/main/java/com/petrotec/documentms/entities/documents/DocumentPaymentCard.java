package com.petrotec.documentms.entities.documents;

import javax.persistence.*;

@Entity(name = "document_payment_card")
public class DocumentPaymentCard {
    private int id;
    private String pan;
    private String track2;
    private String customerCode;
    private String cardCode;
    private String label;
    private DocumentPayment payment;
    private CardSystem cardSystem;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pan")
    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    @Basic
    @Column(name = "track2")
    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    @Basic
    @Column(name = "customer_code")
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    @Basic
    @Column(name = "card_code")
    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    @Basic
    @Column(name = "label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentPaymentCard that = (DocumentPaymentCard) o;

        if (id != that.id) return false;
        if (pan != null ? !pan.equals(that.pan) : that.pan != null) return false;
        if (track2 != null ? !track2.equals(that.track2) : that.track2 != null) return false;
        if (customerCode != null ? !customerCode.equals(that.customerCode) : that.customerCode != null) return false;
        if (cardCode != null ? !cardCode.equals(that.cardCode) : that.cardCode != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pan != null ? pan.hashCode() : 0);
        result = 31 * result + (track2 != null ? track2.hashCode() : 0);
        result = 31 * result + (customerCode != null ? customerCode.hashCode() : 0);
        result = 31 * result + (cardCode != null ? cardCode.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "document_payment_id", referencedColumnName = "id", nullable = false)
    public DocumentPayment getPayment() {
        return payment;
    }

    public void setPayment(DocumentPayment documentPaymentByDocumentPaymentId) {
        this.payment = documentPaymentByDocumentPaymentId;
    }

    @ManyToOne
    @JoinColumn(name = "card_system_id", referencedColumnName = "id", nullable = false)
    public CardSystem getCardSystem() {
        return cardSystem;
    }

    public void setCardSystem(CardSystem cardSystemByCardSystemId) {
        this.cardSystem = cardSystemByCardSystemId;
    }
}
