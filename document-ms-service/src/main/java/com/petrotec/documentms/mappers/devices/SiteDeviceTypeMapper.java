package com.petrotec.documentms.mappers.devices;


import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceTypeDTO;
import com.petrotec.documentms.entities.SiteDeviceType;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SiteDeviceTypeMapper {


    @Named("toDTO")
    @Mapping(target = "detailedDescription", source = "description")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    public abstract SiteDeviceTypeDTO toDTO(SiteDeviceType entity, @Context String locale);

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<SiteDeviceTypeDTO> toDTO(List<SiteDeviceType> entityList, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract SiteDeviceType fromDTO(SiteDeviceTypeDTO dto, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(entity.getDescription(), dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract void updateEntity(@MappingTarget SiteDeviceType entity, SiteDeviceTypeDTO dto, @Context String locale);

}
