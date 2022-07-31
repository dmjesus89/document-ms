package com.petrotec.documentms.dtos.documents;

public class DocumentPromptExtendedDTO {
    private String inputData;
    private PromptDTO prompt;

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }


    public PromptDTO getPrompt() {
        return prompt;
    }

    public void setPrompt(PromptDTO promptByPromptId) {
        this.prompt = promptByPromptId;
    }
}
