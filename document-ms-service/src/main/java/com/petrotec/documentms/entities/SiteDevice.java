package com.petrotec.documentms.entities;

import com.petrotec.service.converters.JpaConverterJsonMapObject;
import com.petrotec.documentms.common.BaseEntity;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "site_device")
@Inheritance(strategy = InheritanceType.JOINED)
public class SiteDevice extends BaseEntity {

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description", length = 45)
    private String description;

    @Column(name = "additional_data", columnDefinition = "json")
    @Convert(converter = JpaConverterJsonMapObject.class)
    private Map<String, Object> additionalData;

    @Column(name = "is_enabled", nullable = false)
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "site_id", referencedColumnName = "id", nullable = false)
    private Site site;

    @ManyToOne
    @JoinColumn(name = "site_device_type_id", referencedColumnName = "id", nullable = false)
    private SiteDeviceType deviceType;

    @ManyToOne
    @JoinColumn(name = "site_device_subtype_id", referencedColumnName = "id", nullable = true)
    private SiteDeviceSubtype deviceSubtype;


    public SiteDevice() {
    }

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

    public Map<String, Object> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Map<String, Object> additionalData) {
        this.additionalData = additionalData;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public SiteDeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(SiteDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public SiteDeviceSubtype getDeviceSubtype() {
        return deviceSubtype;
    }

    public void setDeviceSubtype(SiteDeviceSubtype deviceSubtype) {
        this.deviceSubtype = deviceSubtype;
    }
}
