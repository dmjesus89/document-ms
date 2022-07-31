package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.grade.SiteGradeDTO;
import com.petrotec.documentms.services.SiteGradeService;
import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static io.micronaut.http.HttpStatus.NO_CONTENT;
import static io.micronaut.http.HttpStatus.OK;

@Controller("/api/v1/siteGrades")
public class SiteGradeController {

    private final SiteGradeService service;

    public SiteGradeController(SiteGradeService service) {
        this.service = service;
    }

    @Get
    public BaseResponse<PageResponse<SiteGradeDTO>> list(@Nullable PageAndSorting pageAndSorting,
                                                           @Nullable Filter filterQuery) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        PageResponse<SiteGradeDTO> result = service.listSiteGradesDTO(pageAndSorting, filterQuery, locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), null, result);
    }

    /**
     * List grade associations by site.
     *
     * @param siteCode
     * @return
     */
    @Get("/{siteCode}")
    public BaseResponse<List<SiteGradeDTO>> listSiteGradesBySite(@NotBlank String siteCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        List<SiteGradeDTO> result = service.listSiteGradesBySiteDTO(siteCode, locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), null, result);
    }

    @Post
    public BaseResponse<SiteGradeDTO> createSiteGrade(SiteGradeDTO dto) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        SiteGradeDTO result = service.createSiteGradeDTO(dto, rankOrder, locale);
        return BaseResponse.success(OK.getReason(), OK.getCode(), null, result);

    }

    @Delete
    public BaseResponse<Void> removeSiteGrade(@QueryValue(value = "siteCode") String siteCode, @QueryValue(value = "gradeCode") String gradeCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        service.removeSiteGrade(siteCode, gradeCode, locale);
        return BaseResponse.success(NO_CONTENT.getReason(), NO_CONTENT.getCode(), "Site Grade Deleted", null);
    }

    @Post("/{siteCode}")
    public BaseResponse<List<SiteGradeDTO>> setSiteGrades(String siteCode, @Valid List<SiteGradeDTO> siteGradeList) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Grades associated", service.associateSiteGrades(siteCode, siteGradeList, rankOrder, locale));

    }
}
