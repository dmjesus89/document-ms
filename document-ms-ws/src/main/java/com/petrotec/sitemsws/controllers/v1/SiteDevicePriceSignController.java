package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.siteDevice.SiteDevicePriceSignDTO;
import com.petrotec.documentms.services.SiteDevicePriceSignService;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.validation.Valid;

import static io.micronaut.http.HttpStatus.CREATED;
import static io.micronaut.http.HttpStatus.OK;

@Validated
@Controller(value = "/api/v1/devices/priceSigns")
@Tag(name = "Price Signs")
public class SiteDevicePriceSignController {
    private static final Logger LOG = LoggerFactory.getLogger(SiteDevicePriceSignController.class);

   private final SiteDevicePriceSignService siteDevicePriceSignService;

    public SiteDevicePriceSignController(SiteDevicePriceSignService siteDevicePriceSignService) {
        this.siteDevicePriceSignService = siteDevicePriceSignService;
    }

    @ApiResponse(responseCode = "200", description = "Device Price Sign List")
    @Get
    public BaseResponse<PageResponse<SiteDevicePriceSignDTO>> getPriceSigns(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery) {
        LOG.debug("Handling request for findAll. PageAndSorting: {} Filter: {}", pageAndSorting, filterQuery);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List Price Sign",
                siteDevicePriceSignService.listPriceSign(pageAndSorting, filterQuery));
    }

    @ApiResponse(responseCode = "200", description = "Price Sign Details")
    @ApiResponse(responseCode = "404", description = "Price Sign code not found")
    @Get("/{priceSignCode}")
    public BaseResponse<SiteDevicePriceSignDTO> getPriceSignDetails(@PathVariable String priceSignCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        SiteDevicePriceSignDTO result = siteDevicePriceSignService.getPriceSignDetailsDTO(priceSignCode,locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Price Sign Details", result);
    }

    @ApiResponse(responseCode = "200", description = "Created Price Sign")
    @ApiResponse(responseCode = "409", description = "Price Sign code already exists")
    @Post
    public BaseResponse<SiteDevicePriceSignDTO> createPriceSign(@Valid @Body SiteDevicePriceSignDTO dto) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        SiteDevicePriceSignDTO result = siteDevicePriceSignService.createPriceSignDTO(dto,locale);
        return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Create Site Price Sign", result);
    }

    @ApiResponse(responseCode = "200", description = "Price Sign Updated")
    @ApiResponse(responseCode = "404", description = "Price Sign code not found")
    @Put("/{priceSignCode}")
    public BaseResponse<SiteDevicePriceSignDTO> updatePriceSign(@Valid @Body SiteDevicePriceSignDTO dto, @PathVariable String priceSignCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        SiteDevicePriceSignDTO result = siteDevicePriceSignService.updatePriceSignDTO(priceSignCode, dto,locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Update Site Price Sign", result);
    }

}
