package com.petrotec.documentms.mappers;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityNotFoundException;

import java.util.List;

import com.petrotec.documentms.dtos.site.SiteDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemSiteDTO;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteGroupItem;
import com.petrotec.documentms.entities.SiteGroupItemSite;
import com.petrotec.documentms.repositories.SiteRepository;

import org.mapstruct.*;

@Mapper(componentModel = "jsr330", uses = { SiteMapper.class, RegionMapper.class, SiteProfileMapper.class })
public abstract class SiteGroupItemSiteMapper {
	@Inject
	protected SiteRepository siteRepository;

	@Inject
	protected Provider<SiteGroupItemMapper> siteGroupItemMapper;

	@IterableMapping(qualifiedByName = "toDTO")
	public abstract List<SiteGroupItemSiteDTO> toDTO(List<SiteGroupItemSite> entities, @Context String locale, @Context boolean detailed);

	@Named("toDTO")
	@Mapping(target = "siteGroupItem", expression = "java(siteGroupItemMapper.get().toDTOIgnoringChildrenAndParentAndSites(entity.getSiteGroupItem(), locale, detailed))")
	public abstract SiteGroupItemSiteDTO toDTO(SiteGroupItemSite entity, @Context String locale, @Context boolean detailed);

	@Named("toDTOIgnoringItem")
	@IterableMapping(qualifiedByName = "toDTOIgnoringItem")
	public abstract List<SiteGroupItemSiteDTO> toDTOIgnoringItem(List<SiteGroupItemSite> entities,
			@Context String locale);

	@Named("toDTOIgnoringItem")
	@Mapping(target = "siteGroupItem", ignore = true)
	public abstract SiteGroupItemSiteDTO toDTOIgnoringItem(SiteGroupItemSite entity, @Context String locale);

	@IterableMapping(qualifiedByName = "fromDTO")
	public abstract List<SiteGroupItemSite> fromDTO(List<SiteGroupItemSiteDTO> itemSites,
			@Context SiteGroupItem siteGroupItem);

	@Named("fromDTO")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdOn", ignore = true)
	@Mapping(target = "siteGroupItem", expression = "java(getSite(dto.getSite()))")
	@Mapping(target = "site", expression = "java(getSite(dto.getSite()))")
	public SiteGroupItemSite fromDTO(SiteGroupItemSiteDTO dto, @Context SiteGroupItem siteGroupItem) {
		if (dto == null || siteGroupItem == null) return null;
		SiteGroupItemSite entity = new SiteGroupItemSite();
		entity.setSiteGroupItem(siteGroupItem);
		entity.setSite(getSite(dto.getSite()));
		return entity;
	}

	public void update(@MappingTarget SiteGroupItemSite entity, SiteGroupItemSiteDTO dto) {
		if (dto == null || dto.getSite() == null) return;
		entity.setSite(getSite(dto.getSite()));
	}

	public Site getSite(SiteDTO site) {
		return siteRepository.findByCode(site.getCode())
				.orElseThrow(() -> new EntityNotFoundException("Site with code " + site.getCode() + " wasn't found"));
	}
}