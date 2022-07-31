package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;

import javax.annotation.Nonnull;

@Introspected
public class DocumentPaymentCardDTO {
    @Nonnull
    private String pan;
    @Nonnull
    private String customerCode;
    @Nonnull
    private String cardCode;
    @Nonnull
    private String cardSystemCode;

    private String track2;
    private String label;

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


    public String getCardSystemCode() {
        return cardSystemCode;
    }

    public void setCardSystemCode(String cardSystemByCardSystemId) {
        this.cardSystemCode = cardSystemByCardSystemId;
    }
}
