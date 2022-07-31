package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
@Schema(description = "Represents a payment mode associate the a site", accessMode = Schema.AccessMode.READ_ONLY)
public class SimpleSitePaymentModeDTO extends SimpleDTO {

    private String siteCode;
    private String SiteDescription;

}
