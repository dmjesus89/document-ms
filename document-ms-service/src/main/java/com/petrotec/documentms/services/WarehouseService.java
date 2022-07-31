package com.petrotec.documentms.services;

import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.api.queue.MessageOperationEnum;
import com.petrotec.categories.services.CategoryService;
import com.petrotec.categories.services.PropertyService;
import com.petrotec.queue.annotations.MqttPublish;
import com.petrotec.queue.annotations.MqttPublisher;
import com.petrotec.service.exceptions.EntityAlreadyExistsException;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.service.exceptions.InvalidDataException;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceWarehouseDTO;
import com.petrotec.documentms.dtos.siteDevice.WarehouseTypeDTO;
import com.petrotec.documentms.entities.SiteDeviceWarehouse;
import com.petrotec.documentms.entities.WarehouseType;
import com.petrotec.documentms.mappers.WarehouseTypeMapper;
import com.petrotec.documentms.mappers.devices.SiteDeviceWarehouseMapper;
import com.petrotec.documentms.repositories.SiteDeviceRepository;
import com.petrotec.documentms.repositories.SiteDeviceWarehouseRepository;
import com.petrotec.documentms.repositories.WarehouseTypeRepository;
import com.petrotec.documentms.services.product.ProductService;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Singleton
@MqttPublisher(topicName = "pcs/document-ms/siteDevice")
@Transactional
public class WarehouseService {

    private static final Logger LOG = LoggerFactory.getLogger(WarehouseService.class);

    private final SiteDeviceWarehouseMapper siteDeviceWarehouseMapper;
    private final WarehouseTypeMapper warehouseTypeMapper;
    private final WarehouseTypeRepository warehouseTypeRepository;
    private final SiteDeviceWarehouseRepository siteDeviceWarehouseRepository;
    private final SiteDeviceRepository siteDeviceRepository;
    private final SiteService siteService;
    private final ProductService productService;
    private final PropertyService propertyService;
    private final CategoryService categoryService;
    private final SiteDeviceService siteDeviceService;


    public WarehouseService(SiteDeviceWarehouseRepository siteDeviceWarehouseRepository,
                            SiteDeviceWarehouseMapper siteDeviceWarehouseMapper, WarehouseTypeRepository warehouseTypeRepository,
                            WarehouseTypeMapper warehouseTypeMapper, SiteService siteService, ProductService productService,
                            PropertyService propertyService, CategoryService categoryService, SiteDeviceService siteDeviceService,
                            SiteDeviceRepository siteDeviceRepository) {
        this.siteDeviceWarehouseRepository = siteDeviceWarehouseRepository;
        this.siteDeviceWarehouseMapper = siteDeviceWarehouseMapper;
        this.warehouseTypeRepository = warehouseTypeRepository;
        this.warehouseTypeMapper = warehouseTypeMapper;
        this.siteService = siteService;
        this.productService = productService;
        this.propertyService = propertyService;
        this.categoryService = categoryService;
        this.siteDeviceService = siteDeviceService;
        this.siteDeviceRepository = siteDeviceRepository;
    }

    public List<WarehouseTypeDTO> listWarehouseTypes(String locale) {
        return warehouseTypeMapper.toDTO(warehouseTypeRepository.findAll(), locale);
    }

    public WarehouseType getWarehouseTypeByCode(@NotEmpty String code, String rankOrder, String locale) {
        return warehouseTypeRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(
                "site_ms_ws.site_device_service.site_device_warehouse_type_code_not_found",
                "Warehouse type not found for type" + code));
    }

    public Page<SiteDeviceWarehouse> listWarehouses(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String rankOrder, String locale, String siteCode) {
        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);
        return siteDeviceWarehouseRepository.findBySiteCode(siteCode, Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));
    }

    public PageResponse<SiteDeviceWarehouseDTO> listWarehousesDTO(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String rankOrder, String locale, String siteCode) {
        Page<SiteDeviceWarehouse> result = this.listWarehouses(pageAndSorting, filterQuery, rankOrder, locale, siteCode);

        return PageResponse
                .from(siteDeviceWarehouseMapper.toDTO(result.getContent(), locale),
                        pageAndSorting, () -> Long.valueOf(result.getSize()));
    }

    public Page<SiteDeviceWarehouse> listWarehouses(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String rankOrder, String locale) {
        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);
        int limit = pageAndSorting.getLimit() + 1;
        return siteDeviceWarehouseRepository.findAll(Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));
    }

    public PageResponse<SiteDeviceWarehouseDTO> listWarehousesDTO(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String rankOrder, String locale) {
        Page<SiteDeviceWarehouse> result = this.listWarehouses(pageAndSorting, filterQuery, rankOrder, locale);

        return PageResponse
                .from(siteDeviceWarehouseMapper.toDTO(result.getContent(), locale),
                        pageAndSorting, () -> Long.valueOf(result.getSize()));
    }

    public SiteDeviceWarehouse getWarehouseByCode(@NotEmpty String code, String rankOrder, String locale) {
        return siteDeviceWarehouseRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No Entity found for code.");
            return new EntityNotFoundException("site_ms_ws.site_device_service.site_device_warehouse_code_not_found", "No Entity found for code.");
        });
    }

    public SiteDeviceWarehouse getWarehouseByCode(@NotEmpty String code) {
        return siteDeviceWarehouseRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No Entity found for code.");
            return new EntityNotFoundException("site_ms_ws.site_device_service.site_device_warehouse_code_not_found", "No Entity found for code.");
        });
    }

    public SiteDeviceWarehouse getWarehouseDetails(@NotBlank String code, String rankOrder, String locale) {
        LOG.debug("get Warehouse Details");
        SiteDeviceWarehouse entity = getWarehouseByCode(code, rankOrder, locale);
        LOG.debug("Found Warehouse with code {}: {}", code, entity);
        return entity;
    }

    public SiteDeviceWarehouseDTO getWarehouseDetailsDTO(@NotBlank String code, String rankOrder, String locale) {
        return siteDeviceWarehouseMapper.toDTO(getWarehouseDetails(code, rankOrder, locale), locale);
    }

    @MqttPublish(operationType = MessageOperationEnum.CREATED, topicName = "pcs/document-ms/siteDevice/warehouse")
    protected SiteDeviceWarehouseDTO propagateCreatedToMqtt(SiteDeviceWarehouse warehouse) {
        return siteDeviceWarehouseMapper.toDTO(warehouse, null);
    }

    @MqttPublish(operationType = MessageOperationEnum.UPDATED, topicName = "pcs/document-ms/siteDevice/warehouse")
    protected SiteDeviceWarehouseDTO propagateUpdateToMqtt(SiteDeviceWarehouse warehouse) {
        return siteDeviceWarehouseMapper.toDTO(warehouse, null);
    }

    public SiteDeviceWarehouseDTO createWarehouseDTO(SiteDeviceWarehouseDTO dto, String rankOrder, String locale) {

        if (dto.getCode() != null && !dto.getCode().isEmpty()) {
            siteDeviceWarehouseRepository.findByCode(dto.getCode()).ifPresent(e -> {
                throw new EntityAlreadyExistsException("site_ms_ws.site_device_service.site_device_warehouse_code_already_exists", "Device with code " + dto.getCode() + " already exists.");
            });
        }

        SiteDeviceWarehouse entity = siteDeviceWarehouseMapper.fromDTO(dto, locale);
        try {
            if (dto.getWarehouseCode() != null) {
                siteDeviceWarehouseRepository.findBySiteCodeAndWarehouseCode(dto.getSiteCode(), String.valueOf(dto.getWarehouseCode())).ifPresent(e -> {
                    throw new InvalidDataException("site_ms_ws.site_device_service.site_device_warehouse_number_already_exists", "Fcc Warehouse with code " + dto.getWarehouseCode() + " already exists in the site " + dto.getSiteCode() + ".");
                });
            }

            if (dto.getWarehouseTypeCode().equals("WET") && Objects.isNull(dto.getProductCode())) {
                throw new InvalidDataException("site_ms_ws.site_device_service.site_device_warehouse_product_is_required", "Product Code is required");
            }

            if (dto.isDefaultWarehouse()) {
                siteDeviceWarehouseRepository.findAll().forEach(p -> {
                    p.setDefaultWarehouse(false);
                    siteDeviceWarehouseRepository.save(p);
                });
            }

            entity.setSite(siteService.getSiteByCode(dto.getSiteCode()));
            if (dto.getProductCode() != null) {
                entity.setProduct(productService.getByCode(dto.getProductCode(), rankOrder, locale));
            }
            entity.setWarehouseType(getWarehouseTypeByCode(dto.getWarehouseTypeCode(), rankOrder, locale));

        } catch (EntityNotFoundException exception) {
            throw new InvalidDataException("site_ms_ws.site_device_service.site_device_fcc_invalid_data", "Invalid Warehouse Data received. Error code :" + exception.getSourceCode());
        }

        SiteDeviceWarehouse deviceWarehouse = this.createWarehouse(entity, rankOrder, locale);
        propertyService.setProperties(deviceWarehouse, dto);
        categoryService.setCategories(deviceWarehouse, dto, rankOrder);

        propagateCreatedToMqtt(deviceWarehouse);

        return siteDeviceWarehouseMapper.toDTO(deviceWarehouse, locale);
    }

    protected SiteDeviceWarehouse createWarehouse(SiteDeviceWarehouse entity, String rankOrder, String locale) {
        LOG.debug("create Warehouse");
        entity.setDeviceType(siteDeviceService.getSiteDeviceTypeByCode("WAREHOUSE"));

        if (entity.getCode() != null && !entity.getCode().isEmpty()) {
            siteDeviceRepository.findByCode(entity.getCode()).ifPresent(siteDevice -> {
                throw new EntityAlreadyExistsException("site_ms_ws.site_device_service.site_device_warehouse_code_already_exists", "Device with code " + entity.getCode() + " already exists.");
            });
        } else {
            entity.setCode(UUID.randomUUID().toString());
        }

        try {
            /*This will validate if FccWarehouseCode is a number*/
            Short.valueOf(entity.getWarehouseCode());
        } catch (NumberFormatException e) {
            throw new InvalidDataException("site_ms_ws.site_device_service.site_device_fcc_invalid_data", "Invalid Warehouse Data received. Error code :" + e.getLocalizedMessage());
        }

        SiteDeviceWarehouse savedEntity = siteDeviceWarehouseRepository.save(entity);
        siteDeviceWarehouseRepository.getEntityManager().flush();
        siteDeviceWarehouseRepository.getEntityManager().refresh(savedEntity);
        LOG.debug("Successfully created Warehouse to {}", savedEntity);
        return savedEntity;
    }

    public SiteDeviceWarehouseDTO updateWarehouseDTO(@NotBlank String code, @Valid SiteDeviceWarehouseDTO dto, String rankOrder, String locale) {
        SiteDeviceWarehouse entity = getWarehouseByCode(code, rankOrder, locale);
        siteDeviceWarehouseMapper.updateEntity(entity, dto, locale);

        try {

            if (!dto.getSiteCode().equals(entity.getSite().getCode())) {
                entity.setSite(siteService.getSiteByCode(dto.getSiteCode()));
            }

            if (dto.getProductCode() != null) {
                if (entity.getProduct() == null || !dto.getProductCode().equals(entity.getProduct().getCode())) {
                    entity.setProduct(productService.getByCode(dto.getProductCode(), rankOrder, locale));
                }
            }

            if (dto.getWarehouseCode() != null) {
                siteDeviceWarehouseRepository.findBySiteCodeAndWarehouseCode(dto.getSiteCode(), String.valueOf(dto.getWarehouseCode())).ifPresent(e -> {
                    if (e == entity) return;
                    throw new InvalidDataException("site_ms_ws.site_device_service.site_device_warehouse_number_already_exists", "Fcc Warehouse with code " + dto.getWarehouseCode() + " already exists in the site " + dto.getSiteCode() + ".");
                });
            }

            if (dto.isDefaultWarehouse()) {
                siteDeviceWarehouseRepository.findAll().forEach(p -> {
                    p.setDefaultWarehouse(false);
                    siteDeviceWarehouseRepository.save(p);
                });
            }

            entity.setEnabled(dto.isEnabled());
            entity.setDefaultWarehouse(dto.isDefaultWarehouse());
            propertyService.setProperties(entity, dto);
            categoryService.setCategories(entity, dto, rankOrder);

        } catch (EntityNotFoundException exception) {
            throw new InvalidDataException("site_ms_ws.site_device_service.site_device_fcc_invalid_data", "Invalid Warehouse Data received. Error code :" + exception.getSourceCode());
        }

        SiteDeviceWarehouse result = updateWarehouse(code, entity, rankOrder, locale);
        propagateUpdateToMqtt(result);

        return siteDeviceWarehouseMapper.toDTO(result, locale);
    }

    public SiteDeviceWarehouse updateWarehouse(@NotBlank String code, SiteDeviceWarehouse entity, String rankOrder, String locale) {
        LOG.debug("update Warehouse");
        SiteDeviceWarehouse savedEntity = siteDeviceWarehouseRepository.update(entity);
        LOG.debug("Successfully updated Warehouse to {}", savedEntity);
        return savedEntity;
    }


}
