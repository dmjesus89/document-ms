package com.petrotec.documentms.interfaces;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.site.SiteDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemSiteDTO;

import java.util.List;

public interface ISiteGroupApi {


	BaseResponse<PageResponse<SiteGroupDTO>> listSiteGroups(PageAndSorting pageAndSorting, Filter filterQuery);

	BaseResponse<SiteGroupDTO> getGroupDetail(String code);

	BaseResponse<SiteGroupDTO> createSiteGroup(SiteGroupDTO siteGroupDTO);

	BaseResponse<Void> deleteSiteGroup(String code);

	BaseResponse<SiteGroupDTO> updateSiteGroup(String code, SiteGroupDTO siteGroupDTO);

	BaseResponse<PageResponse<SiteGroupItemDTO>> listSiteGroupItem(String code);

	BaseResponse<SiteGroupItemDTO> createSiteGroupItem(String code, SiteGroupItemDTO siteGroupItemDTO);

	BaseResponse<Void> deleteSiteGroupItem(String groupCode, String itemCode);

	BaseResponse<SiteGroupItemDTO> updateSiteGroupItem(String groupCode, String itemCode,
			SiteGroupItemDTO siteGroupItemDTO);

	BaseResponse<PageResponse<SiteGroupItemSiteDTO>> getSiteGroupItemSite(String groupCode, String itemCode);

	BaseResponse<PageResponse<SiteGroupItemSiteDTO>> createSiteGroupItemSite(String siteGroupCode,
			String siteGroupItemCode, List<SiteDTO> sites);

	BaseResponse<Void> deleteSiteGroupItemSite(String groupCode, String itemCode, String siteCode);

	BaseResponse<SiteGroupItemDTO> getSiteGroupItem(String groupCode, String itemCode);
}
