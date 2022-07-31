package com.petrotec.documentms.dtos.receipt;

import com.petrotec.documentms.dtos.siteDevice.SimpleDTO;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
@Schema(description = "Represents a receipt block type associate the a receipt template", accessMode = Schema.AccessMode.READ_ONLY)
public class ReceiptBlockTypeDTO extends SimpleDTO {
}
