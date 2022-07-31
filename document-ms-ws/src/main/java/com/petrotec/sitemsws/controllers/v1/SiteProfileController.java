package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.ApiError;
import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.siteProfile.SiteProfileCreateDTO;
import com.petrotec.documentms.dtos.siteProfile.SiteProfileDTO;
import com.petrotec.documentms.services.SiteProfileService;
import com.petrotec.documentms.interfaces.ISiteProfileApi;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;


import javax.annotation.Nullable;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * SiteProfileController
 */
@Valid
@Controller("/api/v1/sites/profiles")
public class SiteProfileController implements ISiteProfileApi {

	protected final SiteProfileService service;

	public SiteProfileController(final SiteProfileService service) {
		this.service = service;
	}

	@Override
	@Get("/{siteProfileCode}")
	public BaseResponse<SiteProfileDTO> findProfilesByCode(@PathVariable("siteProfileCode") String siteProfileCode) {
		return service.findByCode(siteProfileCode);
	}

	@Override
	@Get
	public BaseResponse<PageResponse<SiteProfileDTO>> findProfilesAll(@Nullable PageAndSorting pageAndSorting,
																	  @Nullable Filter filters) {
		return service.findAll(filters, pageAndSorting);
	}

	@Override
	@Get("/translated")
	public BaseResponse<PageResponse<SiteProfileDTO>> findAllProfilesTranslated(
			@Nullable PageAndSorting pageAndSorting, @Nullable Filter filters) {
		return service.findAllTranslated(filters, pageAndSorting);
	}

	@Override
	@Post
	public BaseResponse<SiteProfileDTO> addSiteProfile(@NotNull SiteProfileCreateDTO siteProfileCreateDTO) {
		return service.addSiteProfile(siteProfileCreateDTO);
	}

	@Override
	@Delete("/{siteProfileCode}")
	public BaseResponse<SiteProfileDTO> disableSiteProfile(@PathVariable("siteProfileCode") String siteProfileCode) {
		return BaseResponse
				.error(HttpStatus.NOT_IMPLEMENTED.getReason(), HttpStatus.NOT_IMPLEMENTED.getCode(), "Not yet implemented", null);
	}

	@Override
	@Put("/{siteProfileCode}")
	public BaseResponse<SiteProfileDTO> updateSiteProfile(@PathVariable("siteProfileCode") String siteProfileCode,
			@NotNull SiteProfileCreateDTO siteProfileCreateDTO) {
		try {
			return service.updateSiteProfile(siteProfileCode, siteProfileCreateDTO);
		} catch (EntityNotFoundException e) {
			return BaseResponse.error(HttpStatus.NO_CONTENT.getReason(), HttpStatus.NO_CONTENT.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		} catch (Exception e) {
			return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.getReason(), HttpStatus.INTERNAL_SERVER_ERROR.getCode(), null,
					new ApiError(Arrays.asList(e.getMessage())));
		}
	}

}
