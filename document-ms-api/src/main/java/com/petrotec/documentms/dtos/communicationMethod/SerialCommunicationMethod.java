package com.petrotec.documentms.dtos.communicationMethod;

import com.petrotec.documentms.interfaces.ICommunicationMethod;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class SerialCommunicationMethod implements ICommunicationMethod {

    @NotEmpty
    @Schema(description = "Serial device address", required = true)
    @Min(0)
    @Max(255)
    private short address = -1;


    @NotEmpty
    @Schema(description = "Serial device address", required = true, minimum = "0", maximum = "10000")
    @Min(0)
    @Max(10000)
    private short baudRate = -1;

    @Override
    public boolean isValid() {
        if (address < 0 || address > 255) return false;
        if (baudRate < 0 || baudRate > 10000) return false;

        return true;
    }

    @Override
    public METHOD getMethod() {
        return METHOD.SERIAL_DATA;
    }
}
