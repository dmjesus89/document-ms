package com.petrotec.documentms.dtos.siteConfiguration;

public class SiteConfigurationExtendedProductDTO {
    private String code;
    private String fccProductCode;
    private String description;
    private String productGroupCode;
    private String productGroupDescription;
    private Boolean enabled;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFccProductCode() {
        return fccProductCode;
    }

    public void setFccProductCode(String productSiteCode) {
        this.fccProductCode = productSiteCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductGroupCode() {
        return productGroupCode;
    }

    public void setProductGroupCode(String productGroupCode) {
        this.productGroupCode = productGroupCode;
    }

    public String getProductGroupDescription() {
        return productGroupDescription;
    }

    public void setProductGroupDescription(String productGroupDescription) {
        this.productGroupDescription = productGroupDescription;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
