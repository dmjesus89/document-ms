package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.util.Map;

@Schema(description = "Allows the UI to know which protocols are currently supported. Extra fields or properties will be attached to this object",
        accessMode = Schema.AccessMode.READ_ONLY)
@Introspected
public class DeviceProtocolDTO {
    public enum DEVICE_TYPE {
        TANK_GAUGES, FUEL_PUMPS, PRICE_SIGNS, POS, FCC
    }
    @Size(max = 20)
    @Schema(maxLength = 20, description = "Unique Protocol Type code. ", example = "PETROTEC_HDX", nullable = true)
    protected String code;

    @Size(max = 45)
    @Schema(maxLength = 45, description = "Protocol description for the current locale", example = "Petrotec HDX Pump")
    protected String description;

    @Schema(description = "Protocol description for all known locales", nullable = true, example = "{}")
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
