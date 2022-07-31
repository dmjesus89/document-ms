package com.petrotec.documentms.mappers.devices;


import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteDevice.SiteDevicePriceSignDTO;
import com.petrotec.documentms.entities.GradePriceSign;
import com.petrotec.documentms.entities.SiteDevicePriceSign;
import com.petrotec.documentms.mappers.GradeMapper;
import org.mapstruct.*;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SiteDevicePriceSignMapper {

    @Inject
    private SiteDeviceMapper deviceMapper;
    @Inject
    private GradeMapper gradeMapper;
    @Inject
    private TranslateMapper translateMapper;

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<SiteDevicePriceSignDTO> toDTO(List<SiteDevicePriceSign> entityList, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "site", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "priceSignCode", source = "priceSignCode")
    public abstract SiteDevicePriceSign fromDTO(SiteDevicePriceSignDTO dto, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "site", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "grades", ignore = true)
    public abstract void updateEntity(@MappingTarget SiteDevicePriceSign entity, SiteDevicePriceSignDTO dto, @Context String locale);


    @Named("toDTO")
    public SiteDevicePriceSignDTO toDTO(SiteDevicePriceSign entity, @Context String locale) {
        SiteDevicePriceSignDTO deviceDto = new SiteDevicePriceSignDTO();
        deviceMapper.toDTO(deviceDto, entity, locale);
        deviceDto.setPriceSignCode(entity.getPriceSignCode());
        deviceDto.setCommunicationMethodData(entity.getCommunicationMethodData());

        if (entity.getCommunicationMethod() != null){
            deviceDto.setCommunicationMethodTypeCode(entity.getCommunicationMethod().getCode());
            deviceDto.setCommunicationMethodTypeDescription(translateMapper.translatedDescription(entity.getCommunicationMethod().getDescription(), locale));
        }
        if (entity.getDeviceSubtype() != null){
            deviceDto.setProtocolTypeCode(entity.getDeviceSubtype().getCode());
            deviceDto.setProtocolTypeDescription(translateMapper.translatedDescription(entity.getDeviceSubtype().getDescription(), locale));
        }

        List<GradePriceSign> gradePriceSign = entity.getGrades();
        Collections.sort(gradePriceSign, Comparator.comparing(GradePriceSign::getGradeOrder));
        deviceDto.setGrades(gradePriceSign.stream().map(grade -> gradeMapper.toSimpleGrade(grade,locale)).collect(Collectors.toList()));
        deviceDto.setGradeCodes(gradePriceSign.stream().map(grade -> grade.getGrade().getCode()).collect(Collectors.toList()));
        return deviceDto;
    }

}
