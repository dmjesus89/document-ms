package com.petrotec.documentms.entities.documents;


import com.petrotec.service.converters.JpaConverterJson;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "document_sale_type")
public class DocumentSaleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "code")
    private String code;

    @Convert(converter = JpaConverterJson.class)
    @Column(name = "description", columnDefinition = "json", nullable = false)
    private Map<String, String> description;

    @Basic
    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Basic
    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

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

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
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

}
