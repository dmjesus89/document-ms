package com.petrotec.documentms.dtos.documents;

import java.math.BigDecimal;

public class DocumentLineProductDTO {
    private String code;
    private BigDecimal unitPriceNet;
    private BigDecimal unitPriceGross;
    private String description;
    private String descriptionLong;
    private BigDecimal costPriceNet;
    private BigDecimal costPriceGross;
    private byte isEnabled;
    private byte isFuel;
    private String color;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getUnitPriceNet() {
        return unitPriceNet;
    }

    public void setUnitPriceNet(BigDecimal unitPriceNet) {
        this.unitPriceNet = unitPriceNet;
    }

    public BigDecimal getUnitPriceGross() {
        return unitPriceGross;
    }

    public void setUnitPriceGross(BigDecimal unitPriceGross) {
        this.unitPriceGross = unitPriceGross;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public BigDecimal getCostPriceNet() {
        return costPriceNet;
    }

    public void setCostPriceNet(BigDecimal costPriceNet) {
        this.costPriceNet = costPriceNet;
    }

    public BigDecimal getCostPriceGross() {
        return costPriceGross;
    }

    public void setCostPriceGross(BigDecimal costPriceGross) {
        this.costPriceGross = costPriceGross;
    }

    public byte getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(byte isEnabled) {
        this.isEnabled = isEnabled;
    }

    public byte getIsFuel() {
        return isFuel;
    }

    public void setIsFuel(byte isFuel) {
        this.isFuel = isFuel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
