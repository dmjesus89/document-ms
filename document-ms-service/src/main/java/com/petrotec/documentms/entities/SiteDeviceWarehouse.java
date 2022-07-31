package com.petrotec.documentms.entities;

import com.petrotec.categories.domain.categories.IEntityCategoryElement;
import com.petrotec.categories.domain.categories.IEntityWithCategories;
import com.petrotec.categories.domain.properties.IEntityProperty;
import com.petrotec.categories.domain.properties.IEntityWithProperties;
import com.petrotec.documentms.entities.categories.CategoryElementDeviceWarehouse;
import com.petrotec.documentms.entities.product.Product;
import com.petrotec.documentms.entities.properties.PropertyDeviceWarehouse;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "site_device_warehouse")
public class SiteDeviceWarehouse extends SiteDevice implements IEntityWithProperties, IEntityWithCategories {

    @Column(name = "fcc_warehouse_code")
    private String warehouseCode;

    @ManyToOne
    @JoinColumn(name = "warehouse_type_id", referencedColumnName = "id", nullable = false)
    private WarehouseType warehouseType;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "total_capacity")
    private BigDecimal totalCapacity;

    @Column(name = "current_volume")
    private BigDecimal currentVolume;

    @Column(name = "default_warehouse")
    private boolean defaultWarehouse;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "siteDeviceWarehouse")
    private List<CategoryElementDeviceWarehouse> categories = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "siteDeviceWarehouse")
    private List<PropertyDeviceWarehouse> properties = new ArrayList<>();


    public SiteDeviceWarehouse() {
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public WarehouseType getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(WarehouseType warehouseType) {
        this.warehouseType = warehouseType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(BigDecimal totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public BigDecimal getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(BigDecimal currentVolume) {
        this.currentVolume = currentVolume;
    }

    public boolean isDefaultWarehouse() {
        return defaultWarehouse;
    }

    public void setDefaultWarehouse(boolean defaultWarehouse) {
        this.defaultWarehouse = defaultWarehouse;
    }

    public List getCategories() {
        return categories;
    }

    public void setCategories(List categories) {
        this.categories = categories;
    }

    public List getProperties() {
        return properties;
    }

    public void setProperties(List properties) {
        this.properties = properties;
    }

    @Override
    public IEntityProperty newPropertyInstance() {
        PropertyDeviceWarehouse p = new PropertyDeviceWarehouse();
        p.setSiteDeviceWarehouse(this);
        return p;
    }

    @Override
    public IEntityCategoryElement newCategoryElementInstance() {
        CategoryElementDeviceWarehouse s = new CategoryElementDeviceWarehouse();
        s.setSiteDeviceWarehouse(this);
        return s;
    }

}
