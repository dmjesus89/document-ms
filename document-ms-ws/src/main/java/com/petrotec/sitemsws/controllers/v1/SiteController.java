package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.ChangeStatus;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.site.SiteCreateDTO;
import com.petrotec.documentms.dtos.site.SiteCustomDTO;
import com.petrotec.documentms.dtos.site.SiteExtendedDTO;
import com.petrotec.documentms.dtos.site.SiteUpdateDTO;
import com.petrotec.documentms.interfaces.ISiteApi;
import com.petrotec.documentms.services.SiteService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Controller(value = "/api/v1/site")
public class SiteController implements ISiteApi {
    private static final Logger LOG = LoggerFactory.getLogger(SiteController.class);

    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @Get
    public BaseResponse<PageResponse<SiteCustomDTO>> getSites(@Nullable PageAndSorting pageAndSorting,
                                                              @Nullable Filter filterQuery) {
        LOG.debug("Handling request for getSites. PageAndSorting: {} Filter: {}");
        return siteService.getSites(pageAndSorting, filterQuery);
    }

    @Post
    public BaseResponse<SiteExtendedDTO> createSite(@Valid SiteCreateDTO createSite) {
        LOG.debug("Handling request for create site. Create site: {}", createSite);
        return siteService.createSite(createSite);
    }

    @Get("/{siteCode}")
    public BaseResponse<SiteExtendedDTO> getSiteByCode(@NotEmpty String siteCode) {
        LOG.debug("Handling request for get site with code {}", siteCode);
        return siteService.getSiteByCodeDTO(siteCode);
    }

    @Put("/status")
    public BaseResponse<Void> setSiteStatus(@NotNull ChangeStatus changeStatus) {
        LOG.debug("Handling request change status using: {}", changeStatus);
        return siteService.setSiteStatus(changeStatus);
    }

    @Put("/{siteCode}")
    public BaseResponse<SiteExtendedDTO> updateSite(@NotEmpty String siteCode, @Valid SiteUpdateDTO updateSite) {
        LOG.debug("Handling request to updated site with code {}", siteCode);
        return siteService.update(siteCode, updateSite);
    }

}
