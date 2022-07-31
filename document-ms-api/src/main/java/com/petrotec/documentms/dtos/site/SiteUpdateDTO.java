package com.petrotec.documentms.dtos.site;

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
@Schema(description = "Site dto used to update Site")
public class SiteUpdateDTO implements IDtoWithProperties, IDtoWithCategories {

    //@NotNull
    //@Size(min = 1, max = 45)
    @Schema(description = "description for the current locale")
    private String description;

    @Schema(description = "description for the each locale")
    private Map<String,String> detailedDescription;

    @NotNull
    @NotEmpty
    @Schema(description = "Site number (for internal use)")
    @Size(min = 1, max = 45)
    private String siteNumber;

    @Min(1)
    @Max(255)
    @NotNull
    @Schema(description = "Site Profile code", minLength = 1, maxLength = 40, required = true)
    private String siteProfileCode;

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "region code", minLength = 1, maxLength = 40, required = true)
    private String regionCode;

    @Size(min = 0, max = 45)
    @Schema(description = "latitude location, for Geo reference",  minLength = 0, maxLength = 45, required = false)
    private String latitude;

    @Size(min = 0, max = 45)
    @Schema(description = "longitude location, for Geo reference",  minLength = 0, maxLength = 45, required = false)
    private String longitude;

    @NotNull
    @Schema(description = "site status", required = true)
    private boolean enabled;

    @Schema(description = "Site properties")
    private List<PropertyValueDTO> properties;

    @Schema(description = "Site categories")
    private List<CategoryElementDTO> categories;


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

    public String getSiteProfileCode() {
        return siteProfileCode;
    }

    public void setSiteProfileCode(String siteProfileCode) {
        this.siteProfileCode = siteProfileCode;
    }

    public String getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
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