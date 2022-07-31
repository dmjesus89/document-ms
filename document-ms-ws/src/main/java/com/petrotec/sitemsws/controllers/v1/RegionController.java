package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.siteRegion.RegionCreateDTO;
import com.petrotec.documentms.dtos.siteRegion.RegionDTO;
import com.petrotec.documentms.services.RegionService;
import com.petrotec.documentms.interfaces.IRegionApi;
import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * RegionController
 */

@Controller("/api/v1/regions")
public class RegionController implements IRegionApi {
    protected final RegionService regionService;

    public RegionController(final RegionService regionService) {
        this.regionService = regionService;
    }

    @Get
    @Override
    public BaseResponse<PageResponse<RegionDTO>> findAllRegions(@Nullable PageAndSorting pageAndSorting,
            @Nullable Filter filters) {
        return regionService.findAll(pageAndSorting, filters);
    }

    @Get("/{regionCode}")
    @Override
    public BaseResponse<RegionDTO> findRegionByCode(@NotEmpty String regionCode) {
        return regionService.findByCode(regionCode);
    }

    @Put("/{regionCode}")
    @Override
    public BaseResponse<RegionDTO> updateRegionByCode(@NotEmpty String regionCode,
            @NotNull RegionCreateDTO regionDTO) {
        return regionService.updateByCode(regionCode, regionDTO);
    }

    @Post
    @Override
    public BaseResponse<RegionDTO> addRegion(@NotNull RegionCreateDTO regionDTO) {
        return regionService.addRegion(regionDTO);

    }

    @Delete("/{regionCode}")
    @Override
    public BaseResponse<RegionDTO> deleteRegionByCode(@NotEmpty String regionCode) {
        return regionService.setEnabled(regionCode, false);
    }

}
