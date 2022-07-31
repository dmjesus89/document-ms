package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Nonnull;

@Introspected
@Schema(description = "Customer inserted prompt")
public class DocumentPromptDTO {

    @Nonnull
    @Schema(description = "Prompt Value", example = "20000")
    private String inputData;

    @Nonnull
    @Schema(description = "Unique prompt identification", example = "ODOMETER")
    private String promptCode;

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }


    public String getPromptCode() {
        return promptCode;
    }

    public void setPromptCode(String promptByPromptId) {
        this.promptCode = promptByPromptId;
    }
}
