package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.util.Map;

@Schema(description = "Represents a device type", accessMode = Schema.AccessMode.WRITE_ONLY)
@Introspected
public class SiteDeviceTypeDTO {
    @Size(max = 20)
    @Schema(maxLength = 20, description = "Unique device type.",example = "1", nullable = true)
    private String code;

    @Schema(description = "Device Type Description.", nullable = true, example = "{}")
    protected String description;

    @Schema(description = "Device Type detailed descriptions. Each key represents a locale. Overrides the field description if fulfilled", nullable = true, example = "{}")
    protected Map<String, String> detailedDescription; // Mapa das descrições usadas por locale

    @Schema(description = "Represents if Device Type is enabled.", nullable = true, example = "true")
    private boolean enabled;

    public SiteDeviceTypeDTO() {
    }

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

    public Map<String, String> getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(Map<String, String> detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
