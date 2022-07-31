package com.petrotec.documentms.entities.documents;

import com.petrotec.service.converters.JpaConverterJson;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Entity(name = "payment_movement")
public class PaymentMovement {
    private Integer id;
    private String code;
    private Map<String,String> description;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Collection<DocumentPayment> payments;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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


    @Convert(converter = JpaConverterJson.class)
    @Column(name = "description", columnDefinition = "json", nullable = false)
    public Map<String,String> getDescription() {
        return description;
    }

    public void setDescription(Map<String,String> description) {
        this.description = description;
    }

    @Basic
    @Column(name = "created_on")
    @CreationTimestamp
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "updated_on")
    @UpdateTimestamp
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

        PaymentMovement that = (PaymentMovement) o;

        if (id != that.id) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (updatedOn != null ? !updatedOn.equals(that.updatedOn) : that.updatedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "movement")
    public Collection<DocumentPayment> getPayments() {
        return payments;
    }

    public void setPayments(Collection<DocumentPayment> documentPaymentsById) {
        this.payments = documentPaymentsById;
    }
}
