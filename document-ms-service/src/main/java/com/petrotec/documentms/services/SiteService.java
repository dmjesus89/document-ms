package com.petrotec.documentms.services;

import com.petrotec.api.*;
import com.petrotec.api.filter.Filter;
import com.petrotec.api.queue.MessageOperationEnum;
import com.petrotec.categories.services.CategoryService;
import com.petrotec.categories.services.PropertyService;
import com.petrotec.queue.annotations.MqttPublish;
import com.petrotec.queue.annotations.MqttPublisher;
import com.petrotec.service.exceptions.EntityAlreadyExistsException;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.site.SiteCreateDTO;
import com.petrotec.documentms.dtos.site.SiteCustomDTO;
import com.petrotec.documentms.dtos.site.SiteExtendedDTO;
import com.petrotec.documentms.dtos.site.SiteUpdateDTO;
import com.petrotec.documentms.entities.Region;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteProfile;
import com.petrotec.documentms.mappers.SiteMapper;
import com.petrotec.documentms.repositories.SiteRepository;
import io.micronaut.core.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.micronaut.http.HttpStatus.*;

/**
 * SiteService
 */
@Singleton
@Transactional
@MqttPublisher(topicName = "pcs/document-ms/site")
public class SiteService {
    private static final Logger LOG = LoggerFactory.getLogger(SiteService.class);

    private final SiteMapper siteMapper;
    private final SiteRepository siteRepository;
    private final PropertyService propertyService;
    private final CategoryService categoryService;
    private final RegionService regionService;
    private final SiteProfileService siteProfileService;

    public SiteService(final SiteRepository siteRepository,
                       final SiteMapper siteMapper,
                       PropertyService propertyService, CategoryService categoryService, RegionService regionService, SiteProfileService siteProfileService) {
        this.siteRepository = siteRepository;
        this.siteMapper = siteMapper;
        this.propertyService = propertyService;
        this.categoryService = categoryService;
        this.regionService = regionService;
        this.siteProfileService = siteProfileService;
    }


    public void validateUniqueSiteNumber(String siteNumber) {
        if (siteNumber == null) return;

        siteRepository.findBySiteNumber(siteNumber).ifPresent(
                t -> {
                    throw new EntityAlreadyExistsException("site_ms_ws.site.site_number_already_exists", "Can't create site because site with same site number " + siteNumber + " already exists");
                }
        );
    }

    public void validateUniqueSiteCode(String siteCode) {
        if (siteCode == null || siteCode.isEmpty()) return;

        siteRepository.findByCode(siteCode).ifPresent(s -> {
            throw new EntityAlreadyExistsException("site_ms_ws.site.site_code_already_exists", "Can't create site because site with same code " + siteCode + " already exists");
        });
    }

    @MqttPublish(operationType = MessageOperationEnum.CREATED)
    public SiteExtendedDTO createSite(SiteCreateDTO createSite, String locale, String entityCode, String rankOrder) {

        /*Validate if the site code is unique*/
        validateUniqueSiteCode(createSite.getCode());
        validateUniqueSiteNumber(createSite.getSiteNumber());

        /*Check if region is valid*/
        Region region = regionService.getEntityByCode(createSite.getRegionCode());

        /*Check if site profile is valid*/
        SiteProfile siteProfile = siteProfileService.getEntityByCode(createSite.getSiteProfileCode());

        /*Generate site entity*/
        Site site = siteMapper.fromCreateDTO(createSite, region, siteProfile, locale);
        propertyService.setProperties(site, createSite);
        categoryService.setCategories(site, createSite, rankOrder);

        site.setEntityCode(entityCode);
        Site savedSite = siteRepository.save(site);
        siteRepository.getEntityManager().flush();
        siteRepository.getEntityManager().refresh(site);


        // Get SiteDTO with translated descriptions for SiteProfile and Region
        SiteExtendedDTO siteDTO = siteMapper.toExtendedDTO(savedSite, locale);

        return siteDTO;
    }


    public BaseResponse<SiteExtendedDTO> createSite(SiteCreateDTO createSite) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String entityCode = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_ENTITY_CODE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteExtendedDTO siteDTO = createSite(createSite, locale, entityCode, rankOrder);
        return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "New site successfully added", siteDTO);
    }

    public Site getSiteByCode(String siteCode) {
        Optional<Site> site = siteRepository.findByCode(siteCode);
        if (!site.isPresent()) {
            LOG.info("Site with code {} wasn't found", siteCode);
            throw new com.petrotec.service.exceptions.EntityNotFoundException("site_ms_ws.site.site_code_not_found", "Site with code " + siteCode + " not found.");
        }
        return site.get();
    }

    public BaseResponse<SiteExtendedDTO> getSiteByCodeDTO(@NotEmpty String siteCode) {
        try {
            Optional<String> locale = HttpUtilities.getHeader(PCSConstants.ATTR_LOCALE);
            if (!locale.isPresent()) {
                LOG.error("No locale found. Can't return correct site");
                return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                        "No locale for current user was found. Can't proceed", null);
            }

            LOG.info("Retrieving data for site with code " + siteCode);
            Site site = this.getSiteByCode(siteCode);

            SiteExtendedDTO siteDTO = siteMapper.toExtendedDTO(site, locale.get());
            if (LOG.isDebugEnabled()) {
                LOG.debug("Returning siteDTO: ", siteDTO);
            }
            return BaseResponse.success(OK.getReason(), OK.getCode(), "Site " + siteCode + " succesfully retrieved",
                    siteDTO);
        } catch (Exception ex) {
            LOG.error("An error occurred getting site", ex);
            ApiError apiError = new ApiError(Arrays.asList(ex.getMessage()));
            return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(),
                    "Couldn't get site because an error occurred", apiError);
        }
    }

    public BaseResponse<SiteExtendedDTO> update(@NotEmpty String siteCode, SiteUpdateDTO updateSite) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String entityCode = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_ENTITY_CODE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);

        SiteExtendedDTO result = updateDTO(siteCode, updateSite, locale, entityCode, rankOrder);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Site " + siteCode + " successfully updated",
                result);
    }

    @MqttPublish(operationType = MessageOperationEnum.UPDATED)
    public SiteExtendedDTO updateDTO(String siteCode, SiteUpdateDTO updateSite, String locale, String entityCode, String rankOrder) {
        Site site = siteRepository.findByCode(siteCode).orElseThrow(
                () -> new EntityNotFoundException("site.code", "Can't update site because site with code " + siteCode + " doesn't exists")
        );

        if (site.getSiteNumber() != null && !site.getSiteNumber().equals(updateSite.getSiteNumber()))
            validateUniqueSiteNumber(updateSite.getSiteNumber());

        /*Check if region is valid*/
        Region region = regionService.getEntityByCode(updateSite.getRegionCode());

        /*Check if site profile is valid*/
        SiteProfile siteProfile = siteProfileService.getEntityByCode(updateSite.getSiteProfileCode());

        siteMapper.fromUpdateDTO(updateSite, region, siteProfile, site, locale);

        siteRepository.update(site);
        propertyService.setProperties(site, updateSite);
        categoryService.setCategories(site, updateSite, rankOrder);

        SiteExtendedDTO result = siteMapper.toExtendedDTO(site, locale);

        return result;
    }

    public BaseResponse<PageResponse<SiteCustomDTO>> getSites(PageAndSorting pageAndSorting, Filter filters) {
        Optional<String> locale = HttpUtilities.getHeader(PCSConstants.ATTR_LOCALE);
        if (!locale.isPresent()) {
            LOG.error("No locale found. Can't return correct site");
            return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                    "No locale for current user was found. Can't proceed", null);
        }

        try {
            int limit = pageAndSorting.getLimit() + 1;
            int offset = pageAndSorting.getOffset();
            List<Sort> sorting = pageAndSorting.getSorting();
            LOG.debug("Finding all sites. Contains filters? {} PageAndSorting: {}", (filters != null), pageAndSorting);
            return BaseResponse.success(OK.getReason(), OK.getCode(), "List of sites with given filters and pagination", siteRepository.findAll(pageAndSorting, filters, locale.get()));
        } catch (Exception ex) {
            LOG.error("An error occurred getting sites", ex);
            ApiError apiError = new ApiError(Arrays.asList(ex.getMessage()));
            return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(),
                    "Couldn't retrieve sites because an error occurred", apiError);
        }
    }

    public List<Site> findSitesByCodes(List<String> siteCodes) {
        return siteRepository.findByCode(siteCodes);
    }

    public List<SiteCustomDTO> findSitesByCodes(List<String> siteCodes, String locale) {
        List<Site> sites = this.findSitesByCodes(siteCodes);
        return siteMapper.toCustomDTO(sites, locale);
    }

    public List<Site> findSitesByCodesAndEnabled(List<String> siteCodes) {
        return siteRepository.findByCodeAndEnabled(siteCodes);
    }

    public List<SiteCustomDTO> findSitesByCodesAndEnabled(List<String> siteCodes, String locale) {
        List<Site> sites = this.findSitesByCodesAndEnabled(siteCodes);
        return siteMapper.toCustomDTO(sites, locale);
    }

    public BaseResponse<Void> setSiteStatus(ChangeStatus changeStatus) {
        if (CollectionUtils.isEmpty(changeStatus.getCodes())) {
            LOG.error("No site codes. Can't update");
            return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(), "No site codes. Can't update",
                    null);
        }

        List<Site> sites = siteRepository.findByCode(changeStatus.getCodes());
        if (CollectionUtils.isEmpty(sites)) {
            LOG.error("No sites found for codes " + changeStatus.getCodes());
            return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                    "No sites found for codes " + changeStatus.getCodes(), null);
        }

        sites.forEach(site -> site.setEnabled(changeStatus.isEnabled()));
        List<Site> updatedSites = siteRepository.update(sites);
        if (CollectionUtils.isEmpty(updatedSites)) {
            LOG.error("Repository return empty. No sites updated");
            return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                    "Couldn't update sites status", null);
        }
        return BaseResponse.success(OK.getReason(), OK.getCode(),
                "Sites [" + changeStatus.getCodes() + "] updated status to " + changeStatus.isEnabled(), null);
    }

}
