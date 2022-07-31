package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

@Introspected
@Schema(description = "Document Line Fuel data")
public class DocumentLineFuelDTO {


    @Nonnull
    @Schema(description = "Site pump number", example = "1")
    private Integer pump;

    @Nonnull
    @Schema(description = "Pump nozzle number", example = "1")
    private Integer nozzle;

    @Schema(description = "Pump/Nozzle totalizer before the pump started fuelling")
    private BigDecimal startTotalizer;

    @Schema(description = "Pump/Nozzle totalizer at the end of the transaction")
    private BigDecimal endTotalizer;

    @Schema(description = "Pump/Nozzle tank stock reading before the beginning of the transaction")
    private BigDecimal startStock;

    @Schema(description = "Pump/Nozzle tank stock reading after the transaction")
    private BigDecimal endStock;

    @Nonnull
    @Schema(description = "Fcc transaction id. Might not be unique", example = "200")
    private String transactionId;

    public Integer getPump() {
        return pump;
    }

    public void setPump(Integer pump) {
        this.pump = pump;
    }

    public Integer getNozzle() {
        return nozzle;
    }

    public void setNozzle(Integer nozzle) {
        this.nozzle = nozzle;
    }

    public BigDecimal getStartTotalizer() {
        return startTotalizer;
    }

    public void setStartTotalizer(BigDecimal startTotalizer) {
        this.startTotalizer = startTotalizer;
    }

    public BigDecimal getEndTotalizer() {
        return endTotalizer;
    }

    public void setEndTotalizer(BigDecimal endTotalizer) {
        this.endTotalizer = endTotalizer;
    }

    public BigDecimal getStartStock() {
        return startStock;
    }

    public void setStartStock(BigDecimal startStock) {
        this.startStock = startStock;
    }

    public BigDecimal getEndStock() {
        return endStock;
    }

    public void setEndStock(BigDecimal endStock) {
        this.endStock = endStock;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}
