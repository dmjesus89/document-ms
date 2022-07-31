package com.petrotec.documentms.mappers;

import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.grade.SiteGradeDTO;
import com.petrotec.documentms.entities.SiteGrade;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class})
public abstract class SiteGradeMapper {

    @Named(value = "toDTO")
    @Mapping(source = "site.code", target = "siteCode")
    @Mapping(source = "grade.code", target = "gradeCode")
    @Mapping(source = "grade.description", target = "gradeDescription", qualifiedByName = "translateDescription")
    public abstract SiteGradeDTO toDTO(SiteGrade entity, @Context String locale);

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<SiteGradeDTO> toDTO(List<SiteGrade> entityList, @Context String locale);

    @Mapping(target = "id", ignore = true)
    public abstract SiteGrade fromDTO(SiteGradeDTO dto, @Context String locale);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntity(@MappingTarget SiteGrade entity, SiteGradeDTO dto, @Context String locale);

}
