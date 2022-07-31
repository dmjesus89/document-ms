package com.petrotec.documentms.entities.receipt;

import com.petrotec.documentms.common.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "receipt_template")
public class ReceiptTemplateEntity extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_template_type_id", referencedColumnName = "id")
    private ReceiptTemplateType receiptTemplateType;

    @OneToMany(mappedBy = "receiptTemplateEntity", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<ReceiptLine> receiptLines;

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

    public ReceiptTemplateType getReceiptTemplateType() {
        return receiptTemplateType;
    }

    public void setReceiptTemplateType(ReceiptTemplateType receiptTemplateType) {
        this.receiptTemplateType = receiptTemplateType;
    }

    public List<ReceiptLine> getReceiptLines() {
        return receiptLines;
    }

    public void setReceiptLines(List<ReceiptLine> receiptLines) {
        this.receiptLines = receiptLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ReceiptTemplateEntity that = (ReceiptTemplateEntity) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (receiptTemplateType != null ? !receiptTemplateType.equals(that.receiptTemplateType) : that.receiptTemplateType != null)
            return false;
        return receiptLines != null ? receiptLines.equals(that.receiptLines) : that.receiptLines == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (receiptTemplateType != null ? receiptTemplateType.hashCode() : 0);
        result = 31 * result + (receiptLines != null ? receiptLines.hashCode() : 0);
        return result;
    }
}
