package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Schema(description = "Represents a site fuel point , such as a fuel pump side or electric charger", accessMode = Schema.AccessMode.WRITE_ONLY, allOf = {SiteDeviceDTO.class})
@Introspected
public class SiteDeviceFuelPointDTO extends SiteDeviceDTO {

    @Schema(maximum = "255", minimum = "0", description = "Pump number", required = true, example = "1")
    @Max(255)
    @Min(0)
    private Integer pumpNumber;

    @Schema(description = "associated dispenser code")
    private String dispenserCode;

    @NotEmpty
    @Schema(maxLength = 20, description = "Identifies the pump communication method used to connect to the pump", required = true)
    private String communicationMethodTypeCode;

    @NotNull
    @Schema(description = "Communication details according with the communication method used", nullable = true)
    private Map<String, Object> communicationMethodData;

    @NotEmpty
    @Schema(description = "Communication details according with the communication method used", nullable = true)
    private String protocolTypeCode;

    @Schema(description = "Fuel dispenser nozzles")
    private List<SiteDeviceFuelPointNozzleDTO> nozzles = new ArrayList<>();

    @Schema(maxLength = 20, description = "Communication Method description", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private String communicationMethodTypeDescription;

    @Schema(description = "Pump type description", nullable = true, accessMode = Schema.AccessMode.READ_ONLY)
    private String protocolTypeDescription;

    @Schema(maxLength = 20, description = "Number of attached nozzles")
    private Integer totalNozzles;

    public SiteDeviceFuelPointDTO() {
    }

    public Integer getPumpNumber() {
        return pumpNumber;
    }

    public void setPumpNumber(Integer pumpNumber) {
        this.pumpNumber = pumpNumber;
    }

    public List<SiteDeviceFuelPointNozzleDTO> getNozzles() {
        return nozzles;
    }

    public void setNozzles(List<SiteDeviceFuelPointNozzleDTO> nozzles) {
        this.nozzles = nozzles;
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

    public String getDispenserCode() {
        return dispenserCode;
    }

    public void setDispenserCode(String dispenserCode) {
        this.dispenserCode = dispenserCode;
    }

    public String getCommunicationMethodTypeDescription() {
        return communicationMethodTypeDescription;
    }

    public void setCommunicationMethodTypeDescription(String communicationMethodTypeDescription) {
        this.communicationMethodTypeDescription = communicationMethodTypeDescription;
    }

    public String getProtocolTypeDescription() {
        return protocolTypeDescription;
    }

    public void setProtocolTypeDescription(String protocolTypeDescription) {
        this.protocolTypeDescription = protocolTypeDescription;
    }

    public Integer getTotalNozzles() {
        return totalNozzles;
    }

    public void setTotalNozzles(Integer totalNozzles) {
        this.totalNozzles = totalNozzles;
    }
}
