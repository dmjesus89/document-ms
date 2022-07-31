package com.petrotec.documentms.dtos.siteConfiguration;

public class SiteConfigurationExtendedWarehouseDTO {
    private String code;
    private String description;
    private String fccWarehouseCode;
    private String productCode;
    private String typeCode;
    private Boolean enabled;

    public SiteConfigurationExtendedWarehouseDTO() {
    }

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

    public String getFccWarehouseCode() {
        return fccWarehouseCode;
    }

    public void setFccWarehouseCode(String siteWarehouseCode) {
        this.fccWarehouseCode = siteWarehouseCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
