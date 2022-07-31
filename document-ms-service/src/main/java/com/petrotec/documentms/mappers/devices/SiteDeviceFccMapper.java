package com.petrotec.documentms.mappers.devices;


import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteDevice.SimpleFuelPointDTO;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceFccDTO;
import com.petrotec.documentms.dtos.siteDevice.FccTypeDTO;
import com.petrotec.documentms.entities.SiteDeviceFCC;
import com.petrotec.documentms.entities.SiteDeviceSubtype;
import org.mapstruct.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SiteDeviceFccMapper {

    @Inject
    private SiteDeviceMapper deviceMapper;
    @Inject
    private TranslateMapper translateMapper;
    @Inject
    private SiteDeviceFuelPointMapper siteDeviceFuelPointMapper;

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<SiteDeviceFccDTO> toDTO(List<SiteDeviceFCC> entityList, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "deviceSubtype.code", source = "protocolTypeCode")
    @Mapping(target = "site", ignore = true)
    public abstract SiteDeviceFCC fromDTO(SiteDeviceFccDTO dto, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "deviceSubtype.code", source = "protocolTypeCode")
    @Mapping(target = "site", ignore = true)
    public abstract void updateEntity(@MappingTarget SiteDeviceFCC entity, SiteDeviceFccDTO dto, @Context String locale);

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<SiteDeviceFccDTO> toExtendedDTO(List<SiteDeviceFCC> entity, @Context String locale);

    @Named("toDTO")
    public SiteDeviceFccDTO toDTO(SiteDeviceFCC entity, @Context String locale) {
        SiteDeviceFccDTO dto = new SiteDeviceFccDTO();
        deviceMapper.toDTO(dto, entity, locale);
        dto.setProtocolTypeCode(toFccTypeDTO(entity.getDeviceSubtype(), locale).getCode());
        dto.setCommunicationMethodData(entity.getCommunicationMethodData());
        Map<String, Object> map = entity.getCommunicationMethodData();
        if ( map != null ) {
            dto.setCommunicationMethodData( new HashMap<String, Object>( map ) );
        }
        if (entity.getCommunicationMethod() != null){
            dto.setCommunicationMethodTypeCode(entity.getCommunicationMethod().getCode());
            dto.setCommunicationMethodTypeDescription(translateMapper.translatedDescription(entity.getCommunicationMethod().getDescription(), locale));
        }
        List<SimpleFuelPointDTO> simpleFuelPointDTOList = new ArrayList<>();
        List<String> connectedFuelPointDTOList = new ArrayList<>();
        entity.getFuelPoints().forEach(p-> {
            SimpleFuelPointDTO simpleFuelPointDTO = siteDeviceFuelPointMapper.toSimpleFuelPoint(p);
            connectedFuelPointDTOList.add(simpleFuelPointDTO.getCode());
            simpleFuelPointDTOList.add(simpleFuelPointDTO);
        });
        dto.setConnectedFuelPoint(connectedFuelPointDTOList);
        dto.setFuelPoints(simpleFuelPointDTOList);
        dto.setEnabled(entity.isEnabled());

        return dto;
    }

    public FccTypeDTO toFccTypeDTO(SiteDeviceSubtype deviceSubtype, String locale) {
        FccTypeDTO fccTypeDto = new FccTypeDTO();
        fccTypeDto.setCode(deviceSubtype.getCode());
        fccTypeDto.setDescription(translateMapper.translatedDescription(deviceSubtype.getDescription(), locale));
        fccTypeDto.setDetailedDescription(deviceSubtype.getDescription());
        return fccTypeDto;
    }

}
