package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Introspected
public class SimpleGradePriceSignDTO {

    @NotBlank
    @Size(max = 45)
    @Schema(maxLength = 45, description = "Code")
    protected String code;

    @Size(max = 255)
    @Schema(description = "Grade description", example = "description")
    private String description;

    @Schema(description = "Grade Color", example = "blue")
    private String color;

    @Schema(description = "Grade Color", example = "2.0")
    private BigDecimal unitPriceNet;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getUnitPriceNet() {
        return unitPriceNet;
    }

    public void setUnitPriceNet(BigDecimal unitPriceNet) {
        this.unitPriceNet = unitPriceNet;
    }

}
