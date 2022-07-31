package com.petrotec.documentms.mappers.devices;


import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteDevice.SimpleFuelPointDTO;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceFuelPointDTO;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteDeviceFuelPoint;
import com.petrotec.documentms.mappers.SiteMapper;
import com.petrotec.documentms.services.SiteService;
import org.mapstruct.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class, SiteDeviceDispenserMapper.class, SiteMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SiteDeviceFuelPointMapper {

    @Inject
    private SiteDeviceMapper deviceMapper;

    @Inject
    private SiteDeviceFuelPointNozzleMapper nozzleMapper;

    @Inject
    private TranslateMapper translateMapper;

    @Inject
    private SiteService siteService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "nozzles", ignore = true)
    @Mapping(target = "dispenser", ignore = true)
    @Mapping(target = "site", ignore = true)
    @Mapping(target = "description", source = "description")
    public abstract SiteDeviceFuelPoint fromDTO(SiteDeviceFuelPointDTO dto, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "nozzles", ignore = true)
    @Mapping(target = "dispenser", ignore = true)
    @Mapping(target = "site", ignore = true)
    @Mapping(target = "description", source = "description")
    public abstract void updateEntity(@MappingTarget SiteDeviceFuelPoint entity, SiteDeviceFuelPointDTO dto, @Context String locale);

    public Site siteFromCode(String code){
        return siteService.getSiteByCode(code);
    }

    public List<SiteDeviceFuelPointDTO> toDTO(List<SiteDeviceFuelPoint> entityList, @Context String locale){
        return entityList.stream().map(e -> toDTO(e, locale)).collect(Collectors.toList());
    }

    @Named("toDTO")
    public SiteDeviceFuelPointDTO toDTO(SiteDeviceFuelPoint entity, @Context String locale) {
        SiteDeviceFuelPointDTO deviceDto = new SiteDeviceFuelPointDTO();

        deviceMapper.toDTO(deviceDto, entity, locale);

        deviceDto.setNozzles(nozzleMapper.toDTO(entity.getNozzles(),locale));
        deviceDto.setPumpNumber(entity.getPumpNumber());
        deviceDto.setTotalNozzles(entity.getNozzles().size());

        if (entity.getCommunicationMethod() != null){
            deviceDto.setCommunicationMethodTypeCode(entity.getCommunicationMethod().getCode());
            deviceDto.setCommunicationMethodTypeDescription(translateMapper.translatedDescription(entity.getCommunicationMethod().getDescription(), locale));
        }
        if (entity.getDeviceSubtype() != null){
            deviceDto.setProtocolTypeCode(entity.getDeviceSubtype().getCode());
            deviceDto.setProtocolTypeDescription(translateMapper.translatedDescription(entity.getDeviceSubtype().getDescription(), locale));
        }

        deviceDto.setCommunicationMethodData(entity.getCommunicationMethodData());

        return deviceDto;
    }

    public SimpleFuelPointDTO toSimpleFuelPoint(SiteDeviceFuelPoint entity) {
        SimpleFuelPointDTO simpleFuelPointDTO = new SimpleFuelPointDTO();
        simpleFuelPointDTO.setCode(entity.getCode());
        simpleFuelPointDTO.setDescription(entity.getDescription());
        simpleFuelPointDTO.setPumpNumber(entity.getPumpNumber().shortValue());
        return simpleFuelPointDTO;
    }

}
