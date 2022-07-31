package com.petrotec.documentms.mappers.devices;

import com.petrotec.categories.mappers.categories.CategoryAssociationMapper;
import com.petrotec.categories.mappers.properties.PropertyAssociationMapper;
import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.clients.ProductClient;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceWarehouseDTO;
import com.petrotec.documentms.dtos.siteDevice.WarehouseTypeDTO;
import com.petrotec.documentms.entities.SiteDeviceWarehouse;
import com.petrotec.documentms.entities.WarehouseType;
import com.petrotec.documentms.mappers.product.ProductMapper;
import org.mapstruct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SiteDeviceWarehouseMapper {
    private static final Logger LOG = LoggerFactory.getLogger(SiteDeviceWarehouseMapper.class);

    @Inject
    private TranslateMapper translateMapper;

    @Inject
    private SiteDeviceMapper deviceMapper;

    @Inject
    private ProductMapper productMapper;

    @Inject
    private ProductClient productClient;

    @Inject
    private PropertyAssociationMapper propertyAssociationMapper;

    @Inject
    private CategoryAssociationMapper categoryAssociationMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "properties", ignore = true)
    @Mapping(target = "site", ignore = true)
    @Mapping(target = "warehouseType", ignore = true)
    @Mapping(target = "description", source = "description")
    public abstract SiteDeviceWarehouse fromDTO(SiteDeviceWarehouseDTO dto, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "properties", ignore = true)
    @Mapping(target = "site", ignore = true)
    @Mapping(target = "warehouseType", ignore = true)
    @Mapping(target = "description", source = "description")
    public abstract void updateEntity(@MappingTarget SiteDeviceWarehouse entity, SiteDeviceWarehouseDTO dto, @Context String locale);

    public List<SiteDeviceWarehouseDTO> toDTO(List<SiteDeviceWarehouse> entityList, @Context String locale) {
        return entityList.stream().map(e -> toDTO(e, locale)).collect(Collectors.toList());
    }

    @Named("toDTO")
    public SiteDeviceWarehouseDTO toDTO(@MappingTarget SiteDeviceWarehouse entity, @Context String locale) {
        SiteDeviceWarehouseDTO deviceDto = new SiteDeviceWarehouseDTO();
        deviceMapper.toDTO(deviceDto, entity, locale);
        deviceDto.setSiteProduct(productMapper.toDTO(entity.getProduct(), locale));
        deviceDto.setWarehouseType(toDTO(entity.getWarehouseType(), locale));
        deviceDto.setWarehouseTypeCode(entity.getWarehouseType().getCode());
        if (entity.getProduct() != null)
            deviceDto.setProductCode(entity.getProduct().getCode());
        try {
            deviceDto.setWarehouseCode(Short.valueOf(entity.getWarehouseCode()));
            deviceDto.setInventories(
                    StreamSupport.stream(
                            productClient.getWarehouseInventories(locale, "1", "1", "1",
                                    entity.getCode()).getResult().getItems().spliterator(), false).collect(Collectors.toList()));
            deviceDto.setCurrentVolume(entity.getCurrentVolume());
            deviceDto.setTotalCapacity(entity.getTotalCapacity());
            deviceDto.setDefaultWarehouse(entity.isDefaultWarehouse());
            deviceDto.setProperties(propertyAssociationMapper.propertiesToDto(entity.getProperties(), locale));
            deviceDto.setCategories(categoryAssociationMapper.categoriesToDto(entity.getCategories(), locale));
        } catch (Exception e) {
            LOG.error("Could not obtain the FCC Warehouse Code for {}", entity.getWarehouseCode());
        }
        return deviceDto;
    }

    public WarehouseTypeDTO toDTO(WarehouseType warehouseType, String locale) {
        WarehouseTypeDTO posTypeDto = new WarehouseTypeDTO();
        posTypeDto.setCode(warehouseType.getCode());
        posTypeDto.setDescription(translateMapper.translatedDescription(warehouseType.getDescription(), locale));
        posTypeDto.setDetailedDescription(warehouseType.getDescription());
        return posTypeDto;
    }

}
