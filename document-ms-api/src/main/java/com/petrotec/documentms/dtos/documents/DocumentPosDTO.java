package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
public class DocumentPosDTO {
    @Schema(description = "Pos site number.", required = true, example = "1")
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
