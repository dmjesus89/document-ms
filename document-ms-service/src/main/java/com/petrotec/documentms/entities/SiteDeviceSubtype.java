package com.petrotec.documentms.entities;

import com.petrotec.service.converters.JpaConverterJson;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "site_device_subtype")
public class SiteDeviceSubtype {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "description", columnDefinition = "json")
    @Convert(converter = JpaConverterJson.class)
    private Map<String, String> description;

    @Column(name = "is_enabled", nullable = false)
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "device_type_id", referencedColumnName = "id", nullable = false)
    private SiteDeviceType siteDeviceType;


    public SiteDeviceSubtype() {
    }


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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public SiteDeviceType getSiteDeviceType() {
        return siteDeviceType;
    }

    public void setSiteDeviceType(SiteDeviceType siteDeviceType) {
        this.siteDeviceType = siteDeviceType;
    }
}
