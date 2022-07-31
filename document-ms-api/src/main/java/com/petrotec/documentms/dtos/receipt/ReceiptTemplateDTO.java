package com.petrotec.documentms.dtos.receipt;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Introspected
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a receipt template", accessMode = Schema.AccessMode.AUTO)
public class ReceiptTemplateDTO {

    @Schema(description = "Identifies receipt template code", accessMode = Schema.AccessMode.AUTO)
    private String code;
    @NotBlank
    @Schema(description = "Identifies receipt template description", accessMode = Schema.AccessMode.AUTO)
    private String description;
    @Schema(description = "Identifies receipt template type code", accessMode = Schema.AccessMode.AUTO)
    private String receiptTemplateTypeCode;
    @Schema(description = "Identifies receipt template type", accessMode = Schema.AccessMode.READ_ONLY)
    private ReceiptTemplateTypeDTO receiptTemplateTypeDTO;
    @Schema(description = "Identifies receipt line type", accessMode = Schema.AccessMode.AUTO)
    private List<ReceiptLineDTO> receiptLineDTO;

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

    public String getReceiptTemplateTypeCode() {
        return receiptTemplateTypeCode;
    }

    public void setReceiptTemplateTypeCode(String receiptTemplateTypeCode) {
        this.receiptTemplateTypeCode = receiptTemplateTypeCode;
    }

    public ReceiptTemplateTypeDTO getReceiptTemplateTypeDTO() {
        return receiptTemplateTypeDTO;
    }

    public void setReceiptTemplateTypeDTO(ReceiptTemplateTypeDTO receiptTemplateTypeDTO) {
        this.receiptTemplateTypeDTO = receiptTemplateTypeDTO;
    }

    public List<ReceiptLineDTO> getReceiptLineDTO() {
        return receiptLineDTO;
    }

    public void setReceiptLineDTO(List<ReceiptLineDTO> receiptLineDTO) {
        this.receiptLineDTO = receiptLineDTO;
    }
}
