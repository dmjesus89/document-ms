package com.petrotec.documentms.dtos.site;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Map;

@Introspected
@Schema(description = "Represents a Site")
public class SiteDTO {

    @Schema(description = "Site unique code")
    private String code;

    @Schema(description = "Site number (for internal use)")
    private String siteNumber;

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "Entity code")
    private String entityCode;

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "Site description for the current locale")
    private String description;

    @Schema(description = "Site description for each locale")
    private Map<String, String> detailedDescription;

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "Region Description")
    private String region;

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "Region Code")
    private String regionCode;

    @Min(1)
    @Max(255)
    @NotNull
    @Schema(description = "Site Profile code", minLength = 1, maxLength = 40, required = true)
    private String siteProfileCode;

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "Site Profile description", minLength = 0, maxLength = 45)
    private String siteProfileDescription;

    @Size(min = 0, max = 45)
    @Schema(description = "Latitude location, for Mapping reference", minLength = 0, maxLength = 45)
    private String latitude;

    @Size(min = 0, max = 45)
    @Schema(description = "Longitude location, for Mapping reference", minLength = 0, maxLength = 45)
    private String longitude;

    @NotNull
    @Schema(description = "site status")
    private boolean enabled;

    @NotNull
    @Schema(description = "site creation date")
    private LocalDateTime createdOn;

    @NotNull
    @Schema(description = "last site updated on date")
    private LocalDateTime updatedOn;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionCode() {
        return this.regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getSiteProfileCode() {
        return siteProfileCode;
    }

    public void setSiteProfileCode(String siteProfileCode) {
        this.siteProfileCode = siteProfileCode;
    }

    public String getSiteProfileDescription() {
        return siteProfileDescription;
    }

    public void setSiteProfileDescription(String siteProfileDescription) {
        this.siteProfileDescription = siteProfileDescription;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isEnabled() {
        return this.enabled;
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

    public Map<String, String> getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(Map<String, String> detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }


    public String getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}