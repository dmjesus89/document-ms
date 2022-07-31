package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.util.Map;

@Schema(description = "Represents a device subtype", accessMode = Schema.AccessMode.WRITE_ONLY)
@Introspected
public class SiteDeviceSubtypeDTO {
    @Size(max = 20)
    @Schema(maxLength = 20, description = "Unique device subtype.",example = "1", nullable = true)
    private String code;

    @Schema(maxLength = 20, description = "device type .")
    private SiteDeviceTypeDTO deviceType;

    @Schema(description = "Device SubType Description. Each key represents a locale. Overrides the field description if fulfilled", nullable = true, example = "{}")
    private Map<String, String> detailedDescription; // Mapa das descrições usadas por locale

    @Schema(description = "Device SubType Description.", nullable = true, example = "{}")
    protected String description;

    @Schema(description = "Represents if Device SubType is enabled.", nullable = true, example = "true")
    private boolean enabled;

    public SiteDeviceSubtypeDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SiteDeviceTypeDTO getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(SiteDeviceTypeDTO deviceType) {
        this.deviceType = deviceType;
    }

    public Map<String, String> getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(Map<String, String> detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}
