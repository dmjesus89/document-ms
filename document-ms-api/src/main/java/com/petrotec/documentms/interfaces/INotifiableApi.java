package com.petrotec.documentms.interfaces;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.*;
import com.petrotec.documentms.dtos.site.SiteCustomDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupDTO;
import com.petrotec.documentms.dtos.siteGroup.SiteGroupItemDTO;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface INotifiableApi {

	BaseResponse<PageResponse<NotifiableDTO>> findAll(@Nullable PageAndSorting pageAndSorting,
			@Nullable Filter filters);

	BaseResponse<PageResponse<NotifiableElementDTO>> getNotifiableElements(@NotEmpty String siteCode,
			@Nullable String alarmSeverity);

	BaseResponse<NotifiableDTO> addNotifiable(@NotNull NotifiableDTO notifiableDTO);

	BaseResponse<NotifiableDTO> findByCode(@NotEmpty String notifiableCode);

	BaseResponse<NotifiableDTO> updateByCode(@NotEmpty String notifiableCode, @NotNull NotifiableDTO notifiableDTO);

	BaseResponse<Void> deleteByCode(@NotEmpty String notifiableCode);

	BaseResponse<NotifiableDTO> enableNotifiable(@NotEmpty String notifiableCode);

	BaseResponse<NotifiableDTO> disableNotifiable(@NotEmpty String notifiableCode);

	BaseResponse<PageResponse<SiteCustomDTO>> getSitesFromNotifiable(@NotEmpty String code);

	BaseResponse<List<NotifiableSiteDTO>> addSitesToNotifiable(@NotEmpty String notifiableCode, @NotNull List<String> siteCodes);

	BaseResponse<Void> removeSitesFromNotifiable(@NotEmpty String notifiableCode, @NotNull List<String> siteCodes);

	BaseResponse<PageResponse<SiteGroupDTO>> getSiteGroupsFromNotifiable(@NotEmpty String code);

	BaseResponse<PageResponse<SiteGroupItemDTO>> getSiteGroupItemsFromNotifiable(
			@NotEmpty String code,  @NotEmpty String siteGroup);

	BaseResponse<List<NotifiableSiteGroupItemDTO>> addSiteGroupItemsToNotifiable(@NotEmpty String notifiableCode,
			@NotEmpty String siteGroup, @NotEmpty List<String> itemCodes);

	BaseResponse<Void> removeSiteGroupItemsFromNotifiable(@NotEmpty String notifiableCode, @NotEmpty String siteGroup,
			@NotEmpty List<String> itemCodes);

	BaseResponse<PageResponse<NotifiableMethodDTO>> getNotifiableMethods(@Nullable Boolean enabled);

	BaseResponse<PageResponse<NotifiableMethodTranslatedDTO>> getNotifiableMethodsTranslated(@Nullable Boolean enabled);

	BaseResponse<NotifiableElementDTO> addNotifiableElement(@NotEmpty String notifiableCode,
			@NotNull NotifiableElementDTO notifiableElement);

	BaseResponse<NotifiableElementDTO> updateNotifiableElement(@NotEmpty String notifiableCode,
			@NotNull String notifiableElementName, @NotNull NotifiableElementDTO notifiableElement);

	BaseResponse<Void> deleteNotifiableElement(@NotEmpty String notifiableCode, @NotNull String notifiableElementName);

	BaseResponse<NotifiableElementContactDTO> addNotifiableElementContact(@NotEmpty String notifiableCode,
			@NotEmpty String notifiableElementName, @NotNull NotifiableElementContactDTO notifiableElementContactDTO);

	BaseResponse<NotifiableElementContactDTO> updateNotifiableElementContact(@NotEmpty String notifiableCode,
			@NotNull String notifiableElementName, @NotNull String contact,
			@NotNull NotifiableElementContactDTO notifiableElementContactDTO);

	BaseResponse<Void> deleteNotifiableElementContact(@NotEmpty String notifiableCode,
			@NotNull String notifiableElementName, @NotNull String contact);
}
