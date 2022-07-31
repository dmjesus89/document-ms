package com.petrotec.documentms.dtos.siteConfiguration;

import java.util.Map;

public class SiteConfigurationExtendedPumpDTO {
    private String code;
    private Integer pumpNumber;
    private String description;
    private Map<Integer, SiteConfigurationExtendedNozzleDTO> nozzles;
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

    public Integer getPumpNumber() {
        return pumpNumber;
    }

    public void setPumpNumber(Integer pumpNumber) {
        this.pumpNumber = pumpNumber;
    }

    public Map<Integer, SiteConfigurationExtendedNozzleDTO> getNozzles() {
        return nozzles;
    }

    public void setNozzles(Map<Integer, SiteConfigurationExtendedNozzleDTO> nozzles) {
        this.nozzles = nozzles;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
