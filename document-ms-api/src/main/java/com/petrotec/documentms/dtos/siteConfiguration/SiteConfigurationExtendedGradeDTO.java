package com.petrotec.documentms.dtos.siteConfiguration;

public class SiteConfigurationExtendedGradeDTO {
    private String code;
    private String description;
    private Integer fccGradeCode;
    private String productCode;
    private Integer productPercentage;
    private Boolean enabled;

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

    public Integer getFccGradeCode() {
        return fccGradeCode;
    }

    public void setFccGradeCode(Integer siteGradeCode) {
        this.fccGradeCode = siteGradeCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getProductPercentage() {
        return productPercentage;
    }

    public void setProductPercentage(Integer productPercentage) {
        this.productPercentage = productPercentage;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
