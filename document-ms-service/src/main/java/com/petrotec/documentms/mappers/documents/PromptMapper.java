package com.petrotec.documentms.mappers.documents;


import com.petrotec.documentms.dtos.documents.PromptDTO;
import com.petrotec.documentms.entities.documents.Prompt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "jsr330")
public interface PromptMapper {

    @Mapping(target = "description", ignore = true)
    @Mapping(target = "detailedDescription", source = "description")
    PromptDTO toDTO(Prompt entity);

    List<PromptDTO> toDTO(List<Prompt> entityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "description", source = "detailedDescription")
    Prompt fromDTO(PromptDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "description", source = "detailedDescription")
    void updateEntity(@MappingTarget Prompt entity, PromptDTO dto);
}
