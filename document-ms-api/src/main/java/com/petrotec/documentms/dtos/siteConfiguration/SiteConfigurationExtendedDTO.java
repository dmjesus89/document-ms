package com.petrotec.documentms.dtos.siteConfiguration;

import com.petrotec.documentms.dtos.site.SiteDTO;

import java.util.Map;

public class SiteConfigurationExtendedDTO {

    private SiteDTO site;

    private Map<String, SiteConfigurationExtendedProductDTO> products;
    private Map<String, SiteConfigurationExtendedGradeDTO> grades;
    private Map<String, SiteConfigurationExtendedWarehouseDTO> warehouses;
    private Map<String, SiteConfigurationExtendedPumpDTO> pumps;
    private FccCommunicationPetrotecConfigDTO fcc;

    public SiteDTO getSite() {
        return site;
    }

    public void setSite(SiteDTO site) {
        this.site = site;
    }

    public Map<String, SiteConfigurationExtendedProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Map<String, SiteConfigurationExtendedProductDTO> products) {
        this.products = products;
    }

    public Map<String, SiteConfigurationExtendedGradeDTO> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, SiteConfigurationExtendedGradeDTO> grades) {
        this.grades = grades;
    }

    public Map<String, SiteConfigurationExtendedWarehouseDTO> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Map<String, SiteConfigurationExtendedWarehouseDTO> warehouses) {
        this.warehouses = warehouses;
    }

    public Map<String, SiteConfigurationExtendedPumpDTO> getPumps() {
        return pumps;
    }

    public void setPumps(Map<String, SiteConfigurationExtendedPumpDTO> pumps) {
        this.pumps = pumps;
    }

    public FccCommunicationPetrotecConfigDTO getFcc() {
        return fcc;
    }

    public void setFcc(FccCommunicationPetrotecConfigDTO fcc) {
        this.fcc = fcc;
    }

    public static class FccCommunicationPetrotecConfigDTO {
        private FccTypeEnum forecourtClientInterface;

        private Map<String, Object> additionalData;

        public FccTypeEnum getForecourtClientInterface() {
            return forecourtClientInterface;
        }

        public void setForecourtClientInterface(FccTypeEnum forecourtClientInterface) {
            this.forecourtClientInterface = forecourtClientInterface;
        }

        public Map<String, Object> getAdditionalData() {
            return additionalData;
        }

        public void setAdditionalData(Map<String, Object> additionalData) {
            this.additionalData = additionalData;
        }

        public enum FccTypeEnum {PETROTEC, IFSF, FCC_DOMS}
    }
}
