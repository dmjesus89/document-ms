package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Represents a site  dispenser. Represents an island that as several Fuel Pumps", allOf = {SiteDeviceDTO.class})
@Introspected
public class SiteDeviceDispenserDTO extends SiteDeviceDTO {

    @Schema(maximum = "255", minimum = "1", description = "Fuel dispenser number", required = true, example = "1")
    @Max(255)
    @Min(1)
    @NotNull
    private short dispenserNumber;

    @Schema(description = "List of Fuel point codes nozzles")
    private List<String> fuelPointCodes = new ArrayList<>();

    @Schema(description = "List of Fuel Points", accessMode = Schema.AccessMode.READ_ONLY)
    private List<SimpleFuelPointDTO> fuelPoints = new ArrayList<>();

    public SiteDeviceDispenserDTO() {
    }


    public short getDispenserNumber() {
        return dispenserNumber;
    }

    public void setDispenserNumber(short dispenserNumber) {
        this.dispenserNumber = dispenserNumber;
    }

    public List<String> getFuelPointCodes() {
        return fuelPointCodes;
    }

    public void setFuelPointCodes(List<String> fuelPointCodes) {
        this.fuelPointCodes = fuelPointCodes;
    }

    public List<SimpleFuelPointDTO> getFuelPoints() {
        return fuelPoints;
    }

    public void setFuelPoints(List<SimpleFuelPointDTO> fuelPoints) {
        this.fuelPoints = fuelPoints;
    }

}
