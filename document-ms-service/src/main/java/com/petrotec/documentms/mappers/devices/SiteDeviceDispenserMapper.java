package com.petrotec.documentms.mappers.devices;

import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceDispenserDTO;
import com.petrotec.documentms.entities.SiteDeviceDispenser;
import org.mapstruct.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SiteDeviceDispenserMapper {

    @Inject
    private SiteDeviceMapper deviceMapper;

    @Inject
    private SiteDeviceFuelPointMapper siteDeviceFuelPointMapper;


    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<SiteDeviceDispenserDTO> toDTO(List<SiteDeviceDispenser> entityList, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "site", ignore = true)
    @Mapping(target = "description", source = "description")
    public abstract SiteDeviceDispenser fromDTO(SiteDeviceDispenserDTO dto, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "site", ignore = true)
    @Mapping(target = "fuelPoints", ignore = true)
    @Mapping(target = "description", source = "description")
    public abstract void updateEntity(@MappingTarget SiteDeviceDispenser entity, SiteDeviceDispenserDTO dto, @Context String locale);


    @Named("toDTO")
    public SiteDeviceDispenserDTO toDTO(SiteDeviceDispenser entity, @Context String locale) {
        SiteDeviceDispenserDTO deviceDto = new SiteDeviceDispenserDTO();
        deviceMapper.toDTO(deviceDto, entity, locale);
        deviceDto.setDispenserNumber(entity.getDispenserNumber());
        deviceDto.setFuelPoints(entity.getFuelPoints().stream().map(fuelPointDTO -> siteDeviceFuelPointMapper.toSimpleFuelPoint(fuelPointDTO)).collect(Collectors.toList()));
        deviceDto.setFuelPointCodes(entity.getFuelPoints().stream().map(fuelPointDTO -> fuelPointDTO.getCode()).collect(Collectors.toList()));

        return deviceDto;
    }

}
