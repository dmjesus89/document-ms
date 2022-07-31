package com.petrotec.documentms.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrotec.api.*;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.siteProfile.SiteProfileCreateDTO;
import com.petrotec.documentms.dtos.siteProfile.SiteProfileDTO;
import com.petrotec.documentms.entities.SiteProfile;
import com.petrotec.documentms.entities.SiteProfileCustom;
import com.petrotec.documentms.mappers.SiteProfileMapper;
import com.petrotec.documentms.repositories.interfaces.SiteProfileRepository;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.micronaut.http.HttpStatus.*;

/**
 * SiteProfileService
 */
@Singleton
public class SiteProfileService {
    private static final Logger LOG = LoggerFactory.getLogger(SiteProfileService.class);

    private final ObjectMapper objectMapper;
    private final SiteProfileRepository siteProfileRepository;
    private final SiteProfileMapper siteProfileMapper;

    @Inject
    public SiteProfileService(final SiteProfileRepository siteProfileRepository,
                              final SiteProfileMapper siteProfileMapper, final ObjectMapper objectMapper) {
        this.siteProfileRepository = siteProfileRepository;
        this.siteProfileMapper = siteProfileMapper;
        this.objectMapper = objectMapper;
    }

    public SiteProfile getEntityByCode(String siteProfileCode){
        return siteProfileRepository.findByCode(siteProfileCode).orElseThrow(
                () -> new EntityNotFoundException("site_ms_ws.site.profile_code_not_found", "Invalid site profile code received")
        );
    }

    public BaseResponse<SiteProfileDTO> findByCode(String siteProfileCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        LOG.debug("Finding site profile with id " + siteProfileCode);
        Optional<SiteProfile> siteProfile = siteProfileRepository.findByCode(siteProfileCode);
        if (!siteProfile.isPresent()) {
            LOG.error("Couldn't find any site profile with id " + siteProfileCode);
            return BaseResponse.error(HttpStatus.BAD_REQUEST.getReason(), HttpStatus.BAD_REQUEST.getCode(),
                    "Couldn't find any site profiles with id " + siteProfileCode, null);
        }
        LOG.debug("Site profile found: " + siteProfile);
        SiteProfileDTO dto = siteProfileMapper.toDTO(siteProfile.get(), locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Site profile " + siteProfileCode + " details",
                dto);
    }

    public BaseResponse<PageResponse<SiteProfileDTO>> findAll(Filter filters, PageAndSorting pageAndSorting) {
        LOG.debug("Finding all site profiles. Contains filters? {} PageAndSorting: {}", filters != null,
                pageAndSorting);

        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        try {
            int limit = pageAndSorting.getLimit() + 1;
            List<SiteProfileCustom> spList = siteProfileRepository.findAll(filters, pageAndSorting.getOffset(), limit,
                    pageAndSorting.getSorting());

            if (LOG.isTraceEnabled()) {
                LOG.trace("List of found site profiles: " + spList);
            }
            LOG.debug("Converting site profiles to DTO");
            List<SiteProfileDTO> profilesDTO = siteProfileMapper.toDTOFromCustom(spList, locale);

            if (LOG.isTraceEnabled()) {
                LOG.trace("List of site profiles dtos: " + profilesDTO);
            }

            PageResponse<SiteProfileDTO> pageResponse = PageResponse.from(profilesDTO, pageAndSorting,
                    () -> siteProfileRepository.count(filters));

            return BaseResponse.success(OK.getReason(), OK.getCode(), "List of site profiles", pageResponse);
        } catch (Exception ex) {
            LOG.error("Error finding all site profiles", ex);
            ApiError apiError = new ApiError(Arrays.asList(ex.getMessage()));
            return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(),
                    "Error finding site profiles.. ", apiError);
        }
    }

    public BaseResponse<PageResponse<SiteProfileDTO>> findAllTranslated(Filter filters,
                                                                                  PageAndSorting pageAndSorting) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        if (StringUtils.isEmpty(locale)) {
            LOG.error("Couldn't retrieve locale from request");
            return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                    "Couldn't retrieve locale from request", null);
        }
        LOG.debug("Finding all site profiles. Locale: {} Contains filters? {} PageAndSorting: {}", locale,
                filters != null, pageAndSorting);

        try {
            int limit = pageAndSorting.getLimit() + 1;
            List<SiteProfileCustom> spList = siteProfileRepository.findAll(filters,
                    pageAndSorting.getOffset(), limit, pageAndSorting.getSorting());

            if (LOG.isTraceEnabled()) {
                LOG.trace("List of found site profiles: " + spList);
            }
            LOG.debug("Converting site profiles to DTO");
            List<SiteProfileDTO> translatedProfilesDTO = siteProfileMapper.toTranslatedDTO(spList, locale);

            if (LOG.isTraceEnabled()) {
                LOG.trace("List of site profiles dtos: " + translatedProfilesDTO);
            }

            PageResponse<SiteProfileDTO> pageResponse = PageResponse.from(translatedProfilesDTO,
                    pageAndSorting, () -> siteProfileRepository.count(filters));

            return BaseResponse.success(OK.getReason(), OK.getCode(), "List of translated site profiles",
                    pageResponse);
        } catch (Exception ex) {
            LOG.error("Error finding all site profiles", ex);
            ApiError apiError = new ApiError(Arrays.asList(ex.getMessage()));
            return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(),
                    "Error finding site profiles.. ", apiError);
        }
    }

    @Transactional
    public BaseResponse<SiteProfileDTO> addSiteProfile(SiteProfileCreateDTO siteProfileCreateDTO) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        try {
            LOG.debug("Searching for existing site profile with code " + siteProfileCreateDTO.getCode());
            Optional<SiteProfile> existingSiteProfile = siteProfileRepository.findByCode(siteProfileCreateDTO.getCode());
            if (existingSiteProfile.isPresent()) {
                LOG.debug("Site profile with same code found. Can't add with same code");
                return BaseResponse.error(CONFLICT.getReason(), CONFLICT.getCode(),
                        "SiteProfile for " + siteProfileCreateDTO.getCode() + " already exists", null);
            }

            LOG.trace("Converting to SiteProfile entity...");
            SiteProfile siteProfile = siteProfileMapper.fromDTO(siteProfileCreateDTO,locale);
            LOG.debug("Saving new SiteProfile: {}", siteProfile);
            Optional<SiteProfile> storedSite = siteProfileRepository.insertSiteProfile(siteProfile);
            if (!storedSite.isPresent()) {
                LOG.error("An error occurred insert new site profile in database since insert returned no value");
                return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                        "SiteProfile couldn't be stored", null);
            }

            LOG.trace("Converting SiteProfile entity to SiteProfileDTO");
            SiteProfileDTO storedSto = siteProfileMapper.toDTO(storedSite.get(), locale);
            return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Site profile successfully stored",
                    storedSto);
        } catch (Exception ex) {
            LOG.error("Error adding site profile", ex);
            ApiError apiError = new ApiError(Arrays.asList(ex.getMessage()));
            return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(),
                    "Error adding site profile: " + siteProfileCreateDTO, apiError);
        }

    }

    public BaseResponse<SiteProfileDTO> updateSiteProfile(
            String siteProfileCode,
            @NotNull SiteProfileCreateDTO updatedSiteProfile) throws Exception {

        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        try {
            LOG.debug("Searching for existing site profile with id " + siteProfileCode);
            SiteProfile existingSiteProfile = siteProfileRepository.findByCode(siteProfileCode).orElseThrow(() -> {
                LOG.debug("No site with id " + siteProfileCode + " was found. Can't update");
                return new javax.persistence.EntityNotFoundException("No site with id " + siteProfileCode + " was found. Can't update");
            });

            SiteProfile siteProfile = siteProfileMapper.fromDTO(updatedSiteProfile, locale);
            siteProfile.setId(existingSiteProfile.getId());
            Optional<SiteProfile> updatedProfile = siteProfileRepository.updateSiteProfile(siteProfile);
            if (!updatedProfile.isPresent()) {
                LOG.error("An error occurred updating site profile in database since update returned no value");
                return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                        "SiteProfile couldn't be updated", null);
            }
            LOG.debug("Successfully updated siteprofile to {}", updatedProfile.get());
            LOG.trace("Converting SiteProfile to DTO");
            SiteProfileDTO updatedDTO = siteProfileMapper.toDTO(updatedProfile.get(), locale);
            return BaseResponse.success(OK.getReason(), OK.getCode(), "Site profile successfully updated",
                    updatedDTO);
        } catch (Exception ex) {
            LOG.error("Error updating site profile to " + updatedSiteProfile, ex);
            ApiError apiError = new ApiError(Arrays.asList(ex.getMessage()));
            return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(),
                    "Error updating site profile: " + updatedSiteProfile, apiError);
        }

    }

    public BaseResponse<SiteProfileDTO> setEnabled(String siteProfileCode, boolean b) {
        return null;
    }
}
