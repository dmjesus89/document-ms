package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceDispenserDTO;
import com.petrotec.documentms.mappers.devices.SiteDeviceDispenserMapper;
import com.petrotec.documentms.services.SiteDeviceDispenserService;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.annotation.Nullable;
import javax.validation.Valid;

import static io.micronaut.http.HttpStatus.CREATED;
import static io.micronaut.http.HttpStatus.OK;

@Validated
@Controller(value = "/api/v1/devices/fuelDispensers")
@Tag(name = "Site Devices")
public class SiteDeviceDispenserController {

    private final SiteDeviceDispenserService siteDeviceDispenserService;
    private final SiteDeviceDispenserMapper siteDeviceDispenserMapper;

    public SiteDeviceDispenserController(SiteDeviceDispenserService siteDeviceDispenserService, SiteDeviceDispenserMapper siteDeviceDispenserMapper) {
        this.siteDeviceDispenserService = siteDeviceDispenserService;
        this.siteDeviceDispenserMapper = siteDeviceDispenserMapper;
    }

    @ApiResponse(responseCode = "200", description = "Device Dispensers List")
    @Get
    public BaseResponse<PageResponse<SiteDeviceDispenserDTO>> getDispenserDevices(
            @Nullable PageAndSorting pageAndSorting,
            @Nullable Filter filterQuery) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List Device Dispenser", siteDeviceDispenserService.listDispensers(pageAndSorting, filterQuery, locale));
    }

    @ApiResponse(responseCode = "200", description = "Dispenser Details")
    @ApiResponse(responseCode = "404", description = "Dispenser code not found")
    @Get("/{deviceCode}")
    public BaseResponse<SiteDeviceDispenserDTO> getDispenserDetails(String deviceCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteDeviceDispenserDTO result = siteDeviceDispenserService.getDispenserDetailsDTO(deviceCode, rankOrder, locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Dispenser Details", result);
    }

    @ApiResponse(responseCode = "200", description = "Created FuelPoint")
    @ApiResponse(responseCode = "409", description = "Device code already exists")
    @Post
    public BaseResponse<SiteDeviceDispenserDTO> createDispenserDevice(@Valid @Body SiteDeviceDispenserDTO dto) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteDeviceDispenserDTO result = siteDeviceDispenserService.createDispenserDTO(dto, rankOrder, locale);
        return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Create Site Device Dispenser", result);
    }

    @ApiResponse(responseCode = "200", description = "FuelPoint Updated")
    @ApiResponse(responseCode = "404", description = "Device code not found")
    @Put("/{deviceCode}")
    public BaseResponse<SiteDeviceDispenserDTO> updateDispenserDevice(@Valid @Body SiteDeviceDispenserDTO dto, @PathVariable String deviceCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteDeviceDispenserDTO result = siteDeviceDispenserService.updateDispenserDTO(deviceCode, dto, rankOrder, locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Update Site Device Dispenser", result);
    }

}
