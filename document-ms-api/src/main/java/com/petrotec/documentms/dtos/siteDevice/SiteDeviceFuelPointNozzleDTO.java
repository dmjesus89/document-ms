package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Represents a fuel dispenser nozzle.")
@Introspected
public class SiteDeviceFuelPointNozzleDTO {

    @Schema(maxLength = 20, description = "Product Grade code", required = true, example = "diesel_grade")
    private String gradeCode;

    @Schema(maxLength = 20, description = "Warehouse code. The warehouse product code must match the grade product code", required = true, example = "diesel_tank")
    private String warehouseCode;

    @Schema(description = "Nozzle number", required = true, example = "1")
    private Integer nozzleNumber;

    @Schema(description = "Last known totalizer. Will not update if null.", example = "5000")
    private BigDecimal lastTotalizer;

    @Schema(description = "Last known totalizer date. Will not update if null.", example = "2020-01-01T00:00:00")
    private LocalDateTime lastTotalizerOn;

    @Schema(maxLength = 20, description = "Nozzle Grade", accessMode = Schema.AccessMode.READ_ONLY)
    private NozzleGradeDTO grade;

    @Schema(maxLength = 20, description = "Nozzle Warehouse", accessMode = Schema.AccessMode.READ_ONLY)
    private NozzleWarehouseDTO warehouse;


    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public Integer getNozzleNumber() {
        return nozzleNumber;
    }

    public void setNozzleNumber(Integer nozzleNumber) {
        this.nozzleNumber = nozzleNumber;
    }

    public BigDecimal getLastTotalizer() {
        return lastTotalizer;
    }

    public void setLastTotalizer(BigDecimal lastTotalizer) {
        this.lastTotalizer = lastTotalizer;
    }

    public LocalDateTime getLastTotalizerOn() {
        return lastTotalizerOn;
    }

    public void setLastTotalizerOn(LocalDateTime lastTotalizerOn) {
        this.lastTotalizerOn = lastTotalizerOn;
    }

    public NozzleGradeDTO getGrade() {
        return grade;
    }

    public void setGrade(NozzleGradeDTO grade) {
        this.grade = grade;
    }

    public NozzleWarehouseDTO getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(NozzleWarehouseDTO warehouse) {
        this.warehouse = warehouse;
    }

    @Schema(description = "Fuel dispenser nozzle grade.", accessMode = Schema.AccessMode.READ_ONLY)
    public static class NozzleGradeDTO{

        @Schema(description = "Grade code", accessMode = Schema.AccessMode.READ_ONLY, example = "diesel_grade")
        String code;

        @Schema(description = "Grade Description for the current locale", accessMode = Schema.AccessMode.READ_ONLY, example = "100% Diesel Grade")
        String description;

        @Schema(description = "Associated Grade color", accessMode = Schema.AccessMode.READ_ONLY, example = "#00ff00")
        String color;

        @Schema(description = "Petrotec FCC code used to identify grade", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
        Integer fccCode;

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

        public Integer getFccCode() {
            return fccCode;
        }

        public void setFccCode(Integer fccCode) {
            this.fccCode = fccCode;
        }
    }

    @Schema(description = "Fuel dispenser nozzle warehouse association.", accessMode = Schema.AccessMode.READ_ONLY)
    public static class NozzleWarehouseDTO{

        @Schema(description = "Warehouse code", accessMode = Schema.AccessMode.READ_ONLY,  example = "diesel_tank")
        String code;

        @Schema(description = "Warehouse description for the current locale", accessMode = Schema.AccessMode.READ_ONLY,  example = "Diesel Tank")
        String description;

        @Schema(description = "Warehouse type code", accessMode = Schema.AccessMode.READ_ONLY, example = "DRY")
        String warehouseTypeCode;

        @Schema(description = "Petrotec FCC Warehouse code", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
        String fccWarehouseCode;

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

        public String getWarehouseTypeCode() {
            return warehouseTypeCode;
        }

        public void setWarehouseTypeCode(String warehouseTypeCode) {
            this.warehouseTypeCode = warehouseTypeCode;
        }

        public String getFccWarehouseCode() {
            return fccWarehouseCode;
        }

        public void setFccWarehouseCode(String fccWarehouseCode) {
            this.fccWarehouseCode = fccWarehouseCode;
        }
    }
}
