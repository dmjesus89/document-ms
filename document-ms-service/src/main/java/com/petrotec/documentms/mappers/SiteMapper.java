package com.petrotec.documentms.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrotec.categories.mappers.categories.CategoryAssociationMapper;
import com.petrotec.categories.mappers.properties.PropertyAssociationMapper;
import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.site.*;
import com.petrotec.documentms.dtos.siteProfile.SiteProfileDTO;
import com.petrotec.documentms.dtos.siteRegion.RegionDTO;
import com.petrotec.documentms.entities.*;
import com.petrotec.documentms.repositories.SiteDevicePosRepository;
import com.petrotec.documentms.repositories.SiteRepository;
import com.petrotec.documentms.repositories.documents.SiteDeviceRepository;
import org.mapstruct.*;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;

/**
 * SiteMapper
 */
@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class})
public abstract class SiteMapper {

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private SiteRepository siteRepository;

    @Inject
    private SiteDeviceRepository siteDeviceRepository;

    @Inject
    private SiteDevicePosRepository siteDevicePosRepository;

    @Inject
    private TranslateMapper translateMapper;

    @Inject
    private RegionMapper regionMapper;

    @Inject
    private Provider<SiteGroupItemMapper> siteGroupItemMapper;

    @Inject
    private SiteProfileMapper siteProfileMapper;

    @Inject
    PropertyAssociationMapper propertyAssociationMapper;

    @Inject
    CategoryAssociationMapper categoryAssociationMapper;


    @Mapping(target = "region", source = "region.description", qualifiedByName = "translateDescription")
    @Mapping(target = "siteProfileDescription", source = "siteProfile.description", qualifiedByName = "translateDescription")
    @Mapping(target = "code", source = "site.code")
    @Mapping(target = "siteNumber", source = "site.siteNumber")
    @Mapping(target = "enabled", source = "site.enabled")
    @Mapping(target = "detailedDescription", source = "description")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    @Mapping(target = "siteProfileCode", source = "siteProfile.code")
    @Mapping(target = "regionCode", source = "region.code")
    public abstract SiteDTO toDTO(Site site, @Context String locale);

    public abstract List<SiteDTO> toDTO(List<Site> sites, @Context String locale);

    @IterableMapping(qualifiedByName = "toDTOFromCustom")
    public abstract List<SiteCustomDTO> toCustomDTOFromCustom(List<SiteCustom> sites, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(createSite.getDescription(), createSite.getDetailedDescription(), locale))")
    @Mapping(target = "enabled", source = "createSite.enabled")
    @Mapping(target = "code", source = "createSite.code")
    @Mapping(target = "siteNumber", source = "createSite.siteNumber")
    @Mapping(target = "region", source = "region")
    @Mapping(target = "siteProfile", source = "siteProfile")
    @Mapping(target = "properties", ignore = true)
    @Mapping(target = "categories", ignore = true)
    public abstract Site fromCreateDTO(SiteCreateDTO createSite, Region region, SiteProfile siteProfile, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "siteNumber", source = "updateSite.siteNumber")
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(targetSite.getDescription(), updateSite.getDescription(), updateSite.getDetailedDescription(), locale))")
    @Mapping(target = "enabled", source = "updateSite.enabled")
    @Mapping(target = "siteProfile", source = "siteProfile")
    @Mapping(target = "region", source = "region")
    @Mapping(target = "properties", ignore = true)
    @Mapping(target = "categories", ignore = true)
    public abstract void fromUpdateDTO(SiteUpdateDTO updateSite, Region region, SiteProfile siteProfile,
                                       @MappingTarget Site targetSite, @Context String locale);


    public Site getSiteFromCode(String siteCode) {
        return siteRepository.findByCode(siteCode).get();
    }

    public SiteDevice getSiteDeviceFromCode(String siteDeviceCode) {
        return siteDeviceRepository.findByCode(siteDeviceCode).get();
    }

    public SiteDevicePos getSiteDevicePosFromCode(String siteDeviceCode) {
        return siteDevicePosRepository.findByCode(siteDeviceCode).get();
    }

    public SiteSimpleDTO toSimpleDTO(Site source, String locale) {
        if (source == null) return null;
        SiteSimpleDTO siteDto = new SiteSimpleDTO();

        siteDto.setCode(source.getCode());
        siteDto.setSiteNumber(source.getSiteNumber());
        siteDto.setDescription(translateMapper.translatedDescription(source.getDescription(), locale));
        siteDto.setEntityCode(source.getEntityCode());
        if (source.getRegion() != null)
            siteDto.setRegionCode(source.getRegion().getCode());

        return siteDto;
    }

    @IterableMapping(qualifiedByName = "toExtendedDTO")
    public abstract List<SiteExtendedDTO> toExtendedDTO(List<Site> site, @Context String locale);

    @Named("toExtendedDTO")
    public SiteExtendedDTO toExtendedDTO(Site site, @Context String locale) {
        SiteExtendedDTO dto = new SiteExtendedDTO();
        dto.setCode(site.getCode());
        dto.setSiteNumber(site.getSiteNumber());
        dto.setEnabled(site.isEnabled());
        dto.setLongitude(site.getLongitude());
        dto.setLatitude(site.getLatitude());
        dto.setEntityCode(site.getEntityCode());
        dto.setDescription(translateMapper.translatedDescription(site.getDescription(), locale));
        dto.setDetailedDescription(site.getDescription());

        dto.setRegion(regionMapper.toDTO(site.getRegion(), locale));
        dto.setGroups(siteGroupItemMapper.get().toDTO(site.getSiteGroupItems(), locale, false));
        dto.setSiteProfile(siteProfileMapper.toDTO(site.getSiteProfile(), locale));

        dto.setProperties(propertyAssociationMapper.propertiesToDto(site.getProperties(), locale));
        dto.setCategories(categoryAssociationMapper.categoriesToDto(site.getCategories(), locale));
        dto.setCreatedOn(site.getCreatedOn());
        return dto;
    }

    @IterableMapping(qualifiedByName = "toCustomDTO")
    public abstract List<SiteCustomDTO> toCustomDTO(List<Site> site, @Context String locale);

    @Named("toCustomDTO")
    public SiteCustomDTO toCustomDTO(Site site, @Context String locale) {
        SiteCustomDTO dto = new SiteCustomDTO();
        dto.setCode(site.getCode());
        dto.setSiteNumber(site.getSiteNumber());
        dto.setEnabled(site.isEnabled());
        dto.setLongitude(site.getLongitude());
        dto.setLatitude(site.getLatitude());
        dto.setEntityCode(site.getEntityCode());
        dto.setDescription(translateMapper.translatedDescription(site.getDescription(), locale));
        dto.setDetailedDescription(site.getDescription());

        dto.setRegion(regionMapper.toDTO(site.getRegion(), locale));
        dto.setGroups(siteGroupItemMapper.get().toDTO(site.getSiteGroupItems(), locale, false));
        dto.setSiteProfile(siteProfileMapper.toDTO(site.getSiteProfile(), locale));

        dto.setProperties(propertyAssociationMapper.propertiesToMapDto(site.getProperties(), locale));
        dto.setCategories(categoryAssociationMapper.categoriesValueToMapDto(site.getCategories(), locale));
        /* dto.setProperties(propertyAssociationMapper.propertiesToDto(site.getProperties(), locale));
          dto.setCategories(categoryAssociationMapper.categoriesToDto(site.getCategories(), locale));*/
        dto.setCreatedOn(site.getCreatedOn());
        return dto;
    }

    @Named("toDTOFromCustom")
    public SiteCustomDTO toCustomDTOFromCustom(SiteCustom site, @Context String locale) {
        SiteCustomDTO extendedDTO = new SiteCustomDTO();
        extendedDTO.setCode(site.getCode());
        extendedDTO.setSiteNumber(site.getSiteNumber());
        extendedDTO.setEnabled(site.isEnabled());

        extendedDTO.setLongitude(site.getLongitude());
        extendedDTO.setLatitude(site.getLatitude());
        extendedDTO.setDescription(site.getDescription());
        //extendedDTO.setDetailedDescription(site.getDetailedDescription());

        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setCode(site.getRegionCode());
        regionDTO.setDescription(site.getRegionDescription());

        SiteProfileDTO siteProfileDTO = new SiteProfileDTO();
        siteProfileDTO.setCode(site.getSiteProfileCode());
        siteProfileDTO.setDescription(site.getSiteProfileDescription());

        SiteDeviceDataDTO siteDevices = new SiteDeviceDataDTO();
        extendedDTO.setSiteDevices(siteDevices);
        siteDevices.setnFuelPoints(site.getnFuelPoints());
        siteDevices.setnDispensers(site.getnDispensers());
        siteDevices.setnPos(site.getnPos());
        siteDevices.setnWarehouses(site.getnWarehouses());
        siteDevices.setnFccs(site.getnFccs());

        extendedDTO.setRegion(regionDTO);
        extendedDTO.setSiteProfile(siteProfileDTO);
        extendedDTO.setCreatedOn(site.getCreatedOn());
        extendedDTO.setUpdatedOn(site.getUpdatedOn());
        return extendedDTO;
    }
}
