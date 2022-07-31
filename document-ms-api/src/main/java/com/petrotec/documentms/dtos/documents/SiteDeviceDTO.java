package com.petrotec.documentms.dtos.documents;


import com.petrotec.documentms.dtos.site.SiteDTO;

import java.util.Map;

public class SiteDeviceDTO {
    private String code;
    private Map<String, String> description;
    private String configuration;
    private SiteDTO site;

    public SiteDeviceDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public SiteDTO getSite() {
        return site;
    }

    public void setSite(SiteDTO site) {
        this.site = site;
    }
}
