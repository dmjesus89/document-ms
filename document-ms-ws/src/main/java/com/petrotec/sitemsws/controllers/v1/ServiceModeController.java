package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.site.ServiceModeDTO;
import com.petrotec.documentms.interfaces.IServiceMode;
import com.petrotec.documentms.services.DefaultServiceModeService;
import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static io.micronaut.http.HttpStatus.CREATED;
import static io.micronaut.http.HttpStatus.OK;

@Controller("/api/v1/serviceMode")
public class ServiceModeController {

	private final IServiceMode service;

	public ServiceModeController(DefaultServiceModeService service) {
		this.service = service;
	}

	@Get
	public BaseResponse<PageResponse<ServiceModeDTO>> getAll(@Nullable Filter filter,
			@Nullable PageAndSorting pageAndSorting) {
		return BaseResponse.success(OK.getReason(), OK.getCode(), "List of service mode",
				service.findAll(pageAndSorting, filter));
	}

	@Get("/{code}")
	public BaseResponse<ServiceModeDTO> get(@NotBlank String code) {
		return BaseResponse.success(OK.getReason(), OK.getCode(), "Service mode details", service.get(code));
	}

	@Delete("/{code}")
	public BaseResponse<ServiceModeDTO> disable(@NotBlank String code) {
		return BaseResponse.success(OK.getReason(), OK.getCode(), "Service mode details",
				service.setServiceModeEnabled(code, false));
	}

	@Put("/{code}")
	public BaseResponse<ServiceModeDTO> update(@NotBlank String code,
			@Body @Valid ServiceModeDTO serviceModeDTO) {
		return BaseResponse.success(OK.getReason(), OK.getCode(), "Service mode : " + code + " successfully updated",
				service.update(code, serviceModeDTO));
	}

	@Post
	public BaseResponse<ServiceModeDTO> create(@Body @Valid ServiceModeDTO serviceModeDTO) {
		return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Service mode saved with success",
				service.create(serviceModeDTO));
	}

}
