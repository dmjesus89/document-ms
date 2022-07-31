package com.petrotec.documentms.entities;

import com.petrotec.service.converters.JpaConverterJsonMapObject;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "site_device_fcc")
public class SiteDeviceFCC extends SiteDevice {

	@ManyToOne
	@JoinColumn(name = "service_mode_id", referencedColumnName = "id", nullable = false)
	private ServiceMode serviceMode;

	@ManyToOne
	@JoinColumn(name = "fuelling_mode_id", referencedColumnName = "id", nullable = false)
	private FuellingMode fuellingMode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "communication_method_id", referencedColumnName = "id")
	private CommunicationMethod communicationMethod;

	@Column(name = "communication_method_data", columnDefinition = "json")
	@Convert(converter = JpaConverterJsonMapObject.class)
	private Map<String, Object> communicationMethodData;

	@OneToMany(mappedBy = "siteDeviceFCC", cascade = { CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<SiteDeviceFuelPoint> fuelPoints = new ArrayList();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "siteDeviceFCC", cascade = { CascadeType.ALL})
	private List<SiteDeviceTankLevelGauges> siteDeviceTankLevelGauges = new ArrayList();

	@OneToMany(mappedBy = "siteDeviceFCC", cascade = { CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<SiteDevicePriceSign> gradePriceSigns = new ArrayList();

	@ManyToMany
	@JoinTable(name = "site_device_fcc_pos", joinColumns = @JoinColumn(name = "site_device_fcc_id"), inverseJoinColumns = @JoinColumn(name = "site_device_pos_id"))
	private Set<SiteDevicePos> siteDevicePos = new HashSet();

	public SiteDeviceFCC() {
	}

	public ServiceMode getServiceMode() {
		return serviceMode;
	}

	public void setServiceMode(ServiceMode serviceMode) {
		this.serviceMode = serviceMode;
	}

	public FuellingMode getFuellingMode() {
		return fuellingMode;
	}

	public void setFuellingMode(FuellingMode fuellingMode) {
		this.fuellingMode = fuellingMode;
	}

	public List<SiteDeviceFuelPoint> getFuelPoints() {
		return fuelPoints;
	}

	public void setFuelPoints(List<SiteDeviceFuelPoint> fuelPoints) {
		this.fuelPoints = fuelPoints;
	}

	public List<SiteDeviceTankLevelGauges> getSiteDeviceTankLevelGauges() {
		return siteDeviceTankLevelGauges;
	}

	public void setSiteDeviceTankLevelGauges(List<SiteDeviceTankLevelGauges> siteDeviceTankLevelGauges) {
		this.siteDeviceTankLevelGauges = siteDeviceTankLevelGauges;
	}

	public List<SiteDevicePriceSign> getGradePriceSigns() {
		return gradePriceSigns;
	}

	public void setGradePriceSigns(List<SiteDevicePriceSign> gradePriceSigns) {
		this.gradePriceSigns = gradePriceSigns;
	}

	public void setSiteDevicePos(Set<SiteDevicePos> siteDevicePos) {
		this.siteDevicePos = siteDevicePos;
	}

	public Set<SiteDevicePos> getSiteDevicePos() {
		return siteDevicePos;
	}

	public Map<String, Object> getCommunicationMethodData() {
		return communicationMethodData;
	}

	public void setCommunicationMethodData(Map<String, Object> communicationMethodData) {
		this.communicationMethodData = communicationMethodData;
	}

	public CommunicationMethod getCommunicationMethod() {
		return communicationMethod;
	}

	public void setCommunicationMethod(CommunicationMethod communicationMethod) {
		this.communicationMethod = communicationMethod;
	}
}
