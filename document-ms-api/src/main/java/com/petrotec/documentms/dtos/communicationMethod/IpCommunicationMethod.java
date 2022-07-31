package com.petrotec.documentms.dtos.communicationMethod;

import com.petrotec.documentms.interfaces.ICommunicationMethod;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.util.StringUtils;
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
public class IpCommunicationMethod implements ICommunicationMethod {

    @NotEmpty
    @Schema(description = "device ip address", required = true, minLength = 8)
    private String ipAddress;

    @Schema(description = "device ip port", required = true, minimum = "1", maximum = "65535")
    @Min(0)
    @Max(65535)
    private int port;

    @Override
    public boolean isValid() {
        if (ipAddress == null) return false;
        if (StringUtils.isEmpty(ipAddress)) return false;
        if (port < 1 || port > 65535) return false;

        return true;
    }

    @Override
    public METHOD getMethod() {
        return METHOD.IP_PROTOCOL;
    }
}
