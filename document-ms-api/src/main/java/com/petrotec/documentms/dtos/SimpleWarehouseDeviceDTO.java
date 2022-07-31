package com.petrotec.documentms.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents Warehouse device", accessMode = Schema.AccessMode.READ_ONLY)
public class SimpleWarehouseDeviceDTO {

	@Schema(description = "Site device code", accessMode = Schema.AccessMode.READ_ONLY)
	private String siteDeviceCode;
	@Schema(description = "Site device description", accessMode = Schema.AccessMode.READ_ONLY)
	private String siteDeviceDescription;
	@Schema(description = "Warehouse code", accessMode = Schema.AccessMode.READ_ONLY)
	private String warehouseCode;

}
