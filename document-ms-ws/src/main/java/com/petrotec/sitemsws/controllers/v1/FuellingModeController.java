package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.configuration.FuellingModeDTO;
import com.petrotec.documentms.interfaces.IFuellingMode;
import com.petrotec.documentms.services.configuration.IFuellingModeService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.validation.Validated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.validation.Valid;

import static io.micronaut.http.HttpStatus.CREATED;
import static io.micronaut.http.HttpStatus.OK;

@Controller(value = "/api/v1/siteConfiguration/fuellingModes")
@Validated
public class FuellingModeController {

    private static final Logger LOG = LoggerFactory.getLogger(FuellingModeController.class);

    private final IFuellingMode fuellingModeService;

    public FuellingModeController(IFuellingModeService fuellingModeService) {
        this.fuellingModeService = fuellingModeService;
    }

    @Get
    public BaseResponse<PageResponse<FuellingModeDTO>> findAll(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery) {
        LOG.debug("Handling request for findAll. PageAndSorting: {} Filter: {}", pageAndSorting, filterQuery);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List of fuelling mode",
                fuellingModeService.findAll(pageAndSorting, filterQuery));
    }

    @Get("/{code}")
    public BaseResponse<FuellingModeDTO> getByCode(String code) {
        LOG.debug("Handling request to get fuelling mode with code {}", code);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Fuelling mode by code", fuellingModeService.getByCode(code));
    }

    @Post
    public BaseResponse<FuellingModeDTO> create(@Valid FuellingModeDTO fuellingModeDTO) {
        LOG.debug("Handling request for create fueling Mode. Create fueling Mode: {}", fuellingModeDTO);
        return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Fuelling mode saved with success",
                fuellingModeService.create(fuellingModeDTO));
    }

    @Put("/{code}")
    public BaseResponse<FuellingModeDTO> update(String code, @Valid FuellingModeDTO fuellingModeDTO) {
        LOG.debug("Handling request to updated fueling Mode with code {}", code);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Fuelling mode : " + code + " successfully updated",
                fuellingModeService.update(code, fuellingModeDTO));
    }

}
