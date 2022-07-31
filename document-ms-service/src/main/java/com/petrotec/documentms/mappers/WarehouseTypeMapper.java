package com.petrotec.documentms.mappers;


import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteDevice.WarehouseTypeDTO;
import com.petrotec.documentms.entities.WarehouseType;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class WarehouseTypeMapper {


    @Named("toDTO")
    @Mapping(target = "detailedDescription", source = "description")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    public abstract WarehouseTypeDTO toDTO(WarehouseType entity, @Context String locale);

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<WarehouseTypeDTO> toDTO(List<WarehouseType> entityList, @Context String locale);

}
