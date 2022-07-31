package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@Schema(description = "Config payment mode", allOf = {ReferencePaymentModeDTO.class})
public class PaymentModeDTO {

    @NotNull
    @Schema(description = "Unique payment mode code", maxLength = 45, accessMode = Schema.AccessMode.READ_ONLY)
    private String code;

    @NotNull
    @Schema(description = "Payment mode description", required = true, accessMode = Schema.AccessMode.READ_WRITE)
    private String description;

    @Schema(description = "Checks if the current payment mode is enabled", defaultValue = "true", accessMode = Schema.AccessMode.READ_WRITE)
    private boolean isEnabled;

    @NotNull
    @Schema(description = "Payment mode creation date", required = true)
    private LocalDateTime createdOn;

    @NotNull
    @Schema(description = "Payment mode creation date", required = true)
    private LocalDateTime updatedOn;

    @Schema(description = "Document type code", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String documentTypeCode;

    @Schema(description = "Code reference payment mode code", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String referencePaymentModeCode;

    @Schema(description = "Code reference payment mode code", accessMode = Schema.AccessMode.READ_ONLY)
    private DocumentTypeDTO documentType;

    @Schema(description = "Reference payment mode code", accessMode = Schema.AccessMode.READ_ONLY)
    private ReferencePaymentModeDTO referencePaymentModeDTO;

    @Schema(description = "Open cash drawer", accessMode = Schema.AccessMode.READ_WRITE)
    private boolean openCashDrawer;

    @Schema(description = "Number order", accessMode = Schema.AccessMode.READ_WRITE)
    private int orderNo;

    @Schema(description = "Pre payment Allowed", accessMode = Schema.AccessMode.READ_WRITE)
    private boolean prepaymentAllowed;

    @Schema(description = "Refund allowed", accessMode = Schema.AccessMode.READ_WRITE)
    private boolean refundAllowed;

    @Schema(description = "Change Allowed", accessMode = Schema.AccessMode.READ_WRITE)
    private boolean changeAllowed;

    @Schema(description = "Cancel Allowed", accessMode = Schema.AccessMode.READ_WRITE)
    private boolean cancelAllowed;

    @Schema(description = "Printer Allowed", accessMode = Schema.AccessMode.READ_WRITE)
    private boolean printerAllowed;

    @Schema(description = "Pay entire transaction", accessMode = Schema.AccessMode.READ_WRITE)
    private boolean payEntireTransaction;

    @Schema(description = "Receipt Copies", accessMode = Schema.AccessMode.READ_WRITE)
    private int receiptCopies;

    @Schema(description = "Auto Receipt", accessMode = Schema.AccessMode.READ_WRITE)
    private boolean autoReceipt;
}
