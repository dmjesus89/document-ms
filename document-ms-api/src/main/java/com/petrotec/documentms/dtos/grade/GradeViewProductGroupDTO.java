package com.petrotec.documentms.dtos.grade;

import io.swagger.v3.oas.annotations.media.Schema;

public class GradeViewProductGroupDTO {
    @Schema(description = "grade main product group code")
    private String code;

    @Schema(description = "grade main product group color")
    private String color;

    @Schema(description = "grade main product group description for current locale")
    private String description;

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
}
