package com.petrotec.documentms.dtos.siteRegion;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

@Schema(description = "Represents a Region that might be associated to Site")
public class RegionDTO {

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "unique code")
    private String code;

    @NotNull
    @Schema(description = "description for the current locale")
    private String description;

    @Schema(description = "description for each locale")
    private Map<String,String> detailedDescription;

    @NotNull
    @Schema(description = "region status")
    private boolean enabled;


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
