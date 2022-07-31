package com.petrotec.documentms.dtos.grade;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
@Schema(description = "Grade Composition. Each line represents a Product that mades the grade composition")
public class GradeProductDTO {

    @Schema(description = "References grade code", accessMode = Schema.AccessMode.READ_ONLY)
    private String gradeCode;

    @Schema(description = "References product code")
    private String productCode;

    @Schema(description = "References product composition percentage")
    private String productPercentage;

    public GradeProductDTO() {
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductPercentage() {
        return productPercentage;
    }

    public void setProductPercentage(String productPercentage) {
        this.productPercentage = productPercentage;
    }
}
