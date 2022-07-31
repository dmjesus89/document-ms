package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceWarehouseDTO;
import com.petrotec.documentms.services.WarehouseService;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.annotation.Nullable;
import javax.validation.Valid;

import static io.micronaut.http.HttpStatus.CREATED;
import static io.micronaut.http.HttpStatus.OK;

@Validated
@Controller(value = "/api/v1/devices/warehouses")
@Tag(name = "Warehouse Devices")
public class WarehouseController {

	private final WarehouseService warehouseService;

	public WarehouseController(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}


	@ApiResponse(responseCode = "200", description = "Device Warehouse List")
	@Get
	public BaseResponse<PageResponse<SiteDeviceWarehouseDTO>> getWarehouseDevices(
			@Nullable PageAndSorting pageAndSorting,
			@Nullable Filter filterQuery) {
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);

		PageResponse<SiteDeviceWarehouseDTO> pageResult = warehouseService.listWarehousesDTO(pageAndSorting, filterQuery, rankOrder, locale);

		return BaseResponse.success(OK.getReason(), OK.getCode(), "List Site Device Warehouse", pageResult);
	}

	@ApiResponse(responseCode = "200", description = "Pos Details")
	@ApiResponse(responseCode = "404", description = "Device code not found")
	@Get("/{code}")
	public BaseResponse<SiteDeviceWarehouseDTO> getWarehouseDetails(@PathVariable String code) {
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
		SiteDeviceWarehouseDTO result = warehouseService.getWarehouseDetailsDTO(code, rankOrder, locale);
		return BaseResponse.success(OK.getReason(), OK.getCode(), "Pos Details", result);
	}

	@ApiResponse(responseCode = "200", description = "Created Warehouse")
	@ApiResponse(responseCode = "400", description = "Invalid Data")
	@ApiResponse(responseCode = "409", description = "Device code already exists")
	@Post
	public BaseResponse<SiteDeviceWarehouseDTO> createWarehouseDevice(@Valid @Body SiteDeviceWarehouseDTO dto) {
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
		SiteDeviceWarehouseDTO result = warehouseService.createWarehouseDTO(dto, rankOrder, locale);
		return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Create Site Device Warehouse", result);
	}

	@ApiResponse(responseCode = "200", description = "Warehouse Updated")
	@ApiResponse(responseCode = "400", description = "Invalid Data")
	@ApiResponse(responseCode = "404", description = "Device code not found")
	@Put("/{code}")
	public BaseResponse<SiteDeviceWarehouseDTO> updateWarehouseDevice(@Valid @Body SiteDeviceWarehouseDTO dto, @PathVariable String code) {
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
		SiteDeviceWarehouseDTO result = warehouseService.updateWarehouseDTO(code, dto, rankOrder, locale);
		return BaseResponse.success(OK.getReason(), OK.getCode(), "Update Site Device Warehouse", result);
	}
}
