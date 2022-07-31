package com.petrotec.documentms.dtos.fccConfiguration;

public class FccPmpCfgDTO {


    private String pmpType;
    private String pmpTypeProfile;
    private String amountDp;
    private String volumeDp;
    private String unitPriceDp;
    private String presetVolumeDp;
    private String presetAmountDp;
    private String minPresetAmount;
    private String minPresetVolume;

    public FccPmpCfgDTO() {
    }

    public String getPmpType() {
        return pmpType;
    }

    public void setPmpType(String pmpType) {
        this.pmpType = pmpType;
    }

    public String getPmpTypeProfile() {
        return pmpTypeProfile;
    }

    public void setPmpTypeProfile(String pmpTypeProfile) {
        this.pmpTypeProfile = pmpTypeProfile;
    }

    public String getAmountDp() {
        return amountDp;
    }

    public void setAmountDp(String amountDp) {
        this.amountDp = amountDp;
    }

    public String getVolumeDp() {
        return volumeDp;
    }

    public void setVolumeDp(String volumeDp) {
        this.volumeDp = volumeDp;
    }

    public String getUnitPriceDp() {
        return unitPriceDp;
    }

    public void setUnitPriceDp(String unitPriceDp) {
        this.unitPriceDp = unitPriceDp;
    }

    public String getPresetVolumeDp() {
        return presetVolumeDp;
    }

    public void setPresetVolumeDp(String presetVolumeDp) {
        this.presetVolumeDp = presetVolumeDp;
    }

    public String getPresetAmountDp() {
        return presetAmountDp;
    }

    public void setPresetAmountDp(String presetAmountDp) {
        this.presetAmountDp = presetAmountDp;
    }

    public String getMinPresetAmount() {
        return minPresetAmount;
    }

    public void setMinPresetAmount(String minPresetAmount) {
        this.minPresetAmount = minPresetAmount;
    }

    public String getMinPresetVolume() {
        return minPresetVolume;
    }

    public void setMinPresetVolume(String minPresetVolume) {
        this.minPresetVolume = minPresetVolume;
    }
}
