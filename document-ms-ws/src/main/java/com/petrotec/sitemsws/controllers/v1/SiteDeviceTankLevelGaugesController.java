package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.SiteDeviceTankLevelGaugesDTO;
import com.petrotec.documentms.interfaces.ITankLevelGauges;
import com.petrotec.documentms.services.SiteDeviceTankLevelGaugesService;
import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static io.micronaut.http.HttpStatus.CREATED;
import static io.micronaut.http.HttpStatus.OK;

@Valid
@Controller("/api/v1/devices/tankLevelGauges")
public class SiteDeviceTankLevelGaugesController {

	private ITankLevelGauges service;

	public SiteDeviceTankLevelGaugesController(SiteDeviceTankLevelGaugesService service) {
		this.service = service;
	}

	@Get
	public BaseResponse<PageResponse<SiteDeviceTankLevelGaugesDTO>> findAll(@Nullable Filter filter,
			@Nullable PageAndSorting pageAndSorting) {
		return BaseResponse.success(OK.getReason(), OK.getCode(), "List of Tank Level Gauges", service.findAll(pageAndSorting, filter));
	}

	@Get("/{code}")
	public BaseResponse<SiteDeviceTankLevelGaugesDTO> get(@NotBlank String code) {
		return BaseResponse.success(OK.getReason(), OK.getCode(), "Tank Level Gauges mode details", service.getByCode(code));
	}

	@Delete("/{code}")
	public BaseResponse<SiteDeviceTankLevelGaugesDTO> disable(@NotBlank String code) {
		return BaseResponse.success(OK.getReason(), OK.getCode(), "Tank Level Gauges details",
				service.setTankLevelGaugesDTOEnabled(code, false));
	}

	@Put("/{code}")
	public BaseResponse<SiteDeviceTankLevelGaugesDTO> update(@NotBlank String code,
			@Body @Valid SiteDeviceTankLevelGaugesDTO siteDeviceTankLevelGaugesDTO) {
		return BaseResponse.success(OK.getReason(), OK.getCode(), "Tank Level Gauges : " + code + " successfully updated",
				service.update(code, siteDeviceTankLevelGaugesDTO));
	}

	@Post
	public BaseResponse<SiteDeviceTankLevelGaugesDTO> create(@Body @Valid SiteDeviceTankLevelGaugesDTO siteDeviceTankLevelGaugesDTO) {
		return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Tank Level Gauges saved with success",
				service.create(siteDeviceTankLevelGaugesDTO));
	}
}
