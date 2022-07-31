package com.petrotec.documentms.dtos.site;

import com.petrotec.api.dtos.categories.CategoryElementDTO;
import com.petrotec.api.dtos.properties.PropertyValueDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemDTO;
import com.petrotec.documentms.dtos.siteProfile.SiteProfileDTO;
import com.petrotec.documentms.dtos.siteRegion.RegionDTO;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Introspected
@Schema(description = "Site data", accessMode = Schema.AccessMode.READ_ONLY)
public class SiteExtendedDTO {

    @NotNull
    @Schema(description = "unique code", required = true)
    private String code;

    @Schema(description = "Site number (for internal use)")
    private String siteNumber;

    @NotNull
    @Schema(description = "entity code", required = true)
    private String entityCode;

    @NotNull
    @Schema(description = "description for the current Locale")
    private String description;

    @Schema(description = "description map for each locale")
    private Map<String, String> detailedDescription;

    @NotNull
    @Schema(description = "region", required = true)
    private RegionDTO region;

    @Schema(description = "profile", required = true)
    private SiteProfileDTO siteProfile;

    @Schema(description = "site device data",required = true)
    private SiteDeviceDataDTO siteDevices;

    @Size(min = 0, max = 45)
    @Schema(description = "latitude location, for Geo reference", minLength = 0, maxLength = 45, required = false)
    private String latitude;

    @Size(min = 0, max = 45)
    @Schema(description = "longitude location, for Geo reference", minLength = 0, maxLength = 45, required = false)
    private String longitude;

    @NotNull
    @Schema(description = "site status", required = true)
    private boolean enabled;

    @NotNull
    @Schema(description = "site creation date", required = true)
    private LocalDateTime createdOn;

    @NotNull
    @Schema(description = "site creation date", required = true)
    private LocalDateTime updatedOn;

    @Schema(description = "site groups items associated to the current site", required = true)
    private List<SiteGroupItemDTO> groups;

    @Schema(description = "Properties associated to the site")
    private List<PropertyValueDTO> properties;

    @Schema(description = "Categories associated to the site")
    private List<CategoryElementDTO> categories;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(Map<String, String> detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
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

    public List<SiteGroupItemDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<SiteGroupItemDTO> groups) {
        this.groups = groups;
    }

    public String getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
    }

    public SiteProfileDTO getSiteProfile() {
        return siteProfile;
    }

    public void setSiteProfile(SiteProfileDTO siteProfile) {
        this.siteProfile = siteProfile;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public SiteDeviceDataDTO getSiteDevices() {
        return siteDevices;
    }

    public void setSiteDevices(SiteDeviceDataDTO siteDevices) {
        this.siteDevices = siteDevices;
    }

    public List<PropertyValueDTO> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyValueDTO> properties) {
        this.properties = properties;
    }

    public List<CategoryElementDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryElementDTO> categories) {
        this.categories = categories;
    }
}