package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceFuelPointDTO;
import com.petrotec.documentms.services.SiteDeviceFuelPointService;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.annotation.Nullable;
import javax.validation.Valid;

import static io.micronaut.http.HttpStatus.CREATED;
import static io.micronaut.http.HttpStatus.OK;

@Validated
@Controller(value = "/api/v1/")
@Tag(name = "Site Devices")
public class SiteDeviceFuelPointController {

    private final SiteDeviceFuelPointService siteDeviceFuelPointService;

    public SiteDeviceFuelPointController(SiteDeviceFuelPointService siteDeviceFuelPointService) {
        this.siteDeviceFuelPointService = siteDeviceFuelPointService;
    }

    @ApiResponse(responseCode = "200", description = "Device Fuel Point List")
    @Get("/devices/fuelPoints")
    public BaseResponse<PageResponse<SiteDeviceFuelPointDTO>> getFuelPointDevices(
            @Nullable PageAndSorting pageAndSorting,
            @Nullable Filter filterQuery) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);

        PageResponse<SiteDeviceFuelPointDTO> result = siteDeviceFuelPointService.listFuelPointsDTO(pageAndSorting, filterQuery, rankOrder, locale);

        return BaseResponse.success(OK.getReason(), OK.getCode(), "List Device Dispenser", result);
    }

    @ApiResponse(responseCode = "200", description = "FuelPoint details")
    @ApiResponse(responseCode = "404", description = "FuelPoint code not found")
    @Get("/devices/fuelPoints/{deviceCode}")
    public BaseResponse<SiteDeviceFuelPointDTO> getFuelPointsDetails(String deviceCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteDeviceFuelPointDTO result = siteDeviceFuelPointService.getFuelPointDetailsDTO(deviceCode, rankOrder, locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Fuel Point Details", result);
    }

    @ApiResponse(responseCode = "200", description = "Created FuelPoint")
    @ApiResponse(responseCode = "409", description = "Device code already exists")
    @Post("/devices/fuelPoints")
    public BaseResponse<SiteDeviceFuelPointDTO> createFuelPointDevice(@Valid @Body SiteDeviceFuelPointDTO dto) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteDeviceFuelPointDTO result = siteDeviceFuelPointService.createFuelPointDTO(dto, rankOrder, locale);
        return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Create Site Device FuelPoint", result);
    }

    @ApiResponse(responseCode = "200", description = "FuelPoint Updated")
    @ApiResponse(responseCode = "404", description = "Device code not found")
    @Put("/devices/fuelPoints/{deviceCode}")
    public BaseResponse<SiteDeviceFuelPointDTO> updateFuelPointDevice(@Valid @Body SiteDeviceFuelPointDTO dto, @PathVariable String deviceCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteDeviceFuelPointDTO result = siteDeviceFuelPointService.updateFuelPointDTO(deviceCode, dto, rankOrder, locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Update Site Device FuelPoint", result);
    }


}
