package com.petrotec.documentms.entities.documents;

import javax.persistence.*;

@Entity(name = "tax_item")
public class TaxItem {
    private short id;
    private String code;
    private String description;
    private byte isFixedRate;
    private byte isEnabled;

    @Id
    @Column(name = "id")
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "is_fixed_rate")
    public byte getIsFixedRate() {
        return isFixedRate;
    }

    public void setIsFixedRate(byte isFixedRate) {
        this.isFixedRate = isFixedRate;
    }

    @Basic
    @Column(name = "is_enabled")
    public byte getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(byte isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxItem taxItem = (TaxItem) o;

        if (id != taxItem.id) return false;
        if (isFixedRate != taxItem.isFixedRate) return false;
        if (isEnabled != taxItem.isEnabled) return false;
        if (code != null ? !code.equals(taxItem.code) : taxItem.code != null) return false;
        if (description != null ? !description.equals(taxItem.description) : taxItem.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) isFixedRate;
        result = 31 * result + (int) isEnabled;
        return result;
    }
}
