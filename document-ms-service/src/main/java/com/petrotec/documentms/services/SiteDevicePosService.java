package com.petrotec.documentms.services;

import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.api.queue.MessageOperationEnum;
import com.petrotec.queue.annotations.MqttPublish;
import com.petrotec.queue.annotations.MqttPublisher;
import com.petrotec.service.exceptions.EntityAlreadyExistsException;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.service.exceptions.InvalidDataException;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.siteDevice.SiteDevicePosDTO;
import com.petrotec.documentms.dtos.siteDevice.VirtualPosDTO;
import com.petrotec.documentms.entities.SiteDeviceFuelPoint;
import com.petrotec.documentms.entities.SiteDevicePos;
import com.petrotec.documentms.entities.SiteDeviceType;
import com.petrotec.documentms.entities.VirtualPos;
import com.petrotec.documentms.mappers.devices.SiteDevicePosMapper;
import com.petrotec.documentms.mappers.devices.VirtualPosMapper;
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
import java.util.*;
import java.util.stream.Collectors;


@Singleton
@MqttPublisher(topicName = "pcs/document-ms/siteDevice")
@Transactional
public class SiteDevicePosService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteDevicePosService.class);

    private final SiteDeviceRepository siteDeviceRepository;
    private final SiteDevicePosRepository siteDevicePosRepository;
    private final SiteDevicePosMapper siteDevicePosMapper;
    private final SiteDeviceTypeRepository siteDeviceTypeRepository;
    private final SiteDeviceSubtypeRepository siteDeviceSubtypeRepository;
    private final VirtualPosMapper virtualPosMapper;
    private final SiteService siteService;
    private final CommunicationMethodService communicationMethodService;
    private final SiteDeviceFuelPointRepository siteDeviceFuelPointRepository;
    private final VirtualPosRepository virtualPosRepository;


    public SiteDevicePosService(SiteDeviceRepository siteDeviceRepository,
                                SiteDevicePosRepository siteDevicePosRepository, SiteDevicePosMapper siteDevicePosMapper,
                                SiteService siteService,
                                SiteDeviceTypeRepository siteDeviceTypeRepository,
                                SiteDeviceSubtypeRepository siteDeviceSubtypeRepository, VirtualPosMapper virtualPosMapper,
                                CommunicationMethodService communicationMethodService, SiteDeviceFuelPointRepository siteDeviceFuelPointRepository, VirtualPosRepository virtualPosRepository) {
        this.siteDeviceRepository = siteDeviceRepository;
        this.siteDevicePosRepository = siteDevicePosRepository;
        this.siteDevicePosMapper = siteDevicePosMapper;
        this.siteService = siteService;
        this.siteDeviceTypeRepository = siteDeviceTypeRepository;
        this.siteDeviceSubtypeRepository = siteDeviceSubtypeRepository;
        this.virtualPosMapper = virtualPosMapper;
        this.communicationMethodService = communicationMethodService;
        this.siteDeviceFuelPointRepository = siteDeviceFuelPointRepository;
        this.virtualPosRepository = virtualPosRepository;
    }

    public SiteDeviceType getSiteDeviceTypeByCode(@NotEmpty String code, String rankOrder, String locale) {
        return siteDeviceTypeRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No siteDeviceType found for code.");
            return new EntityNotFoundException("site_ms_ws.site_device_service_pos.site_device_type_code_not_found", "No siteDeviceType found for code." + code);
        });
    }

    public PageResponse<SiteDevicePosDTO> listPos(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        Page<SiteDevicePos> result = siteDevicePosRepository.findAll(Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));

        PageResponse<SiteDevicePosDTO> pageResult = PageResponse
                .from(siteDevicePosMapper.toDTO(result.getContent(), locale),
                        pageAndSorting, () -> Long.valueOf(result.getSize()));

        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);
        return pageResult;
    }

    public PageResponse<SiteDevicePosDTO> listPosBySite(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String siteCode) {
        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        Page<SiteDevicePos> result = siteDevicePosRepository.findBySiteCode(siteCode, Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));

        return PageResponse
                .from(siteDevicePosMapper.toDTO(result.getContent(), locale),
                        pageAndSorting, () -> Long.valueOf(result.getSize()));
    }

    public SiteDevicePos getPosByCode(@NotEmpty String code, String rankOrder, String locale) {
        return siteDevicePosRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No Entity found for code.");
            return new EntityNotFoundException("site_ms_ws.site_device_service_pos.site_device_pos_code_not_found", "No POS Device Entity found for code:" + code);
        });
    }

    public SiteDevicePos getPosDetails(@NotBlank String code, String rankOrder, String locale) {
        LOG.debug("get ProductGroup Details");
        SiteDevicePos entity = getPosByCode(code, rankOrder, locale);
        LOG.debug("Found ProductGroup with code {}: {}", code, entity);
        return entity;
    }

    public SiteDevicePosDTO getPosDetailsDTO(@NotBlank String code, String rankOrder, String locale) {
        return siteDevicePosMapper.toDTO(getPosDetails(code, rankOrder, locale), locale);
    }

    @MqttPublish(operationType = MessageOperationEnum.CREATED, topicName = "pcs/document-ms/siteDevice/pos")
    protected SiteDevicePosDTO propagateCreatedToMqtt(SiteDevicePos siteDevicePos) {
        return siteDevicePosMapper.toDTO(siteDevicePos, null);
    }

    @MqttPublish(operationType = MessageOperationEnum.UPDATED, topicName = "pcs/document-ms/siteDevice/pos")
    protected SiteDevicePosDTO propagateUpdatedToMqtt(SiteDevicePos siteDevicePos) {
        return siteDevicePosMapper.toDTO(siteDevicePos, null);
    }

    protected void validUniquePosNumber(String siteCode, Integer posNumber) {
        siteDevicePosRepository.findBySiteCodeAndNumber(siteCode, posNumber).ifPresent(e -> {
            throw new InvalidDataException("site_ms_ws.site_device_service_pos.site_device_pos_number_already_exists", "Pos with number " + posNumber + " already exists in the site " + siteCode + ".");
        });
    }

    protected void validUniqueVirtualPosNumber(String siteCode, Integer posNumber) {
        virtualPosRepository.findByNumberAndSiteDevicePosSiteCode(posNumber, siteCode).ifPresent(e -> {
            throw new InvalidDataException("site_ms_ws.site_device_service_pos.site_device_virtual_pos_number_already_exists", "Virtual Pos with number " + posNumber + " already exists in the site " + siteCode + ".");
        });
    }

    public SiteDevicePosDTO createPosDTO(SiteDevicePosDTO dto, String rankOrder, String locale) {
        SiteDevicePos siteDevicePos = siteDevicePosMapper.fromDTO(dto, locale);
        try {
            if (dto.getPosNumber() != null)
                validUniquePosNumber(dto.getSiteCode(), dto.getPosNumber());

            populate(dto, rankOrder, locale, siteDevicePos);

        } catch (EntityNotFoundException exception) {
            throw new InvalidDataException("site_ms_ws.site_device_service_pos.site_device_pos_invalid_data", "Invalid POS Data received. Error code :" + exception.getSourceCode());
        }

        SiteDevicePos updatedEntity = this.createPos(siteDevicePos, rankOrder, locale);

        propagateCreatedToMqtt(updatedEntity);
        return siteDevicePosMapper.toDTO(updatedEntity, locale);
    }

    protected SiteDevicePos createPos(SiteDevicePos entity, String rankOrder, String locale) {
        LOG.debug("create pos");

        if (entity.getCode() != null && !entity.getCode().isEmpty()) {
            siteDeviceRepository.findByCode(entity.getCode()).ifPresent(siteDevice -> {
                throw new EntityAlreadyExistsException("site_ms_ws.site_device_service_pos.site_device_pos_code_already_exists", "Device with code " + entity.getCode() + " already exists.");
            });

        } else {
            entity.setCode(UUID.randomUUID().toString());
        }

        SiteDevicePos savedEntity = siteDevicePosRepository.save(entity);
        siteDevicePosRepository.getEntityManager().flush();
        siteDevicePosRepository.getEntityManager().refresh(savedEntity);
        LOG.debug("Successfully created pos to {}", savedEntity);
        return savedEntity;
    }

    public SiteDevicePosDTO updatePosDTO(@NotBlank String code, SiteDevicePosDTO dto, String rankOrder, String locale) {
        SiteDevicePos entity = getPosByCode(code, rankOrder, locale);
        siteDevicePosMapper.updateEntity(entity, dto, locale);

        if (dto.getPosNumber() != null && !dto.getPosNumber().equals(entity.getNumber()))
            validUniquePosNumber(dto.getSiteCode(), dto.getPosNumber());

        populate(dto, rankOrder, locale, entity);

        SiteDevicePos result = updatePos(code, entity, rankOrder, locale);

        propagateUpdatedToMqtt(result);
        return siteDevicePosMapper.toDTO(result, locale);
    }

    private void populate(SiteDevicePosDTO dto, String rankOrder, String locale, SiteDevicePos siteDevicePos) {
        if (siteDevicePos.getSite() == null)
            siteDevicePos.setSite(siteService.getSiteByCode(dto.getSiteCode()));

        siteDevicePos.setDeviceType(this.getSiteDeviceTypeByCode("POS", rankOrder, locale));

        siteDevicePos.setDeviceSubtype(siteDeviceSubtypeRepository.findByCodeAndSiteDeviceTypeCode(dto.getProtocolTypeCode(), "POS").orElseThrow(
                () -> new InvalidDataException("site_ms_ws.site_device_service_pos.site_device_sub_type_code_not_found", "POS with Sub Type code " + dto.getProtocolTypeCode() + " not found.")
        ));

        communicationMethodService.setDeviceCommunicationInterface(siteDevicePos, dto.getCommunicationMethodTypeCode(), dto.getCommunicationMethodData());

        Map<String, SiteDeviceFuelPoint> existingFuelPoint =
                siteDevicePos.getFuelPoints().stream().collect(Collectors.toMap(n -> n.getCode(), n -> n));

        dto.getConnectedFuelPoints().forEach(code -> {
            SiteDeviceFuelPoint siteDeviceFuelPoint = existingFuelPoint.get(code);
            if (siteDeviceFuelPoint != null) {
                existingFuelPoint.remove(code);
            } else {
                SiteDeviceFuelPoint fuelPoint = getFuelPointByCode(code);
                validateUniqueFuelPointBySite(fuelPoint, dto.getSiteCode());
                siteDevicePos.getFuelPoints().add(fuelPoint);
            }
        });
        siteDevicePos.getFuelPoints().removeAll(existingFuelPoint.values());


        Map<String, VirtualPos> existingVirtualPos =
                siteDevicePos.getVirtualPos().stream().collect(Collectors.toMap(n -> n.getCode(), n -> n));

        dto.getVirtualPos().forEach(vp -> {
            if (existingVirtualPos.get(vp.getCode()) != null) {
                populateVirtualPos(siteDevicePos, vp, dto.getSiteCode(), locale);
                existingVirtualPos.remove(vp.getCode());
            } else {
                VirtualPos virtualPos = populateVirtualPos(siteDevicePos, vp, dto.getSiteCode(), locale);
                siteDevicePos.getVirtualPos().add(virtualPos);
            }
        });

        siteDevicePos.getVirtualPos().removeAll(existingVirtualPos.values());
        for(VirtualPos vp: existingVirtualPos.values()){
            virtualPosRepository.delete(vp);
        }

    }

    public VirtualPos populateVirtualPos(SiteDevicePos siteDevicePos, VirtualPosDTO virtualDto, String siteCode, String locale) {
        VirtualPos virtualPos = virtualPosMapper.fromDTO(virtualDto, locale);
        if (!Optional.ofNullable(virtualDto.getCode()).isPresent()) {
            if (virtualDto.getPosNumber() != null)
                validUniqueVirtualPosNumber(siteCode, virtualDto.getPosNumber());
            virtualPos.setCode(UUID.randomUUID().toString());
            virtualPos.setFuelPoints(virtualDto.getConnectedPumps().stream().map(fuelPoint -> getFuelPointByCode(fuelPoint)).collect(Collectors.toList()));
        } else {
            virtualPos = getVirtualPosByCode(virtualDto.getCode());
            if (virtualDto.getPosNumber() != null && !virtualDto.getPosNumber().equals(virtualPos.getNumber()))
                validUniqueVirtualPosNumber(siteCode, virtualDto.getPosNumber());
            virtualPosMapper.updateEntity(virtualPos, virtualDto, locale);
            virtualPos.setFuelPoints(virtualDto.getConnectedPumps().stream().map(fuelPoint -> getFuelPointByCode(fuelPoint)).collect(Collectors.toList()));
        }

        validateUniqueFuelPointBySite(virtualPos.getFuelPoints(), siteCode);
        validateExistFuelPointInPos(siteDevicePos, virtualPos);
        virtualPos.setSiteDevicePos(siteDevicePos);
        return virtualPos;
    }

    public SiteDevicePos updatePos(@NotBlank String code, SiteDevicePos entity, String rankOrder, String locale) {
        LOG.debug("update pos");
        SiteDevicePos savedEntity = siteDevicePosRepository.update(entity);
        siteDevicePosRepository.getEntityManager().flush();
        siteDevicePosRepository.getEntityManager().refresh(savedEntity);
        LOG.debug("Successfully updated pos to {}", savedEntity);
        return savedEntity;
    }

    public SiteDeviceFuelPoint getFuelPointByCode(@NotEmpty String code) {
        return siteDeviceFuelPointRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No FuelPoint found for code: {}", code);
            return new InvalidDataException("site_ms_ws.site_device_service_pos.site_device_fuel_point_code_not_found", "No FuelPoint found for code:" + code);
        });
    }

    public VirtualPos getVirtualPosByCode(@NotEmpty String code) {
        return virtualPosRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No Virtual Pos found for code: {}", code);
            return new InvalidDataException("site_ms_ws.site_device_service_pos.virtual_pos_code_not_found", "No Virtual Pos found for code:" + code);
        });
    }

    private void validateExistFuelPointInPos(SiteDevicePos siteDevicePos, VirtualPos virtualPos) {
        virtualPos.getFuelPoints().forEach(v -> {
            if (!siteDevicePos.getFuelPoints().stream().map(fuelPoint -> fuelPoint.getCode()).collect(Collectors.toList()).contains(v.getCode())) {
                throw new InvalidDataException("site_ms_ws.site_device_service_pos.fuel_point_vp_code_different_site_code", "There is fuel point in virtual pos without");
            }
        });
    }

    private void validateUniqueFuelPointBySite(SiteDeviceFuelPoint fuelPoint, String siteCode) {
        if (!fuelPoint.getSite().getCode().equals(siteCode)) {
            throw new InvalidDataException("site_ms_ws.site_device_service_pos.fuel_point_code_different_site", "There is different site code");
        }
    }

    private void validateUniqueFuelPointBySite(List<SiteDeviceFuelPoint> fuelPoints, String siteCode) {
        fuelPoints.forEach(fuelPoint -> {
            if (!fuelPoint.getSite().getCode().equals(siteCode)) {
                throw new InvalidDataException("site_ms_ws.site_device_service_pos.fuel_point_code_different_site", "There is different site code");
            }
        });
    }

}
