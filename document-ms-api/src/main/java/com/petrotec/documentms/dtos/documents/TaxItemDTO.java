package com.petrotec.documentms.dtos.documents;

public class TaxItemDTO {
    private String code;
    private String description;
    private String descriptionI18N;
    private byte isFixedRate;
    private byte isEnabled;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionI18N() {
        return descriptionI18N;
    }

    public void setDescriptionI18N(String descriptionI18N) {
        this.descriptionI18N = descriptionI18N;
    }

    public byte getIsFixedRate() {
        return isFixedRate;
    }

    public void setIsFixedRate(byte isFixedRate) {
        this.isFixedRate = isFixedRate;
    }

    public byte getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(byte isEnabled) {
        this.isEnabled = isEnabled;
    }

}
