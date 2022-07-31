package com.petrotec.documentms.entities;

import com.petrotec.service.converters.JpaConverterJson;

import javax.persistence.*;

import java.util.Map;

/** CustomRegion */
@Entity
public class RegionCustom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description", columnDefinition = "json")
    @Convert(converter = JpaConverterJson.class)
    private Map<String,String> description;

    @Column(name = "enabled")
    private boolean enabled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
