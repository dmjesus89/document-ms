package com.petrotec.documentms.entities;

import com.petrotec.service.converters.JpaConverterJsonMapObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "site_device_fuel_point")
public class SiteDeviceFuelPoint extends SiteDevice implements CommunicationDevice {

    @ManyToOne
    @JoinColumn(name = "site_device_fcc_id", referencedColumnName = "id")
    private SiteDeviceFCC siteDeviceFCC;

    @Column(name = "pump_number")
    private Integer pumpNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communication_method_id", referencedColumnName = "id")
    private CommunicationMethod communicationMethod;

    @Column(name = "communication_method_data", columnDefinition = "json")
    @Convert(converter = JpaConverterJsonMapObject.class)
    private Map<String, Object> communicationMethodData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_device_dispenser_id", referencedColumnName = "id", nullable = false)
    private SiteDeviceDispenser dispenser;

    @OneToMany(mappedBy = "fuelPoint", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SiteDeviceFuelPointNozzle> nozzles = new ArrayList<>();

    public SiteDeviceFuelPoint() {
    }

    public SiteDeviceFCC getSiteDeviceFCC() {
        return siteDeviceFCC;
    }

    public void setSiteDeviceFCC(SiteDeviceFCC siteDeviceFCC) {
        this.siteDeviceFCC = siteDeviceFCC;
    }

    public Integer getPumpNumber() {
        return pumpNumber;
    }

    public void setPumpNumber(Integer pumpNumber) {
        this.pumpNumber = pumpNumber;
    }

    public List<SiteDeviceFuelPointNozzle> getNozzles() {
        return nozzles;
    }

    public void setNozzles(List<SiteDeviceFuelPointNozzle> nozzles) {
        this.nozzles = nozzles;
    }

    public SiteDeviceDispenser getDispenser() {
        return dispenser;
    }

    public void setDispenser(SiteDeviceDispenser dispenser) {
        this.dispenser = dispenser;
    }

    @Override
    public CommunicationMethod getCommunicationMethod() {
        return communicationMethod;
    }

    @Override
    public void setCommunicationMethod(CommunicationMethod communicationMethod) {
        this.communicationMethod = communicationMethod;
    }

    @Override
    public Map<String, Object> getCommunicationMethodData() {
        return communicationMethodData;
    }

    @Override
    public void setCommunicationMethodData(Map<String, Object> communicationMethodData) {
        this.communicationMethodData = communicationMethodData;
    }
}
