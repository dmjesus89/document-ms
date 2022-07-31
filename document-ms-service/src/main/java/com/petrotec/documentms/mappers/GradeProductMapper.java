package com.petrotec.documentms.mappers;


import com.petrotec.documentms.dtos.grade.GradeProductDTO;
import com.petrotec.documentms.entities.GradeProduct;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "jsr330")
public abstract class GradeProductMapper {

    @Named("toDTO")
    @Mapping(target = "productCode", source = "product.code")
    @Mapping(target = "gradeCode", source = "grade.code")
    public abstract GradeProductDTO toDTO(GradeProduct entity, @Context String locale);

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<GradeProductDTO> toDTO(Set<GradeProduct> entityList, @Context String locale);

}
