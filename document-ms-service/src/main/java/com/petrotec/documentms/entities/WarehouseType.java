package com.petrotec.documentms.entities;

import com.petrotec.service.converters.JpaConverterJson;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "warehouse_type")
public class WarehouseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", columnDefinition = "json")
    @Convert(converter = JpaConverterJson.class)
    private Map<String, String> description;

    private String code;


    public WarehouseType() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
