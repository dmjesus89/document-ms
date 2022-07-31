package com.petrotec.documentms.dtos.receipt;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
@Schema(description = "Represents a receipt line associate the a receipt template", accessMode = Schema.AccessMode.READ_ONLY)
public class ReceiptLineDTO {

    @Schema(description = "Identifies number line of receipt line", accessMode = Schema.AccessMode.AUTO)
    private Long lineNo;
    @Schema(description = "Identifies data of a line of a receipt line", accessMode = Schema.AccessMode.AUTO)
    private String lineData;
    @Schema(description = "Identifies layout of a line of a receipt line code", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String receiptLayoutCode;
    @Schema(description = "Identifies receipt block type code", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String receiptBlockTypeCode;
    @Schema(description = "Identifies layout of a line of a receipt line", accessMode = Schema.AccessMode.READ_ONLY)
    private ReceiptLayoutDTO receiptLayoutDTO;
    @Schema(description = "Identifies receipt block type", accessMode = Schema.AccessMode.READ_ONLY)
    private ReceiptBlockTypeDTO receiptBlockTypeDTO;

    public Long getLineNo() {
        return lineNo;
    }

    public void setLineNo(Long lineNo) {
        this.lineNo = lineNo;
    }

    public String getLineData() {
        return lineData;
    }

    public void setLineData(String lineData) {
        this.lineData = lineData;
    }

    public String getReceiptLayoutCode() {
        return receiptLayoutCode;
    }

    public void setReceiptLayoutCode(String receiptLayoutCode) {
        this.receiptLayoutCode = receiptLayoutCode;
    }

    public String getReceiptBlockTypeCode() {
        return receiptBlockTypeCode;
    }

    public void setReceiptBlockTypeCode(String receiptBlockTypeCode) {
        this.receiptBlockTypeCode = receiptBlockTypeCode;
    }

    public ReceiptLayoutDTO getReceiptLayoutDTO() {
        return receiptLayoutDTO;
    }

    public void setReceiptLayoutDTO(ReceiptLayoutDTO receiptLayoutDTO) {
        this.receiptLayoutDTO = receiptLayoutDTO;
    }

    public ReceiptBlockTypeDTO getReceiptBlockTypeDTO() {
        return receiptBlockTypeDTO;
    }

    public void setReceiptBlockTypeDTO(ReceiptBlockTypeDTO receiptBlockTypeDTO) {
        this.receiptBlockTypeDTO = receiptBlockTypeDTO;
    }
}
