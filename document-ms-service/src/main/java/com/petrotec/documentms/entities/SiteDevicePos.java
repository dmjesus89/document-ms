package com.petrotec.documentms.entities;

import com.petrotec.service.converters.JpaConverterJsonMapObject;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "site_device_pos")
public class SiteDevicePos extends SiteDevice implements CommunicationDevice {

    @Column(name = "number")
    private Integer number;

    @ManyToMany(mappedBy = "siteDevicePos", cascade = { CascadeType.ALL})
    private Set<SiteDeviceFCC> siteDeviceFCC = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "communication_method_id", referencedColumnName = "id")
    private CommunicationMethod communicationMethod;

    @Column(name = "communication_method_data", columnDefinition = "json")
    @Convert(converter = JpaConverterJsonMapObject.class)
    private Map<String, Object> communicationMethodData;

    @OneToMany(mappedBy = "siteDevicePos", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VirtualPos> virtualPos = new ArrayList<>();

    @OneToMany( fetch = FetchType.LAZY)
    private List<SiteDeviceFuelPoint> fuelPoints = new ArrayList<>();

    public SiteDevicePos() {
    }

    public SiteDevicePos(Integer number, CommunicationMethod communicationMethod, Map<String, Object> communicationMethodData, List<VirtualPos> virtualPos, List<SiteDeviceFuelPoint> fuelPoints) {
        this.number = number;
        this.communicationMethod = communicationMethod;
        this.communicationMethodData = communicationMethodData;
        this.virtualPos = virtualPos;
        this.fuelPoints = fuelPoints;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Set<SiteDeviceFCC> getSiteDeviceFCC() {
        return siteDeviceFCC;
    }

    public void setSiteDeviceFCC(Set<SiteDeviceFCC> siteDeviceFCC) {
        this.siteDeviceFCC = siteDeviceFCC;
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

    public List<VirtualPos> getVirtualPos() {
        return virtualPos;
    }

    public void setVirtualPos(List<VirtualPos> virtualPos) {
        this.virtualPos = virtualPos;
    }

    public List<SiteDeviceFuelPoint> getFuelPoints() {
        return fuelPoints;
    }

    public void setFuelPoints(List<SiteDeviceFuelPoint> fuelPoints) {
        this.fuelPoints = fuelPoints;
    }

}
