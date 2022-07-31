package com.petrotec.documentms.interfaces;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.siteRegion.RegionCreateDTO;
import com.petrotec.documentms.dtos.siteRegion.RegionDTO;

/**
 * RegionApiSpec
 */
public interface IRegionApi {

    BaseResponse<PageResponse<RegionDTO>> findAllRegions(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filters);

    BaseResponse<RegionDTO> findRegionByCode(@NotEmpty String regionCode);

    BaseResponse<RegionDTO> updateRegionByCode(@NotEmpty String regionCode, @NotNull RegionCreateDTO regionDTO);

    BaseResponse<RegionDTO> addRegion(@NotNull RegionCreateDTO regionDTO);

    BaseResponse<RegionDTO> deleteRegionByCode(@NotEmpty String regionCode);

}