package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.siteDevice.SiteDevicePosDTO;
import com.petrotec.documentms.services.SiteDevicePosService;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.annotation.Nullable;
import javax.validation.Valid;

import static io.micronaut.http.HttpStatus.CREATED;
import static io.micronaut.http.HttpStatus.OK;

@Validated
@Controller(value = "/api/v1/devices/pos")
@Tag(name = "Site Pos Devices")
public class SiteDevicePosController {

    private final SiteDevicePosService siteDevicePosService;

    public SiteDevicePosController(SiteDevicePosService siteDevicePosService) {
        this.siteDevicePosService = siteDevicePosService;
    }

    @ApiResponse(responseCode = "200", description = "Pos Device List")
    @Get
    public BaseResponse<PageResponse<SiteDevicePosDTO>> getPosDevices(
            @Nullable PageAndSorting pageAndSorting,
            @Nullable Filter filterQuery) {

        return BaseResponse.success(OK.getReason(), OK.getCode(), "List POS Devices", siteDevicePosService.listPos(pageAndSorting, filterQuery));
    }

    @ApiResponse(responseCode = "200", description = "Pos Details")
    @ApiResponse(responseCode = "404", description = "Device code not found")
    @Get("/{deviceCode}")
    public BaseResponse<SiteDevicePosDTO> getPosDetails(String deviceCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteDevicePosDTO result = siteDevicePosService.getPosDetailsDTO(deviceCode, rankOrder, locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Pos Details", result);
    }

    @ApiResponse(responseCode = "200", description = "Created POS")
    @ApiResponse(responseCode = "409", description = "Device code already exists")
    @Post
    public BaseResponse<SiteDevicePosDTO> createPosDevice(@Valid @Body SiteDevicePosDTO dto) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteDevicePosDTO result = siteDevicePosService.createPosDTO(dto, rankOrder, locale);
        return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Create Site Device POS", result);
    }

    @ApiResponse(responseCode = "200", description = "POS Updated")
    @ApiResponse(responseCode = "404", description = "Device code not found")
    @Put("/{deviceCode}")
    public BaseResponse<SiteDevicePosDTO> updatePosDevice(@Valid @Body SiteDevicePosDTO dto, @PathVariable String deviceCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteDevicePosDTO result = siteDevicePosService.updatePosDTO(deviceCode, dto, rankOrder, locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Update Site Device POS", result);
    }


}
