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
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceFuelPointDTO;
import com.petrotec.documentms.entities.SiteDeviceFuelPoint;
import com.petrotec.documentms.entities.SiteDeviceFuelPointNozzle;
import com.petrotec.documentms.entities.SiteGrade;
import com.petrotec.documentms.mappers.devices.SiteDeviceFuelPointMapper;
import com.petrotec.documentms.mappers.devices.SiteDeviceFuelPointNozzleMapper;
import com.petrotec.documentms.repositories.*;
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
@MqttPublisher(topicName = "pcs/document-ms/siteDevice/fuelpoint")
@Transactional
public class SiteDeviceFuelPointService {
    private static final Logger LOG = LoggerFactory.getLogger(SiteDeviceFuelPointService.class);

    private final SiteDeviceRepository siteDeviceRepository;

    private final SiteDeviceFuelPointRepository siteDeviceFuelPointRepository;
    private final SiteDeviceFuelPointNozzleRepository siteDeviceFuelPointNozzleRepository;
    private final SiteDeviceFuelPointMapper siteDeviceFuelPointMapper;
    private final SiteDeviceFuelPointNozzleMapper siteDeviceFuelPointNozzleMapper;
    private final SiteService siteService;

    private final SiteDeviceService siteDeviceService;

    private final SiteDeviceSubtypeRepository siteDeviceSubtypeRepository;
    private final CommunicationMethodService communicationMethodService;


    public SiteDeviceFuelPointService(SiteDeviceRepository siteDeviceRepository, SiteDeviceFuelPointRepository siteDeviceFuelPointRepository, SiteDeviceFuelPointNozzleRepository siteDeviceFuelPointNozzleRepository, SiteDeviceFuelPointMapper siteDeviceFuelPointMapper, SiteDeviceFuelPointNozzleMapper siteDeviceFuelPointNozzleMapper, SiteService siteService, SiteDeviceService siteDeviceService, SiteDeviceSubtypeRepository siteDeviceSubtypeRepository, CommunicationMethodService communicationMethodService) {
        this.siteDeviceRepository = siteDeviceRepository;
        this.siteDeviceFuelPointRepository = siteDeviceFuelPointRepository;
        this.siteDeviceFuelPointNozzleRepository = siteDeviceFuelPointNozzleRepository;
        this.siteDeviceFuelPointMapper = siteDeviceFuelPointMapper;
        this.siteDeviceFuelPointNozzleMapper = siteDeviceFuelPointNozzleMapper;
        this.siteService = siteService;
        this.siteDeviceService = siteDeviceService;
        this.siteDeviceSubtypeRepository = siteDeviceSubtypeRepository;
        this.communicationMethodService = communicationMethodService;
    }


    /*******************************/
    /********** FuelPoints *********/
    /*******************************/

    public Page<SiteDeviceFuelPoint> listFuelPoints(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String rankOrder, String siteCode) {
        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);
        int limit = pageAndSorting.getLimit() + 1;
        return siteDeviceFuelPointRepository.findBySiteCode(siteCode, Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));
    }

    public PageResponse<SiteDeviceFuelPointDTO> listFuelPointsDTO(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String rankOrder, String locale, String siteCode) {
        Page<SiteDeviceFuelPoint> result = this.listFuelPoints(pageAndSorting, filterQuery, rankOrder, siteCode);
        return PageResponse
                .from(siteDeviceFuelPointMapper.toDTO(result.getContent(), locale),
                        pageAndSorting, () -> Long.valueOf(result.getSize()));
    }

    public Page<SiteDeviceFuelPoint> listFuelPoints(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String rankOrder) {
        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);
        int limit = pageAndSorting.getLimit() + 1;
        return siteDeviceFuelPointRepository.findAll(Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));
    }

    public PageResponse<SiteDeviceFuelPointDTO> listFuelPointsDTO(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String rankOrder, String locale) {
        Page<SiteDeviceFuelPoint> result = this.listFuelPoints(pageAndSorting, filterQuery, rankOrder);
        return PageResponse
                .from(siteDeviceFuelPointMapper.toDTO(result.getContent(), locale),
                        pageAndSorting, () -> Long.valueOf(result.getSize()));
    }

    public SiteDeviceFuelPoint getFuelPointByCode(@NotEmpty String code) {
        return siteDeviceFuelPointRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No FuelPoint found for code.");
            return new EntityNotFoundException("site_ms_ws.site_device_service.site_device_fuel_point_code_not_found", "No FuelPoint found for code:" + code);
        });
    }

    public SiteDeviceFuelPoint getFuelPointDetails(@NotBlank String code) {
        LOG.debug("get FuelPoint Details");
        SiteDeviceFuelPoint entity = getFuelPointByCode(code);
        LOG.debug("Found FuelPoint with code {}: {}", code, entity);
        return entity;
    }

    public SiteDeviceFuelPointDTO getFuelPointDetailsDTO(@NotBlank String code, String rankOrder, String locale) {
        return siteDeviceFuelPointMapper.toDTO(getFuelPointDetails(code), locale);
    }

    @MqttPublish(operationType = MessageOperationEnum.CREATED, topicName = "pcs/document-ms/siteDevice/fuelpoint")
    protected SiteDeviceFuelPointDTO publishCreateEntity(SiteDeviceFuelPoint fuelPoint) {
        return siteDeviceFuelPointMapper.toDTO(fuelPoint, null);
    }

    @MqttPublish(operationType = MessageOperationEnum.UPDATED, topicName = "pcs/document-ms/siteDevice/fuelpoint")
    protected SiteDeviceFuelPointDTO publishUpdatedEntity(SiteDeviceFuelPoint fuelPoint) {
        return siteDeviceFuelPointMapper.toDTO(fuelPoint, null);
    }

    protected void validUniquePumpNumber(String siteCode, Integer pumpNumber) {
        siteDeviceFuelPointRepository.findBySiteCodeAndPumpNumber(siteCode, pumpNumber).ifPresent(e -> {
            throw new InvalidDataException("site_ms_ws.site_device_service.site_device_fuel_point_pump_number_already_exists", "FuelPoint with pump number " + pumpNumber + " already exists in the site " + siteCode + ".");
        });
    }

    public SiteDeviceFuelPointDTO createFuelPointDTO(SiteDeviceFuelPointDTO dto, String rankOrder, String locale) {
        SiteDeviceFuelPoint entity;
        try {
            if (dto.getPumpNumber() != null) {
                validUniquePumpNumber(dto.getSiteCode(), dto.getPumpNumber());
            }

            entity = siteDeviceFuelPointMapper.fromDTO(dto, locale);

            entity.setDeviceType(siteDeviceService.getSiteDeviceTypeByCode("FUEL_POINT"));

            fillFuelPointData(dto, entity);

            entity.setNozzles(dto.getNozzles().stream().map(nozzleDto -> siteDeviceFuelPointNozzleMapper.fromDTO(nozzleDto, dto, locale)).collect(Collectors.toList()));
            entity.getNozzles().forEach(n -> n.setFuelPoint(entity));

        } catch (EntityNotFoundException exception) {
            throw new InvalidDataException("site_ms_ws.site_device_service.site_device_fuel_point_invalid_data", "Invalid Fuel Point Data received. Error code :" + exception.getSourceCode());
        }

        SiteDeviceFuelPoint createdEntity = this.createFuelPoint(entity, rankOrder, locale);

        publishCreateEntity(createdEntity);
        return siteDeviceFuelPointMapper.toDTO(createdEntity, locale);
    }

    private void fillFuelPointData(SiteDeviceFuelPointDTO dto, SiteDeviceFuelPoint entity) {
        entity.setSite(siteService.getSiteByCode(dto.getSiteCode()));
        entity.setDeviceSubtype(siteDeviceSubtypeRepository.findByCodeAndSiteDeviceTypeCode(dto.getProtocolTypeCode(),"FUEL_POINT").orElseThrow(
                () -> new InvalidDataException("site_ms_ws.fuel_point_service.pump_protocol_not_found", "FuelPoint with protocol code " + dto.getProtocolTypeCode() + " not found.")
        ));

        communicationMethodService.setDeviceCommunicationInterface(entity,dto.getCommunicationMethodTypeCode(),dto.getCommunicationMethodData());
    }


    public SiteDeviceFuelPoint createFuelPoint(SiteDeviceFuelPoint entity, String rankOrder, String locale) {
        LOG.debug("create FuelPoint");
        entity.setDeviceType(siteDeviceService.getSiteDeviceTypeByCode("FUEL_POINT"));

        if (entity.getCode() != null && !entity.getCode().isEmpty()) {
            siteDeviceRepository.findByCode(entity.getCode()).ifPresent(siteDevice -> {
                throw new EntityAlreadyExistsException("site_ms_ws.site_device_service.site_device_fuel_point_code_already_exists", "Device with code " + entity.getCode() + " already exists.");
            });
        } else {
            entity.setCode(UUID.randomUUID().toString());
        }

        SiteDeviceFuelPoint savedEntity = siteDeviceFuelPointRepository.save(entity);
        siteDeviceFuelPointRepository.getEntityManager().flush();
        siteDeviceFuelPointRepository.getEntityManager().refresh(savedEntity);
        LOG.debug("Successfully created FuelPoint to {}", savedEntity);
        return savedEntity;
    }

    public SiteDeviceFuelPointDTO updateFuelPointDTO(@NotBlank String code, SiteDeviceFuelPointDTO dto, String rankOrder, String locale) {
        SiteDeviceFuelPoint entity = getFuelPointByCode(code);
        try {
            if (dto.getPumpNumber() != null && !dto.getPumpNumber().equals(entity.getPumpNumber()))
                validUniquePumpNumber(dto.getSiteCode(), dto.getPumpNumber());

            siteDeviceFuelPointMapper.updateEntity(entity, dto, locale);


            Map<Integer, SiteDeviceFuelPointNozzle> existingNozzles =
                    entity.getNozzles().stream().collect(Collectors.toMap(n -> n.getNozzleNumber(), n -> n));

            dto.getNozzles().forEach(nozzle -> {
                SiteDeviceFuelPointNozzle matchNozzle = existingNozzles.get(nozzle.getNozzleNumber());
                if (matchNozzle != null) {
                    existingNozzles.remove(nozzle.getNozzleNumber());
                    siteDeviceFuelPointNozzleMapper.updateEntity(matchNozzle, nozzle, dto, locale);
                } else {
                    SiteDeviceFuelPointNozzle newNozzle = siteDeviceFuelPointNozzleMapper.fromDTO(nozzle, dto, locale);
                    newNozzle.setFuelPoint(entity);
                    entity.getNozzles().add(newNozzle);
                }
            });

            //Remove nozzles that were not sent
            entity.getNozzles().removeAll(existingNozzles.values());

            fillFuelPointData(dto, entity);

        } catch (EntityNotFoundException exception) {
            throw new InvalidDataException("site_ms_ws.site_device_service.site_device_fuel_point_invalid_data", "Invalid Fuel Point Data received. Error code :" + exception.getSourceCode());
        }
        SiteDeviceFuelPoint result = updateFuelPoint(code, entity, rankOrder, locale);
        publishUpdatedEntity(result);
        return siteDeviceFuelPointMapper.toDTO(result, locale);
    }

    public SiteDeviceFuelPoint updateFuelPoint(@NotBlank String code, SiteDeviceFuelPoint entity, String rankOrder, String locale) {
        LOG.debug("update FuelPoint");
        SiteDeviceFuelPoint savedEntity = siteDeviceFuelPointRepository.update(entity);
        LOG.debug("Successfully updated FuelPoint to {}", savedEntity);
        return savedEntity;
    }

    public boolean isSiteGradeAssociatedToAnyNozzle(SiteGrade siteGrade) {
        return siteDeviceFuelPointNozzleRepository.existsBySiteGrade(siteGrade);
    }
}
