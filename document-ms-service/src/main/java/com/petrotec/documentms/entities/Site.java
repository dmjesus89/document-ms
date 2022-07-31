package com.petrotec.documentms.entities;

import com.petrotec.categories.domain.categories.IEntityCategoryElement;
import com.petrotec.categories.domain.categories.IEntityWithCategories;
import com.petrotec.categories.domain.properties.IEntityProperty;
import com.petrotec.categories.domain.properties.IEntityWithProperties;
import com.petrotec.service.converters.JpaConverterJson;
import com.petrotec.documentms.entities.categories.CategoryElementSite;
import com.petrotec.documentms.entities.properties.PropertySite;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Site
 */
@Entity
@Table(name = "site")
public class Site implements IEntityWithProperties, IEntityWithCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "site_profile_id")
    private SiteProfile siteProfile;

    @Column(name = "site_number")
    private String siteNumber;

    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    @Column(name = "entity_code")
    private String entityCode;

    @Column(name = "description", columnDefinition = "json")
    @Convert(converter = JpaConverterJson.class)
    private Map<String, String> description;

    @Column(name = "is_enabled")
    private boolean enabled;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "site")
    private List<CategoryElementSite> categories = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "site")
    private List<PropertySite> properties = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "site_group_item_site", joinColumns = @JoinColumn(name = "site_id"), inverseJoinColumns = @JoinColumn(name = "site_group_item_id"))
    private List<SiteGroupItem> siteGroupItems;

    @OneToMany(mappedBy = "site", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<SiteGrade> siteGrades = new HashSet<>();

    public Site() {
    }

    public Site(int id, Region region, SiteProfile siteProfile, String code, Map<String, String> description, boolean enabled,
                String latitude, String longitude, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this.id = id;
        this.siteProfile = siteProfile;
        this.region = region;
        this.code = code;
        this.description = description;
        this.enabled = enabled;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public SiteProfile getSiteProfile() {
        return this.siteProfile;
    }

    public void setSiteProfile(SiteProfile siteProfile) {
        this.siteProfile = siteProfile;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getDescription() {
        return this.description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public List<SiteGroupItem> getSiteGroupItems() {
        return siteGroupItems;
    }

    public void setSiteGroupItems(List<SiteGroupItem> siteGroupItems) {
        this.siteGroupItems = siteGroupItems;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public List getProperties() {
        return properties;
    }

    public Set<SiteGrade> getSiteGrades() {
        return siteGrades;
    }

    public void setSiteGrades(Set<SiteGrade> siteGrades) {
        this.siteGrades = siteGrades;
    }

    @Override
    public void setProperties(List properties) {
        this.properties = properties;
    }

    @Override
    public IEntityProperty newPropertyInstance() {
        PropertySite p = new PropertySite();
        p.setSite(this);
        return p;
    }


    public List getCategories() {
        return categories;
    }

    @Override
    public void setCategories(List categories) {
        this.categories = categories;
    }

    @Override
    public IEntityCategoryElement newCategoryElementInstance() {
        CategoryElementSite s = new CategoryElementSite();
        s.setSite(this);
        return s;
    }

}
