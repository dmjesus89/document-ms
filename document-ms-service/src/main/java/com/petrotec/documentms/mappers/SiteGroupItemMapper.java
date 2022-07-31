package com.petrotec.documentms.mappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemSiteDTO;
import com.petrotec.documentms.entities.SiteGroup;
import com.petrotec.documentms.entities.SiteGroupItem;
import com.petrotec.documentms.entities.SiteGroupItemSite;
import com.petrotec.documentms.repositories.interfaces.SiteGroupItemRepository;

import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import io.micronaut.core.util.CollectionUtils;

/** SiteGroupItemMapper */
@Mapper(componentModel = "jsr330", uses = { SiteMapper.class, SiteGroupItemSiteMapper.class, SiteGroupMapper.class, TranslateMapper.class })
public abstract class SiteGroupItemMapper {

    @Inject
    public ObjectMapper objectMapper;
    
    @Inject
    protected SiteGroupItemRepository siteGroupItemRepository;

    @Inject
    protected SiteGroupItemSiteMapper siteGroupItemSiteMapper;

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<SiteGroupItemDTO> toDTO(List<SiteGroupItem> entities, @Context String locale, @Context boolean detailed);

    @Named(value = "toDTO")
    @Mapping(target = "itemSites", source = "siteGroupItemSite", qualifiedByName = "toDTOIgnoringItem")
    @Mapping(target = "children", expression = "java(toDTO(entity.getChildren() ,locale,detailed))")
    @Mapping(target = "parent", source = "parent", qualifiedByName = "toDTOIgnoringChildrenAndParent")
    @Mapping(target = "siteGroup", source = "siteGroup", qualifiedByName = "toDTOIgnoringItems")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    @Mapping(target = "detailedDescription", source = "description")
    public abstract SiteGroupItemDTO toDTO(SiteGroupItem entity, @Context String locale, @Context boolean detailed);

    @Named(value = "toDTOIgnoringChildrenAndParent")
    @IterableMapping(qualifiedByName = "toDTOIgnoringChildrenAndParent")
    protected abstract List<SiteGroupItemDTO> toDTOIgnoringChildrenAndParent(List<SiteGroupItem> entity,@Context String locale, @Context boolean detailed);

    @Named(value = "toDTOIgnoringChildrenAndParent")
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "itemSites", source = "siteGroupItemSite", qualifiedByName = "toDTOIgnoringItem")
    @Mapping(target = "siteGroup", source = "siteGroup", qualifiedByName = "toDTOIgnoringItems")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    @Mapping(target = "detailedDescription", source = "description")
    protected abstract SiteGroupItemDTO toDTOIgnoringChildrenAndParent(SiteGroupItem entity, @Context String locale, @Context boolean detailed);

    @Named(value = "toDTOIgnoringChildrenAndParentAndSites")
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "itemSites", ignore = true)
    @Mapping(target = "siteGroup", source = "siteGroup", qualifiedByName = "toDTOIgnoringItems")
    @Mapping(target = "description", source = "description", qualifiedByName = "translateDescription")
    @Mapping(target = "detailedDescription", source = "description")
    public abstract SiteGroupItemDTO toDTOIgnoringChildrenAndParentAndSites(SiteGroupItem entity, @Context String locale, @Context boolean detailed);

    public List<SiteGroupItem> fromDTO(List<SiteGroupItemDTO> dtos, @Context SiteGroup siteGroup, @Context String locale) {
        if (CollectionUtils.isEmpty(dtos)) return null;
        return dtos.stream().map(dto -> fromDTO(dto, siteGroup,locale)).collect(Collectors.toList());
    }

    public List<SiteGroupItem> fromDTOChild(List<SiteGroupItemDTO> dtos, @Context SiteGroup siteGroup,
            @Context SiteGroupItem siteGroupItem, @Context String locale) {
        if (CollectionUtils.isEmpty(dtos)) return null;
        return dtos.stream().map(dto -> fromDTOChild(dto, siteGroup, siteGroupItem,locale)).collect(Collectors.toList());
    }

    @Named(value = "fromDTO")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "code", source = "dto.code")
    @Mapping(target = "siteGroup", source = "siteGroup")
    @Mapping(target = "parent", expression = "java(getParent(siteGroupItem, dto, siteGroup))")
    @Mapping(target = "children", expression = "java(fromDTOChild(dto.getChildren(), siteGroup, siteGroupItem,locale))")
    @Mapping(target = "siteGroupItemSite", expression = "java(siteGroupItemSiteMapper.fromDTO(dto.getItemSites(), siteGroupItem))")
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract SiteGroupItem fromDTO(SiteGroupItemDTO dto, SiteGroup siteGroup, @Context String locale);

    @Named(value = "fromDTOChild")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "code", source = "dto.code")
    @Mapping(target = "leaf", source = "dto.leaf")
    @Mapping(target = "siteGroup", source = "siteGroup")
    @Mapping(target = "parent", source = "parent")
    @Mapping(target = "children", expression = "java(fromDTOChild(dto.getChildren(), siteGroup, siteGroupItem, locale))")
    @Mapping(target = "siteGroupItemSite", expression = "java(siteGroupItemSiteMapper.fromDTO(dto.getItemSites(), siteGroupItem))")
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract SiteGroupItem fromDTOChild(SiteGroupItemDTO dto, SiteGroup siteGroup, SiteGroupItem parent, @Context String locale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "leaf", ignore = true)
    @Mapping(target = "code", source = "dto.code")
    @Mapping(target = "siteGroup", source = "siteGroup")
    @Mapping(target = "parent", expression = "java(getParent(entity, dto, siteGroup))")
    @Mapping(target = "siteGroupItemSite", expression = "java(updateItemSite(dto.getItemSites(), entity))")
    @Mapping(target = "description", expression = "java(translateMapper.setDescription(entity.getDescription(), dto.getDescription(), dto.getDetailedDescription(), locale))")
    public abstract void update(@MappingTarget SiteGroupItem entity, SiteGroupItemDTO dto, SiteGroup siteGroup, @Context String locale);

    public List<SiteGroupItemSite> updateItemSite(List<SiteGroupItemSiteDTO> sites, SiteGroupItem entity) {
        List<SiteGroupItemSite> itemSites = entity.getSiteGroupItemSite();

        if (sites == null || itemSites == null) return itemSites;

        for (SiteGroupItemSiteDTO dto : sites) {
            if (dto.getSite() == null) continue;

            Optional<SiteGroupItemSite> existingEntity = itemSites.stream()
                    .filter(e -> e.getSite().getCode().equals(dto.getSite().getCode())).findFirst();
            if (existingEntity.isPresent()) {
                siteGroupItemSiteMapper.update(existingEntity.get(), dto);
            }
            else {
                SiteGroupItemSite createdEntity = siteGroupItemSiteMapper.fromDTO(dto, entity);
                itemSites.add(createdEntity);
            }
        }
        return itemSites;
    }

    @Named(value = "getParent")
    protected SiteGroupItem getParent(SiteGroupItem siteGroupItem, SiteGroupItemDTO siteGroupItemDTO,
            SiteGroup siteGroup) {
        SiteGroupItemDTO parent = siteGroupItemDTO.getParent();
        if (parent == null) return null;

        if (parent.getCode().equals(siteGroupItem.getCode())) {
            return siteGroupItem;
        }
        return siteGroupItemRepository.findByCodeAndSiteGroup(parent.getCode(), siteGroup.getCode())
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find parent for site group "
                        + siteGroup.getCode() + " and site group item " + parent.getCode()));
    }

}