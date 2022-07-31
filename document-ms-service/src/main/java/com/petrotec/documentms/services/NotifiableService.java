package com.petrotec.documentms.services;

import com.petrotec.api.*;
import com.petrotec.api.filter.Filter;
import com.petrotec.api.filter.logic.And;
import com.petrotec.api.filter.logic.Compare.GreaterOrEqual;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.*;
import com.petrotec.documentms.dtos.site.SiteCustomDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemDTO;
import com.petrotec.documentms.entities.Notifiable;
import com.petrotec.documentms.entities.*;
import com.petrotec.documentms.mappers.*;
import com.petrotec.documentms.repositories.*;
import com.petrotec.documentms.repositories.interfaces.*;
import io.micronaut.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.micronaut.http.HttpStatus.*;

import javax.inject.Singleton;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.security.AccessControlException;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
@Transactional
public class NotifiableService {

	private static final Logger LOG = LoggerFactory.getLogger(NotifiableService.class);
	private static final int MIN_ROOT_RANK_ORDER = 1;

	private final SiteRepository siteRepository;
	private final SiteGroupItemRepository siteGroupItemRepository;

	private final NotifiableMethodRepository notifiableMethodRepository;
	private final NotifiableRepository notifiableRepository;
	private final NotifiableElementRepository notifiableElementRepository;
	private final NotifiableElementContactRepository notifiableElementContactRepository;
	private final NotifiableSiteRepository notifiableSiteRepository;
	private final NotifiableSiteGroupItemRepository notifiableSiteGroupItemRepository;

	private final NotifiableMapper notifiableMapper;
	private final NotifiableElementMapper notifiableElementMapper;
	private final NotifiableElementContactMapper notifiableElementContactMapper;
	private final NotifiableSiteMapper notifiableSiteMapper;
	private final NotifiableSiteGroupMapper notifiableSiteGroupMapper;
	private final NotifiableMethodMapper notifiableMethodMapper;

	private final SiteService siteService;
	private final SiteGroupItemService siteGroupItemService;
	private final SiteGroupItemMapper siteGroupItemMapper;

	public NotifiableService(SiteRepository siteRepository, NotifiableMethodRepository notifiableMethodRepository,
							 NotifiableElementRepository notifiableElementRepository,
							 NotifiableElementContactRepository notifiableElementContactRepository,
							 NotifiableRepository notifiableRepository, NotifiableMapper notifiableMapper,
							 NotifiableSiteRepository notifiableSiteRepository,
							 NotifiableSiteGroupItemRepository notifiableSiteGroupItemRepository,
							 NotifiableSiteMapper notifiableSiteMapper, NotifiableSiteGroupMapper notifiableSiteGroupMapper,
							 NotifiableMethodMapper notifiableMethodMapper, NotifiableElementMapper notifiableElementMapper,
							 NotifiableElementContactMapper notifiableElementContactMapper,
							 SiteGroupItemRepository siteGroupItemRepository, SiteService siteService,
							 SiteGroupItemService siteGroupItemService, SiteGroupItemMapper siteGroupItemMapper) {
		this.siteRepository = siteRepository;
		this.siteGroupItemRepository = siteGroupItemRepository;
		this.notifiableMethodRepository = notifiableMethodRepository;
		this.notifiableRepository = notifiableRepository;
		this.notifiableMapper = notifiableMapper;
		this.notifiableElementRepository = notifiableElementRepository;
		this.notifiableElementContactRepository = notifiableElementContactRepository;
		this.notifiableSiteRepository = notifiableSiteRepository;
		this.notifiableSiteGroupItemRepository = notifiableSiteGroupItemRepository;
		this.notifiableSiteMapper = notifiableSiteMapper;
		this.notifiableSiteGroupMapper = notifiableSiteGroupMapper;
		this.notifiableMethodMapper = notifiableMethodMapper;
		this.notifiableElementMapper = notifiableElementMapper;
		this.notifiableElementContactMapper = notifiableElementContactMapper;
		this.siteService = siteService;
		this.siteGroupItemService = siteGroupItemService;
		this.siteGroupItemMapper = siteGroupItemMapper;
	}

	public BaseResponse<PageResponse<NotifiableDTO>> findAll(PageAndSorting pageAndSorting, Filter filters) {
		LOG.debug("Finding all notifiables. Contains filters? {} PageAndSorting: {}", filters != null, pageAndSorting);

		try {
			int rankOrder = Integer.parseInt(com.petrotec.service.util.HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER));

			String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

			// If not root, only display viewable by user rank order restriction
			if (rankOrder > MIN_ROOT_RANK_ORDER) {
				GreaterOrEqual restriction = new GreaterOrEqual("entity_rank_order", rankOrder);
				// If filter exists, creates AND, else just filter by rank order
				filters = Optional.ofNullable(filters).map(f -> (Filter) new And(f, restriction)).orElse(restriction);
			}

			int limit = pageAndSorting.getLimit() + 1;
			List<NotifiableCustom> notifiableList = notifiableRepository.findAll(filters, pageAndSorting.getOffset(),
					limit, pageAndSorting.getSorting());

			if (LOG.isTraceEnabled()) {
				LOG.trace("List of found notifiables: " + notifiableList);
			}
			LOG.debug("Converting notifiables to DTO");
			List<NotifiableDTO> notifiableDTOs = notifiableMapper
					.toDTO(notifiableMapper.customToEntities(notifiableList), locale, false);

			if (LOG.isTraceEnabled()) {
				LOG.trace("List of notifiables dtos: " + notifiableDTOs);
			}
			final Filter appliedFilter = filters;
			PageResponse<NotifiableDTO> pageResponse = PageResponse.from(notifiableDTOs, pageAndSorting,
					() -> notifiableRepository.count(appliedFilter));

			return BaseResponse.success(OK.getReason(), OK.getCode(), "List of notifiables", pageResponse);
		} catch (Exception ex) {
			LOG.error("Error finding all notifiables", ex);
			ApiError apiError = new ApiError(Arrays.asList(ex.getMessage()));
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Error finding notifiables.. ", apiError);
		}
	}

	/** Returns a list of notifiable elements
	 *
	 * @param siteCode      filter by siteCode
	 * @param alarmSeverity filter by alarmSeverity
	 * @param enabled       filter by enabled
	 * @return
	 * @throws IllegalArgumentException
	 * @throws Exception */
	public PageResponse<NotifiableElementDTO> getNotifiableElements(String siteCode, String alarmSeverity,
			Boolean enabled) throws IllegalArgumentException, Exception {
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		LOG.info("Retrieving list of notifiable elements. Filters -> siteCode: {}, alarmSeverity: {}, enabled: {}",
				siteCode, alarmSeverity, enabled);
		List<NotifiableElement> notifiableElements = notifiableElementContactRepository.findAll(siteCode,
				alarmSeverity, enabled);
		LOG.debug("Found {} notifiable elements entities", notifiableElements.size());

		if (notifiableElements != null && notifiableElements.size() > 0) {
			// Adiciona ao inicio para garantir que a Petrotec recebe sempre os emails
			notifiableElements.addAll(0, notifiableElementContactRepository.findAllBelongingToEntity(enabled));
		}
		List<NotifiableElementDTO> dtos = notifiableElementMapper.toDTO(notifiableElements,locale, true);
		LOG.debug("Details of notifiables element dtos: {}", dtos);

		PageResponse<NotifiableElementDTO> pageResponse = new PageResponse<>();
		pageResponse.setItems(dtos);
		pageResponse.setLast(true);
		pageResponse.setSize(dtos.size());
		return pageResponse;
	}

	public NotifiableDTO addNotifiable(NotifiableDTO notifiableDTO) throws Exception {

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		if (notifiableDTO.getCode() == null || notifiableDTO.getCode().length() == 0) {
			notifiableDTO.setCode(UUID.randomUUID().toString());
		}

		LOG.info("Creating notifiable with code " + notifiableDTO.getCode());
		if (notifiableRepository.findByCode(notifiableDTO.getCode()).isPresent()) {
			String errorMsg = "Notifiable with code " + notifiableDTO.getCode() + " already exists";
			LOG.warn(errorMsg);
			throw new EntityExistsException(errorMsg);
		}

		LOG.debug("Received notifiable object is: {}", notifiableDTO);
		applyEntityRestrictions(notifiableDTO);

		Notifiable entity = notifiableMapper.fromDTO(notifiableDTO, locale);
		LOG.debug("Persisting notifiable entity: {}", entity);
		notifiableRepository.save(entity);

		LOG.info("New notifiable ({}) succesfully saved", notifiableDTO.getCode());
		return notifiableMapper.toDTO(entity, locale, true);
	}

	/** According to JIRA issue CLOUDAPPDEV-279, if user is not ROOT, notifiable MUST BE same rank_order value as logged
	 * user
	 *
	 * @param notifiableDTO */
	public static void applyEntityRestrictions(NotifiableDTO notifiableDTO) {
		int rankOrder = Integer.parseInt(HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER));
		String entityCode = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_ENTITY);
		if (rankOrder > MIN_ROOT_RANK_ORDER) {
			LOG.info("User rank order ({}) is below min root rank order ({}). Ignoring notifiable object {}", rankOrder,
					MIN_ROOT_RANK_ORDER, PCSConstants.ATTR_RANK_ORDER);
			notifiableDTO.setEntityRankOrder(rankOrder);
			notifiableDTO.setEntityCode(entityCode);
		}
		else {
			LOG.info("Logged user is root. Using notifiable object rank order ({})",
					notifiableDTO.getEntityRankOrder());
			if (StringUtils.isEmpty(notifiableDTO.getEntityCode())) {
				LOG.info("User didn't specify any entity code. Setting logged user entity code ({})", entityCode);
				notifiableDTO.setEntityCode(entityCode);
			}
		}
	}

	/** Checks if current logged user has permission to modify notifiable. Rules according to JIRA issue CLOUDAPPDEV-279
	 *
	 * @param notifiable
	 * @return true if user is root or notifiable rank_order is above user rank_order */
	public static void verifyPermission(Notifiable notifiable) {
		int loggedRank = Integer.parseInt(HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER));
		LOG.info("Verifying permissions that logged user (with {} {}) has to notifiable {} with {} of {}",
				PCSConstants.ATTR_RANK_ORDER, loggedRank, notifiable.getCode(), PCSConstants.ATTR_RANK_ORDER,
				notifiable.getEntityRankOrder());
		if (loggedRank > MIN_ROOT_RANK_ORDER && loggedRank > notifiable.getEntityRankOrder()) {
			String errorMsg = "Current logged user, with " + PCSConstants.ATTR_RANK_ORDER + " " + loggedRank
					+ " doesn't have permission to update notifiable " + notifiable.getCode() + " with "
					+ PCSConstants.ATTR_RANK_ORDER + " " + notifiable.getEntityRankOrder();
			LOG.warn(errorMsg);
			throw new AccessControlException(errorMsg);
		}
	}

	public NotifiableDTO findByCode(String notifiableCode) throws Exception {
		Notifiable notifiable = notifiableRepository.findByCode(notifiableCode)
				.orElseThrow(() -> new EntityNotFoundException("Notifiable does not exist"));

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		return notifiableMapper.toDTO(notifiable, locale, true);
	}

	/** Updates notifiable with given code
	 *
	 * @param notifiableCode
	 * @param notifiableDTO
	 * @return
	 * @throws Exception */
	public NotifiableDTO update(String notifiableCode, NotifiableDTO notifiableDTO) throws Exception {
		LOG.info("Updating notifiable " + notifiableCode);

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		Notifiable entity = notifiableRepository.findByCode(notifiableCode)
				.orElseThrow(() -> new EntityNotFoundException("Notifiable for code " + notifiableCode + " not found"));

		// Verify if user has permissions to update notifiable
		verifyPermission(entity);

		// Applies restrictions to modifying notifiable
		applyEntityRestrictions(notifiableDTO);

		LOG.debug("Updating notifiable {} with properties from dto: {}", entity, notifiableDTO);
		notifiableMapper.update(entity, notifiableDTO, locale);
		LOG.trace("Persisting updated entity {}", entity);
		Notifiable updatedEntity = notifiableRepository.update(entity);
		LOG.debug("Successfully persisted updated entity: {}", entity);
		return notifiableMapper.toDTO(updatedEntity, locale, true);
	}

	public void delete(String notifiableCode) throws Exception {
		LOG.info("Removing notifiable {}", notifiableCode);
		Notifiable toBeRemoved = notifiableRepository.findByCode(notifiableCode)
				.orElseThrow(() -> new EntityNotFoundException("Notifiable does not exist"));

		// Verify if user has permissions to update notifiable
		verifyPermission(toBeRemoved);

		/*
		 * if (toBeRemoved.getNotifiableElements() != null) { for(NotifiableElement element :
		 * toBeRemoved.getNotifiableElements()) { disableNotifiableElement(notifiableCode, element.getName()); } }
		 */

		List<NotifiableSite> notifiableSites = notifiableSiteRepository.findByNotifiableCode(notifiableCode);
		if (notifiableSites != null && !notifiableSites.isEmpty()) {
			List<String> siteCodes = notifiableSites.stream().map(NotifiableSite::getSiteCode)
					.collect(Collectors.toList());
			removeSitesFromNotifiable(notifiableCode, siteCodes);
		}

		List<NotifiableSiteGroupItem> notifiableSiteGroups = notifiableSiteGroupItemRepository
				.findByNotifiableCodeWithSiteGroupCode(notifiableCode);

		for (NotifiableSiteGroupItem sg : notifiableSiteGroups) {
			List<NotifiableSiteGroupItem> notifiableSiteGroupItems = notifiableSiteGroupItemRepository
					.findByNotifiableCodeAndSiteGroupCode(notifiableCode, sg.getSiteGroupCode());

			List<String> itemCodes = notifiableSiteGroupItems.stream()
					.map(NotifiableSiteGroupItem::getSiteGroupItemCode).collect(Collectors.toList());
			removeSiteGroupItemsFromNotifiable(notifiableCode, sg.getSiteGroupCode(), itemCodes);
		}

		notifiableRepository.delete(toBeRemoved);
		LOG.info("Successfully removed notifiable {}", toBeRemoved);
	}

	public PageResponse<SiteCustomDTO> getSitesFromNotifiable(String notifiableCode) {
		LOG.info("Retrieving list of sites from notifiable.");
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		if (!notifiableRepository.findByCode(notifiableCode).isPresent()) {
			throw new EntityNotFoundException("Notifiable does not exist");
		}

		List<NotifiableSite> notifiableSites = notifiableSiteRepository.findByNotifiableCode(notifiableCode);

		if (notifiableSites.isEmpty()) {
			return PageResponse.empty();
			// throw new EntityNotFoundException("No Sites Associated with Notifiable");
		}

		List<String> siteCodes = notifiableSites.stream().map(NotifiableSite::getSiteCode).collect(Collectors.toList());
		List<SiteCustomDTO> dtos = siteService.findSitesByCodes(siteCodes, locale);

		PageResponse<SiteCustomDTO> pageResponse = new PageResponse<>();
		pageResponse.setItems(dtos);
		pageResponse.setLast(true);
		pageResponse.setSize(dtos.size());
		return pageResponse;
	}

	public List<NotifiableSiteDTO> addSitesToNotifiable(String notifiableCode, List<String> siteCodes)
			throws Exception {
		LOG.info("Adding sites {} to notifiable {}", siteCodes, notifiableCode);
		Notifiable notifiable = notifiableRepository.findByCode(notifiableCode)
				.orElseThrow(() -> new EntityNotFoundException("Notifiable does not exist"));

		// Verify if user has permissions to update notifiable
		verifyPermission(notifiable);

		List<NotifiableSite> associatedSites = notifiableSiteRepository
				.findByNotifiableCodeAndSiteCodeIn(notifiableCode, siteCodes);
		if (!associatedSites.isEmpty())
			throw new EntityExistsException("Sites " + siteCodes.toString() + " already associated to notifiable");

		// TODO check if any site does not exist
		// this is not good makes many calls, this should get all and then evaluate which is missing
		List<String> existingSiteCodes = siteRepository.findByCode(siteCodes).stream().map(Site::getCode)
				.collect(Collectors.toList());
		for (String siteCode : siteCodes) {
			if (!existingSiteCodes.contains(siteCode))
				throw new EntityNotFoundException("Site " + siteCode + " is not enabled");
		}

		List<NotifiableSite> notifiableSites = new ArrayList<>();
		for (String siteCode : siteCodes) {
			NotifiableSite notifiableSite = new NotifiableSite();
			notifiableSite.setNotifiableCode(notifiableCode);
			notifiableSite.setSiteCode(siteCode);
			notifiableSites.add(notifiableSite);
		}

		List<NotifiableSite> savedEntities = notifiableSiteRepository.saveAll(notifiableSites);
		LOG.debug("Successfully saved NotifiableSite with ids", savedEntities.toString());
		return notifiableSiteMapper.toDTO(savedEntities);
	}

	public void removeSitesFromNotifiable(String notifiableCode, List<String> siteCodes) throws Exception {
		LOG.info("Removing sites {} from notifiable {}", siteCodes.toString(), notifiableCode);

		/* TODO better way to do this, this is slow */
		List<NotifiableSite> notifiableSites = new ArrayList<>();
		for (String siteCode : siteCodes) {
			notifiableSites.add(notifiableSiteRepository.findByNotifiableCodeAndSiteCode(notifiableCode, siteCode)
					.orElseThrow(() -> new EntityNotFoundException("NotifiableSite does not exist")));
		}

		/* TODO better way to do this, this is slow */
		for (NotifiableSite notifiableSite : notifiableSites) {
			notifiableSiteRepository.delete(notifiableSite);
		}
		LOG.info("Successfully removed sites {} from notifiable {}", siteCodes.toString(), notifiableCode);
	}

	public PageResponse<SiteGroupDTO> getSiteGroupsFromNotifiable(String notifiableCode) {
		LOG.info("Retrieving list of sitegroups from notifiable.");
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		if (!notifiableRepository.findByCode(notifiableCode).isPresent()) {
			throw new EntityNotFoundException("Notifiable does not exist");
		}

		List<NotifiableSiteGroupItem> notifiableSiteGroupItems = notifiableSiteGroupItemRepository
				.findByNotifiableCodeWithSiteGroupCode(notifiableCode);

		if (notifiableSiteGroupItems.isEmpty()) {
			return PageResponse.empty();
		}

		Map<String, SiteGroupDTO> sgDTOs = new HashMap<>();
		notifiableSiteGroupItems.sort(new Comparator<NotifiableSiteGroupItem>() {
			@Override
			public int compare(NotifiableSiteGroupItem o1, NotifiableSiteGroupItem o2) {
				return (int) (o1.getId() - o2.getId());
			}
		});
		for (NotifiableSiteGroupItem notifiableSiteGroupItem : notifiableSiteGroupItems) {
			SiteGroupDTO currentSiteGroup = null;

			if (sgDTOs.containsKey(notifiableSiteGroupItem.getSiteGroupCode())) {
				currentSiteGroup = sgDTOs.get(notifiableSiteGroupItem.getSiteGroupCode());
			}
			else {
				currentSiteGroup = sgDTOs.get(notifiableSiteGroupItem.getSiteGroupCode());
				if (currentSiteGroup == null) {
					currentSiteGroup = new SiteGroupDTO();
					currentSiteGroup.setCode(notifiableSiteGroupItem.getSiteGroupCode());
					currentSiteGroup.setItems(new ArrayList<>());
				}
				sgDTOs.put(currentSiteGroup.getCode(), currentSiteGroup);
			}

			SiteGroupItem siteGroupItem = siteGroupItemService.getSiteGroupItem(
					notifiableSiteGroupItem.getSiteGroupCode(), notifiableSiteGroupItem.getSiteGroupItemCode());

			/*
			 * fullyLoaded.put(siteGroupItem.getCode(), true); if (sgis.containsKey(siteGroupItem.getCode())) {
			 * sgis.get(siteGroupItem.getCode()).getChildren().clear(); continue; }
			 * this.buildParentsForSiteGroupItem(currentSiteGroup, siteGroupItem, null, sgis, fullyLoaded, locale);
			 */

			SiteGroupItemDTO siteGroupItemDTO = siteGroupItemMapper.toDTO(siteGroupItem, locale, false);
			currentSiteGroup.getItems().add(siteGroupItemDTO);

		}

		PageResponse<SiteGroupDTO> pageResponse = new PageResponse<>();
		pageResponse.setItems(sgDTOs.values());
		pageResponse.setLast(true);
		pageResponse.setSize(sgDTOs.size());
		return pageResponse;
	}

	private void buildParentsForSiteGroupItem(SiteGroupDTO siteGroupDTO, SiteGroupItem current,
			SiteGroupItemDTO currentDTO, Map<String, SiteGroupItemDTO> sgis, Map<String, Boolean> fullyLoaded,
			String locale) {

		if (sgis.containsKey(current.getCode())) {
			return;
		}

		SiteGroupItemDTO siteGroupItemDTO = siteGroupItemMapper.toDTO(current, locale, false);

		siteGroupItemDTO.setChildren(new ArrayList<>());
		sgis.put(current.getCode(), siteGroupItemDTO);

		if (currentDTO != null) {
			if (siteGroupItemDTO.getChildren() == null) siteGroupItemDTO.setChildren(new ArrayList<>());
			siteGroupItemDTO.getChildren().add(currentDTO);
		}

		if (current.getParent() != null) {
			SiteGroupItem parent = current.getParent();

			if (!sgis.containsKey(parent.getCode())) {

				buildParentsForSiteGroupItem(siteGroupDTO, parent, siteGroupItemDTO, sgis, fullyLoaded, locale);
			}
			else {
				if (fullyLoaded.containsKey(parent.getCode()) && fullyLoaded.get(parent.getCode()) == true) {
					// Ja fui carregado algures
					return;
				}
				else {
					siteGroupDTO.getItems().add(siteGroupItemDTO);
				}
			}

		}
		else siteGroupDTO.getItems().add(siteGroupItemDTO);
	}

	public PageResponse<SiteGroupItemDTO> getSiteGroupItemsFromNotifiable(String notifiableCode, String siteGroupCode) {
		LOG.info("Retrieving list of sites from notifiable.");
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		if (!notifiableRepository.findByCode(notifiableCode).isPresent()) {
			throw new EntityNotFoundException("Notifiable does not exist");
		}

		List<NotifiableSiteGroupItem> notifiableSiteGroupItems = notifiableSiteGroupItemRepository
				.findByNotifiableCodeAndSiteGroupCode(notifiableCode, siteGroupCode);

		if (notifiableSiteGroupItems.isEmpty()) {
			throw new EntityNotFoundException("No SiteGroupItems Associated with Notifiable");
		}

		List<String> siteGroupItemCodes = notifiableSiteGroupItems.stream()
				.map(NotifiableSiteGroupItem::getSiteGroupItemCode).collect(Collectors.toList());
		List<SiteGroupItemDTO> dtos = siteGroupItemService.getSiteGroupItemByCodes(siteGroupItemCodes, locale);

		PageResponse<SiteGroupItemDTO> pageResponse = new PageResponse<>();
		pageResponse.setItems(dtos);
		pageResponse.setLast(true);
		pageResponse.setSize(dtos.size());
		return pageResponse;
	}

	public List<NotifiableSiteGroupItemDTO> addSiteGroupItemsToNotifiable(String notifiableCode, String siteGroupCode,
			@NotEmpty List<String> siteGroupItemCodes) throws Exception {
		LOG.info("Adding notifiable {} to site group {} item {}", notifiableCode, siteGroupCode, siteGroupItemCodes);
		if (!notifiableRepository.findByCode(notifiableCode).isPresent())
			throw new EntityNotFoundException("Notifiable does not exist");

		// TODO better way to do this, this is slow
		for (String siteGroupItemCode : siteGroupItemCodes) {
			if (!siteGroupItemRepository.findByCodeAndSiteGroup(siteGroupItemCode, siteGroupCode).isPresent()) {
				throw new EntityNotFoundException("Site Group Item " + siteGroupItemCode + " does not exist");
			}
		}

		// TODO better way to do this, this is slow
		for (String siteGroupItemCode : siteGroupItemCodes) {
			if (notifiableSiteGroupItemRepository.findByNotifiableCodeAndSiteGroupCodeAndSiteGroupItemCode(
					notifiableCode, siteGroupCode, siteGroupItemCode).isPresent()) {
				throw new EntityExistsException("NotifiableSiteGroupItem exists.");
			}
		}

		List<NotifiableSiteGroupItem> notifiableSiteGroupItems = new ArrayList<>();
		for (String siteGroupItemCode : siteGroupItemCodes) {
			NotifiableSiteGroupItem entity = new NotifiableSiteGroupItem();
			entity.setSiteGroupCode(siteGroupCode);
			entity.setNotifiableCode(notifiableCode);
			entity.setSiteGroupItemCode(siteGroupItemCode);
			notifiableSiteGroupItems.add(entity);
		}
		LOG.debug("Persisting entities {}", notifiableSiteGroupItems);
		List<NotifiableSiteGroupItem> savedEntities = notifiableSiteGroupItemRepository
				.saveAll(notifiableSiteGroupItems);
		LOG.debug("Successfully persisted NotifiableSiteGroupItems with ids {}",
				savedEntities.stream().map(NotifiableSiteGroupItem::getId).collect(Collectors.toList()).toString());
		List<NotifiableSiteGroupItemDTO> dtos = notifiableSiteGroupMapper.toDTO(savedEntities);
		return dtos;
	}

	public void removeSiteGroupItemsFromNotifiable(String notifiableCode, String siteGroupCode,
			@NotEmpty List<String> siteGroupItemCodes) throws Exception {
		LOG.info("Removing notifiable {} from site group {} items {}", notifiableCode, siteGroupCode,
				siteGroupItemCodes.toString());

		// TODO better way to do this, this is slow
		List<NotifiableSiteGroupItem> notifiableSiteGroupItems = new ArrayList<>();
		for (String siteGroupItemCode : siteGroupItemCodes) {
			notifiableSiteGroupItems.add(notifiableSiteGroupItemRepository
					.findByNotifiableCodeAndSiteGroupCodeAndSiteGroupItemCode(notifiableCode, siteGroupCode,
							siteGroupItemCode)
					.orElseThrow(() -> new EntityNotFoundException(
							"Unable to get NotifiableSiteGroupItem from item " + siteGroupItemCode)));
		}
		// TODO better way to do this, this is slow
		for (NotifiableSiteGroupItem notifiableSiteGroupItem : notifiableSiteGroupItems) {
			notifiableSiteGroupItemRepository.delete(notifiableSiteGroupItem);
		}
		LOG.info("Successfully removed notifiable {} from site group {} items {}", notifiableCode, siteGroupCode,
				siteGroupItemCodes.toString());
	}

	public NotifiableDTO setNotifiableEnabled(String notifiableCode, boolean enabled) throws Exception {
		LOG.info("Setting notifiable {} enabled to ", notifiableCode, enabled);

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		Notifiable entity = notifiableRepository.findByCode(notifiableCode)
				.orElseThrow(() -> new EntityNotFoundException("Unable to get Notifiable"));

		// Verify if user has permissions to update notifiable
		verifyPermission(entity);

		entity.setEnabled(enabled);
		Notifiable savedEntity = notifiableRepository.update(entity);
		LOG.info("Successfully updated notifiable {} enabled to {}", notifiableCode, enabled);
		return notifiableMapper.toDTO(savedEntity, locale, true);
	}

	/** Returns a list of notifiable methods
	 *
	 * @return list of notifiable methods */
	public PageResponse<NotifiableMethodDTO> getNotifiableMethods(Boolean enabled) {
		LOG.info("Retrieving list of notifiable methods");
		List<NotifiableMethodDTO> dtos = new ArrayList<>();

		if (enabled != null && enabled) {
			dtos.addAll(notifiableMethodMapper.toDTO(notifiableMethodRepository.findAllByEnabledTrue()));
		}
		else {
			dtos.addAll(notifiableMethodMapper.toDTO(notifiableMethodRepository.findAll()));
		}

		PageResponse<NotifiableMethodDTO> pageResponse = new PageResponse<>();
		pageResponse.setItems(dtos);
		pageResponse.setLast(true);
		pageResponse.setSize(dtos.size());
		return pageResponse;
	}

	/** Returns a list of notifiable methods translated into logged user language
	 *
	 * @return list of notifiable methods */
	public PageResponse<NotifiableMethodTranslatedDTO> getNotifiableMethodsTranslated(Boolean enabled) {
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		LOG.info("Retrieving list of notifiable methods for locale {}", locale);
		List<NotifiableMethodTranslatedDTO> dtos = new ArrayList<>();

		if (enabled != null && enabled) {
			dtos.addAll(notifiableMethodMapper.toTranslatedDTO(notifiableMethodRepository.findAllByEnabledTrue(),
					locale));
		}
		else {
			dtos.addAll(notifiableMethodMapper.toTranslatedDTO(notifiableMethodRepository.findAll(), locale));
		}

		PageResponse<NotifiableMethodTranslatedDTO> pageResponse = new PageResponse<>();
		pageResponse.setItems(dtos);
		pageResponse.setLast(true);
		pageResponse.setSize(dtos.size());
		return pageResponse;
	}

	/** Adds a new notifiable element to the given notifiable
	 *
	 * @param notifiableCode       code from the notifiable to be added with new element
	 * @param notifiableElementDTO element that will be added
	 * @return persisted element */
	public NotifiableElementDTO addNotifiableElement(String notifiableCode, NotifiableElementDTO notifiableElementDTO) {

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		if (notifiableElementDTO.getCode() == null) notifiableElementDTO.setCode(UUID.randomUUID().toString());

		LOG.info("Adding new notifiable element with name {} from notifiable {}", notifiableElementDTO.getCode(),
				notifiableCode);
		Notifiable notifiable = notifiableRepository.findByCode(notifiableCode)
				.orElseThrow(() -> new EntityNotFoundException("No notifiable found for code " + notifiableCode));

		// Verify if user has permissions to update notifiable
		verifyPermission(notifiable);

		NotifiableElement entity = notifiableElementMapper.fromDTO(notifiableElementDTO, notifiable, locale);
		LOG.debug("Saving notifiable element {}", entity);
		entity = notifiableElementRepository.save(entity);
		LOG.debug("Notifiable element with name {} was saved successfully with id {}", entity.getName(),
				entity.getId());

		return notifiableElementMapper.toDTO(entity, locale, true);
	}

	/** Updates notifiable elements from notifiable with given code. If notifiable element doesn't exist, creates it.
	 *
	 * @param notifiableCode       code from the notifiable to be added with new element
	 * @param elementName          name from the notifiable element being updated
	 * @param notifiableElementDTO notifiable element update object
	 * @return stored notifiable element */
	public NotifiableElementDTO updateNotifiableElement(String notifiableCode, @NotNull String elementName,
			NotifiableElementDTO notifiableElementDTO) throws Exception {

		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		LOG.info("Updating notifiable element named " + elementName + " from notifiable " + notifiableCode);
		NotifiableElement notifiableElement = notifiableElementRepository
				.findByNameAndNotifiableCode(elementName, notifiableCode)
				.orElseThrow(() -> new EntityNotFoundException("No notifiable element found for name " + elementName
						+ " and notifiable code " + notifiableCode));

		// Verify if user has permissions to update notifiable
		verifyPermission(notifiableElement.getNotifiable());

		LOG.debug("Current notifiable element is {}", notifiableElement);
		LOG.debug("Received update notifiable element object is {}", notifiableElementDTO);
		notifiableElementMapper.update(notifiableElement, notifiableElementDTO, locale);

		LOG.trace("Update object properties merge with current result: {}", notifiableElement);
		notifiableElementRepository.save(notifiableElement);

		LOG.debug("Entity sucessfully saved. Properties: {}", notifiableElement);
		NotifiableElementDTO dto = notifiableElementMapper.toDTO(notifiableElement, locale, true);
		return dto;
	}

	public void disableNotifiableElement(String notifiableCode, String notifiableElementName) throws Exception {
		LOG.info("Disabling notifiable element {} for notifiable {}", notifiableElementName, notifiableCode);
		NotifiableElement notifiableElement = notifiableElementRepository
				.findByNameAndNotifiableCode(notifiableElementName, notifiableCode)
				.orElseThrow(() -> new EntityNotFoundException("Unable to get NotifiableElement"));

		// Verify if user has permissions to update notifiable
		verifyPermission(notifiableElement.getNotifiable());

		for (NotifiableElementContact contact : notifiableElement.getNotifiableElementContacts()) {
			deleteNotifiableElementContact(notifiableCode, notifiableElementName, contact.getContact());
		}

		notifiableElementRepository.delete(notifiableElement);
		LOG.info("Successfully disabled notifiable element {} for notifiable {}", notifiableElementName,
				notifiableCode);
	}

	/** Adds new notifiable element contact to a existing notifiable element
	 *
	 * @param notifiableCode notifiable code it belongs to
	 * @param elementName    name of the element to add new contact
	 * @param contactDTO     contact object to be added
	 * @return stored notifiable element contact */
	public NotifiableElementContactDTO addNotifiableElementContact(String notifiableCode, String elementName,
			NotifiableElementContactDTO contactDTO) {
		LOG.info("Add notifiable element contact {} to notifiable {} and element {}", contactDTO.getContact(),
				notifiableCode, elementName);
		NotifiableElement elementEntity = notifiableElementRepository
				.findByNameAndNotifiableCode(elementName, notifiableCode)
				.orElseThrow(() -> new EntityNotFoundException("Couldn't find notifiable element with name "
						+ elementName + " and notifiable " + notifiableCode));

		// Verify if user has permissions to update notifiable
		verifyPermission(elementEntity.getNotifiable());

		// Verify if element contact is repeated
		notifiableElementContactRepository
				.findByNameAndNotifiableCodeAndContact(elementName, notifiableCode, contactDTO.getContact())
				.ifPresent(existing -> {
					throw new EntityExistsException("Notifiable element contact with contact " + contactDTO.getContact()
							+ " already exists for notifiable element " + elementName);
				});

		NotifiableElementContact contactEntity = notifiableElementContactMapper.fromDTO(contactDTO, elementEntity);
		contactEntity = notifiableElementContactRepository.save(contactEntity);
		LOG.debug("Element {} contact {} sucessfully saved with id {}", elementName, contactDTO.getContact(),
				contactEntity.getId());
		return notifiableElementContactMapper.toDTO(contactEntity);
	}

	/** Updates a notifiable element contact
	 *
	 * @param notifiableCode notifiable code it belongs to
	 * @param elementName    name of the element to add new contact
	 * @param contact        value of the contact to be updated
	 * @param contactDTO     properties to be updated
	 * @return stored notifiable element contact */
	public NotifiableElementContactDTO updateNotifiableElementContact(String notifiableCode, String elementName,
			String contact, NotifiableElementContactDTO contactDTO) {
		LOG.info("Updating notifiable element contact {} with notifiable {} and element {}", contactDTO.getContact(),
				notifiableCode, elementName);
		NotifiableElementContact contactEntity = notifiableElementContactRepository
				.findByNameAndNotifiableCodeAndContact(elementName, notifiableCode, contact)
				.orElseThrow(() -> new EntityNotFoundException(
						"Couldn't find notifiable element contact " + contactDTO.getContact() + " with element name "
								+ elementName + " and notifiable " + notifiableCode));

		// Verify if user has permissions to update notifiable
		verifyPermission(contactEntity.getNotifiableElement().getNotifiable());

		LOG.debug("Updating stored contact element ({}) with contact properties ({})", contactEntity, contactDTO);
		notifiableElementContactMapper.update(contactEntity, contactDTO);

		LOG.debug("Updating contact element entity: {}", contactEntity);
		contactEntity = notifiableElementContactRepository.save(contactEntity);
		LOG.debug("Updated entity succesfully stored");
		return notifiableElementContactMapper.toDTO(contactEntity);
	}

	public void deleteNotifiableElementContact(String notifiableCode, String notifiableElementName, String contact)
			throws Exception {
		LOG.info("Deleting notifiable element contact {} for element {} and notifiable {}", contact,
				notifiableElementName, notifiableCode);

		NotifiableElementContact contactEntity = notifiableElementContactRepository
				.findByNameAndNotifiableCodeAndContact(notifiableElementName, notifiableCode, contact)
				.orElseThrow(() -> new EntityNotFoundException("Unable to get NotifiableElementContact"));

		// Verify if user has permissions to update notifiable
		verifyPermission(contactEntity.getNotifiableElement().getNotifiable());

		notifiableElementContactRepository.delete(contactEntity);
		LOG.info("Successfully deleted notifiable element contact {} for element {} and notifiable {}", contact,
				notifiableElementName, notifiableCode);
	}

}
