package com.petrotec.documentms.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "site_device_dispenser")
public class SiteDeviceDispenser extends SiteDevice {

    @Column(name = "dispenser_number")
    private short dispenserNumber;

    @OneToMany(mappedBy = "dispenser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SiteDeviceFuelPoint> fuelPoints = new ArrayList<>();

    public SiteDeviceDispenser() {
    }

    public short getDispenserNumber() {
        return dispenserNumber;
    }

    public void setDispenserNumber(short dispenserNumber) {
        this.dispenserNumber = dispenserNumber;
    }

    public List<SiteDeviceFuelPoint> getFuelPoints() {
        return fuelPoints;
    }

    public void setFuelPoints(List<SiteDeviceFuelPoint> fuelPoints) {
        this.fuelPoints = fuelPoints;
    }
}
