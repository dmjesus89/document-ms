package com.petrotec.documentms.dtos.site;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
@Schema(description = "Site device data", accessMode = Schema.AccessMode.READ_ONLY)
public class SiteDeviceDataDTO {
    @Schema(description = "Number of FuelPoints", accessMode = Schema.AccessMode.READ_ONLY)
    private int nFuelPoints;

    @Schema(description = "Number of Dispensers", accessMode = Schema.AccessMode.READ_ONLY)
    private int nDispensers;

    @Schema(description = "Number of POSs", accessMode = Schema.AccessMode.READ_ONLY)
    private int nPos;

    @Schema(description = "Number of Warehouses", accessMode = Schema.AccessMode.READ_ONLY)
    private int nWarehouses;

    @Schema(description = "Number of Fccs", accessMode = Schema.AccessMode.READ_ONLY)
    private int nFccs;

    public int getnFuelPoints() {
        return nFuelPoints;
    }

    public void setnFuelPoints(int nFuelPoints) {
        this.nFuelPoints = nFuelPoints;
    }

    public int getnDispensers() {
        return nDispensers;
    }

    public void setnDispensers(int nDispensers) {
        this.nDispensers = nDispensers;
    }

    public int getnPos() {
        return nPos;
    }

    public void setnPos(int nPos) {
        this.nPos = nPos;
    }

    public int getnWarehouses() {
        return nWarehouses;
    }

    public void setnWarehouses(int nWarehouses) {
        this.nWarehouses = nWarehouses;
    }

    public int getnFccs() {
        return nFccs;
    }

    public void setnFccs(int nFccs) {
        this.nFccs = nFccs;
    }
}
