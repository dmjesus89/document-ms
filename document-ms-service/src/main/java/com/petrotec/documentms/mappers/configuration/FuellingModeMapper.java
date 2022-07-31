package com.petrotec.documentms.mappers.configuration;


import com.petrotec.documentms.dtos.configuration.FuellingModeDTO;
import com.petrotec.documentms.entities.FuellingMode;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "jsr330")
public abstract class FuellingModeMapper {

    @Named("toDTO")
    public abstract FuellingModeDTO toDTO(FuellingMode entity);

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<FuellingModeDTO> toDTO(List<FuellingMode> entityList);

    @Named("toEntity")
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    public abstract FuellingMode toEntity(FuellingModeDTO fuellingModeDTO);

    @Mapping(target = "code", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    public abstract void update(@MappingTarget FuellingMode entity, FuellingModeDTO dto);
}
