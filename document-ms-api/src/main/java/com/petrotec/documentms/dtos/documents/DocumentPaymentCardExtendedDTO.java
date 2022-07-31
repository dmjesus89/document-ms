package com.petrotec.documentms.dtos.documents;

public class DocumentPaymentCardExtendedDTO {
    private String pan;
    private String track2;
    private String customerCode;
    private String cardCode;
    private String label;
    private CardSystemDTO cardSystem;

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public CardSystemDTO getCardSystem() {
        return cardSystem;
    }

    public void setCardSystem(CardSystemDTO cardSystemByCardSystemId) {
        this.cardSystem = cardSystemByCardSystemId;
    }
}
