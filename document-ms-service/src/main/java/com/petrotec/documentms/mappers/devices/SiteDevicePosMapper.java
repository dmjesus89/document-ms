package com.petrotec.documentms.mappers.devices;


import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteDevice.SiteDevicePosDTO;
import com.petrotec.documentms.dtos.siteDevice.VirtualPosDTO;
import com.petrotec.documentms.dtos.siteDevice.PosTypeDTO;
import com.petrotec.documentms.entities.SiteDevicePos;
import com.petrotec.documentms.entities.SiteDeviceSubtype;
import org.mapstruct.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SiteDevicePosMapper {

    @Inject
    private TranslateMapper translateMapper;

    @Inject
    private SiteDeviceMapper deviceMapper;

    @Inject
    private SiteDeviceFuelPointMapper siteDeviceFuelPointMapper;

    @Inject
    private VirtualPosMapper virtualPosMapper;

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<SiteDevicePosDTO> toDTO(List<SiteDevicePos> entityList, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "deviceSubtype", ignore = true)
    @Mapping(target = "virtualPos", ignore = true)
    @Mapping(target = "site", ignore = true)
    @Mapping(target = "number", source = "posNumber")
    @Mapping(target = "description", source = "description")
    public abstract SiteDevicePos fromDTO(SiteDevicePosDTO dto, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "deviceSubtype", ignore = true)
    @Mapping(target = "virtualPos", ignore = true)
    @Mapping(target = "fuelPoints", ignore = true)
    @Mapping(target = "site", ignore = true)
    @Mapping(target = "number", source = "posNumber")
    @Mapping(target = "description", source = "description")
    public abstract void updateEntity(@MappingTarget SiteDevicePos entity, SiteDevicePosDTO dto, @Context String locale);

    @Named("toDTO")
    public SiteDevicePosDTO toDTO(SiteDevicePos entity, @Context String locale) {
        SiteDevicePosDTO deviceDto = new SiteDevicePosDTO();
        deviceMapper.toDTO(deviceDto, entity, locale);
        deviceDto.setPosNumber(entity.getNumber());
        deviceDto.setPosType(toDTO(entity.getDeviceSubtype(), locale));
        deviceDto.setPosTypeCode(deviceDto.getPosType().getCode());
        deviceDto.setCommunicationMethodData(entity.getCommunicationMethodData());

        if (entity.getCommunicationMethod() != null) {
            deviceDto.setCommunicationMethodTypeCode(entity.getCommunicationMethod().getCode());
            deviceDto.setCommunicationMethodTypeDescription(translateMapper.translatedDescription(entity.getCommunicationMethod().getDescription(), locale));
        }
        if (entity.getDeviceSubtype() != null) {
            deviceDto.setProtocolTypeCode(entity.getDeviceSubtype().getCode());
            deviceDto.setProtocolTypeDescription(translateMapper.translatedDescription(entity.getDeviceSubtype().getDescription(), locale));
        }

        deviceDto.setFuelPoints(entity.getFuelPoints().stream().map(fuelPointDTO -> siteDeviceFuelPointMapper.toSimpleFuelPoint(fuelPointDTO)).collect(Collectors.toList()));
        deviceDto.setConnectedFuelPoints(entity.getFuelPoints().stream().map(fuelPointDTO -> fuelPointDTO.getCode()).collect(Collectors.toList()));

        entity.getVirtualPos().forEach(v -> {
                    VirtualPosDTO virtualPos = virtualPosMapper.toDTO(v, locale);
                    virtualPos.setConnectedPumps(v.getFuelPoints().stream().map(fuelPointDTO -> fuelPointDTO.getCode()).collect(Collectors.toList()));
                    deviceDto.getVirtualPos().add(virtualPos);
                }
        );
        return deviceDto;
    }

    public PosTypeDTO toDTO(SiteDeviceSubtype deviceSubtype, String locale) {
        PosTypeDTO posTypeDto = new PosTypeDTO();
        posTypeDto.setCode(deviceSubtype.getCode());
        posTypeDto.setDescription(translateMapper.translatedDescription(deviceSubtype.getDescription(), locale));
        posTypeDto.setDetailedDescription(deviceSubtype.getDescription());
        return posTypeDto;
    }

}
