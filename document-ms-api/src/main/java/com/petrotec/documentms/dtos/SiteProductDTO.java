package com.petrotec.documentms.dtos;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class SiteProductDTO {

    private String siteCode;
    private String productCode;
    private String fccProductCode;

    public SiteProductDTO() {
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getFccProductCode() {
        return fccProductCode;
    }

    public void setFccProductCode(String fccProductCode) {
        this.fccProductCode = fccProductCode;
    }
}
