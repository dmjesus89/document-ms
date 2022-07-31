package com.petrotec.documentms.mappers;

import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteRegion.RegionCreateDTO;
import com.petrotec.documentms.dtos.siteRegion.RegionDTO;
import com.petrotec.documentms.entities.Region;
import com.petrotec.documentms.entities.RegionCustom;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * RegionMapper
 */
@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class})
public abstract class RegionMapper {

    public abstract List<RegionDTO> toDTO(List<RegionCustom> region, @Context String locale);

    @Mapping(target = "detailedDescription", source = "description")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    public abstract RegionDTO toDTO(RegionCustom region, @Context String locale);

    @Mapping(target = "detailedDescription", source = "description")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    public abstract RegionDTO toDTO(Region region, @Context String locale);

    @Mapping(target = "description", expression = "java(translateMapper.setDescription(dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract Region fromDTO(RegionCreateDTO dto, @Context String locale);

}
