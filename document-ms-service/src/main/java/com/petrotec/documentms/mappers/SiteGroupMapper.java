package com.petrotec.documentms.mappers;

import javax.inject.Inject;
import javax.inject.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemDTO;
import com.petrotec.documentms.entities.SiteGroup;
import com.petrotec.documentms.entities.SiteGroupCustom;
import com.petrotec.documentms.entities.SiteGroupItem;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import io.micronaut.core.util.CollectionUtils;

/** SiteGroupMapper */
@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class})
public abstract class SiteGroupMapper {

    @Inject
    protected ObjectMapper objectMapper;

    @Inject
    protected TranslateMapper translateMapper;
    
    @Inject
    protected Provider<SiteGroupItemMapper> siteGroupItemMapper;

    public abstract List<SiteGroup> fromDTO(List<SiteGroupDTO> dtos, @Context String locale);

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "items", expression = "java(siteGroupItemMapper.get().fromDTO(dto.getItems(), siteGroup, locale))")
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract SiteGroup fromDTO(SiteGroupDTO dto, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "items", expression = "java(updateItems(entity, dto, locale))")
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(entity.getDescription(), dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract void update(@MappingTarget SiteGroup entity, SiteGroupDTO dto, @Context String locale);

    public abstract List<SiteGroupDTO> toDTO(List<SiteGroup> entities, @Context String locale, @Context boolean detailed);

    public abstract List<SiteGroup> customToEntities(List<SiteGroupCustom> customEntities);

    @Mapping(target = "items", expression = "java(siteGroupItemMapper.get().toDTO(entity.getItems() ,locale,detailed))")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    @Mapping(target = "detailedDescription", source = "description")
    public abstract SiteGroupDTO toDTO(SiteGroup entity, @Context String locale, @Context boolean detailed);

    @Named("toDTOIgnoringItems")
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    @Mapping(target = "detailedDescription", source = "description")
    public abstract SiteGroupDTO toDTOIgnoringItems(SiteGroup entity, @Context String locale, @Context boolean detailed);

    @Named("updateItems")
    public List<SiteGroupItem> updateItems(SiteGroup entity, SiteGroupDTO dto, @Context String locale) {
        if (CollectionUtils.isEmpty(dto.getItems())) {
            return null;
        }

        List<SiteGroupItem> items = Optional.ofNullable(entity.getItems()).orElse(new ArrayList<>());

        for (SiteGroupItemDTO item : dto.getItems()) {
            Optional<SiteGroupItem> sgi = items.stream().filter(sg -> sg.getCode().equals(item.getCode())).findFirst();
            if (sgi.isPresent()) {
                siteGroupItemMapper.get().update(sgi.get(), item, entity,locale);
            }
            else {
                items.add(siteGroupItemMapper.get().fromDTO(item, entity,locale));
            }
        }
        return items;
    }

}