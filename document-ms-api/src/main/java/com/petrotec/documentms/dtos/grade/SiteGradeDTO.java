package com.petrotec.documentms.dtos.grade;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
@Schema(description = "Represents a site grade. This grade can be associated to a nozzle and a warehouse", allOf = GradeDTO.class)
public class SiteGradeDTO {

    @Schema(description = "Grade code")
    private String gradeCode;

    @Schema(description = "Grade description code")
    private String gradeDescription;

    @Schema(description = "Site code")
    private String siteCode;

    @Schema(description = "Site code")
    private Integer fccGradeCode;

    public SiteGradeDTO() {
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGradeDescription() {
        return gradeDescription;
    }

    public void setGradeDescription(String gradeDescription) {
        this.gradeDescription = gradeDescription;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Integer getFccGradeCode() {
        return fccGradeCode;
    }

    public void setFccGradeCode(Integer fccGradeCode) {
        this.fccGradeCode = fccGradeCode;
    }
}
