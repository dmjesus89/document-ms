package com.petrotec.documentms.entities.properties;

import com.petrotec.categories.domain.properties.IEntityProperty;
import com.petrotec.categories.domain.properties.Property;
import com.petrotec.documentms.entities.SiteDeviceWarehouse;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "property_device_warehouse")
@Entity
public class PropertyDeviceWarehouse implements IEntityProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_device_warehouse_id")
    private SiteDeviceWarehouse siteDeviceWarehouse;

    private String value;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Property getProperty() {
        return property;
    }

    @Override
    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    @Override
    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    @Override
    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public SiteDeviceWarehouse getSiteDeviceWarehouse() {
        return siteDeviceWarehouse;
    }

    public void setSiteDeviceWarehouse(SiteDeviceWarehouse siteDeviceWarehouse) {
        this.siteDeviceWarehouse = siteDeviceWarehouse;
    }
}
