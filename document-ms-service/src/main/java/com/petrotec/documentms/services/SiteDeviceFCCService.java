package com.petrotec.documentms.services;

import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.queue.annotations.MqttPublisher;
import com.petrotec.service.exceptions.EntityAlreadyExistsException;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.service.exceptions.InvalidDataException;
import com.petrotec.documentms.dtos.siteDevice.*;
import com.petrotec.documentms.entities.*;
import com.petrotec.documentms.mappers.devices.SimpleMapper;
import com.petrotec.documentms.mappers.devices.SiteDeviceFccMapper;
import com.petrotec.documentms.repositories.*;
import com.petrotec.documentms.repositories.configuration.FuellingModeRepository;
import com.petrotec.documentms.repositories.interfaces.ServiceModeRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.*;

import static com.petrotec.documentms.errors.EntityNotFoundMessages.SITE_DEVICE__FCC_CODE;

@Singleton
@MqttPublisher(topicName = "pcs/document-ms/siteDevice")
@Transactional
public class SiteDeviceFCCService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteDeviceFCCService.class);

    private final SiteDeviceFCCRepository siteDeviceFCCRepository;
    private final SiteDeviceFccMapper siteDeviceFccMapper;
    private final SimpleMapper simpleMapper;
    private final SiteService siteService;
    private final SiteDeviceTypeRepository siteDeviceTypeRepository;
    private final ServiceModeRepository serviceModeRepository;
    private final FuellingModeRepository fuellingModeRepository;
    private final SiteDeviceTankLevelGaugesRepository siteDeviceTankLevelGaugesRepository;
    private final SiteDeviceFuelPointRepository siteDeviceFuelPointRepository;
    private final SiteDevicePriceSignRepository siteDevicePriceSignRepository;
    private final SiteDeviceSubtypeRepository siteDeviceSubtypeRepository;
    private final SiteDevicePosRepository siteDevicePosRepository;
    private final CommunicationMethodRepository communicationMethodRepository;


    public SiteDeviceFCCService(SiteDeviceFCCRepository siteDeviceFCCRepository,
                                SiteDeviceFccMapper siteDeviceFccMapper, SiteService siteService,
                                SiteDeviceTypeRepository siteDeviceTypeRepository, ServiceModeRepository serviceModeRepository,
                                FuellingModeRepository fuellingModeRepository,
                                SiteDeviceTankLevelGaugesRepository siteDeviceTankLevelGaugesRepository, SiteDeviceFuelPointRepository siteDeviceFuelPointRepository,
                                SiteDevicePriceSignRepository siteDevicePriceSignRepository,
                                SiteDeviceSubtypeRepository siteDeviceSubtypeRepository, SiteDevicePosRepository siteDevicePosRepository,
                                CommunicationMethodRepository communicationMethodRepository, SimpleMapper simpleMapper) {
        this.siteDeviceFCCRepository = siteDeviceFCCRepository;
        this.siteDeviceFccMapper = siteDeviceFccMapper;
        this.siteService = siteService;
        this.siteDeviceTypeRepository = siteDeviceTypeRepository;
        this.serviceModeRepository = serviceModeRepository;
        this.fuellingModeRepository = fuellingModeRepository;
        this.siteDeviceTankLevelGaugesRepository = siteDeviceTankLevelGaugesRepository;
        this.siteDeviceFuelPointRepository = siteDeviceFuelPointRepository;
        this.siteDevicePriceSignRepository = siteDevicePriceSignRepository;
        this.siteDeviceSubtypeRepository = siteDeviceSubtypeRepository;
        this.siteDevicePosRepository = siteDevicePosRepository;
        this.communicationMethodRepository = communicationMethodRepository;
        this.simpleMapper = simpleMapper;
    }

    public PageResponse<SiteDeviceFccDTO> listFccDevices(PageAndSorting pageAndSorting, Filter filterQuery, String locale) {
        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);
        Page<SiteDeviceFCC> page = siteDeviceFCCRepository.findByDeviceTypeCode("FCC", Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));

        return PageResponse.from(toDTO(page.getContent(), locale), pageAndSorting, () -> Long.valueOf(page.getSize()));
    }

    public PageResponse<SiteDeviceFccDTO> listFccDevicesBySite(PageAndSorting pageAndSorting, Filter filterQuery, String siteCode, String locale) {
        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);

        Page<SiteDeviceFCC> page = siteDeviceFCCRepository.findBySiteCodeAndDeviceTypeCode(siteCode, "FCC", Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));

        return PageResponse.from(toDTO(page.getContent(), locale), pageAndSorting, () -> Long.valueOf(page.getSize()));
    }

    public SiteDeviceFccDTO createFccDeviceDTO(@Valid SiteDeviceFccDTO dto, String rankOrder, String locale) {
        SiteDeviceFCC entity = siteDeviceFccMapper.fromDTO(dto, locale);
        if (Objects.isNull(dto.getCode())) {
            entity.setCode(UUID.randomUUID().toString());
        }
        try {
            entity.setDeviceType(getSiteDeviceTypeByCode("FCC"));
            entity.setSite(siteService.getSiteByCode(dto.getSiteCode()));
            entity.setDeviceSubtype(this.getSiteDeviceSubtypeByCode(dto.getProtocolTypeCode()));
            if (dto.getCode() != null && !entity.getCode().isEmpty()) {
                siteDeviceFCCRepository.findByCode(dto.getCode()).ifPresent(e -> {
                    throw new EntityAlreadyExistsException("site_ms_ws.site_device_service.site_device_fcc_code_already_exists", "Device with code " + dto.getCode() + " already exists.");
                });
            }
            entity.setServiceMode(serviceModeRepository.findByCode(dto.getServiceModeCode()).orElseThrow(
                    () -> new EntityNotFoundException("site_ms_ws.site_device_service.service_mode_not_found", "Not found service mode code")));
            entity.setFuellingMode(fuellingModeRepository.findByCode(dto.getFuellingModeCode()).orElseThrow(
                    () -> new EntityNotFoundException("site_ms_ws.site_device_service.fuelling_mode_not_found", "Not found fuelling mode code")));
            entity.setCommunicationMethod(communicationMethodRepository.findByCode(dto.getCommunicationMethodTypeCode()).orElseThrow(
                    () -> new EntityNotFoundException("site_ms_ws.site_device_service_communication_method.code_not_found", "Communication Method code not found")
            ));

            SiteDeviceFCC saveEntity = siteDeviceFCCRepository.save(entity);

            List<SiteDeviceFuelPoint> fuelPointsList = new ArrayList<>();
            if (Objects.nonNull(dto.getConnectedFuelPoint())) {
                dto.getConnectedFuelPoint().forEach(p -> {
                    SiteDeviceFuelPoint fuelPoint = siteDeviceFuelPointRepository.findByCode(p).orElseThrow(
                            () -> new EntityNotFoundException("site_ms_ws.site_device_service.fuel_point_not_found",
                                    "Not found fuel point code"));
                    fuelPoint.setSiteDeviceFCC(saveEntity);
                    fuelPointsList.add(fuelPoint);
                });
                entity.setFuelPoints(fuelPointsList);
            }
            List<SiteDevicePriceSign> siteDevicePriceSignList = new ArrayList<>();
            if (Objects.nonNull(dto.getConnectedPriceSings())) {
                dto.getConnectedPriceSings().forEach(p -> {
                    SiteDevicePriceSign siteDevicePriceSign = siteDevicePriceSignRepository.findByCode(p)
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "site_ms_ws.site_device_service.price_sign_not_found",
                                    "Not found price sign code"));
                    siteDevicePriceSign.setSiteDeviceFCC(saveEntity);
                    siteDevicePriceSignList.add(siteDevicePriceSign);
                });
                entity.setGradePriceSigns(siteDevicePriceSignList);
            }
            List<SiteDeviceTankLevelGauges> siteDeviceTankLevelGaugesList = new ArrayList<>();
            if (Objects.nonNull(dto.getConnectedTLGs())) {
                dto.getConnectedTLGs().forEach(p -> {
                    SiteDeviceTankLevelGauges siteDeviceTankLevelGauges = siteDeviceTankLevelGaugesRepository.findByCode(
                            p).orElseThrow(() -> new EntityNotFoundException(
                            "site_ms_ws.site_device_service.tank_level_gauges_not_found",
                            "Not found tank level gauges code"));
                    siteDeviceTankLevelGauges.setSiteDeviceFCC(saveEntity);
                    siteDeviceTankLevelGaugesList.add(siteDeviceTankLevelGauges);
                });
                entity.setSiteDeviceTankLevelGauges(siteDeviceTankLevelGaugesList);
            }
            if (Objects.nonNull(dto.getConnectedPOs())) {
                Set<SiteDevicePos> siteDevicePosList = new HashSet<>();
                dto.getConnectedPOs().forEach(p -> {
                    siteDevicePosList.add(siteDevicePosRepository.findByCode(p).orElseThrow(
                            () -> new EntityNotFoundException("site_ms_ws.site_device_service.tank_level_gauges_not_found", "Not found tank level gauges code")));
                });
                entity.setSiteDevicePos(siteDevicePosList);
            }
        } catch (EntityNotFoundException exception) {
            throw new InvalidDataException("site_ms_ws.site_device_service.site_device_fcc_invalid_data", "Invalid Fcc Data received. Error code :" + exception.getSourceCode());
        }

        SiteDeviceFCC deviceFCC = this.createDevice(entity);
        return completeToDTO(deviceFCC, siteDeviceFccMapper.toDTO(deviceFCC, locale));
    }

    private SiteDeviceFccDTO completeToDTO(SiteDeviceFCC deviceFCC, SiteDeviceFccDTO siteDeviceFccDTO) {
        SimpleFuellingModeDTO simpleFuellingMode = simpleMapper.toSimpleFuellingModeDTO(
                fuellingModeRepository.findByCode(deviceFCC.getFuellingMode().getCode()).orElseThrow(
                        () -> new EntityNotFoundException("site_ms_ws.site_device_service.fuelling_mode_not_found",
                                "Not found fuelling mode code")));
        siteDeviceFccDTO.setFuellingModeCode(simpleFuellingMode.getCode());
        siteDeviceFccDTO.setFuellingMode(simpleFuellingMode);

        SimpleServiceModeDTO simpleServiceModeDTO = simpleMapper.toSimpleServiceModeDTO(
                serviceModeRepository.findByCode(deviceFCC.getServiceMode().getCode()).orElseThrow(
                        () -> new EntityNotFoundException("site_ms_ws.site_device_service.service_mode_not_found",
                                "Not found service mode code")));
        siteDeviceFccDTO.setServiceModeCode(simpleServiceModeDTO.getCode());
        siteDeviceFccDTO.setServiceMode(simpleServiceModeDTO);

        List<SimplePriceSignDTO> simplePriceSignDTOList = new ArrayList<>();
        List<String> connectedPriceSingsList = new ArrayList<>();
        if (Objects.nonNull(deviceFCC.getGradePriceSigns())) {
            deviceFCC.getGradePriceSigns().forEach(p -> {
                SimplePriceSignDTO simplePriceSignDTO = simpleMapper.toSimplePriceSignDTO(
                        siteDevicePriceSignRepository.findByCode(p.getCode()).orElseThrow(
                                () -> new EntityNotFoundException("site_ms_ws.site_device_service.price_sign_not_found",
                                        "Not found price sign code")));
                connectedPriceSingsList.add(simplePriceSignDTO.getCode());
                simplePriceSignDTOList.add(simplePriceSignDTO);
            });
        }
        siteDeviceFccDTO.setConnectedPriceSings(connectedPriceSingsList);
        siteDeviceFccDTO.setPriceSigns(simplePriceSignDTOList);

        List<SimpleSiteDeviceTlgDTO> simpleSiteDeviceTlgDTOList = new ArrayList<>();
        List<String> connectedSiteDeviceTlgDTOList = new ArrayList<>();
        if (Objects.nonNull(deviceFCC.getSiteDeviceTankLevelGauges())) {
            deviceFCC.getSiteDeviceTankLevelGauges().forEach(p -> {
                SimpleSiteDeviceTlgDTO simpleSiteDeviceTlgDTO = simpleMapper.toSimpleTlgDTO(
                        siteDeviceTankLevelGaugesRepository.findByCode(p.getCode()).orElseThrow(
                                () -> new EntityNotFoundException(
                                        "site_ms_ws.site_device_service.tank_level_gauges_not_found",
                                        "Not found tank level gauges code")));
                connectedSiteDeviceTlgDTOList.add(simpleSiteDeviceTlgDTO.getCode());
                simpleSiteDeviceTlgDTOList.add(simpleSiteDeviceTlgDTO);
            });
        }
        siteDeviceFccDTO.setConnectedTLGs(connectedSiteDeviceTlgDTOList);
        siteDeviceFccDTO.setTlgs(simpleSiteDeviceTlgDTOList);

        List<SimpleSiteDevicePosDTO> simpleSiteDevicePosDTOList = new ArrayList<>();
        List<String> connectedSiteDevicePosDTOList = new ArrayList<>();
        if (Objects.nonNull(deviceFCC.getSiteDevicePos())) {
            deviceFCC.getSiteDevicePos().forEach(p -> {
                SimpleSiteDevicePosDTO simpleSiteDevicePosDTO = simpleMapper.toSimpleSiteDevicePos(
                        siteDevicePosRepository.findByCode(p.getCode()).orElseThrow(
                                () -> new EntityNotFoundException("site_ms_ws.site_device_service.device_fcc_pos",
                                        "Not found site device fcc")));
                simpleSiteDevicePosDTOList.add(simpleSiteDevicePosDTO);
                connectedSiteDevicePosDTOList.add(simpleSiteDevicePosDTO.getCode());
            });
        }
        siteDeviceFccDTO.setPos(simpleSiteDevicePosDTOList);
        siteDeviceFccDTO.setConnectedPOs(connectedSiteDevicePosDTOList);
        return siteDeviceFccDTO;
    }

    public List<SiteDeviceFccDTO> toDTO(List<SiteDeviceFCC> entity, String locale) {
        if (entity == null) {
            return null;
        }

        List<SiteDeviceFccDTO> list = new ArrayList<SiteDeviceFccDTO>(entity.size());
        for (SiteDeviceFCC siteDeviceFCC : entity) {
            list.add(completeToDTO(siteDeviceFCC, siteDeviceFccMapper.toDTO(siteDeviceFCC, locale)));
        }

        return list;
    }

    public SiteDeviceFccDTO updateFccDTO(String code, SiteDeviceFccDTO dto, String rankOrder, String locale) {

        SiteDeviceFCC entity = null;
        try {
            entity = getDeviceByCode(code);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(SITE_DEVICE__FCC_CODE, "No Fcc Found for code " + code);
        }

        try {
            if (!entity.getDeviceType().getCode().equals("FCC")) {
                throw new EntityNotFoundException("site_ms_ws.site_device_service.site_device_fcc_code_not_found", "No Fcc Found for code " + code);
            }

            if (Objects.nonNull(dto) && Objects.nonNull(dto.getDescription()) && !dto.getDescription()
                    .equals(entity.getDescription())) {
                entity.setDescription(dto.getDescription());
            }

            if (Objects.nonNull(dto) && Objects.nonNull(dto.getAdditionalData()) && !dto.getAdditionalData()
                    .equals(entity.getAdditionalData())) {
                entity.setAdditionalData(dto.getAdditionalData());
            }

            if (Objects.nonNull(dto) && Objects.nonNull(dto.getSiteCode()) && !dto.getSiteCode()
                    .equals(entity.getSite().getCode())) {
                entity.setSite(siteService.getSiteByCode(dto.getSiteCode()));
            }

            if (Objects.nonNull(dto) && Objects.nonNull(dto.getProtocolTypeCode()) && !dto.getProtocolTypeCode()
                    .equals(entity.getDeviceType())) {
                entity.setDeviceSubtype(this.getSiteDeviceSubtypeByCode(dto.getProtocolTypeCode()));
            }

            if (Objects.nonNull(dto) && Objects.nonNull(dto.getCommunicationMethodTypeCode()) && !dto.getCommunicationMethodTypeCode()
                    .equals(entity.getCommunicationMethod().getCode())) {
                entity.setCommunicationMethod(communicationMethodRepository.findByCode(dto.getCommunicationMethodTypeCode())
                        .orElseThrow(() -> new EntityNotFoundException("communication_method.code_not_found", "Communication Method code not found")));
            }

            if (Objects.nonNull(dto) && !dto.getCommunicationMethodData()
                    .equals(entity.getCommunicationMethodData())) {
                Map<String, Object> map = dto.getCommunicationMethodData();
                if (map != null) {
                    entity.setCommunicationMethodData(new HashMap<String, Object>(map));
                }
            }

            if (Objects.nonNull(dto) && Objects.nonNull(dto.getServiceModeCode()) && !dto.getServiceModeCode()
                    .equals(entity.getServiceMode().getCode())) {
                entity.setServiceMode(serviceModeRepository.findByCode(dto.getServiceModeCode()).orElseThrow(
                        () -> new EntityNotFoundException("site_ms_ws.site_device_service.service_mode_not_found", "Not found service mode code " + dto.getServiceModeCode())));
            }

            if (Objects.nonNull(dto) && Objects.nonNull(dto.getFuellingModeCode()) && !dto.getFuellingModeCode()
                    .equals(entity.getFuellingMode().getCode())) {
                entity.setFuellingMode(fuellingModeRepository.findByCode(dto.getFuellingModeCode()).orElseThrow(
                        () -> new EntityNotFoundException("site_ms_ws.site_device_service.fuelling_mode_not_found", "Not found fuelling mode code " + dto.getFuellingModeCode())));
            }

            if (Objects.nonNull(dto) && Objects.nonNull(dto.getConnectedTLGs()) && Objects.nonNull(entity.getSiteDeviceTankLevelGauges())) {
                for (SiteDeviceTankLevelGauges siteTankLevelGauges : entity.getSiteDeviceTankLevelGauges()) {
                    siteTankLevelGauges.setSiteDeviceFCC(null);
                    siteDeviceTankLevelGaugesRepository.save(siteTankLevelGauges);
                }

                entity.setSiteDeviceTankLevelGauges(new ArrayList<>());
                for (String connectedTLG : dto.getConnectedTLGs()) {
                    SiteDeviceTankLevelGauges siteDeviceTankLevelGauges = siteDeviceTankLevelGaugesRepository.findByCode(connectedTLG)
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "site_ms_ws.site_device_service.tank_level_gauges_not_found",
                                    "Not found tank level gauges code"));
                    siteDeviceTankLevelGauges.setSiteDeviceFCC(entity);
                    entity.getSiteDeviceTankLevelGauges().add(siteDeviceTankLevelGauges);
                }
            }

            if (Objects.nonNull(dto) && Objects.nonNull(dto.getConnectedPriceSings()) && Objects.nonNull(entity.getGradePriceSigns())) {
                for (SiteDevicePriceSign gradePriceSign : entity.getGradePriceSigns()) {
                    gradePriceSign.setSiteDeviceFCC(null);
                    siteDevicePriceSignRepository.save(gradePriceSign);
                }
                entity.setGradePriceSigns(new ArrayList<>());
                for (String connectedPriceSing : dto.getConnectedPriceSings()) {
                    SiteDevicePriceSign siteDevicePriceSign = siteDevicePriceSignRepository.findByCode(connectedPriceSing)
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "site_ms_ws.site_device_service.price_sign_not_found",
                                    "Not found price sign code"));
                    siteDevicePriceSign.setSiteDeviceFCC(entity);
                    entity.getGradePriceSigns().add(siteDevicePriceSign);
                }
            }

            if (Objects.nonNull(dto) && Objects.nonNull(dto.getConnectedFuelPoint()) && Objects.nonNull(entity.getFuelPoints())) {
                for (SiteDeviceFuelPoint siteDeviceFuelPoint : entity.getFuelPoints()) {
                    siteDeviceFuelPoint.setSiteDeviceFCC(null);
                    siteDeviceFuelPointRepository.save(siteDeviceFuelPoint);
                }
                entity.setFuelPoints(new ArrayList<>());
                for (String connectedFuelPoint : dto.getConnectedFuelPoint()) {
                    SiteDeviceFuelPoint fuelPoint = siteDeviceFuelPointRepository.findByCode(connectedFuelPoint).orElseThrow(
                            () -> new EntityNotFoundException("site_ms_ws.site_device_service.fuel_point_not_found",
                                    "Not found fuel point code"));
                    fuelPoint.setSiteDeviceFCC(entity);
                    entity.getFuelPoints().add(fuelPoint);
                }
            }

            if (Objects.nonNull(dto) && Objects.nonNull(dto.getConnectedPOs()) && Objects.nonNull(entity.getSiteDevicePos())) {
                Set<SiteDevicePos> siteDevicePosList = new HashSet<>();
                dto.getConnectedPOs().forEach(p -> {
                    siteDevicePosList.add(siteDevicePosRepository.findByCode(p).orElseThrow(
                            () -> new EntityNotFoundException("site_ms_ws.site_device_service.device_fcc_pos", "Not found site device fcc")));
                });
                entity.setSiteDevicePos(siteDevicePosList);
            }
            entity.setEnabled(dto.isEnabled());

        } catch (EntityNotFoundException exception) {
            throw new InvalidDataException("site_ms_ws.site_device_service.site_device_fcc_invalid_data", "Invalid FCC Data received. Error code :" + exception.getSourceCode());
        }

        entity.setDeviceSubtype(getSiteDeviceSubtypeByCode(dto.getProtocolTypeCode()));

        SiteDeviceFCC deviceFCC = updateDevice(entity);
        return completeToDTO(deviceFCC, siteDeviceFccMapper.toDTO(deviceFCC, locale));
    }

    public SiteDeviceFccDTO getFccDevice(String code, String locale) {
        SiteDeviceFCC entity = null;
        try {
            entity = getDeviceByCode(code);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("site_ms_ws.site_device_service.site_device_fcc_code_not_found", "No Fcc Found for code " + code);
        }

        if (!entity.getDeviceType().getCode().equals("FCC")) {
            throw new EntityNotFoundException("site_ms_ws.site_device_service.site_device_fcc_code_not_found", "No Fcc Found for code " + code);
        }
        SiteDeviceFccDTO siteDeviceFccDTO = siteDeviceFccMapper.toDTO(entity, locale);
        completeToDTO(entity, siteDeviceFccDTO);
        return siteDeviceFccDTO;
    }

    public SiteDeviceType getSiteDeviceTypeByCode(@NotEmpty String code) {
        return siteDeviceTypeRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No siteDeviceType found for code.");
            return new EntityNotFoundException("site_ms_ws.site_device_service.site_device_type_code_not_found", "No siteDeviceType found for code." + code);
        });
    }

    public SiteDeviceSubtype getSiteDeviceSubtypeByCode(@NotEmpty String code) {
        return siteDeviceSubtypeRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No SiteDeviceSubtype found for code.");
            return new EntityNotFoundException("site_ms_ws.site_device_service.site_device_type_code_not_found", "No SiteDeviceSubtype found for code:" + code);
        });
    }

    public SiteDeviceFCC createDevice(SiteDeviceFCC entity) {
        LOG.debug("create Device of type " + entity.getDeviceType().getCode());

        SiteDeviceFCC savedEntity = siteDeviceFCCRepository.saveAndFlush(entity);
        LOG.debug("Successfully created Device to {}", savedEntity);
        return savedEntity;
    }

    public SiteDeviceFCC updateDevice(SiteDeviceFCC entity) {
        LOG.debug("update Device of type " + entity.getDeviceType().getCode());
        SiteDeviceFCC savedEntity = siteDeviceFCCRepository.update(entity);
        LOG.debug("Successfully updated Device to {}", savedEntity);
        return savedEntity;
    }

    public SiteDeviceFCC getDeviceByCode(@NotEmpty String code) {
        return siteDeviceFCCRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No Entity found for code.");
            return new EntityNotFoundException("site_ms_ws.site_device_service.site_device_code_not_found", "No Entity found for code." + code);
        });
    }
}
