package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.ApiError;
import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.site.SiteDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemSiteDTO;
import com.petrotec.documentms.services.SiteGroupItemService;
import com.petrotec.documentms.services.SiteGroupService;
import com.petrotec.documentms.interfaces.ISiteGroupApi;
import static io.micronaut.http.HttpStatus.*;

import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.List;

@Controller("/api/v1/siteGroups")
public class SiteGroupController implements ISiteGroupApi {

	final private SiteGroupService siteGroupService;
	final private SiteGroupItemService siteGroupItemService;

	public SiteGroupController(SiteGroupService siteGroupService, SiteGroupItemService siteGroupItemService) {
		this.siteGroupService = siteGroupService;
		this.siteGroupItemService = siteGroupItemService;
	}

	@Override
	@Get
	public BaseResponse<PageResponse<SiteGroupDTO>> listSiteGroups(@Nullable PageAndSorting pageAndSorting,
			@Nullable Filter filterQuery) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					siteGroupService.getSiteGroups(pageAndSorting, filterQuery));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Override
	@Get("/{code}")
	public BaseResponse<SiteGroupDTO> getGroupDetail( String code) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					siteGroupService.getSiteGroup(code));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}


	@Override
	@Post
	public BaseResponse<SiteGroupDTO> createSiteGroup(SiteGroupDTO groupDTO) {
		try {
			return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Site group successfully updated",
					siteGroupService.createSiteGroup(groupDTO));
		} catch (IllegalArgumentException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Required argument not found", new ApiError(e));
		} catch (EntityExistsException e) {
			return BaseResponse.error(CONFLICT.getReason(), CONFLICT.getCode(), "New site group was not created",
					new ApiError(e));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(), "User can't create site group",
					new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(e));
		}
	}

	@Override
	@Put("/{code}")
	public BaseResponse<SiteGroupDTO> updateSiteGroup( String code,SiteGroupDTO siteGroupDTO) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), "Site group successfully updated",
					siteGroupService.updateSiteGroup(code, siteGroupDTO));
		} catch (EntityNotFoundException e) {
			return BaseResponse.error(NO_CONTENT.getReason(), NO_CONTENT.getCode(),
					"Site group " + code + " was not found", new ApiError(e));
		} catch (IllegalArgumentException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Required argument not found", new ApiError(e));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(),
					"Access to SiteGroup " + code + " is not allowed to logged user", new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(e));
		}
	}

	@Delete("/{code}")
	@Override
	public BaseResponse<Void> deleteSiteGroup( String code) {
		try {
			siteGroupService.disableByCode(code);
			return BaseResponse.success(NO_CONTENT.getReason(), NO_CONTENT.getCode(), "Site group deleted", null);
		} catch (IllegalArgumentException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Required argument not found", new ApiError(e));
		} catch (EntityNotFoundException e) {
			return BaseResponse.error(CONFLICT.getReason(), CONFLICT.getCode(),
					"Site group " + code + " was not found", new ApiError(e));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(),
					"Access to SiteGroup " + code + " is not allowed to logged user", new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(e));
		}
	}

	@Get("/{code}/items")
	@Override
	public BaseResponse<PageResponse<SiteGroupItemDTO>> listSiteGroupItem(String code) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					siteGroupItemService.getSiteGroupItems(code));
		} catch (IllegalArgumentException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Required argument not found", new ApiError(e));
		} catch (EntityNotFoundException e) {
			return BaseResponse.success(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Site group " + code + " was not found", PageResponse.empty());
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(),
					"Access to SiteGroup " + code + " is not allowed to logged user", new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Post("/{code}/items")
	@Override
	public BaseResponse<SiteGroupItemDTO> createSiteGroupItem( String code, SiteGroupItemDTO siteGroupItemDTO) {
		try {
			return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), null,
					siteGroupItemService.createSiteGroupItem(code, siteGroupItemDTO));
		} catch (EntityExistsException e) {
			return BaseResponse.error(CONFLICT.getReason(), CONFLICT.getCode(),
					"New site group item was not created", new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Get("/{code}/items/{itemCode}")
	@Override
	public BaseResponse<SiteGroupItemDTO> getSiteGroupItem( String code,String itemCode) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					siteGroupItemService.getSiteGroupItemDTO(code, itemCode));
		} catch (IllegalArgumentException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Required argument not found", new ApiError(e));
		} catch (EntityNotFoundException e) {
			return BaseResponse.error(NO_CONTENT.getReason(), NO_CONTENT.getCode(),
					"Site group " + code + " was not found", new ApiError(e));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(),
					"Access to SiteGroup " + code + " is not allowed to logged user", new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Put("/{code}/items/{itemCode}")
	@Override
	public BaseResponse<SiteGroupItemDTO> updateSiteGroupItem(String code, String itemCode, SiteGroupItemDTO siteGroupItemDTO) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					siteGroupItemService.updateSiteGroupItem(code, itemCode, siteGroupItemDTO));
		} catch (IllegalArgumentException illegalArgumentException) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Required argument not found", new ApiError(illegalArgumentException));
		} catch (EntityNotFoundException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Site group item " + itemCode + " and site group " + code + " was not found", new ApiError(e));
		} catch (AccessControlException accessControlException) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(),
					"Access to SiteGroup " + code + " is not allowed to logged user",
					new ApiError(accessControlException));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Delete("/{code}/items/{item}")
	@Override
	public BaseResponse<Void> deleteSiteGroupItem(String code,String item) {
		try {
			siteGroupItemService.deleteSiteGroupItem(code, item);
			return BaseResponse.success(OK.getReason(), OK.getCode(),
					"Site Group Item " + item + " from Site Group " + code + " deleted.", null);
		} catch (EntityNotFoundException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Required entity was not found", new ApiError(e));
		} catch (IllegalArgumentException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(), "Illegal argument exception",
					new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(e));
		}
	}

	@Get("/{code}/items/{item}/sites")
	@Override
	public BaseResponse<PageResponse<SiteGroupItemSiteDTO>> getSiteGroupItemSite( String code, String item) {
		try {
			return BaseResponse.success(OK.getReason(), OK.getCode(), null,
					siteGroupItemService.getSiteGroupItemSite(code, item));
		} catch (IllegalArgumentException illegalArgumentException) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					"Required argument not found", new ApiError(illegalArgumentException));
		} catch (EntityNotFoundException e) {
			return BaseResponse.success(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
					" Site Group Item Sites from site group item " + item + " and site group " + code
							+ " with was not found",
					PageResponse.empty());
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(),
					"Access to SiteGroup " + code + " is not allowed to logged user", new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Post("/{code}/items/{item}/sites")
	@Override
	public BaseResponse<PageResponse<SiteGroupItemSiteDTO>> createSiteGroupItemSite( String code,  String item, List<SiteDTO> sites) {
		try {
			PageResponse<SiteGroupItemSiteDTO> result = siteGroupItemService.addSiteToSiteGroupItem(code, item,
					sites);
			BaseResponse<PageResponse<SiteGroupItemSiteDTO>> success = BaseResponse.success(CREATED.getReason(),
					CREATED.getCode(), null, result);
			return success;
		} catch (EntityNotFoundException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(), "Required entity was not found",
					new ApiError(e));
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(),
					"Access to SiteGroup " + code + " is not allowed to logged user", new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

	@Delete("/{code}/items/{item}/sites/{site}")
	@Override
	public BaseResponse<Void> deleteSiteGroupItemSite(String code, String item, String site) {
		try {
			siteGroupItemService.deleteSiteGroupItemSite(code, item, site);
			return BaseResponse.success(NO_CONTENT.getReason(), NO_CONTENT.getCode(), null, null);
		} catch (AccessControlException e) {
			return BaseResponse.error(FORBIDDEN.getReason(), FORBIDDEN.getCode(),
					"Access to SiteGroup " + code + " is not allowed to logged user", new ApiError(e));
		} catch (EntityNotFoundException e) {
			return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(), "Required entity was not found",
					new ApiError(e));
		} catch (Exception e) {
			return BaseResponse.error(INTERNAL_SERVER_ERROR.getReason(), INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

}
