package com.petrotec.documentms.services;

import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.api.queue.MessageOperationEnum;
import com.petrotec.queue.annotations.MqttPublish;
import com.petrotec.queue.annotations.MqttPublisher;
import com.petrotec.service.exceptions.EntityAlreadyExistsException;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.service.exceptions.InvalidDataException;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceDispenserDTO;
import com.petrotec.documentms.entities.SiteDeviceDispenser;
import com.petrotec.documentms.entities.SiteDeviceFuelPoint;
import com.petrotec.documentms.mappers.devices.SiteDeviceDispenserMapper;
import com.petrotec.documentms.repositories.SiteDeviceDispenserRepository;
import com.petrotec.documentms.repositories.SiteDeviceFuelPointRepository;
import com.petrotec.documentms.repositories.SiteDeviceRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@MqttPublisher(topicName = "pcs/document-ms/siteDevice")
@Transactional
public class SiteDeviceDispenserService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteDeviceDispenserService.class);

    private final SiteDeviceRepository siteDeviceRepository;
    private final SiteDeviceDispenserRepository siteDeviceDispenserRepository;
    private final SiteDeviceDispenserMapper siteDeviceDispenserMapper;
    private final SiteService siteService;
    private final SiteDeviceService siteDeviceService;
    private final SiteDeviceFuelPointRepository siteDeviceFuelPointRepository;

    public SiteDeviceDispenserService(SiteDeviceRepository siteDeviceRepository, SiteDeviceDispenserRepository siteDeviceDispenserRepository, SiteDeviceDispenserMapper siteDeviceDispenserMapper, SiteService siteService, SiteDeviceService siteDeviceService, SiteDeviceFuelPointRepository siteDeviceFuelPointRepository) {
        this.siteDeviceRepository = siteDeviceRepository;
        this.siteDeviceDispenserRepository = siteDeviceDispenserRepository;
        this.siteDeviceDispenserMapper = siteDeviceDispenserMapper;
        this.siteService = siteService;
        this.siteDeviceService = siteDeviceService;
        this.siteDeviceFuelPointRepository = siteDeviceFuelPointRepository;
    }


    public PageResponse<SiteDeviceDispenserDTO> listDispensersBySite(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String siteCode, String locale) {
        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);
        Page<SiteDeviceDispenser> result = siteDeviceDispenserRepository.findBySiteCode(siteCode, Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));

        PageResponse<SiteDeviceDispenserDTO> pageResult = PageResponse
                .from(siteDeviceDispenserMapper.toDTO(result.getContent(), locale),
                        pageAndSorting, () -> Long.valueOf(result.getSize()));

        return pageResult;
    }

    public PageResponse<SiteDeviceDispenserDTO> listDispensers(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String locale) {
        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);
        Page<SiteDeviceDispenser> result = siteDeviceDispenserRepository.findAll(Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));

        PageResponse<SiteDeviceDispenserDTO> pageResult = PageResponse
                .from(siteDeviceDispenserMapper.toDTO(result.getContent(), locale),
                        pageAndSorting, () -> Long.valueOf(result.getSize()));

        return pageResult;
    }

    public SiteDeviceDispenser getDispenserByCode(@NotEmpty String code) {
        return siteDeviceDispenserRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No Entity found for code.");
            return new EntityNotFoundException("site_ms_ws.site_device_dispenser_service.site_device_dispenser_code_not_found", "No Entity found for code:" + code);
        });
    }

    public SiteDeviceDispenser getDispenserDetails(@NotBlank String code) {
        LOG.debug("get Dispenser Details");
        SiteDeviceDispenser entity = getDispenserByCode(code);
        LOG.debug("Found Dispenser with code {}: {}", code, entity);
        return entity;
    }

    public SiteDeviceDispenserDTO getDispenserDetailsDTO(@NotBlank String code, String rankOrder, String locale) {
        return siteDeviceDispenserMapper.toDTO(getDispenserDetails(code), locale);
    }


    @MqttPublish(operationType = MessageOperationEnum.CREATED, topicName = "pcs/document-ms/siteDevice/dispenser")
    protected SiteDeviceDispenserDTO propagateCreatedToMqtt(SiteDeviceDispenser dispenser) {
        return siteDeviceDispenserMapper.toDTO(dispenser, null);
    }

    @MqttPublish(operationType = MessageOperationEnum.UPDATED, topicName = "pcs/document-ms/siteDevice/dispenser")
    protected SiteDeviceDispenserDTO propagateUpdatedToMqtt(SiteDeviceDispenser dispenser) {
        return siteDeviceDispenserMapper.toDTO(dispenser, null);
    }

    public SiteDeviceDispenserDTO createDispenserDTO(SiteDeviceDispenserDTO dto, String rankOrder, String locale) {

        SiteDeviceDispenser entity = siteDeviceDispenserMapper.fromDTO(dto, locale);
        entity.setDeviceType(siteDeviceService.getSiteDeviceTypeByCode("DISPENSER"));

        entity.setFuelPoints(dto.getFuelPointCodes().stream().map(fuelPoint -> getFuelPointByCode(fuelPoint)).collect(Collectors.toList()));
        populateDispenserData(dto, entity);

        SiteDeviceDispenser createdEntity = this.createDispenser(entity, rankOrder, locale);
        propagateCreatedToMqtt(createdEntity);
        return siteDeviceDispenserMapper.toDTO(createdEntity, locale);
    }

    public SiteDeviceDispenser createDispenser(SiteDeviceDispenser entity, String rankOrder, String locale) {
        LOG.debug("create Dispenser");


        if (entity.getCode() != null && !entity.getCode().isEmpty()) {
            siteDeviceRepository.findByCode(entity.getCode()).ifPresent(siteDevice -> {
                throw new EntityAlreadyExistsException("site_ms_ws.site_device_dispenser_service.site_device_dispenser_code_already_exists", "Device with code " + entity.getCode() + " already exists.");
            });
        } else {
            entity.setCode(UUID.randomUUID().toString());
        }

        SiteDeviceDispenser savedEntity = siteDeviceDispenserRepository.save(entity);
        siteDeviceDispenserRepository.getEntityManager().flush();
        siteDeviceDispenserRepository.getEntityManager().refresh(savedEntity);
        LOG.debug("Successfully created Dispenser to {}", savedEntity);
        return savedEntity;
    }

    public SiteDeviceDispenserDTO updateDispenserDTO(@NotBlank String code, SiteDeviceDispenserDTO dto, String rankOrder, String locale) {
        SiteDeviceDispenser entity = getDispenserByCode(code);
        siteDeviceDispenserMapper.updateEntity(entity, dto, locale);

        Map<String, SiteDeviceFuelPoint> existingNozzles =
                entity.getFuelPoints().stream().collect(Collectors.toMap(n -> n.getCode(), n -> n));

        dto.getFuelPointCodes().forEach(nozzle -> {
            SiteDeviceFuelPoint matchNozzle = existingNozzles.get(nozzle);
            if (matchNozzle != null) {
                existingNozzles.remove(nozzle);
            } else {
                SiteDeviceFuelPoint newNozzle = getFuelPointByCode(nozzle);
                entity.getFuelPoints().add(newNozzle);
            }
        });
        existingNozzles.values().forEach(fp -> fp.setDispenser(null));
        entity.getFuelPoints().removeAll(existingNozzles.values());
        populateDispenserData(dto, entity);

        SiteDeviceDispenser result = updateDispenser(code, entity, rankOrder, locale);
        return siteDeviceDispenserMapper.toDTO(result, locale);
    }

    private void populateDispenserData(SiteDeviceDispenserDTO dto, SiteDeviceDispenser entity) {
        try {
            if (entity.getSite() == null)
                entity.setSite(siteService.getSiteByCode(dto.getSiteCode()));

            siteDeviceDispenserRepository.findBySiteCodeAndDispenserNumber(dto.getSiteCode(), dto.getDispenserNumber()).ifPresent(e -> {
                if (e != entity)
                    throw new InvalidDataException("site_ms_ws.site_device_dispenser_service.site_device_dispenser_number_code_already_exists", "Dispenser with number " + dto.getDispenserNumber() + " already exists in the site " + dto.getSiteCode() + ".");
            });

        } catch (EntityNotFoundException exception) {
            throw new InvalidDataException("site_ms_ws.site_device_dispenser_service.site_device_dispenser_invalid_data", "Invalid Dispenser Data received. Error code :" + exception.getSourceCode());
        }

        entity.getFuelPoints().forEach(siteDeviceFuelPoint -> {
            if (!siteDeviceFuelPoint.getSite().getCode().equals(entity.getSite().getCode())) {
                throw new InvalidDataException("site_ms_ws.site_device_dispenser_service.fuel_point_code_different_site_code", "The dispenser site code does not match the fuelpoint site code");
            }
            siteDeviceFuelPoint.setDispenser(entity);
        });
    }

    public SiteDeviceDispenser updateDispenser(@NotBlank String code, SiteDeviceDispenser entity, String rankOrder, String locale) {
        LOG.debug("update Dispenser");
        SiteDeviceDispenser savedEntity = siteDeviceDispenserRepository.update(entity);
        LOG.debug("Successfully updated Dispenser to {}", savedEntity);
        return savedEntity;
    }

    public SiteDeviceFuelPoint getFuelPointByCode(@NotEmpty String code) {
        return siteDeviceFuelPointRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No FuelPoint found for code: {}", code);
            return new InvalidDataException("site_ms_ws.site_device_dispenser_service.site_device_fuel_point_code_not_found", "No FuelPoint found for code:" + code);
        });
    }
}
