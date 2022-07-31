package com.petrotec.documentms.dtos.site;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.petrotec.api.dtos.categories.CategoryElementDTO;
import com.petrotec.api.dtos.categories.IDtoWithCategories;
import com.petrotec.api.dtos.properties.IDtoWithProperties;
import com.petrotec.api.dtos.properties.PropertyValueDTO;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;

import java.util.List;
import java.util.Map;

@Introspected
@Schema(description = "Data for Site Creation")
public class SiteCreateDTO implements IDtoWithProperties, IDtoWithCategories {

    @Schema(description = "Site system code")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String code = null;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 45)
    @Schema(description = "Site number. Used as site identifier", required = true)
    private String siteNumber;

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "Site description for the current locale")
    private String description;

    @Schema(description = "Site description for each locale")
    private Map<String,String> detailedDescription;

    @Min(1)
    @Max(255)
    @NotNull
    @Schema(description = "Site Profile code", minLength = 1, maxLength = 40, required = true)
    private String siteProfileCode;

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "Region code", minLength = 1, maxLength = 40, required = true)
    private String regionCode;

    @Size(min = 0, max = 45)
    @Schema(description = "Latitude location, for Mapping reference")
    private String latitude;

    @Size(min = 0, max = 45)
    @Schema(description = "Longitude location, for Mapping reference")
    private String longitude;

    @NotNull
    @Schema(description = "site status")
    private boolean enabled;

    @Schema(description = "Site properties")
    private List<PropertyValueDTO> properties;

    @Schema(description = "Site categories")
    private List<CategoryElementDTO> categories;


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

    public String getRegionCode() {
        return this.regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
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

    public Map<String, String> getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(Map<String, String> detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
    }

    public String getSiteProfileCode() {
        return siteProfileCode;
    }

    public void setSiteProfileCode(String siteProfileCode) {
        this.siteProfileCode = siteProfileCode;
    }


    @Override
    public List<CategoryElementDTO> getCategories() {
        return categories;
    }

    @Override
    public void setCategories(List<CategoryElementDTO> categories) {
        this.categories = categories;
    }

    @Override
    public List<PropertyValueDTO> getProperties() {
        return properties;
    }

    @Override
    public void setProperties(List<PropertyValueDTO> properties) {
        this.properties = properties;
    }
}