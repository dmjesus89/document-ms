package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Introspected
public class SimpleFuelPointDTO {

    @Size(max = 45)
    @Schema(maxLength = 45, description = "Code")
    protected String code;

    @NotNull
    @Schema(description = "Pump Number")
    private short pumpNumber;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Fuel point description", example = " some text here")
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public short getPumpNumber() {
        return pumpNumber;
    }

    public void setPumpNumber(short pumpNumber) {
        this.pumpNumber = pumpNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}