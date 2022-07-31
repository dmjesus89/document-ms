package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.util.Map;

@Schema(description = "Represents a POS Type", accessMode = Schema.AccessMode.READ_ONLY)
@Introspected
public class PosTypeDTO {
    @Size(max = 20)
    @Schema(maxLength = 20, description = "Unique Pos Type code. Leave blank or null to be self generated", example = "OPT", nullable = true)
    protected String code;

    @Size(max = 45)
    @Schema(maxLength = 45, description = "Pos Type description. It will be used to fill the description Detail with the current locale description", example = "pos_type_description")
    protected String description;

    @Schema(description = "Pos Type detailed descriptions. Each key represents a locale. Overrides the field description if fulfilled", nullable = true, example = "{}")
    protected Map<String, String> detailedDescription;

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
}
