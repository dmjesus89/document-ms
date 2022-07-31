package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.grade.GradeDTO;
import com.petrotec.documentms.dtos.grade.GradeViewDTO;
import com.petrotec.documentms.dtos.grade.SiteGradeDTO;
import com.petrotec.documentms.entities.Grade;
import com.petrotec.documentms.entities.SiteGrade;
import com.petrotec.documentms.mappers.GradeMapper;
import com.petrotec.documentms.mappers.SiteGradeMapper;
import com.petrotec.documentms.services.GradeService;
import io.micronaut.data.model.Page;
import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;

import static io.micronaut.http.HttpStatus.*;

@Controller("/api/v1/")
public class GradeController {

    private final GradeService service;
    private final GradeMapper mapper;
    private final SiteGradeMapper siteGradeMapper;

    public GradeController(GradeService service, GradeMapper mapper, SiteGradeMapper siteGradeMapper) {
        this.service = service;
        this.mapper = mapper;
        this.siteGradeMapper = siteGradeMapper;
    }

    @Get("/grades/")
    public BaseResponse<PageResponse<GradeDTO>> list(@Nullable PageAndSorting pageAndSorting,
                                                     @Nullable Filter filterQuery) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);

        Page<Grade> result = service.list(pageAndSorting, filterQuery, rankOrder);

        PageResponse<GradeDTO> pageResult = PageResponse
                .from(mapper.toDTO(result.getContent(), locale),
                        pageAndSorting, () -> Long.valueOf(result.getSize()));
        return BaseResponse.success(OK.getReason(), OK.getCode(), null, pageResult);
    }

    @Get("/grades/search")
    public BaseResponse<PageResponse<GradeViewDTO>> listCustom(@Nullable PageAndSorting pageAndSorting,
                                                         @Nullable Filter filterQuery) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);

        PageResponse<GradeViewDTO> pageResult = service.listView(pageAndSorting, filterQuery, rankOrder);

        return BaseResponse.success(OK.getReason(), OK.getCode(), null, pageResult);
    }

    @Get("/grades/{code}")
    public BaseResponse<GradeDTO> details(@NotBlank String code) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);

        return BaseResponse.success(OK.getReason(), OK.getCode(), null, service.detailsDTO(code, rankOrder, locale));
    }

    @Post("/grades/")
    public BaseResponse<GradeDTO> create(GradeDTO dto) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);

        return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), null, service.createDTO(dto, rankOrder, locale));
    }

    @Put("/grades/{code}")
    public BaseResponse<GradeDTO> update(@NotBlank String code, GradeDTO dto) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);

        return BaseResponse.success(OK.getReason(), OK.getCode(), null,
                service.updateDTO(code, dto, rankOrder, locale));
    }

    @Get("/sites/{siteCode}/grades")
    public BaseResponse<PageResponse<SiteGradeDTO>> getGradesBySite(@Nullable PageAndSorting pageAndSorting,
                                              @Nullable Filter filterQuery, @NotBlank String siteCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        String rankOrder = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_RANK_ORDER);

        Page<SiteGrade> result = service.getSiteGrades(pageAndSorting,filterQuery,siteCode);
        PageResponse<SiteGradeDTO> pageResult = PageResponse
                .from(siteGradeMapper.toDTO(result.getContent(), locale),
                        pageAndSorting, () -> Long.valueOf(result.getSize()));

        return BaseResponse.success(OK.getReason(), OK.getCode(), null, pageResult);
    }

}
