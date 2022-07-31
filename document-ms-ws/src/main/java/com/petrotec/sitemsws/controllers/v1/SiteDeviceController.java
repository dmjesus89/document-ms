package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.SiteDeviceTankLevelGaugesDTO;
import com.petrotec.documentms.dtos.siteDevice.*;
import com.petrotec.documentms.services.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static io.micronaut.http.HttpStatus.OK;

@Validated
@Controller(value = "/api/v1/")
@Tag(name = "Site Devices")
public class SiteDeviceController {

    private final SiteDeviceService siteDeviceService;
    private final DeviceProtocolService deviceProtocolService;
    private final SiteDeviceFuelPointService siteDeviceFuelPointService;
    private final SiteDevicePosService siteDevicePosService;
    private final WarehouseService warehouseService;
    private final SiteDeviceTankLevelGaugesService siteDeviceTankLevelGaugesService;
    private final SiteDeviceFCCService siteDeviceFCCService;
    private final SiteDeviceDispenserService siteDeviceDispenserService;
    private final SiteDevicePriceSignService siteDevicePriceSignService;


    public SiteDeviceController(SiteDeviceService siteDeviceService, DeviceProtocolService deviceProtocolService,
                                SiteDeviceFuelPointService siteDeviceFuelPointService,
                                SiteDevicePosService siteDevicePosService, WarehouseService warehouseService, SiteDeviceTankLevelGaugesService siteDeviceTankLevelGaugesService,
                                SiteDeviceFCCService siteDeviceFCCService,
                                SiteDeviceDispenserService siteDeviceDispenserService, SiteDevicePriceSignService siteDevicePriceSignService) {
        this.siteDeviceService = siteDeviceService;
        this.deviceProtocolService = deviceProtocolService;
        this.siteDeviceFuelPointService = siteDeviceFuelPointService;
        this.siteDevicePosService = siteDevicePosService;
        this.warehouseService = warehouseService;
        this.siteDeviceTankLevelGaugesService = siteDeviceTankLevelGaugesService;
        this.siteDeviceFCCService = siteDeviceFCCService;
        this.siteDeviceDispenserService = siteDeviceDispenserService;
        this.siteDevicePriceSignService = siteDevicePriceSignService;
    }

    @ApiResponse(responseCode = "200", description = "deviceTypes List")
    @Get("/devices/deviceTypes")
    public BaseResponse<PageResponse<SiteDeviceTypeDTO>> getDeviceTypes() {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        List<SiteDeviceTypeDTO> result = siteDeviceService.listSiteDeviceTypes(locale);
        PageResponse<SiteDeviceTypeDTO> pageResult = PageResponse.from(result, new PageAndSorting(0, result.size(), true, null), () -> Long.valueOf(result.size()));
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List deviceTypes", pageResult);
    }

    @ApiResponse(responseCode = "200", description = "deviceSubtypes List")
    @Get("/devices/deviceSubtypes")
    public BaseResponse<PageResponse<SiteDeviceSubtypeDTO>> getDeviceSubtypes(@Nullable String siteDeviceTypeCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        List<SiteDeviceSubtypeDTO> result = deviceProtocolService.listSiteDeviceSubtypes(siteDeviceTypeCode, locale);
        PageResponse<SiteDeviceSubtypeDTO> pageResult = PageResponse.from(result, new PageAndSorting(0, result.size(), true, null), () -> Long.valueOf(result.size()));
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List deviceSubtypes", pageResult);
    }

    @ApiResponse(responseCode = "200", description = "Support Tank level gauges Equipments")
    @Get("/devices/tankLevelGaugeTypes")
    public BaseResponse<PageResponse<SiteDeviceSubtypeDTO>> getTankGaugeTypes() {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        List<SiteDeviceSubtypeDTO> result = deviceProtocolService.listTLGTypes(locale);
        PageResponse<SiteDeviceSubtypeDTO> pageResult = PageResponse.from(result, new PageAndSorting(0, result.size(), true, null), () -> Long.valueOf(result.size()));
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List TankGaugeTypes", pageResult);
    }

    @ApiResponse(responseCode = "200", description = "Supported Price Sign Equipments")
    @Get("/devices/priceSignTypes")
    public BaseResponse<PageResponse<SiteDeviceSubtypeDTO>> getPriceSignTypes() {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        List<SiteDeviceSubtypeDTO> result = deviceProtocolService.listPriceSignsTypes(locale);
        PageResponse<SiteDeviceSubtypeDTO> pageResult = PageResponse.from(result, new PageAndSorting(0, result.size(), true, null), () -> Long.valueOf(result.size()));
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List PriceSignTypes", pageResult);
    }


    @ApiResponse(responseCode = "200", description = "Supported Fuel Pump Equipments")
    @Get("/devices/pumpTypes")
    public BaseResponse<PageResponse<SiteDeviceSubtypeDTO>> getPumpTypes() {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        List<SiteDeviceSubtypeDTO> result = deviceProtocolService.listPumpTypes(locale);
        PageResponse<SiteDeviceSubtypeDTO> pageResult = PageResponse.from(result, new PageAndSorting(0, result.size(), true, null), () -> Long.valueOf(result.size()));
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List PumpTypes", pageResult);
    }

    @ApiResponse(responseCode = "200", description = "Supported Forecourt controllers Equipments")
    @Get("/devices/fccTypes")
    public BaseResponse<PageResponse<SiteDeviceSubtypeDTO>> getFccTypes() {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        List<SiteDeviceSubtypeDTO> result = deviceProtocolService.listFccTypes(locale);
        PageResponse<SiteDeviceSubtypeDTO> pageResult = PageResponse.from(result, new PageAndSorting(0, result.size(), true, null), () -> Long.valueOf(result.size()));
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List FccTypes", pageResult);
    }

    @ApiResponse(responseCode = "200", description = "Support POS Equipments")
    @Get("/devices/posTypes")
    public BaseResponse<PageResponse<SiteDeviceSubtypeDTO>> getPosTypes() {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        List<SiteDeviceSubtypeDTO> result = deviceProtocolService.listPosTypes(locale);
        PageResponse<SiteDeviceSubtypeDTO> pageResult = PageResponse.from(result, new PageAndSorting(0, result.size(), true, null), () -> Long.valueOf(result.size()));
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List posTypes", pageResult);
    }

    @ApiResponse(responseCode = "200", description = "warehouseTypes List")
    @Get("/devices/warehouseTypes")
    public BaseResponse<PageResponse<WarehouseTypeDTO>> getWarehouseTypes() {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        List<WarehouseTypeDTO> result = warehouseService.listWarehouseTypes(locale);
        PageResponse<WarehouseTypeDTO> pageResult = PageResponse.from(result, new PageAndSorting(0, result.size(), true, null), () -> Long.valueOf(result.size()));
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List warehouseTypes", pageResult);
    }


    /************************/
    /********** POS *********/
    /************************/

    @ApiResponse(responseCode = "200", description = "Site Device POS List")
    @Get("/sites/{siteCode}/devices/pos")
    public BaseResponse<PageResponse<SiteDevicePosDTO>> getSitePosDevices(
            @Nullable PageAndSorting pageAndSorting,
            @Nullable Filter filterQuery,
            String siteCode) {

        return BaseResponse.success(OK.getReason(), OK.getCode(), "List Site Device POS", siteDevicePosService.listPosBySite(pageAndSorting,filterQuery,siteCode));
    }

    /*******************************/
    /********** WAREHOUSES *********/
    /*******************************/

    @ApiResponse(responseCode = "200", description = "Site Device Warehouse List filtered by Site")
    @Get("/sites/{siteCode}/devices/warehouses")
    public BaseResponse<PageResponse<SiteDeviceWarehouseDTO>> getSiteWarehouseDevices(
            @Nullable PageAndSorting pageAndSorting,
            @Nullable Filter filterQuery,
            @NotNull @NotEmpty String siteCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);

        PageResponse<SiteDeviceWarehouseDTO> pageResult = warehouseService.listWarehousesDTO(pageAndSorting, filterQuery, rankOrder, locale, siteCode);

        return BaseResponse.success(OK.getReason(), OK.getCode(), "List Site Device Warehouse", pageResult);
    }

    /*******************************/
    /********** FuelPoints *********/
    /*******************************/

    @ApiResponse(responseCode = "200", description = "Site Device FuelPoint List")
    @Get("/sites/{siteCode}/devices/fuelPoints")
    public BaseResponse<PageResponse<SiteDeviceFuelPointDTO>> getSiteFuelPointDevices(
            @Nullable PageAndSorting pageAndSorting,
            @Nullable Filter filterQuery,
            String siteCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);

        PageResponse<SiteDeviceFuelPointDTO> result = siteDeviceFuelPointService.listFuelPointsDTO(pageAndSorting, filterQuery, rankOrder, locale, siteCode);

        return BaseResponse.success(OK.getReason(), OK.getCode(), "List Site Device FuelPoint", result);
    }


    /*******************************/
    /********** DISPENSERS *********/
    /*******************************/

    @ApiResponse(responseCode = "200", description = "Site Device Dispenser List")
    @Get("/sites/{siteCode}/devices/fuelDispensers")
    public BaseResponse<PageResponse<SiteDeviceDispenserDTO>> getSiteDispenserDevices(
            @Nullable PageAndSorting pageAndSorting,
            @Nullable Filter filterQuery,
            String siteCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List Site Device Dispenser", siteDeviceDispenserService.listDispensersBySite(pageAndSorting, filterQuery, siteCode, locale));
    }

    /*******************************/
    /**********  FORECOURT *********/
    /*******************************/

    @ApiResponse(responseCode = "200", description = "Site Device Forecourt List")
    @Get("/sites/{siteCode}/devices/fcc")
    public BaseResponse<PageResponse<SiteDeviceFccDTO>> getSiteForecourtDevices(
            @Nullable PageAndSorting pageAndSorting,
            @Nullable Filter filterQuery,
            @NotEmpty @NotNull String siteCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        return BaseResponse.success(OK.getReason(), OK.getCode(), "List Site Device Forecourt", siteDeviceFCCService.listFccDevicesBySite(pageAndSorting, filterQuery, siteCode,locale));
    }


    /*******************************/
    /*** TANK LEVEL GAUGES *********/
    /*******************************/

    @ApiResponse(responseCode = "200", description = "Site Device tank level gauge List filtered by Site")
    @Get("/sites/{siteCode}/devices/tankLevelGauges")
    public BaseResponse<PageResponse<SiteDeviceTankLevelGaugesDTO>> getSiteTankLevelGauges(
            @Nullable PageAndSorting pageAndSorting,
            @Nullable Filter filterQuery,
            @NotNull @NotEmpty String siteCode) {

        PageResponse<SiteDeviceTankLevelGaugesDTO> pageResult = siteDeviceTankLevelGaugesService.listTankLevelGaugesDTO(pageAndSorting, filterQuery, siteCode);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List Site Device tank level gauge", pageResult);
    }


    /*******************************/
    /********** Price Signs *********/
    /*******************************/

    @ApiResponse(responseCode = "200", description = "Site Device Price Signs List")
    @Get("/sites/{siteCode}/devices/priceSigns")
    public BaseResponse<PageResponse<SiteDevicePriceSignDTO>> getPriceSigns(
            @Nullable PageAndSorting pageAndSorting,
            @Nullable Filter filterQuery,
            String siteCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        return BaseResponse.success(OK.getReason(), OK.getCode(), "List Site Device Dispenser", siteDevicePriceSignService.listPriceSign(pageAndSorting, filterQuery, siteCode, locale));
    }

}
