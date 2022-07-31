package com.petrotec.documentms.mappers.devices;

import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceDTO;
import com.petrotec.documentms.entities.SiteDevice;
import com.petrotec.documentms.mappers.SiteMapper;
import org.mapstruct.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SiteDeviceMapper {

    @Inject
    private SiteMapper siteMapper;

    @Inject
    private SiteDeviceTypeMapper siteDeviceTypeMapper;


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "site", ignore = true)
    @Mapping(target = "description", source = "description")
    public abstract SiteDevice fromDTO(SiteDeviceDTO dto, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "site", ignore = true)
    @Mapping(target = "description", source = "description")
    public abstract void updateEntity(@MappingTarget SiteDevice entity, SiteDeviceDTO dto, @Context String locale);

    public List<SiteDeviceDTO> toDTO(List<SiteDevice> entityList, @Context String locale) {
        return entityList.stream().map(e -> {
            SiteDeviceDTO r = new SiteDeviceDTO();
            toDTO(r, e, locale);
            return r;
        }).collect(Collectors.toList());
    }

    @Named("toDTO")
    public void toDTO(@MappingTarget SiteDeviceDTO target, SiteDevice source, @Context String locale) {
        target.setCode(source.getCode());
        target.setCreatedOn(source.getCreatedOn());
        target.setUpdatedOn(source.getUpdatedOn());
        target.setEnabled(source.isEnabled());
        target.setDescription(source.getDescription());
        target.setAdditionalData(source.getAdditionalData());
        target.setSiteDeviceType(siteDeviceTypeMapper.toDTO(source.getDeviceType(), locale));
        target.setSite(siteMapper.toSimpleDTO(source.getSite(), locale));
        target.setSiteCode(source.getSite().getCode());
    }
}
