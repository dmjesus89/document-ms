package com.petrotec.documentms.services;

import com.petrotec.api.*;
import com.petrotec.api.filter.Filter;
import com.petrotec.api.queue.MessageOperationEnum;
import com.petrotec.queue.annotations.MqttPublish;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.siteRegion.RegionCreateDTO;
import com.petrotec.documentms.dtos.siteRegion.RegionDTO;
import com.petrotec.documentms.entities.Region;
import com.petrotec.documentms.entities.RegionCustom;
import com.petrotec.documentms.mappers.RegionMapper;
import com.petrotec.documentms.repositories.interfaces.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.micronaut.http.HttpStatus.*;

/**
 * RegionService
 */
@Singleton
public class RegionService {
    private static final Logger LOG = LoggerFactory.getLogger(RegionService.class);

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    public RegionService(final RegionRepository regionRepository, final RegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

    public Region getEntityByCode(String regionCode) {
        return regionRepository.findByCode(regionCode).orElseThrow(
                () -> new EntityNotFoundException("site.regionCode", "Invalid region code received")
        );
    }

    @NotNull
    public BaseResponse<PageResponse<RegionDTO>> findAll(PageAndSorting pageAndSorting, Filter filters) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        LOG.debug("Finding all regions. Contains filters? {} PageAndSorting: {}", filters != null, pageAndSorting);
        int limit = pageAndSorting.getLimit() + 1;
        List<RegionCustom> regionsList = regionRepository.findAll(filters, pageAndSorting.getOffset(), limit,
                pageAndSorting.getSorting());

        if (LOG.isTraceEnabled()) {
            LOG.trace("List of found regions: " + regionsList);
        }
        LOG.debug("Converting regions to DTO");
        List<RegionDTO> regionDTOs = regionMapper.toDTO(regionsList, locale);

        if (LOG.isTraceEnabled()) {
            LOG.trace("List of regions dtos: " + regionDTOs);
        }

        PageResponse<RegionDTO> pageResponse = PageResponse.from(regionDTOs, pageAndSorting,
                () -> regionRepository.count(filters));
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List of regions", pageResponse);
    }


    public BaseResponse<RegionDTO> updateByCode(@Positive @NotEmpty String regionCode,
                                                @NotNull RegionCreateDTO regionDTO) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        try {
            LOG.debug("Searching for existing region with code " + regionCode);
            Optional<Region> existingRegion = regionRepository.findByCode(regionCode);
            if (!existingRegion.isPresent()) {
                LOG.debug("No site with code " + regionCode + " was found. Can't update");
                return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                        "No site with code " + regionCode + " was found. Can't update", null);
            }

            Region region = regionMapper.fromDTO(regionDTO, locale);
            region.setId(existingRegion.get().getId());
            Optional<Region> updatedRegion = regionRepository.updateRegion(region);
            if (!updatedRegion.isPresent()) {
                LOG.error("An error occurred updating region in database since update returned no value");
                return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                        "Region couldn't be updated", null);
            }
            LOG.debug("Successfully updated region to {}", updatedRegion.get());
            RegionDTO updatedDTO = regionMapper.toDTO(updatedRegion.get(), locale);
            LOG.trace("Converted Region to DTO: " + updatedDTO);
            propagateUpdatedToMqtt(updatedRegion.get(), locale);
            return BaseResponse.success(OK.getReason(), OK.getCode(), "Region successfully updated", updatedDTO);
        } catch (Exception ex) {
            LOG.error("Error updating region to " + regionDTO, ex);
            ApiError apiError = new ApiError(Arrays.asList(ex.getMessage()));
            return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(),
                    "Error updating region: " + regionDTO, apiError);
        }
    }

    public BaseResponse<RegionDTO> addRegion(@NotNull RegionCreateDTO regionDTO) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        try {
            LOG.debug("Searching for existing region with code " + regionDTO.getCode());
            Optional<Region> existingRegion = regionRepository.findByCode(regionDTO.getCode());
            if (existingRegion.isPresent()) {
                LOG.debug("Region with same code found. Can't add with same code");
                return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                        "Region with code " + regionDTO.getCode() + " already exists. Can't add with same code", null);
            }

            LOG.trace("Converting to Region entity...");
            Region region = regionMapper.fromDTO(regionDTO, locale);
            LOG.debug("Saving new Region: {}", region);
            Optional<Region> storedRegion = regionRepository.insertRegion(region);
            if (!storedRegion.isPresent()) {
                LOG.error("An error occurred insert new region in database since insert returned no value");
                return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                        "Region couldn't be stored", null);
            }

            LOG.trace("Converting Region entity to RegionDTO");
            RegionDTO storedDTO = regionMapper.toDTO(storedRegion.get(), locale);
            propagateCreatedToMqtt(region, locale);
            return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Region successfully stored",
                    storedDTO);
        } catch (Exception ex) {
            LOG.error("Error adding region", ex);
            ApiError apiError = new ApiError(Arrays.asList(ex.getMessage()));
            return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(),
                    "Error adding region: " + regionDTO, apiError);
        }
    }

    @MqttPublish(operationType = MessageOperationEnum.CREATED, topicName = "pcs/document-ms/region")
    protected RegionDTO propagateCreatedToMqtt(Region region, String locale) {
        return regionMapper.toDTO(region, locale);
    }

    @MqttPublish(operationType = MessageOperationEnum.UPDATED, topicName = "pcs/document-ms/region")
    protected RegionDTO propagateUpdatedToMqtt(Region region, String locale) {
        return regionMapper.toDTO(region, locale);
    }

    public BaseResponse<RegionDTO> setEnabled(@Positive @NotEmpty String regionCode, boolean enabled) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        try {
            LOG.debug("Searching for existing region with code " + regionCode);
            Optional<Region> existingRegion = regionRepository.findByCode(regionCode);
            if (!existingRegion.isPresent()) {
                LOG.debug("No region found for code " + regionCode);
                return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                        "No region found for code " + regionCode, null);
            }

            LOG.debug("Setting region " + regionCode + " enabled to " + enabled);
            existingRegion.get().setEnabled(enabled);
            Optional<Region> updatedRegion = regionRepository.updateRegion(existingRegion.get());

            if (!updatedRegion.isPresent()) {
                LOG.error("Couldn't update region enabled status");
                return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(),
                        "Couldn't update " + regionCode + " enabled to " + enabled, null);
            }

            RegionDTO updatedDTO = regionMapper.toDTO(updatedRegion.get(), locale);
            propagateUpdatedToMqtt(updatedRegion.get(), locale);
            return BaseResponse.success(OK.getReason(), OK.getCode(), "Region successfully stored", updatedDTO);
        } catch (Exception ex) {
            LOG.error("Error updating region " + regionCode + " enabled to " + enabled, ex);
            ApiError apiError = new ApiError(Arrays.asList(ex.getMessage()));
            return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                    "Error updating region " + regionCode + " enabled to " + enabled, apiError);
        }
    }

    public BaseResponse<RegionDTO> findByCode(@NotEmpty String regionCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        try {
            LOG.debug("Searching for existing region with code " + regionCode);
            Optional<Region> region = regionRepository.findByCode(regionCode);
            if (!region.isPresent()) {
                LOG.debug("No region found for code " + regionCode);
                return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                        "No region found for code " + regionCode, null);
            }
            LOG.debug("Found region with code {}: {}", regionCode, region.get());
            RegionDTO regionDTO = regionMapper.toDTO(region.get(), locale);
            return BaseResponse.success(OK.getReason(), OK.getCode(), "Region with code " + regionCode, regionDTO);
        } catch (Exception ex) {
            LOG.error("Error finding region with code" + regionCode, ex);
            ApiError apiError = new ApiError(Arrays.asList(ex.getMessage()));
            return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(),
                    "Error finding region with code" + regionCode, apiError);
        }
    }
}
