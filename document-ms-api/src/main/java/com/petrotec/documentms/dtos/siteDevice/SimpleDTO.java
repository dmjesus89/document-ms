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
@Schema(description = "Represents a generic simple device", accessMode = Schema.AccessMode.READ_ONLY)
public class SimpleDTO {

	private String code;
	private String description;

}
