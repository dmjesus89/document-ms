package com.petrotec.documentms.dtos.siteDevice;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Introspected
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Represents a simple DTO", accessMode = Schema.AccessMode.READ_ONLY, allOf = { SimpleDTO.class })
public class SimpleServiceModeDTO extends SimpleDTO {

}
