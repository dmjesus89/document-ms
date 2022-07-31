package com.petrotec.documentms.services;

import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.exceptions.EntityAlreadyExistsException;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.service.exceptions.InvalidDataException;
import com.petrotec.service.spec.SpecificationFilter;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.siteDevice.SiteDevicePriceSignDTO;
import com.petrotec.documentms.entities.Grade;
import com.petrotec.documentms.entities.GradePriceSign;
import com.petrotec.documentms.entities.SiteDevicePriceSign;
import com.petrotec.documentms.mappers.devices.SiteDevicePriceSignMapper;
import com.petrotec.documentms.repositories.GradeRepository;
import com.petrotec.documentms.repositories.SiteDevicePriceSignRepository;
import com.petrotec.documentms.repositories.SiteDeviceRepository;
import com.petrotec.documentms.repositories.SiteDeviceSubtypeRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@Transactional
public class SiteDevicePriceSignService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteDevicePriceSignService.class);

    private final SiteDeviceRepository siteDeviceRepository;
    private final SiteDevicePriceSignRepository siteDevicePriceSignRepository;
    private final SiteDevicePriceSignMapper siteDevicePriceSignMapper;
    private final SiteService siteService;
    private final SiteDeviceService siteDeviceService;
    private final CommunicationMethodService communicationMethodService;
    private final SiteDeviceSubtypeRepository siteDeviceSubtypeRepository;
    private final SpecificationFilter<SiteDevicePriceSign> specificationFilter;
    private final GradeRepository gradeRepository;


    public SiteDevicePriceSignService(SiteDeviceRepository siteDeviceRepository, SiteDevicePriceSignRepository siteDevicePriceSignRepository, SiteDevicePriceSignMapper siteDevicePriceSignMapper, SiteService siteService, SiteDeviceService siteDeviceService, CommunicationMethodService communicationMethodService, SiteDeviceSubtypeRepository siteDeviceSubtypeRepository, SpecificationFilter<SiteDevicePriceSign> specificationFilter, GradeRepository gradeRepository) {
        this.siteDeviceRepository = siteDeviceRepository;
        this.siteDevicePriceSignRepository = siteDevicePriceSignRepository;
        this.siteDevicePriceSignMapper = siteDevicePriceSignMapper;
        this.siteService = siteService;
        this.siteDeviceService = siteDeviceService;
        this.communicationMethodService = communicationMethodService;
        this.siteDeviceSubtypeRepository = siteDeviceSubtypeRepository;
        this.specificationFilter = specificationFilter;
        this.gradeRepository = gradeRepository;
    }

    public PageResponse<SiteDevicePriceSignDTO> listPriceSign(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery) {
        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        return PageResponse.from(
                specificationFilter.findAll(filterQuery, SiteDevicePriceSign.class, pageAndSorting).stream().map(item -> siteDevicePriceSignMapper.toDTO(item,locale))
                        .collect(Collectors.toList()), pageAndSorting,
                () -> specificationFilter.size(filterQuery, SiteDevicePriceSign.class));
    }

    public PageResponse<SiteDevicePriceSignDTO>  listPriceSign(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery,String siteCode, String locale) {
        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);

        Page result = siteDevicePriceSignRepository.findBySiteCode(siteCode, Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));;
        return PageResponse
                .from(siteDevicePriceSignMapper.toDTO(result.getContent(), locale),
                        pageAndSorting, () -> Long.valueOf(result.getSize()));

    }

    public SiteDevicePriceSign getPriceSignByCode(@NotEmpty String code) {
        return siteDevicePriceSignRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No Entity found for code.");
            return new EntityNotFoundException("site_ms_ws.site_device_price_sign_service.site_device_price_sign_code_not_found", "No Entity found for code: " + code);
        });
    }

    public SiteDevicePriceSign getPriceSignDetails(@NotBlank String code) {
        LOG.debug("get Price Sign Details");
        SiteDevicePriceSign entity = getPriceSignByCode(code);
        LOG.debug("Found Price Sign with code {}: {}", code, entity);
        return entity;
    }

    public SiteDevicePriceSignDTO getPriceSignDetailsDTO(@NotBlank String code, String locale) {
        return siteDevicePriceSignMapper.toDTO(getPriceSignDetails(code), locale);
    }

    public SiteDevicePriceSignDTO createPriceSignDTO(SiteDevicePriceSignDTO dto, String locale) {
        SiteDevicePriceSign entity = siteDevicePriceSignMapper.fromDTO(dto, locale);
        entity.setDeviceType(siteDeviceService.getSiteDeviceTypeByCode("PRICE_SIGN"));

        populatePriceSignData(dto, entity);

        SiteDevicePriceSign createdEntity = this.createPriceSign(entity);
        return siteDevicePriceSignMapper.toDTO(createdEntity, locale);
    }

    public SiteDevicePriceSign createPriceSign(SiteDevicePriceSign entity) {
        LOG.debug("Create PriceSign");

        if (entity.getCode() != null && !entity.getCode().isEmpty()) {
            siteDeviceRepository.findByCode(entity.getCode()).ifPresent(siteDevice -> {
                throw new EntityAlreadyExistsException("site_ms_ws.site_device_price_sign_service.site_device_code_already_exists", "Device with code " + entity.getCode() + " already exists.");
            });
        } else {
            entity.setCode(UUID.randomUUID().toString());
        }

        SiteDevicePriceSign savedEntity = siteDevicePriceSignRepository.save(entity);
        siteDevicePriceSignRepository.getEntityManager().flush();
        siteDevicePriceSignRepository.getEntityManager().refresh(savedEntity);
        LOG.debug("Successfully created price sign to {}", savedEntity);
        return savedEntity;
    }

    public SiteDevicePriceSignDTO updatePriceSignDTO(@NotBlank String code, SiteDevicePriceSignDTO dto, String locale) {
        SiteDevicePriceSign entity = getPriceSignByCode(code);
        siteDevicePriceSignMapper.updateEntity(entity, dto, locale);
        List<GradePriceSign> grades = entity.getGrades().stream().collect(Collectors.toList());
        for (GradePriceSign grade : grades) {
            if (!dto.getGradeCodes().contains(grade.getGrade().getCode())) {
                entity.getGrades().remove(grade);
            } else {
                if (dto.getGradeCodes().contains(grade.getGrade().getCode())) {
                    dto.getGradeCodes().remove(grade.getGrade().getCode());
                }
            }
        }
        populatePriceSignData(dto, entity);

        SiteDevicePriceSign result = updatePriceSign(entity);
        return siteDevicePriceSignMapper.toDTO(result, locale);
    }

    private void populatePriceSignData(SiteDevicePriceSignDTO dto, SiteDevicePriceSign entity) {
        try {
            if (entity.getSite() == null)
                entity.setSite(siteService.getSiteByCode(dto.getSiteCode()));

            entity.setDeviceSubtype(siteDeviceSubtypeRepository.findByCodeAndSiteDeviceTypeCode(dto.getProtocolTypeCode(), "PRICE_SIGN").orElseThrow(
                    () -> new InvalidDataException("site_ms_ws.site_device_price_sign_service.site_device_price_not_found", "Price Sign with protocol code " + dto.getProtocolTypeCode() + " not found.")
            ));

            siteDevicePriceSignRepository.findBySiteCodeAndPriceSignCode(dto.getSiteCode(), dto.getPriceSignCode()).ifPresent(e -> {
                if (e != entity)
                    throw new InvalidDataException("site_ms_ws.site_device_price_sign_service.site_device_price_sign_code_already_exists", "Price Sign with code " + dto.getPriceSignCode() + " already exists in the site " + dto.getSiteCode() + ".");
            });

            if (!dto.getCommunicationMethodTypeCode().isEmpty()) {
                communicationMethodService.setDeviceCommunicationInterface(entity, dto.getCommunicationMethodTypeCode(), dto.getCommunicationMethodData());
            }


            int idx = 0;
            for (String gradeCode : dto.getGradeCodes()) {
                GradePriceSign newGradePriceSign = new GradePriceSign();
                newGradePriceSign.setGrade(getGradeByCode(gradeCode));
                newGradePriceSign.setSiteDevicePriceSign(entity);
                newGradePriceSign.setGradeOrder(++idx);
                entity.getGrades().add(newGradePriceSign);
            }

        } catch (EntityNotFoundException exception) {
            throw new InvalidDataException("site_ms_ws.site_device_price_sign_service.site_device_price_sign_invalid_data", "Invalid Price Sign Data received. Error code :" + exception.getSourceCode());
        }

    }

    public SiteDevicePriceSign updatePriceSign(SiteDevicePriceSign entity) {
        LOG.debug("update price sign");
        SiteDevicePriceSign savedEntity = siteDevicePriceSignRepository.update(entity);
        LOG.debug("Successfully updated price sign to {}", savedEntity);
        return savedEntity;
    }


    public Grade getGradeByCode(@NotEmpty String gradeCode) {
        return gradeRepository.findByCode(gradeCode).orElseThrow(() -> {
            LOG.debug("No Grade found for grade code.");
            return new InvalidDataException("site_ms_ws.site_device_price_sign_service.site_device_grade_code_not_found", "No Grade found for code: " + gradeCode);
        });
    }

}
