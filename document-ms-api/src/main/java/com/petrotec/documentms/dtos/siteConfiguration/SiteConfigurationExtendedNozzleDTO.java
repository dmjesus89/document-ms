package com.petrotec.documentms.dtos.siteConfiguration;

public class SiteConfigurationExtendedNozzleDTO {
    private Integer nozzleNumber;
    private String warehouseCode;
    private String gradeCode;
    private String productCode;
    private String fccWarehouseCode;
    private String fccProductCode;
    private String fccGradeCode;

    public Integer getNozzleNumber() {
        return nozzleNumber;
    }

    public void setNozzleNumber(Integer nozzleNumber) {
        this.nozzleNumber = nozzleNumber;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setFccWarehouseCode(String siteWarehouseCode) {
        this.fccWarehouseCode = siteWarehouseCode;
    }

    public String getFccWarehouseCode() {
        return fccWarehouseCode;
    }

    public void setFccProductCode(String siteProductCode) {
        this.fccProductCode = siteProductCode;
    }

    public String getFccProductCode() {
        return fccProductCode;
    }

    public void setFccGradeCode(String siteGradeCode) {
        this.fccGradeCode = siteGradeCode;
    }

    public String getFccGradeCode() {
        return fccGradeCode;
    }
}
