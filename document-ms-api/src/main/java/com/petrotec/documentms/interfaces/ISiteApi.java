package com.petrotec.documentms.interfaces;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.ChangeStatus;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.site.SiteCreateDTO;
import com.petrotec.documentms.dtos.site.SiteCustomDTO;
import com.petrotec.documentms.dtos.site.SiteExtendedDTO;
import com.petrotec.documentms.dtos.site.SiteUpdateDTO;

/**
 * SiteApiSpec
 */
public interface ISiteApi {

    BaseResponse<PageResponse<SiteCustomDTO>> getSites(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filters);

    BaseResponse<SiteExtendedDTO> createSite(@NotNull SiteCreateDTO createSite);

    BaseResponse<SiteExtendedDTO> getSiteByCode(@NotEmpty String siteCode);

    BaseResponse<SiteExtendedDTO> updateSite(@NotEmpty String siteCode, @NotNull SiteUpdateDTO updateSite);

    BaseResponse<Void> setSiteStatus(@NotNull ChangeStatus changeStatus);
}