package com.petrotec.documentms.dtos.documents.search;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Introspected
@SuperBuilder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentProductLineDetailsDTO extends DocumentHeaderDTO {
    private String documentLineCode;
    private Integer documentLineNo;

    private BigDecimal documentLineQuantity;
    private BigDecimal documentLineUnitPriceNet;
    private BigDecimal documentLineUnitPriceGross;
    private BigDecimal documentLineNetAmount;
    private BigDecimal documentLineTaxAmount;
    private BigDecimal documentLineGrossAmount;
    private BigDecimal documentLineDiscountAmount;
    private BigDecimal documentLineTotalAmount;
    private BigDecimal documentLineCostPriceNet;
    private BigDecimal documentLineCostPriceGross;

    private String documentLineProductCode;
    private String documentLineProductColor;
    private String documentLineProductDescription;
    private String documentLineProductShortDescription;

    private String documentLineProductGroupCode;
    private String documentLineProductGroupColor;
    private String documentLineProductGroupDescription;

    private String documentLineProductFamilyCode;
    private String documentLineProductFamilyColor;
    private String documentLineProductFamilyDescription;

    private Integer documentLineFuelPump;
    private Integer documentLineFuelNozzle;

    private BigDecimal documentLineFuelStartTotalizer;
    private BigDecimal documentLineFuelEndTotalizer;
    private BigDecimal documentLineFuelStartStock;
    private BigDecimal documentLineFuelEndStock;
    private BigDecimal documentLineFuelDiffQuantity;
    private BigDecimal documentLineFuelDiffUnitPriceNet;
    private BigDecimal documentLineFuelDiffUnitPriceGross;
    private BigDecimal documentLineFuelDiffTotalNetAmount;
    private BigDecimal documentLineFuelDiffTotalGrossAmount;
}
