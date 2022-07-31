package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceFccDTO;
import com.petrotec.documentms.services.SiteDeviceFCCService;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.annotation.Nullable;
import javax.validation.Valid;

import static io.micronaut.http.HttpStatus.CREATED;
import static io.micronaut.http.HttpStatus.OK;

@Validated
@Controller(value = "/api/v1/devices/fcc")
@Tag(name = "Site Devices FCC")
public class SiteDeviceFccController {
    private final SiteDeviceFCCService siteDeviceFCCService;

    public SiteDeviceFccController(SiteDeviceFCCService siteDeviceFCCService) {
        this.siteDeviceFCCService = siteDeviceFCCService;
    }

    @ApiResponse(responseCode = "200", description = "Site Device Forecourt List")
    @Get
    public BaseResponse<PageResponse<SiteDeviceFccDTO>> getForecourtDevices(
            @Nullable PageAndSorting pageAndSorting,
            @Nullable Filter filterQuery) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        return BaseResponse.success(OK.getReason(), OK.getCode(), "List Site Device Forecourt", siteDeviceFCCService.listFccDevices(pageAndSorting, filterQuery,locale));
    }

    @ApiResponse(responseCode = "200", description = "Device Obtained")
    @ApiResponse(responseCode = "404", description = "Device code not found")
    @Get("/{deviceCode}")
    public BaseResponse<SiteDeviceFccDTO> getFccDevice(@PathVariable String deviceCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        SiteDeviceFccDTO result = siteDeviceFCCService.getFccDevice(deviceCode, locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Update Forecourt Device", result);
    }

    @ApiResponse(responseCode = "200", description = "Forecourt Device")
    @ApiResponse(responseCode = "409", description = "Device code already exists")
    @Post
    public BaseResponse<SiteDeviceFccDTO> createForecourtDevice(@Valid @Body SiteDeviceFccDTO dto) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteDeviceFccDTO result = siteDeviceFCCService.createFccDeviceDTO(dto, rankOrder, locale);
        return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Create Forecourt Device", result);
    }

    @ApiResponse(responseCode = "200", description = "Forecourt Updated")
    @ApiResponse(responseCode = "404", description = "Device code not found")
    @Put("/{deviceCode}")
    public BaseResponse<SiteDeviceFccDTO> updateForecourtDevice(@Valid @Body SiteDeviceFccDTO dto, @PathVariable String deviceCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteDeviceFccDTO result = siteDeviceFCCService.updateFccDTO(deviceCode, dto, rankOrder, locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Update Forecourt Device", result);
    }

}
