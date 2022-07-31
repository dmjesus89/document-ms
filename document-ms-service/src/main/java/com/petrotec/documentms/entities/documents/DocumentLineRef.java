package com.petrotec.documentms.entities.documents;

import javax.persistence.*;

@Entity(name = "document_line_ref")
public class DocumentLineRef {
    private Long documentLineId;
    private DocumentLine referencedDocumentLine;
    private DocumentLine documentLine;

    @Id
    @Column(name = "document_line_id")
    public Long getDocumentLineId() {
        return documentLineId;
    }

    public void setDocumentLineId(Long documentLineId) {
        this.documentLineId = documentLineId;
    }

    @OneToOne
    @JoinColumn(name = "document_line_id")
    @MapsId
    public DocumentLine getDocumentLine() {
        return documentLine;
    }

    public void setDocumentLine(DocumentLine documentLineByDocumentLineId) {
        this.documentLine = documentLineByDocumentLineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentLineRef that = (DocumentLineRef) o;

        if (documentLineId != that.documentLineId) return false;

        return true;
    }


    @ManyToOne
    @JoinColumn(name = "ref_document_line_id", referencedColumnName = "id", nullable = false)
    public DocumentLine getReferencedDocumentLine() {
        return referencedDocumentLine;
    }

    public void setReferencedDocumentLine(DocumentLine documentLineByRefDocumentLineId) {
        this.referencedDocumentLine = documentLineByRefDocumentLineId;
    }
}
