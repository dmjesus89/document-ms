package com.petrotec.documentms.mappers;

import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteProfile.SiteProfileCreateDTO;
import com.petrotec.documentms.dtos.siteProfile.SiteProfileDTO;
import com.petrotec.documentms.entities.SiteProfile;
import com.petrotec.documentms.entities.SiteProfileCustom;
import org.mapstruct.*;

import java.util.List;

/**
 * SiteProfileMapper
 */
@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class})
public abstract class SiteProfileMapper {

    public abstract List<SiteProfileDTO> toDTOFromCustom(List<SiteProfileCustom> siteProfiles, @Context String locale);

    public abstract List<SiteProfileDTO> toDTO(List<SiteProfile> siteProfiles, @Context String locale);

    @Mapping(target = "detailedDescription", source = "description")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    public abstract SiteProfileDTO toDTO(SiteProfile siteProfile, @Context String locale);

    @Mapping(target = "detailedDescription", source = "description")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    public abstract SiteProfileDTO toDTO(SiteProfileCustom siteProfile, @Context String locale);

    @Mapping(target = "description", expression = "java(translateMapper.setDescription(dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract SiteProfile fromDTO(SiteProfileCreateDTO dto, @Context String locale);

    public abstract List<SiteProfileDTO> toTranslatedDTO(List<SiteProfileCustom> siteProfile, @Context String locale);

}
