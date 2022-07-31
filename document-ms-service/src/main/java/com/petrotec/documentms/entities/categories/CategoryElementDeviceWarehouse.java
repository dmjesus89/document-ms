package com.petrotec.documentms.entities.categories;

import com.petrotec.categories.domain.categories.Category;
import com.petrotec.categories.domain.categories.CategoryElement;
import com.petrotec.categories.domain.categories.IEntityCategoryElement;
import com.petrotec.documentms.entities.SiteDeviceWarehouse;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "category_element_device_warehouse")
public class CategoryElementDeviceWarehouse implements IEntityCategoryElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_element_id")
    private CategoryElement categoryElement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_device_warehouse_id")
    private SiteDeviceWarehouse siteDeviceWarehouse;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    public CategoryElementDeviceWarehouse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryElement getCategoryElement() {
        return categoryElement;
    }

    public void setCategoryElement(CategoryElement categoryElement) {
        this.categoryElement = categoryElement;
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

    public SiteDeviceWarehouse getSiteDeviceWarehouse() {
        return siteDeviceWarehouse;
    }

    public void setSiteDeviceWarehouse(SiteDeviceWarehouse siteDeviceWarehouse) {
        this.siteDeviceWarehouse = siteDeviceWarehouse;
    }
}
