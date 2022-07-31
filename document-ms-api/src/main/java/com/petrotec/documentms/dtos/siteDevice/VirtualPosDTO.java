package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Introspected
public class VirtualPosDTO {

    @Schema(maxLength = 20, description = "Virtual pos code.", required = true)
    private String code;

    @NotNull
    @Schema(description = "Pos site number.", required = true, example = "1", minimum = "1", maximum = "255")
    private Integer posNumber;

    @NotBlank
    @Schema(maxLength = 20, description = "Virtual pos description.", required = true)
    private String description;

    @Schema(description = "Represents if there is printer.", nullable = true)
    private boolean hasPrinter = true;

    @Schema(description = "Represents all pumps that can be connected to the system", nullable = true)
    private List<String> connectedPumps = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasPrinter() {
        return hasPrinter;
    }

    public void setHasPrinter(boolean hasPrinter) {
        this.hasPrinter = hasPrinter;
    }

    public List<String> getConnectedPumps() {
        return connectedPumps;
    }

    public void setConnectedPumps(List<String> connectedPumps) {
        this.connectedPumps = connectedPumps;
    }

    public Integer getPosNumber() {
        return posNumber;
    }

    public void setPosNumber(Integer posNumber) {
        this.posNumber = posNumber;
    }

}
