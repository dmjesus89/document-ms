package com.petrotec.documentms.dtos.siteDevice;

import com.petrotec.documentms.interfaces.ICommunicationMethod;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Schema(description = "Represents a site device price sign.", allOf = {SiteDeviceDTO.class})
@Introspected
public class SiteDevicePriceSignDTO extends SiteDeviceDTO {

    @Size(max = 36)
    @NotBlank
    @Schema(maxLength = 36, description = "Price signal code", required = true)
    protected String priceSignCode;

    @NotEmpty
    @Schema(maxLength = 20, description = "Identifies the communication method used to connect", required = true)
    private String communicationMethodTypeCode;


    @Schema(maxLength = 20, description = "Communication Method description", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private String communicationMethodTypeDescription;


    @Schema(description = "Communication method type", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    protected ICommunicationMethod communicationMethodType;


    @NotNull
    @Schema(description = "Communication details according with the communication method used", nullable = true)
    private Map<String, Object> communicationMethodData;

    @NotEmpty
    @Schema(description = "Protocol details according with the communication method used", nullable = true)
    private String protocolTypeCode;

    @Schema(description = "Protocol type description", nullable = true, accessMode = Schema.AccessMode.READ_ONLY)
    private String protocolTypeDescription;


    @Schema(description = "Indicate which grades are associated with each segment.", accessMode = Schema.AccessMode.READ_ONLY)
    private List<SimpleGradePriceSignDTO> grades = new ArrayList<>();

    @Schema(description = "Indicate which grades are associated with each segment.", required = true)
    @NotNull
    protected List<String> gradeCodes = new ArrayList<>();

    public String getPriceSignCode() {
        return priceSignCode;
    }

    public void setPriceSignCode(String priceSignCode) {
        this.priceSignCode = priceSignCode;
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

    public List<String> getGradeCodes() {
        return gradeCodes;
    }

    public void setGradeCodes(List<String> gradeCodes) {
        this.gradeCodes = gradeCodes;
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

    public List<SimpleGradePriceSignDTO> getGrades() {
        return grades;
    }

    public void setGrades(List<SimpleGradePriceSignDTO> grades) {
        this.grades = grades;
    }
}
