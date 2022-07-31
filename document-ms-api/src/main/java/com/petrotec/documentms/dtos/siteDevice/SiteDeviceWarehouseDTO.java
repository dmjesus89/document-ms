package com.petrotec.documentms.dtos.siteDevice;

import com.petrotec.api.dtos.categories.CategoryElementDTO;
import com.petrotec.api.dtos.categories.IDtoWithCategories;
import com.petrotec.api.dtos.properties.IDtoWithProperties;
import com.petrotec.api.dtos.properties.PropertyValueDTO;
import com.petrotec.product.api.dtos.ProductExtendedDTO;
import com.petrotec.product.api.dtos.inventory.InventoryStockExtendedDTO;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Represents a site warehouse", accessMode = Schema.AccessMode.WRITE_ONLY, allOf = { SiteDeviceDTO.class })
@Introspected
public class SiteDeviceWarehouseDTO extends SiteDeviceDTO implements IDtoWithProperties, IDtoWithCategories {

    @Schema(maxLength = 3, maximum = "255", minimum = "1", description = "Petrotec code to overcome our fcc cop limitations", required = true, example = "1")
    @Max(255)
    @Min(0)
    private Short warehouseCode;

    @Schema(maxLength = 20, description = "Warehouse Type Code.", required = true, example = "WET")
    private String warehouseTypeCode;


    @Schema(maxLength = 20, description = "Fcc Type Code", required = true, example = "DRY", accessMode = Schema.AccessMode.READ_ONLY)
    private WarehouseTypeDTO warehouseType;

    @Schema(maxLength = 20, description = "Product Code.", required = true, example = "PETROL")
    private String productCode;

    @Schema(description = "Product associated with this Warehouse", accessMode = Schema.AccessMode.READ_ONLY)
    private ProductExtendedDTO siteProduct;

    @Schema(description = "Total Capacity. Can on be set on create",  example = "0", defaultValue = "0")
    private BigDecimal totalCapacity;

    @Schema(description = "Current Volume.  Can on be set on create",  example = "0", defaultValue = "0")
    private BigDecimal currentVolume;

    @Schema(description = "Default warehouse",  example = "0", defaultValue = "false")
    private boolean defaultWarehouse;


    @Schema(description = "Inventory details associated to the current warehouse", accessMode = Schema.AccessMode.READ_ONLY)
    private List<InventoryStockExtendedDTO> inventories;


    @Schema(description = "Warehouse properties")
    private List<PropertyValueDTO> properties;

    @Schema(description = "Warehouse categories")
    private List<CategoryElementDTO> categories;


    public SiteDeviceWarehouseDTO() {
    }

    public Short getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(Short warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseTypeCode() {
        return warehouseTypeCode;
    }

    public void setWarehouseTypeCode(String warehouseTypeCode) {
        this.warehouseTypeCode = warehouseTypeCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    @Override
    public List<CategoryElementDTO> getCategories() {
        return categories;
    }

    @Override
    public void setCategories(List<CategoryElementDTO> categories) {
        this.categories = categories;
    }

    @Override
    public List<PropertyValueDTO> getProperties() {
        return properties;
    }

    @Override
    public void setProperties(List<PropertyValueDTO> properties) {
        this.properties = properties;
    }

    public WarehouseTypeDTO getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(WarehouseTypeDTO warehouseType) {
        this.warehouseType = warehouseType;
    }

    public ProductExtendedDTO getSiteProduct() {
        return siteProduct;
    }

    public void setSiteProduct(ProductExtendedDTO siteProduct) {
        this.siteProduct = siteProduct;
    }

    public List<InventoryStockExtendedDTO> getInventories() {
        return inventories;
    }

    public void setInventories(List<InventoryStockExtendedDTO> inventories) {
        this.inventories = inventories;
    }
}
