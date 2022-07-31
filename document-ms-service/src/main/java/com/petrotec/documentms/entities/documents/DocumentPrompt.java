package com.petrotec.documentms.entities.documents;

import javax.persistence.*;

@Entity(name = "document_prompt")
public class DocumentPrompt {
    private Long id;
    private String inputData;
    private Document document;
    private Prompt prompt;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "input_data")
    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document documentByDocumentId) {
        this.document = documentByDocumentId;
    }

    @ManyToOne
    @JoinColumn(name = "prompt_id", referencedColumnName = "id", nullable = false)
    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt promptByPromptId) {
        this.prompt = promptByPromptId;
    }
}
