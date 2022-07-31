package com.petrotec.documentms.dtos.siteDevice;

import com.petrotec.documentms.interfaces.ICommunicationMethod;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Schema(description = "Represents a site POS", allOf = {SiteDeviceDTO.class})
@Introspected
@ToString
public class SiteDevicePosDTO extends SiteDeviceDTO {


    @Schema(description = "Communication method type", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    protected ICommunicationMethod communicationMethodType;
    @Valid
    @Schema(description = "Represents all virtual pos code", nullable = true)
    List<VirtualPosDTO> virtualPos = new ArrayList<>();
    @NotNull
    @Schema(description = "Pos site number.", required = true, example = "1", minimum = "1", maximum = "255")
    private Integer posNumber;
    @Schema(maxLength = 20, description = "Pos Type.", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private PosTypeDTO posType;
    @Schema(maxLength = 20, description = "Pos Type Code.", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private String posTypeCode;
    @NotEmpty
    @Schema(maxLength = 20, description = "Identifies the communication method used to connect", required = true)
    private String communicationMethodTypeCode;
    @Schema(maxLength = 20, description = "Communication Method description", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private String communicationMethodTypeDescription;
    @NotNull
    @Schema(description = "Communication details according with the communication method used", nullable = true)
    private Map<String, Object> communicationMethodData;
    @NotEmpty
    @Schema(description = "Communication details according with the communication method used", nullable = true)
    private String protocolTypeCode;
    @Schema(description = "Protocol type description", nullable = true, accessMode = Schema.AccessMode.READ_ONLY)
    private String protocolTypeDescription;
    @Schema(description = "List of Fuel point codes")
    private List<String> connectedFuelPoints = new ArrayList<>();

    @Schema(description = "List of Fuel Points", accessMode = Schema.AccessMode.READ_ONLY)
    private List<SimpleFuelPointDTO> fuelPoints = new ArrayList<>();

    public Integer getPosNumber() {
        return posNumber;
    }

    public void setPosNumber(Integer posNumber) {
        this.posNumber = posNumber;
    }

    public String getCommunicationMethodTypeCode() {
        return communicationMethodTypeCode;
    }

    public void setCommunicationMethodTypeCode(String communicationMethodTypeCode) {
        this.communicationMethodTypeCode = communicationMethodTypeCode;
    }

    public Map<String, Object> getCommunicationMethodData() {
        return communicationMethodData;
    }

    public void setCommunicationMethodData(Map<String, Object> communicationMethodData) {
        this.communicationMethodData = communicationMethodData;
    }

    public String getProtocolTypeCode() {
        return protocolTypeCode;
    }

    public void setProtocolTypeCode(String protocolTypeCode) {
        this.protocolTypeCode = protocolTypeCode;
    }

    public List<VirtualPosDTO> getVirtualPos() {
        return virtualPos;
    }

    public void setVirtualPos(List<VirtualPosDTO> virtualPos) {
        this.virtualPos = virtualPos;
    }

    public List<String> getConnectedFuelPoints() {
        return connectedFuelPoints;
    }

    public void setConnectedFuelPoints(List<String> connectedFuelPoints) {
        this.connectedFuelPoints = connectedFuelPoints;
    }

    public List<SimpleFuelPointDTO> getFuelPoints() {
        return fuelPoints;
    }

    public void setFuelPoints(List<SimpleFuelPointDTO> fuelPoints) {
        this.fuelPoints = fuelPoints;
    }

    public PosTypeDTO getPosType() {
        return posType;
    }

    public void setPosType(PosTypeDTO posType) {
        this.posType = posType;
    }

    public String getPosTypeCode() {
        return posTypeCode;
    }

    public void setPosTypeCode(String posTypeCode) {
        this.posTypeCode = posTypeCode;
    }

    public String getCommunicationMethodTypeDescription() {
        return communicationMethodTypeDescription;
    }

    public void setCommunicationMethodTypeDescription(String communicationMethodTypeDescription) {
        this.communicationMethodTypeDescription = communicationMethodTypeDescription;
    }

    public ICommunicationMethod getCommunicationMethodType() {
        return communicationMethodType;
    }

    public void setCommunicationMethodType(ICommunicationMethod communicationMethodType) {
        this.communicationMethodType = communicationMethodType;
    }

    public String getProtocolTypeDescription() {
        return protocolTypeDescription;
    }

    public void setProtocolTypeDescription(String protocolTypeDescription) {
        this.protocolTypeDescription = protocolTypeDescription;
    }
}
