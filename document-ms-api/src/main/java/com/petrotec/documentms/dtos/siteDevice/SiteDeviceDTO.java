package com.petrotec.documentms.dtos.siteDevice;

import com.petrotec.documentms.dtos.site.SiteSimpleDTO;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Represents a generic site device")
@Introspected
public class SiteDeviceDTO {

    @Size(max = 36)
    @Schema(maxLength = 36, description = "Unique device code. Leave blank or null to be self generated",example = "1", nullable = true, accessMode = Schema.AccessMode.READ_ONLY)
    protected String code;

    @Size(max = 36)
    @NotBlank
    @Schema(maxLength = 36, description = "Identifies the site", required = true, example = "P025")
    protected String siteCode;

    @Size(max = 20)
    @Schema(maxLength = 20, description = "Device site", accessMode = Schema.AccessMode.READ_ONLY)
    protected SiteSimpleDTO site;

    @NotBlank
    @Size(max = 45)
    @Schema(description = "Site description for current locale. *Description or Detailed Description must be fulfilled", nullable = true, example = "{}", maxLength = 45)
    protected String description;

    @Schema(description = "Allows the Site device to be extended with specific configurations.", nullable = true, example = "{}")
    protected Map<String, Object> additionalData;

    @Schema(description = "Represents if Device is enabled.", nullable = true, example = "true")
    protected boolean enabled = true;

    @Schema(description = "Site device type.", example = "{}", accessMode = Schema.AccessMode.READ_ONLY)
    protected SiteDeviceTypeDTO siteDeviceType;

    @Schema(description = "Device creation datetime.", example = "2020-01-01T00:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    protected LocalDateTime createdOn;

    @Schema(description = "Last Device update datetime.", example = "2020-01-01T00:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    protected LocalDateTime updatedOn;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public SiteSimpleDTO getSite() {
        return site;
    }

    public void setSite(SiteSimpleDTO site) {
        this.site = site;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Map<String, Object> additionalData) {
        this.additionalData = additionalData;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public SiteDeviceTypeDTO getSiteDeviceType() {
        return siteDeviceType;
    }

    public void setSiteDeviceType(SiteDeviceTypeDTO siteDeviceType) {
        this.siteDeviceType = siteDeviceType;
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
}
