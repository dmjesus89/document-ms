package com.petrotec.documentms.dtos;

import com.petrotec.documentms.dtos.siteDevice.SiteDeviceDTO;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Introspected
@Schema(description = "Represents site device tank level gauges", allOf = {SiteDeviceDTO.class})
public class SiteDeviceTankLevelGaugesDTO extends SiteDeviceDTO {

	@NotEmpty
	@Schema(maxLength = 20, description = "Identifies code tank level gauges", accessMode = Schema.AccessMode.READ_ONLY)
	private String code;

	@NotNull
	@Schema(description = "Tank level gauges code", required = true, minimum = "1", maximum = "32767")
	@Min(1)
	@Max(32767)
	private Short tlgCode;

	@NotBlank
	@Schema(description = "Site device code", required = true)
	private String siteCode;

	@NotBlank
	@Max(45)
	@Schema(description = "Site device description", required = true, maxLength = 45)
	private String description;

	@NotEmpty
	@Schema(maxLength = 20, description = "Identifies the tank level gauges communication method used to connect to the pump", required = true)
	private String communicationMethodTypeCode;

	@Schema(maxLength = 20, description = "Communication Method description", accessMode = Schema.AccessMode.READ_ONLY)
	private String communicationMethodTypeDescription;

	@NotNull
	@Schema(description = "Communication details according with the communication method used", required = true)
	protected Map<String, Object> communicationMethodData;

	@Schema(description = "Warehouse device", accessMode = Schema.AccessMode.READ_ONLY)
	private SimpleWarehouseDeviceDTO simpleTankDeviceDTO;

	@NotBlank
	@Schema(description = "Warehouse device", accessMode = Schema.AccessMode.WRITE_ONLY, required = true)
	private String warehouseCode;

	@NotBlank
	@Schema(description = "Communication details according with the communication method used", required = true)
	private String protocolTypeCode;

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	public Short getTlgCode() {
		return tlgCode;
	}

	public void setTlgCode(Short tlgCode) {
		this.tlgCode = tlgCode;
	}

	@Override
	public String getSiteCode() {
		return siteCode;
	}

	@Override
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	public String getCommunicationMethodTypeCode() {
		return communicationMethodTypeCode;
	}

	public void setCommunicationMethodTypeCode(String communicationMethodTypeCode) {
		this.communicationMethodTypeCode = communicationMethodTypeCode;
	}

	public String getCommunicationMethodTypeDescription() {
		return communicationMethodTypeDescription;
	}

	public void setCommunicationMethodTypeDescription(String communicationMethodTypeDescription) {
		this.communicationMethodTypeDescription = communicationMethodTypeDescription;
	}

	public Map<String, Object> getCommunicationMethodData() {
		return communicationMethodData;
	}

	public void setCommunicationMethodData(Map<String, Object> communicationMethodData) {
		this.communicationMethodData = communicationMethodData;
	}

	public SimpleWarehouseDeviceDTO getSimpleTankDeviceDTO() {
		return simpleTankDeviceDTO;
	}

	public void setSimpleTankDeviceDTO(SimpleWarehouseDeviceDTO simpleTankDeviceDTO) {
		this.simpleTankDeviceDTO = simpleTankDeviceDTO;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getProtocolTypeCode() {
		return protocolTypeCode;
	}

	public void setProtocolTypeCode(String protocolTypeCode) {
		this.protocolTypeCode = protocolTypeCode;
	}
}

