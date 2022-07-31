package com.petrotec.documentms.dtos.site;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Introspected
public class SiteSimpleDTO {

    @Schema(description = "Site code",example = "P025")
    private String code;

    @Schema(description = "Site number (for internal use)")
    private String siteNumber;

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "Site entity code",example = "1")
    private String entityCode;

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "Site description for current locale",example = "Petrotec Site")
    private String description;

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "Site description for each locale",example = "{\"en-en\":\"Petrotec Site\"}")
    private String detailedDescription;

    @NotNull
    @Size(min = 1, max = 45)
    @Schema(description = "Site region Code",example = "PT_MADEIRA")
    private String regionCode;

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

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
    }
}