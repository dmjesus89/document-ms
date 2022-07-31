package com.petrotec.documentms.interfaces;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.siteProfile.SiteProfileCreateDTO;
import com.petrotec.documentms.dtos.siteProfile.SiteProfileDTO;

import javax.annotation.Nullable;

/**
 * SiteProfileApiSpec
 */
public interface ISiteProfileApi {

    BaseResponse<SiteProfileDTO> findProfilesByCode(String siteProfileCode);

    BaseResponse<PageResponse<SiteProfileDTO>> findProfilesAll(@Nullable PageAndSorting pageAndSorting,
                                                               @Nullable Filter filters);

    BaseResponse<PageResponse<SiteProfileDTO>> findAllProfilesTranslated(@Nullable PageAndSorting pageAndSorting,
                                                                                   @Nullable Filter filters);

    BaseResponse<SiteProfileDTO> addSiteProfile(SiteProfileCreateDTO siteProfileCreateDTO);

    BaseResponse<SiteProfileDTO> disableSiteProfile(String siteProfileCode);

    BaseResponse<SiteProfileDTO> updateSiteProfile(String siteProfileCode, SiteProfileCreateDTO siteProfileCreateDTO);
}
