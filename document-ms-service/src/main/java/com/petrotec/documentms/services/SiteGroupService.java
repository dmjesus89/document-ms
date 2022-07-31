package com.petrotec.documentms.services;

import com.petrotec.service.util.HttpUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.security.AccessControlException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.api.filter.logic.And;
import com.petrotec.api.filter.logic.Compare.GreaterOrEqual;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupDTO;
import com.petrotec.documentms.entities.SiteGroup;
import com.petrotec.documentms.entities.SiteGroupCustom;
import com.petrotec.documentms.mappers.SiteGroupMapper;
import com.petrotec.documentms.repositories.interfaces.SiteGroupRepository;


import io.micronaut.core.util.StringUtils;

@Singleton
@Transactional
public class SiteGroupService {
	private static final Logger LOG = LoggerFactory.getLogger(SiteGroupService.class);
	private static final int MIN_ROOT_RANK_ORDER = 1;

	private final SiteGroupRepository siteGroupRepository;
	private final SiteGroupMapper siteGroupMapper;

	public SiteGroupService(SiteGroupRepository siteGroupRepository, SiteGroupMapper siteGroupMapper) {
		this.siteGroupRepository = siteGroupRepository;
		this.siteGroupMapper = siteGroupMapper;
	}

	/** According to JIRA issue CLOUDAPPDEV-279, if user is not ROOT, SiteGroup MUST BE same rank_order value as logged
	 * user
	 *
	 * @param siteGroupDTO */
	public static void applyEntityRestrictions(SiteGroupDTO siteGroupDTO) {
		int rankOrder = Integer.parseInt(HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER));
		String entityCode = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_ENTITY);
		if (rankOrder > MIN_ROOT_RANK_ORDER) {
			LOG.info("User rank order ({}) is below min root rank order ({}). Ignoring sitegroup object {}", rankOrder,
					MIN_ROOT_RANK_ORDER, PCSConstants.ATTR_RANK_ORDER);
			siteGroupDTO.setEntityRankOrder(rankOrder);
			siteGroupDTO.setEntityCode(entityCode);
		}
		else {
			LOG.info("Logged user is root. Using sitegroup object rank order ({})", siteGroupDTO.getEntityRankOrder());
			if (StringUtils.isEmpty(siteGroupDTO.getEntityCode())) {
				LOG.info("User didn't specify any entity code. Setting logged user entity code ({})", entityCode);
				siteGroupDTO.setEntityCode(entityCode);
			}
		}
	}

	/** Checks if current logged user has permission to modify siteGroup. Rules according to JIRA issue CLOUDAPPDEV-279
	 *
	 * @param siteGroup
	 * @return true if user is root or siteGroup rank_order is above user rank_order */
	public static void verifyPermission(SiteGroup siteGroup) throws IllegalArgumentException, AccessControlException {
		int loggedRank = Integer.parseInt(HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER));
		LOG.info("Verifying permissions that logged user (with {} {}) has to siteGroup {} with {} of {}",
				PCSConstants.ATTR_RANK_ORDER, loggedRank, siteGroup.getCode(), PCSConstants.ATTR_RANK_ORDER,
				siteGroup.getEntityRankOrder());
		if (loggedRank > MIN_ROOT_RANK_ORDER && loggedRank > siteGroup.getEntityRankOrder()) {
			String errorMsg = "Current logged user, with " + PCSConstants.ATTR_RANK_ORDER + " " + loggedRank
					+ " doesn't have permission to update siteGroup " + siteGroup.getCode() + " with "
					+ PCSConstants.ATTR_RANK_ORDER + " " + siteGroup.getEntityRankOrder();
			LOG.warn(errorMsg);
			throw new AccessControlException(errorMsg);
		}
	}

	public PageResponse<SiteGroupDTO> getSiteGroups(PageAndSorting pageAndSorting, Filter filters) {
		LOG.debug("Retrieving site groups with pageSorting {} and filters {}", pageAndSorting, filters);

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		int rankOrder = Integer.parseInt(HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER));

		// If not root, only display viewable by user rank order restriction
		if (rankOrder > MIN_ROOT_RANK_ORDER) {
			GreaterOrEqual restriction = new GreaterOrEqual("entity_rank_order", rankOrder);
			// If filter exists, creates AND, else just filter by rank order
			filters = Optional.ofNullable(filters).map(f -> (Filter) new And(f, restriction)).orElse(restriction);
		}

		int limit = pageAndSorting.getLimit() + 1;
		List<SiteGroupCustom> siteGroups = siteGroupRepository.findAll(filters, pageAndSorting.getOffset(), limit,
				pageAndSorting.getSorting());
		LOG.debug("List of site groups found: {}", siteGroups);
		List<SiteGroupDTO> dtos = siteGroupMapper.toDTO(siteGroupMapper.customToEntities(siteGroups), locale, false);

		final Filter appliedFilter = filters;
		PageResponse<SiteGroupDTO> pageResponse = PageResponse.from(dtos, pageAndSorting,
				() -> siteGroupRepository.count(appliedFilter));
		LOG.trace("Page response built is {}", pageResponse);
		return pageResponse;
	}

	public SiteGroupDTO getSiteGroup(String groupCode) {
		LOG.info("Retrieving siteGroup with code " + groupCode);

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		Optional<SiteGroup> siteGroup = siteGroupRepository.findByCode(groupCode);
		if (!siteGroup.isPresent()) {
			LOG.info("Can't retrieve Site Group. Site Group with code " + groupCode + " doesn't exist");
			throw new EntityExistsException("Site Group with code " + groupCode + " doesn't exist");
		}
		// Applies restrictions to updating details
		SiteGroupDTO savedDTO = siteGroupMapper.toDTO(siteGroup.get(), locale, true);
		return savedDTO;
	}

	/** Creates a new site group
	 *
	 * @param groupDTO
	 * @return
	 * @throws Exception */
	public SiteGroupDTO createSiteGroup(SiteGroupDTO groupDTO) {
		
		if (groupDTO.getCode() == null || groupDTO.getCode().length() == 0)
		{
			groupDTO.setCode(UUID.randomUUID().toString());
		}
		
		LOG.info("Creating a new siteGroup with code " + groupDTO.getCode());

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		String groupCode = groupDTO.getCode();

		if (siteGroupRepository.findByCode(groupCode).isPresent()) {
			LOG.info("Can't create new Site Group. Site Group with code " + groupCode + " already exists");
			throw new EntityExistsException("Site Group with code " + groupCode + " already exists");
		}
		// Applies restrictions to updating details
		applyEntityRestrictions(groupDTO);

		LOG.debug("Creating a new siteGroup: {}", groupDTO);
		SiteGroup entity = siteGroupMapper.fromDTO(groupDTO,locale);
		LOG.debug("Saving entity {}", entity);
		SiteGroup savedEntity = siteGroupRepository.save(entity);
		LOG.info("A new SiteGroup has been created with code {}", savedEntity.getCode());
		SiteGroupDTO savedDTO = siteGroupMapper.toDTO(savedEntity, locale, true);
		return savedDTO;
	}

	public SiteGroupDTO updateSiteGroup(String groupCode, SiteGroupDTO groupDTO) {
		LOG.info("Updating siteGroup with code " + groupCode);

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		SiteGroup siteGroup = siteGroupRepository.findByCode(groupCode)
				.orElseThrow(() -> new EntityNotFoundException("No siteGroup found with code " + groupCode));

		// Verify if logged user has permissions to modify this siteGroup
		verifyPermission(siteGroup);

		// Applies restrictions to updating details
		applyEntityRestrictions(groupDTO);

		siteGroupMapper.update(siteGroup, groupDTO,locale);
		LOG.trace("SiteGroup entity after updates: {}", siteGroup);

		SiteGroup updatedEntity = siteGroupRepository.update(siteGroup);
		LOG.info("SiteGroup {} has been succesfully updated", updatedEntity);

		SiteGroupDTO updatedDTO = siteGroupMapper.toDTO(updatedEntity, locale, true);
		return updatedDTO;
	}

	public void disableByCode(String groupCode)
			throws EntityNotFoundException, AccessControlException, IllegalArgumentException {
		LOG.info("Disabling siteGroup with code " + groupCode);
		SiteGroup siteGroup = siteGroupRepository.findByCode(groupCode)
				.orElseThrow(() -> new EntityNotFoundException("No siteGroup found with code " + groupCode));

		// Verify if logged user has permissions to modify this siteGroup
		verifyPermission(siteGroup);

		siteGroup.setEnabled(false);
		siteGroupRepository.update(siteGroup);
		LOG.info("Site group {} sucessfully disabled", groupCode);
		return;
	}

	public SiteGroup getSiteGroupByCode(String code) {
		return siteGroupRepository.findByCode(code)
				.orElseThrow(() -> new EntityNotFoundException("Notifiable does not exist"));
	}

	public SiteGroupDTO getSiteGroupDTOByCode(String code, String locale) {
		return this.siteGroupMapper.toDTO(this.getSiteGroupByCode(code), locale, true);
	}

	public List<SiteGroup> getSiteGroupsByCodes(List<String> codes) {
		return siteGroupRepository.findByCode(codes);
	}

	public List<SiteGroupDTO> getSiteGroupDTOsByCodes(List<String> codes, String locale) {
		return this.siteGroupMapper.toDTO(this.getSiteGroupsByCodes(codes), locale, true);
	}

}
