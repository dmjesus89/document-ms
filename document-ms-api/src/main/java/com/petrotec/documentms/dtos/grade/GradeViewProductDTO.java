package com.petrotec.documentms.dtos.grade;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class GradeViewProductDTO {
    @Schema(description = "grade main product code")
    private String code;

    @Schema(description = "grade main product color")
    private String color;

    @Schema(description = "grade main product description for current locale")
    private String description;

    @Schema(description = "grade main product short description for current locale")
    private String shortDescription;

    @Schema(description = "grade main product percentage")
    private BigDecimal percentage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
}
