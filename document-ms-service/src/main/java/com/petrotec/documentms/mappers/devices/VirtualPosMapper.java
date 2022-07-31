package com.petrotec.documentms.mappers.devices;


import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteDevice.VirtualPosDTO;
import com.petrotec.documentms.entities.VirtualPos;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class VirtualPosMapper {

    @Named("toDTO")
    @Mapping(target = "posNumber", source = "number")
    public abstract VirtualPosDTO toDTO(VirtualPos entity, @Context String locale);

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<VirtualPosDTO> toDTO(List<VirtualPos> entityList, @Context String locale);

    @Named("fromDTO")
    @Mapping(target = "number", source = "posNumber")
    public abstract VirtualPos fromDTO(VirtualPosDTO dto, @Context String locale);

    @IterableMapping(qualifiedByName = "fromDTO")
    public abstract List<VirtualPos> fromDTO(List<VirtualPosDTO> dto, @Context String locale);

    @Mapping(target = "number", source = "posNumber")
    public abstract void updateEntity(@MappingTarget VirtualPos entity, VirtualPosDTO dto, @Context String locale);

}
