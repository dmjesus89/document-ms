package com.petrotec.documentms.entities.receipt;

import com.petrotec.documentms.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "receipt_line")
public class ReceiptLine extends BaseEntity {

    @Column(name = "line_no")
    private Long lineNo;

    @Column(name = "line_data")
    private String lineData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_template_id", referencedColumnName = "id")
    private ReceiptTemplateEntity receiptTemplateEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_block_type_id", referencedColumnName = "id")
    private ReceiptBlockType receiptBlockTypes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_layout_id", referencedColumnName = "id")
    private ReceiptLayout receiptLayout;

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

    public ReceiptTemplateEntity getReceiptTemplateEntity() {
        return receiptTemplateEntity;
    }

    public void setReceiptTemplateEntity(ReceiptTemplateEntity receiptTemplateEntity) {
        this.receiptTemplateEntity = receiptTemplateEntity;
    }

    public ReceiptBlockType getReceiptBlockTypes() {
        return receiptBlockTypes;
    }

    public void setReceiptBlockTypes(ReceiptBlockType receiptBlockTypes) {
        this.receiptBlockTypes = receiptBlockTypes;
    }

    public ReceiptLayout getReceiptLayout() {
        return receiptLayout;
    }

    public void setReceiptLayout(ReceiptLayout receiptLayout) {
        this.receiptLayout = receiptLayout;
    }
}
