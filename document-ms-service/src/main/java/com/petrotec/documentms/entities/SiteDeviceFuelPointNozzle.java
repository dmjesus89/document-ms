package com.petrotec.documentms.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "site_device_fuel_point_nozzle")
public class SiteDeviceFuelPointNozzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(name = "nozzle_number")
    private Integer nozzleNumber;

    @ManyToOne
    @JoinColumn(name = "site_grade_id", referencedColumnName = "id", nullable = false)
    private SiteGrade siteGrade;

    @Column(name = "last_totalizer")
    private BigDecimal lastTotalizer;

    @Column(name = "last_totalizer_on")
    private LocalDateTime lastTotalizerOn;

    @ManyToOne
    @JoinColumn(name = "site_device_id", referencedColumnName = "id", nullable = false)
    private SiteDeviceFuelPoint fuelPoint;

    @ManyToOne
    @JoinColumn(name = "site_device_warehouse_id", referencedColumnName = "id", nullable = false)
    private SiteDeviceWarehouse warehouse;


    public SiteDeviceFuelPointNozzle() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNozzleNumber() {
        return nozzleNumber;
    }

    public void setNozzleNumber(Integer nozzleNumber) {
        this.nozzleNumber = nozzleNumber;
    }

    public SiteGrade getSiteGrade() {
        return siteGrade;
    }

    public void setSiteGrade(SiteGrade siteGrade) {
        this.siteGrade = siteGrade;
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

    public SiteDeviceFuelPoint getFuelPoint() {
        return fuelPoint;
    }

    public void setFuelPoint(SiteDeviceFuelPoint fuelPoint) {
        this.fuelPoint = fuelPoint;
    }

    public SiteDeviceWarehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(SiteDeviceWarehouse warehouse) {
        this.warehouse = warehouse;
    }
}
