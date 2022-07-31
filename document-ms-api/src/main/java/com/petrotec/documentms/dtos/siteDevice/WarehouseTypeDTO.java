package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.util.Map;

@Schema(description = "Represents a warehouse type", accessMode = Schema.AccessMode.WRITE_ONLY)
@Introspected
public class WarehouseTypeDTO {
    public enum WAREHOUSE_TYPE{
        WET, DRY
    }
    @Size(max = 20)
    @Schema(maxLength = 20, description = "Unique warehouse type.", example = "1", nullable = true)
    private String code;

    private String description;

    @Schema(description = "warehouse Type Description. Each key represents a locale. Overrides the field description if fulfilled", nullable = true, example = "{}")
    private Map<String, String> detailedDescription;


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
