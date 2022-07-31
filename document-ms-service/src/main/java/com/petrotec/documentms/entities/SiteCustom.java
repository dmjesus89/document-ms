package com.petrotec.documentms.entities;


import com.petrotec.api.dtos.PropertiesAndCategoriesDTO;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * SiteRegionProfileView
 */
public class SiteCustom implements PropertiesAndCategoriesDTO {

    private String code;
    private String description;

    private String regionDescription;

    private String siteNumber;

    private String regionCode;

    private String siteProfileDescription;

    private int siteProfileId;

    private int nFuelPoints;

    private int nDispensers;

    private int nPos;

    private int nWarehouses;

    private int nFccs;

    private String siteProfileCode;

    private String latitude;

    private String longitude;

    private boolean enabled;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private Map<String, Object> properties;
    private Map<String, Object> categories;


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

    public String getRegionDescription() {
        return regionDescription;
    }

    public void setRegionDescription(String regionDescription) {
        this.regionDescription = regionDescription;
    }

    public String getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getSiteProfileDescription() {
        return siteProfileDescription;
    }

    public void setSiteProfileDescription(String siteProfileDescription) {
        this.siteProfileDescription = siteProfileDescription;
    }

    public int getSiteProfileId() {
        return siteProfileId;
    }

    public void setSiteProfileId(int siteProfileId) {
        this.siteProfileId = siteProfileId;
    }

    public int getnFuelPoints() {
        return nFuelPoints;
    }

    public void setnFuelPoints(int nFuelPoints) {
        this.nFuelPoints = nFuelPoints;
    }

    public int getnDispensers() {
        return nDispensers;
    }

    public void setnDispensers(int nDispensers) {
        this.nDispensers = nDispensers;
    }

    public int getnPos() {
        return nPos;
    }

    public void setnPos(int nPos) {
        this.nPos = nPos;
    }

    public int getnWarehouses() {
        return nWarehouses;
    }

    public void setnWarehouses(int nWarehouses) {
        this.nWarehouses = nWarehouses;
    }

    public int getnFccs() {
        return nFccs;
    }

    public void setnFccs(int nFccs) {
        this.nFccs = nFccs;
    }

    public String getSiteProfileCode() {
        return siteProfileCode;
    }

    public void setSiteProfileCode(String siteProfileCode) {
        this.siteProfileCode = siteProfileCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public Map<String, Object> getCategories() {
        return categories;
    }

    @Override
    public void setCategories(Map<String, Object> categories) {
        this.categories = categories;
    }
}