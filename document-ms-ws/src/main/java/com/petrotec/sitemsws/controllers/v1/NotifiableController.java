package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.ApiError;
import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.*;
import com.petrotec.documentms.dtos.site.SiteCustomDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemDTO;
import com.petrotec.documentms.services.NotifiableService;
import com.petrotec.documentms.interfaces.INotifiableApi;
import io.micronaut.http.annotation.*;

import static io.micronaut.http.HttpStatus.*;

import javax.annotation.Nullable;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.List;

@Controller("/api/v1/notifiables")
public class NotifiableController implements INotifiableApi {
	protected final NotifiableService notifiableService;

	public NotifiableController(final NotifiableService notifiableService) {
		this.notifiableService = notifiableService;
	}

	@Get
	@Override
	public BaseResponse<PageResponse<NotifiableDTO>> findAll(@Nullable PageAndSorting pageAndSorting,
			@Nullable Filter filters) {
		return notifiableService.findAll(pageAndSorting, filters);
	}

	@Get("/elements")
	@Override
	public BaseResponse<PageResponse<NotifiableElementDTO>> getNotifiableElements(@NotEmpty String siteCode, @Nullable String alarmSeverity) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					notifiableService.getNotifiableElements(siteCode, alarmSeverity, true));
		} catch (IllegalArgumentException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Required argument not found", new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Post
	@Override
	public BaseResponse<NotifiableDTO> addNotifiable(@NotNull NotifiableDTO notifiableDTO) {
		try {
			return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), null,
					notifiableService.addNotifiable(notifiableDTO));
		} catch (IllegalArgumentException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Required argument not found", new ApiError(e));
		} catch (EntityExistsException e) {
			return BaseResponse.error(CONFLICT.getReason(), CONFLICT.getCode(), "Entity already exists",
					new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}

	}

	@Get("/{code}")
	@Override
	public BaseResponse<NotifiableDTO> findByCode( @NotEmpty String code) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					notifiableService.findByCode(code));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityExistsException e) {
			return BaseResponse.error(NO_CONTENT.getReason(), NO_CONTENT.getCode(), "Entity not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Put("/{code}")
	@Override
	public BaseResponse<NotifiableDTO> updateByCode(@NotEmpty String code,
			@NotNull NotifiableDTO notifiableDTO) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					notifiableService.update(code, notifiableDTO));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityExistsException e) {
			return BaseResponse.error(NO_CONTENT.getReason(), NO_CONTENT.getCode(), "Entity not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Delete("/{code}")
	@Override
	public BaseResponse<Void> deleteByCode(@NotEmpty String code) {
		try {
			notifiableService.delete(code);
			return BaseResponse.success(OK.getReason(), OK.getCode(), null, null);
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException | EntityExistsException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(), "Entity not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}

	}

	@Post("/{code}/enable")
	@Override
	public BaseResponse<NotifiableDTO> enableNotifiable(@NotEmpty String code) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), "Enabled notifiable",
					notifiableService.setNotifiableEnabled(code, true));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException | EntityExistsException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(), "Entity not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Post("/{code}/disable")
	@Override
	public BaseResponse<NotifiableDTO> disableNotifiable( @NotEmpty String code) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), "Disabled notifiable",
					notifiableService.setNotifiableEnabled(code, false));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException | EntityExistsException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(), "Entity not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Get("/{code}/sites")
	@Override
	public BaseResponse<PageResponse<SiteCustomDTO>> getSitesFromNotifiable(@NotEmpty String code) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					notifiableService.getSitesFromNotifiable(code));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException | EntityExistsException e) {
			return BaseResponse.success(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Entity from code " + code + " not found.", PageResponse.empty());
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Post("/{code}/sites")
	@Override
	public BaseResponse<List<NotifiableSiteDTO>> addSitesToNotifiable(
			@PathVariable("code") @NotEmpty String code, @NotNull List<String> siteCodes) {
		try {
			return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), null,
					notifiableService.addSitesToNotifiable(code, siteCodes));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(), "Site not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (EntityExistsException e) {
			return BaseResponse.error(CONFLICT.getReason(), CONFLICT.getCode(), "Entity already exists found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Delete("/{code}/sites{siteCodes}")
	@Override
	public BaseResponse<Void> removeSitesFromNotifiable(@NotEmpty String code,
			@NotNull List<String> siteCodes) {
		try {
			notifiableService.removeSitesFromNotifiable(code, siteCodes);
			return BaseResponse.success(NO_CONTENT.getReason(), NO_CONTENT.getCode(),
					"Removed Sites " + siteCodes.toString() + " from notifiable", null);
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException | EntityExistsException e) {
			return BaseResponse.error(NO_CONTENT.getReason(), NO_CONTENT.getCode(), "Entity not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}

	}

	@Get("/{code}/siteGroups")
	@Override
	public BaseResponse<PageResponse<SiteGroupDTO>> getSiteGroupsFromNotifiable(
			@NotEmpty String code) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					notifiableService.getSiteGroupsFromNotifiable(code));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException e) {
			return BaseResponse.success(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(), "Entity not found.", PageResponse.empty());
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Get("/{code}/siteGroups/{siteGroup}/items")
	@Override
	public BaseResponse<PageResponse<SiteGroupItemDTO>> getSiteGroupItemsFromNotifiable(
			@NotEmpty String code, @NotEmpty String siteGroup) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					notifiableService.getSiteGroupItemsFromNotifiable(code, siteGroup));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException e) {
			return BaseResponse.success(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(), "Entity not found.",
					PageResponse.empty());
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Post("/{code}/siteGroups/{siteGroup}/items")
	@Override
	public BaseResponse<List<NotifiableSiteGroupItemDTO>> addSiteGroupItemsToNotifiable(
			@NotEmpty String code,
			@NotEmpty String siteGroup, @NotEmpty List<String> itemCodes) {
		try {
			return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), null,
					notifiableService.addSiteGroupItemsToNotifiable(code, siteGroup, itemCodes));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(), "entity  not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (EntityExistsException e) {
			return BaseResponse.error(CONFLICT.getReason(), CONFLICT.getCode(), "Entity already exists found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Delete("/{code}/siteGroups/{siteGroup}/items{?itemCodes}")
	@Override
	public BaseResponse<Void> removeSiteGroupItemsFromNotifiable(@NotEmpty String code,
			@NotEmpty String siteGroup,
			@QueryValue(value = "itemCodes") @Nullable @NotEmpty List<String> itemCodes) {
		try {
			notifiableService.removeSiteGroupItemsFromNotifiable(code, siteGroup, itemCodes);
			return BaseResponse.success(NO_CONTENT.getReason(), NO_CONTENT.getCode(),
					"Removed Site Group Items " + itemCodes.toString() + " from notifiable", null);
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException | EntityExistsException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(), "Entity not found.",
					new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(e));
		}

	}

	@Get("/notifiableMethods")
	@Override
	public BaseResponse<PageResponse<NotifiableMethodDTO>> getNotifiableMethods(@Nullable Boolean enabled) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null, notifiableService.getNotifiableMethods(enabled));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Get("/notifiableMethods/translated")
	@Override
	public BaseResponse<PageResponse<NotifiableMethodTranslatedDTO>> getNotifiableMethodsTranslated(@Nullable Boolean enabled) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					notifiableService.getNotifiableMethodsTranslated(enabled));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Post("/{code}/elements/")
	@Override
	public BaseResponse<NotifiableElementDTO> addNotifiableElement(
			@NotEmpty String code,
			@NotNull NotifiableElementDTO notifiableElement) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					notifiableService.addNotifiableElement(code, notifiableElement));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException | EntityExistsException e) {
			return BaseResponse.error(NO_CONTENT.getReason(), NO_CONTENT.getCode(), "Entity not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}

	}

	@Put("/{code}/elements/{name}")
	@Override
	public BaseResponse<NotifiableElementDTO> updateNotifiableElement(@NotEmpty String code,@NotNull String name, @NotNull NotifiableElementDTO notifiableElement) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null, notifiableService
					.updateNotifiableElement(code, name, notifiableElement));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException | EntityExistsException e) {
			e.printStackTrace();
			return BaseResponse.error(NO_CONTENT.getReason(), NO_CONTENT.getCode(), "Entity not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			e.printStackTrace();
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}

	}

	@Delete("/{code}/elements/{name}")
	@Override
	public BaseResponse<Void> deleteNotifiableElement(@NotEmpty String code, @NotNull String name) {
		try {
			notifiableService.disableNotifiableElement(code, name);
			return BaseResponse.success(OK.getReason(), OK.getCode(), null, null);
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException | EntityExistsException e) {
			return BaseResponse.error(NO_CONTENT.getReason(), NO_CONTENT.getCode(), "Entity not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}

	}

	@Post("/{code}/elements/{name}/contacts")
	@Override
	public BaseResponse<NotifiableElementContactDTO> addNotifiableElementContact(
			@NotEmpty String code,
			@NotEmpty String name,
			@NotNull NotifiableElementContactDTO notifiableElementContactDTO) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null, notifiableService
					.addNotifiableElementContact(code, name, notifiableElementContactDTO));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException | EntityExistsException e) {
			return BaseResponse.error(NO_CONTENT.getReason(), NO_CONTENT.getCode(), e.getLocalizedMessage(),
					new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(e));
		}

	}

	@Put("/{code}/elements/{name}/contacts/{contact}")
	@Override
	public BaseResponse<NotifiableElementContactDTO> updateNotifiableElementContact(
			@NotEmpty String code,
			@NotNull String name,
			@NotNull String contact,
			@NotNull NotifiableElementContactDTO notifiableElementContactDTO) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					notifiableService.updateNotifiableElementContact(code, name, contact,
							notifiableElementContactDTO));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException | EntityExistsException e) {
			return BaseResponse.error(NO_CONTENT.getReason(), NO_CONTENT.getCode(), "Entity not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}

	}

	@Delete("/{code}/elements/{name}/contacts/{contact}")
	@Override
	public BaseResponse<Void> deleteNotifiableElementContact( @NotEmpty String code,@NotNull String name, @NotNull String contact) {
		try {
			notifiableService.deleteNotifiableElementContact(code, name, contact);
			return BaseResponse.success(OK.getReason(), OK.getCode(), null, null);
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "Permission exception",
					new ApiError(e));
		} catch (EntityNotFoundException | EntityExistsException e) {
			return BaseResponse.error(NO_CONTENT.getReason(), NO_CONTENT.getCode(), "Entity not found.",
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}

	}

}
