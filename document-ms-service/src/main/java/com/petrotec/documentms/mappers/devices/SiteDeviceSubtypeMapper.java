package com.petrotec.documentms.mappers.devices;


import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceSubtypeDTO;
import com.petrotec.documentms.entities.SiteDeviceSubtype;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class, SiteDeviceTypeMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SiteDeviceSubtypeMapper {

    @Named("toDTO")
    @Mapping(target = "detailedDescription", source = "description")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    public abstract SiteDeviceSubtypeDTO toDTO(SiteDeviceSubtype entity, @Context String locale);

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<SiteDeviceSubtypeDTO> toDTO(List<SiteDeviceSubtype> entityList, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract SiteDeviceSubtype fromDTO(SiteDeviceSubtypeDTO dto, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(entity.getDescription(), dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract void updateEntity(@MappingTarget SiteDeviceSubtype entity, SiteDeviceSubtypeDTO dto, @Context String locale);

}
