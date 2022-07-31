package com.petrotec.documentms.entities.documents;


import com.petrotec.service.converters.JpaConverterJson;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "document_type")
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "prefix")
    private String prefix;

    @Convert(converter = JpaConverterJson.class)
    @Column(name = "description", columnDefinition = "json", nullable = false)
    private Map<String, String> description;

    @Basic
    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Basic
    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Basic
    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "documentType")
    private Collection<DocumentSerie> series;

    @OneToMany(mappedBy = "documentType")
    private Collection<PaymentMode> paymentModes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentType that = (DocumentType) o;

        if (id != that.id) return false;
        if (code != that.code) return false;
        if (isEnabled != that.isEnabled) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        return updatedOn != null ? updatedOn.equals(that.updatedOn) : that.updatedOn == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, prefix, description, isEnabled, createdOn, updatedOn, series, paymentModes);
    }

    public Collection<DocumentSerie> getSeries() {
        return series;
    }

    public void setSeries(Collection<DocumentSerie> documentSeriesById) {
        this.series = documentSeriesById;
    }

    public Collection<PaymentMode> getPaymentModes() {
        return paymentModes;
    }

    public void setPaymentModes(Collection<PaymentMode> paymentModesById) {
        this.paymentModes = paymentModesById;
    }
}
