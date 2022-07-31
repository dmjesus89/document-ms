package com.petrotec.documentms.services;

import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageResponse;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.site.SiteDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemSiteDTO;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteGroup;
import com.petrotec.documentms.entities.SiteGroupItem;
import com.petrotec.documentms.entities.SiteGroupItemSite;
import com.petrotec.documentms.mappers.SiteGroupItemMapper;
import com.petrotec.documentms.mappers.SiteGroupItemSiteMapper;
import com.petrotec.documentms.repositories.interfaces.SiteGroupItemRepository;
import com.petrotec.documentms.repositories.interfaces.SiteGroupItemSiteRepository;
import com.petrotec.documentms.repositories.interfaces.SiteGroupRepository;
import com.petrotec.documentms.repositories.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@Transactional
public class SiteGroupItemService {
	private static final Logger LOG = LoggerFactory.getLogger(SiteGroupItemService.class);

	private final SiteRepository siteRepository;
	private final SiteGroupRepository siteGroupRepository;
	private final SiteGroupItemRepository siteGroupItemRepository;
	private final SiteGroupItemSiteRepository siteGroupItemSiteRepository;
	private final SiteGroupItemMapper siteGroupItemMapper;
	private final SiteGroupItemSiteMapper siteGroupItemSiteMapper;

	public SiteGroupItemService(SiteRepository siteRepository, SiteGroupRepository siteGroupRepository,
			SiteGroupItemRepository siteGroupItemRepository, SiteGroupItemSiteRepository siteGroupItemSiteRepository,
			SiteGroupItemMapper siteGroupItemMapper, SiteGroupItemSiteMapper siteGroupItemSiteMapper) {
		this.siteRepository = siteRepository;
		this.siteGroupRepository = siteGroupRepository;
		this.siteGroupItemRepository = siteGroupItemRepository;
		this.siteGroupItemSiteRepository = siteGroupItemSiteRepository;
		this.siteGroupItemMapper = siteGroupItemMapper;
		this.siteGroupItemSiteMapper = siteGroupItemSiteMapper;
	}

	public PageResponse<SiteGroupItemDTO> getSiteGroupItems(String groupCode) {
		LOG.info("Retrieving site group items for site group " + groupCode);
		int rankOrder = Integer.parseInt(HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER));

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		List<SiteGroupItem> entities = siteGroupItemRepository.findBySiteGroupCodeAndRankOrderBiggerThan(groupCode,
				rankOrder);
		List<SiteGroupItemDTO> dtos = siteGroupItemMapper.toDTO(entities, locale, true);
		LOG.debug("Found {} SiteGroupItems for group {}", dtos.size(), groupCode);

		// We always return the complete list of site groups
		PageResponse<SiteGroupItemDTO> pageResponse = new PageResponse<>();
		pageResponse.setItems(dtos);
		pageResponse.setLast(true);
		pageResponse.setSize(dtos.size());

		return pageResponse;
	}

	public SiteGroupItemDTO createSiteGroupItem(String siteGroupCode, SiteGroupItemDTO dto) {
		if (dto.getCode() == null || dto.getCode().length() == 0)
		{
			dto.setCode(UUID.randomUUID().toString());
		}

		LOG.info("Creating a new site group item with code {} with site group {}", dto.getCode(), siteGroupCode);

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		SiteGroup siteGroup = siteGroupRepository.findByCode(siteGroupCode).orElseThrow(
				() -> new EntityNotFoundException("Site group with code " + siteGroupCode + " wasn't found"));

		// Verify if user has permissions
		SiteGroupService.verifyPermission(siteGroup);

		// Check if already exists
		if (siteGroupItemRepository.findByCodeAndSiteGroup(dto.getCode(), siteGroup).isPresent()) {
			String errorMsg = "Site group item with code " + dto.getCode() + " and site group code " + siteGroupCode
					+ " already exists";
			EntityExistsException exception = new EntityExistsException(errorMsg);
			LOG.info(errorMsg);
			throw exception;
		}

		LOG.debug("Generating site group item entity from received dto {}", dto);
		SiteGroupItem entity = siteGroupItemMapper.fromDTO(dto, siteGroup,locale);
		SiteGroupItem savedEntity = siteGroupItemRepository.save(entity);
		LOG.info("New site group item with code {} and site group {} successfully saved with id {}",
				savedEntity.getCode(), siteGroupCode, savedEntity.getId());
		SiteGroupItemDTO savedDTO = siteGroupItemMapper.toDTO(savedEntity, locale, true);
		LOG.debug("Returning saved entity as dto {}", savedDTO);
		return savedDTO;
	}

	public SiteGroupItemDTO updateSiteGroupItem(String siteGroupCode, String itemCode,
			SiteGroupItemDTO siteGroupItemDTO) throws Exception {
		LOG.info("Updating site group item code {} from site group {}", itemCode, siteGroupCode);

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		SiteGroup siteGroup = siteGroupRepository.findByCode(siteGroupCode)
				.orElseThrow(() -> new IllegalArgumentException("No siteGroup found with code " + siteGroupCode));

		// Verify if logged user has permissions to modify this siteGroup
		SiteGroupService.verifyPermission(siteGroup);

		// Check if updated entity exists
		SiteGroupItem itemEntity = siteGroupItemRepository.findByCodeAndSiteGroup(itemCode, siteGroup)
				.orElseThrow(() -> new EntityNotFoundException("Site group item with code " + itemCode
						+ " and site group code " + siteGroupCode + " wasn't found"));
		LOG.trace("Existing site group item {}", itemEntity);
		LOG.debug("Updating existing item with data from dto {}", siteGroupItemDTO);
		SiteGroupItem sgi = itemEntity.getParent();
		siteGroupItemMapper.update(itemEntity, siteGroupItemDTO, siteGroup,locale);
		itemEntity.setParent(sgi);

		SiteGroupItem updatedEntity = siteGroupItemRepository.save(itemEntity);
		LOG.info("Site group item successfully updated");
		
		SiteGroupItemDTO updatedDTO = siteGroupItemMapper.toDTO(updatedEntity, locale, true);
		LOG.debug("Returning updated dto object {}", updatedDTO);
		return updatedDTO;
	}

	public void deleteSiteGroupItem(String groupCode, String itemCode)
			throws EntityNotFoundException, IllegalArgumentException {
		LOG.info("Deleting site group item {} from group item {}", itemCode, groupCode);

		SiteGroup siteGroup = siteGroupRepository.findByCode(groupCode)
				.orElseThrow(() -> new EntityNotFoundException("No siteGroup found with code " + groupCode));

		// Verify if logged user has permissions to modify this siteGroup
		SiteGroupService.verifyPermission(siteGroup);

		SiteGroupItem siteGroupItem = siteGroupItemRepository.findByCodeAndSiteGroup(itemCode, siteGroup)
				.orElseThrow(() -> new EntityNotFoundException("Site group item with code " + itemCode
						+ " and site group code " + groupCode + " wasn't found"));

		LOG.debug("Deleting site group item entity: {}", siteGroupItem);
		siteGroupItemRepository.delete(siteGroupItem);
		LOG.debug("Site group item {} from site group {} deleted with success", itemCode, groupCode);
		return;
	}

	public SiteGroupItem getSiteGroupItem(String groupCode, String itemCode) {
		LOG.info("Getting site group {} item {}", groupCode, itemCode);

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		LOG.debug("User locale is {}", locale);

		SiteGroupItem siteGroupItem = siteGroupItemRepository.findByCodeAndSiteGroup(itemCode, groupCode)
				.orElseThrow(() -> new EntityNotFoundException("Site group item with code " + itemCode
						+ " and site group code " + groupCode + " wasn't found"));

		// Verify if logged user has permissions to modify this siteGroup
		SiteGroupService.verifyPermission(siteGroupItem.getSiteGroup());

		return siteGroupItem;
	}

	public SiteGroupItemDTO getSiteGroupItemDTO(String groupCode, String itemCode) {
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		LOG.debug("User locale is {}", locale);
		return siteGroupItemMapper.toDTO(this.getSiteGroupItem(groupCode, itemCode), locale, true);
	}

	public PageResponse<SiteGroupItemSiteDTO> addSiteToSiteGroupItem(String groupCode, String itemCode,
			List<SiteDTO> sites) throws IllegalArgumentException, EntityNotFoundException {
		LOG.info("Adding sites to site group item {} from group item {}", itemCode, groupCode);

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		LOG.debug("User locale is {}", locale);

		SiteGroup siteGroup = siteGroupRepository.findByCode(groupCode)
				.orElseThrow(() -> new EntityNotFoundException("No siteGroup found with code " + groupCode));

		// Verify if logged user has permissions to modify this siteGroup
		SiteGroupService.verifyPermission(siteGroup);

		SiteGroupItem siteGroupItem = siteGroupItemRepository.findByCodeAndSiteGroup(itemCode, siteGroup)
				.orElseThrow(() -> new EntityNotFoundException("Site group item with code " + itemCode
						+ " and site group code " + groupCode + " wasn't found"));

		List<String> sitesCodes = sites.stream().map(SiteDTO::getCode).collect(Collectors.toList());
		LOG.debug("Adding sites ({}) to site group item {} and site group {}", sitesCodes, itemCode, groupCode);
		List<Site> siteEntities = siteRepository.findByCodeAndEnabled(sitesCodes);

		//TODO validate if site is legit
		List<String> existingSiteCodes = siteEntities.stream().map(Site::getCode).collect(Collectors.toList());
		for (String siteCode : sitesCodes) {
			if (!existingSiteCodes.contains(siteCode))
				throw new EntityNotFoundException("Site " + siteCode + " is not valid");
		}

		List<SiteGroupItemSite> siteGroupItemSites = new ArrayList<>();
		for (Site site : siteEntities) {
			SiteGroupItemSite siteGroupItemSite = new SiteGroupItemSite();
			siteGroupItemSite.setSite(site);
			siteGroupItemSite.setSiteGroupItem(siteGroupItem);
			siteGroupItemSites.add(siteGroupItemSite);
		}

		LOG.trace("Saving site group item sites {}", siteGroupItemSites);
		List<SiteGroupItemSite> savedEntities = siteGroupItemSiteRepository.saveAll(siteGroupItemSites);
		LOG.debug("Successfully saved {} site group item sites", savedEntities.size());
		if (LOG.isTraceEnabled()) {
			LOG.trace("Saved site group item sites: {}", savedEntities);
		}
		List<SiteGroupItemSiteDTO> savedDtos = siteGroupItemSiteMapper.toDTO(siteGroupItemSites, locale, true);

		// We always return the complete list of site groups
		PageResponse<SiteGroupItemSiteDTO> pageResponse = new PageResponse<>();
		pageResponse.setItems(savedDtos);
		pageResponse.setLast(true);
		pageResponse.setSize(savedDtos.size());

		return pageResponse;
	}

	public PageResponse<SiteGroupItemSiteDTO> getSiteGroupItemSite(String groupCode, String itemCode)
			throws EntityNotFoundException, IllegalArgumentException, AccessControlException {
		LOG.info("Retrieving SiteGroupItemSite from Site Group " + groupCode + " and Site Group Item " + itemCode);

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		LOG.debug("User locale is {}", locale);

		SiteGroup siteGroup = siteGroupRepository.findByCode(groupCode)
				.orElseThrow(() -> new EntityNotFoundException("No siteGroup found with code " + groupCode));

		// Verify if logged user has permissions to view this siteGroup
		SiteGroupService.verifyPermission(siteGroup);

		List<SiteGroupItemSite> entities = siteGroupItemSiteRepository.findAllWithSiteEnabledBy(groupCode, itemCode);
		LOG.trace("Found entities: {}", entities);

		List<SiteGroupItemSiteDTO> dtos = siteGroupItemSiteMapper.toDTO(entities, locale, true);
		LOG.debug("Returning found SiteGroupItemSites ()", dtos.size());

		// We always return the complete list of site groups
		PageResponse<SiteGroupItemSiteDTO> pageResponse = new PageResponse<>();
		pageResponse.setItems(dtos);
		pageResponse.setLast(true);
		pageResponse.setSize(dtos.size());

		return pageResponse;
	}

	public void deleteSiteGroupItemSite(String groupCode, String itemCode, String siteCode)
			throws IllegalArgumentException, EntityNotFoundException, AccessControlException {
		LOG.info("Deleting SiteGroupItemSite with site " + siteCode + " and Site Group " + groupCode
				+ " and Site Group Item " + itemCode);

		SiteGroup siteGroup = siteGroupRepository.findByCode(groupCode)
				.orElseThrow(() -> new EntityNotFoundException("No siteGroup found with code " + groupCode));

		// Verify if logged user has permissions to modify this siteGroup
		SiteGroupService.verifyPermission(siteGroup);

		SiteGroupItemSite siteGroupItemSite = siteGroupItemSiteRepository.findBy(groupCode, itemCode, siteCode)
				.orElseThrow(() -> new EntityNotFoundException("No SiteGroupItemSite found with site " + siteCode
						+ " and Site Group " + groupCode + " and Site Group Item " + itemCode));
		siteGroupItemSiteRepository.delete(siteGroupItemSite);
		LOG.info("SiteGroupItemSite successfully deleted");
		return;
	}

	public List<SiteGroupItem> getSiteGroupItemByCodes(List<String> siteGroupItemCodes){
		return siteGroupItemRepository.findByCodes(siteGroupItemCodes);
	}

	public List<SiteGroupItemDTO> getSiteGroupItemByCodes(List<String> siteGroupItemCodes, String locale){
		List<SiteGroupItem> siteGroupItems = this.getSiteGroupItemByCodes(siteGroupItemCodes);
		return siteGroupItemMapper.toDTO(siteGroupItems, locale, true);
	}

}
