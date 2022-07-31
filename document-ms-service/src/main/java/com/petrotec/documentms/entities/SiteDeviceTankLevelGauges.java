package com.petrotec.documentms.entities;

import com.petrotec.service.converters.JpaConverterJsonMapObject;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "site_device_tank_level_gauge")
public class SiteDeviceTankLevelGauges extends SiteDevice implements CommunicationDevice  {

	@ManyToOne
	@JoinColumn(name = "site_device_fcc_id", referencedColumnName = "id")
	private SiteDeviceFCC siteDeviceFCC;

	@Column(name = "tlg_code")
	private Short tlgCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "communication_method_id", referencedColumnName = "id")
	private CommunicationMethod communicationMethod;

	@Column(name = "communication_method_data", columnDefinition = "json")
	@Convert(converter = JpaConverterJsonMapObject.class)
	private Map<String, Object> communicationMethodData;

	@ManyToOne
	@JoinColumn(name = "site_device_warehouse_id", referencedColumnName = "id", nullable = false)
	private SiteDeviceWarehouse siteDeviceWarehouse;


	public SiteDeviceTankLevelGauges() {
	}

	public SiteDeviceFCC getSiteDeviceFCC() {
		return siteDeviceFCC;
	}

	public void setSiteDeviceFCC(SiteDeviceFCC siteDeviceFCC) {
		this.siteDeviceFCC = siteDeviceFCC;
	}

	public Short getTlgCode() {
		return tlgCode;
	}

	public void setTlgCode(Short tlgCode) {
		this.tlgCode = tlgCode;
	}

	public SiteDeviceWarehouse getSiteDeviceWarehouse() {
		return siteDeviceWarehouse;
	}

	public void setSiteDeviceWarehouse(SiteDeviceWarehouse siteDeviceWarehouse) {
		this.siteDeviceWarehouse = siteDeviceWarehouse;
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
