package com.petrotec.documentms.entities.receipt;


import com.petrotec.documentms.common.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "receipt_template_type")
public class ReceiptTemplateType extends BaseEntity {

    @Column(name = "code")
    private String code;

    @OneToMany
    @JoinTable(name = "template_block_type", joinColumns = @JoinColumn(name = "template_type_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "block_template_id", referencedColumnName = "id"))
    private List<ReceiptBlockType> receiptBlockTypes;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ReceiptBlockType> getReceiptBlockTypes() {
        return receiptBlockTypes;
    }

    public void setReceiptBlockTypes(List<ReceiptBlockType> receiptBlockTypes) {
        this.receiptBlockTypes = receiptBlockTypes;
    }
}
