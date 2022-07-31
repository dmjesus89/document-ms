package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Schema(description = "Represents a site warehouse", accessMode = Schema.AccessMode.WRITE_ONLY, allOf = {SiteDeviceDTO.class})
@Introspected
@NoArgsConstructor
public class SiteDeviceFccDTO extends SiteDeviceDTO {

    @NotNull
    @Schema(description = "Communication details according with the communication method used", nullable = true)
    protected Map<String, Object> communicationMethodData;
    @NotNull
    @NotEmpty
    @Schema(maxLength = 20, description = "Communication details according with the communication method used", required = true)
    private String protocolTypeCode;
    @NotEmpty
    @Schema(maxLength = 20, description = "Identifies the tank level gauges communication method used to connect to the pump", required = true)
    private String communicationMethodTypeCode;
    @Schema(maxLength = 20, description = "Communication Method description", accessMode = Schema.AccessMode.READ_ONLY)
    private String communicationMethodTypeDescription;
    @Schema(description = "Unique service mode code", maxLength = 45)
    private String serviceModeCode;

    @Schema(description = "Unique fuelling mode code", maxLength = 45)
    private String fuellingModeCode;

    @Schema(description = "Identifies all pumps turn on this fcc", accessMode = Schema.AccessMode.READ_WRITE)
    private List<String> connectedFuelPoint;

    @Schema(description = "Identifies all price polls turn on this fcc", accessMode = Schema.AccessMode.READ_WRITE)
    private List<String> connectedPriceSings;

    @Schema(description = "Identifies all tank level gauges turn on this fcc", accessMode = Schema.AccessMode.READ_WRITE)
    private List<String> connectedTLGs;

    @Schema(description = "Identifies all POS mode turn on this fcc", accessMode = Schema.AccessMode.READ_WRITE)
    private List<String> connectedPOs;

    @Schema(description = "Identifies all pumps turn on this fcc", accessMode = Schema.AccessMode.READ_ONLY)
    private List<SimpleFuelPointDTO> fuelPoints;

    @Schema(description = "Identifies all service mode turn on this fcc", accessMode = Schema.AccessMode.READ_ONLY)
    private SimpleServiceModeDTO serviceMode;

    @Schema(description = "Identifies all fuelling mode turn on this fcc", accessMode = Schema.AccessMode.READ_ONLY)
    private SimpleFuellingModeDTO fuellingMode;

    @Schema(description = "Identifies all price polls turn on this fcc", accessMode = Schema.AccessMode.READ_ONLY)
    private List<SimplePriceSignDTO> priceSigns;

    @Schema(description = "Identifies all tank level gauges turn on this fcc", accessMode = Schema.AccessMode.READ_ONLY)
    private List<SimpleSiteDeviceTlgDTO> tlgs;

    @Schema(description = "Identifies all POS mode turn on this fcc", accessMode = Schema.AccessMode.READ_ONLY)
    private List<SimpleSiteDevicePosDTO> pos;

    public String getProtocolTypeCode() {
        return protocolTypeCode;
    }

    public void setProtocolTypeCode(String protocolTypeCode) {
        this.protocolTypeCode = protocolTypeCode;
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

    public String getServiceModeCode() {
        return serviceModeCode;
    }

    public void setServiceModeCode(String serviceModeCode) {
        this.serviceModeCode = serviceModeCode;
    }

    public String getFuellingModeCode() {
        return fuellingModeCode;
    }

    public void setFuellingModeCode(String fuellingModeCode) {
        this.fuellingModeCode = fuellingModeCode;
    }

    public List<String> getConnectedFuelPoint() {
        return connectedFuelPoint;
    }

    public void setConnectedFuelPoint(List<String> connectedFuelPoint) {
        this.connectedFuelPoint = connectedFuelPoint;
    }

    public List<String> getConnectedPriceSings() {
        return connectedPriceSings;
    }

    public void setConnectedPriceSings(List<String> connectedPriceSings) {
        this.connectedPriceSings = connectedPriceSings;
    }

    public List<String> getConnectedTLGs() {
        return connectedTLGs;
    }

    public void setConnectedTLGs(List<String> connectedTLGs) {
        this.connectedTLGs = connectedTLGs;
    }

    public List<String> getConnectedPOs() {
        return connectedPOs;
    }

    public void setConnectedPOs(List<String> connectedPOs) {
        this.connectedPOs = connectedPOs;
    }

    public List<SimpleFuelPointDTO> getFuelPoints() {
        return fuelPoints;
    }

    public void setFuelPoints(List<SimpleFuelPointDTO> fuelPoints) {
        this.fuelPoints = fuelPoints;
    }

    public SimpleServiceModeDTO getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(SimpleServiceModeDTO serviceMode) {
        this.serviceMode = serviceMode;
    }

    public SimpleFuellingModeDTO getFuellingMode() {
        return fuellingMode;
    }

    public void setFuellingMode(SimpleFuellingModeDTO fuellingMode) {
        this.fuellingMode = fuellingMode;
    }

    public List<SimplePriceSignDTO> getPriceSigns() {
        return priceSigns;
    }

    public void setPriceSigns(List<SimplePriceSignDTO> priceSigns) {
        this.priceSigns = priceSigns;
    }

    public List<SimpleSiteDeviceTlgDTO> getTlgs() {
        return tlgs;
    }

    public void setTlgs(List<SimpleSiteDeviceTlgDTO> tlgs) {
        this.tlgs = tlgs;
    }

    public List<SimpleSiteDevicePosDTO> getPos() {
        return pos;
    }

    public void setPos(List<SimpleSiteDevicePosDTO> pos) {
        this.pos = pos;
    }
}
