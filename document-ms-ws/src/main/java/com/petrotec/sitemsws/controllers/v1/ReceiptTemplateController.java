package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.receipt.ReceiptBlockTypeDTO;
import com.petrotec.documentms.dtos.receipt.ReceiptTemplateDTO;
import com.petrotec.documentms.dtos.receipt.ReceiptTemplateTypeDTO;
import com.petrotec.documentms.services.interfaces.ReceiptTemplate;
import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static io.micronaut.http.HttpStatus.OK;

@Controller("/api/v1")
public class ReceiptTemplateController {

    private final ReceiptTemplate service;

    public ReceiptTemplateController(ReceiptTemplate service) {
        this.service = service;
    }

    @Get("/receipts/templateTypes")
    public BaseResponse<PageResponse<ReceiptTemplateTypeDTO>> getAll(@Nullable Filter filter,
                                                             @Nullable PageAndSorting pageAndSorting) {
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List of templates types",
                service.getTemplateType(pageAndSorting, filter));
    }

    @Get("/receipts/templateTypes/{code}/blocks")
    public BaseResponse<PageResponse<ReceiptBlockTypeDTO>> getBlockTypeByTypeCode(@NotBlank @PathVariable String code){
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List of block types", service.getBlockTypeByTypeCode(code));
    }

    @Get("/receipts/templates")
    public BaseResponse<PageResponse<ReceiptTemplateDTO>> getTemplates() {
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List of receipt templates", service.getTemplates());
    }

    @Get("/receipts/templates/{code}")
    public BaseResponse<ReceiptTemplateDTO> getTemplatesByCode(@NotBlank @PathVariable String code) {
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List of receipt templates", service.getTemplatesByCode(code));
    }

    @Post("/receipts/templates")
    public BaseResponse<ReceiptTemplateDTO> createTemplate(@Valid @Body ReceiptTemplateDTO receiptTemplateDTO) {
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Create with success receipt template.", service.createTemplateByCode(null, receiptTemplateDTO));
    }

    @Put("/receipts/templates/{code}")
    public BaseResponse<ReceiptTemplateDTO> updateTemplateByCode(@Nullable @PathVariable String code, @Valid @Body ReceiptTemplateDTO receiptTemplateDTO) {
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Update with success receipt template.", service.createTemplateByCode(code, receiptTemplateDTO));
    }

    @Delete("/receipts/templates/{code}")
    public BaseResponse<Boolean> deleteTemplateByCode(@NotBlank @PathVariable String code) {
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Delete with success receipt template.", service.deleteTemplateByCode(code));
    }

    @Get("/receipts/templates/bySite/{siteCode}")
    public BaseResponse<List<ReceiptTemplateDTO>> getTemplatesBySiteCode(@NotBlank @PathVariable String siteCode) {
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List of receipt template by site code with success.", service.getTemplatesBySiteCode(siteCode));
    }

    @Put("/sites/{siteCode}/receipts/templates/{templateCode}")
    public BaseResponse<Boolean> associatedTemplateBySite(@NotBlank @PathVariable String templateCode, @NotBlank @PathVariable String siteCode) {
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Associated of site the receipt template with success.", service.associatedTemplateBySite(templateCode, siteCode));
    }

    @Delete("/sites/{siteCode}/receipts/templates/{templateCode}")
    public BaseResponse<Boolean> deleteAssociateTemplateBySite(@NotBlank @PathVariable String templateCode, @NotBlank @PathVariable String siteCode) {
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Unassociated of site the receipt template with success.", service.deleteAssociateTemplateBySite(templateCode, siteCode));
    }
}
