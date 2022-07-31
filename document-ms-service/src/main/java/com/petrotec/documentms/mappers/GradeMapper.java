package com.petrotec.documentms.mappers;


import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.grade.GradeDTO;
import com.petrotec.documentms.dtos.siteDevice.SimpleGradePriceSignDTO;
import com.petrotec.documentms.entities.Grade;
import com.petrotec.documentms.entities.GradePriceSign;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "jsr330", uses = {GradeProductMapper.class, TranslateMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class GradeMapper {

    @Named("toDTO")
    @Mapping(target = "detailedDescription", source = "description")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    @Mapping(target = "products", source = "gradeProducts")
    public abstract GradeDTO toDTO(Grade entity, @Context String locale);

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<GradeDTO> toDTO(List<Grade> entityList, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", source = "enabled")
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract Grade fromDTO(GradeDTO dto, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "enabled", source = "enabled")
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(entity.getDescription(), dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract void updateEntity(@MappingTarget Grade entity, GradeDTO dto, @Context String locale);

    public SimpleGradePriceSignDTO toSimpleGrade(GradePriceSign entity, @Context String locale) {
        SimpleGradePriceSignDTO simpleGradeDTO = new SimpleGradePriceSignDTO();
        simpleGradeDTO.setCode(entity.getGrade().getCode());
        simpleGradeDTO.setDescription(entity.getGrade().getDescription().get(locale));
        simpleGradeDTO.setColor(entity.getGrade().getColor());
        simpleGradeDTO.setUnitPriceNet(entity.getGrade().getUnitPriceNet());
        return simpleGradeDTO;
    }

}
