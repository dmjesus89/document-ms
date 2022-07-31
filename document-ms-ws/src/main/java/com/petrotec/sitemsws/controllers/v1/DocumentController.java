package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.documents.DocumentDTO;
import com.petrotec.documentms.dtos.documents.DocumentExtendedDTO;
import com.petrotec.documentms.dtos.documents.DocumentReceiptDTO;
import com.petrotec.documentms.dtos.documents.DocumentReceiptExtraDataDTO;
import com.petrotec.documentms.dtos.documents.search.DocumentHeaderDTO;
import com.petrotec.documentms.dtos.documents.search.DocumentProductLineDetailsDTO;
import com.petrotec.documentms.services.documents.DocumentHeaderService;
import com.petrotec.documentms.services.documents.DocumentProductDetailsService;
import com.petrotec.documentms.services.documents.DocumentService;
import io.micronaut.http.annotation.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static io.micronaut.http.HttpStatus.OK;

@Controller("/api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentHeaderService saleHeaderService;
    private final DocumentProductDetailsService documentProductDetailsService;

    public DocumentController(DocumentService documentService, DocumentHeaderService saleHeaderService, DocumentProductDetailsService documentProductDetailsService) {
        this.documentService = documentService;
        this.saleHeaderService = saleHeaderService;
        this.documentProductDetailsService = documentProductDetailsService;
    }

    @Get("/{?documentCode*}")
    public BaseResponse<PageResponse<DocumentExtendedDTO>> getDocumentsByCode(@Nullable PageAndSorting pageAndSorting,
                                                                              @Nullable Filter filters, @QueryValue List<String> documentCode) {

        filters = handleDocumentCodeArgument(filters, documentCode, "code");

        PageResponse<DocumentExtendedDTO> result = documentService.getDocuments(pageAndSorting, filters);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "getDocumentsByCode", result);
    }


    @Post
    public BaseResponse<DocumentExtendedDTO> integrateDocument(@Nonnull DocumentDTO documentDTO) {
        DocumentExtendedDTO result = documentService.integrateDocument(documentDTO);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "integrateDocument", result);
    }

    @Get("/headers")
    public BaseResponse<PageResponse<DocumentHeaderDTO>> getSaleHeader(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filters) {
        return BaseResponse.success(OK.getReason(), OK.getCode(), "getDocumentHeaderDetail", saleHeaderService.getDocumentHeaders(pageAndSorting, filters));
    }

    @Get("/productLineDetails")
    public BaseResponse<PageResponse<DocumentProductLineDetailsDTO>> getDocumentProductLineDetails(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filters) {
        return BaseResponse.success(OK.getReason(), OK.getCode(), "getDocumentProductLineDetails", documentProductDetailsService.getDocumentProductLineDetails(pageAndSorting, filters));
    }


    @Get("/{documentCode}")
    public BaseResponse<DocumentExtendedDTO> getDocumentDetails(@Nonnull String documentCode) {
        DocumentExtendedDTO result = documentService.getDocumentByCode(documentCode);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "getDocumentDetails", result);
    }

    @Post("/{documentCode}/receipt")
    public BaseResponse<DocumentExtendedDTO> saveDocumentReceipt(@Nonnull String documentCode, DocumentReceiptDTO receiptDTO) {
        DocumentExtendedDTO result = documentService.insertDocumentReceiptData(documentCode, receiptDTO);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "saveDocumentReceipt", result);
    }


    @Put("{documentCode}/receipt")
    public BaseResponse<DocumentReceiptExtraDataDTO> incrementReceiptCopies(String documentCode) {

        DocumentReceiptExtraDataDTO result = documentService.increaseDocumentReceiptCopies(documentCode);

        return BaseResponse.success(OK.getReason(), OK.getCode(), "incrementReceiptCopies", result);
    }

    @Get("/receipts{?documentCode*}")
    public BaseResponse<PageResponse<DocumentReceiptExtraDataDTO>> getDocumentReceiptsByDocumentCode(@Nullable PageAndSorting pageAndSorting,
                                                                                                     @Nullable Filter filters, @QueryValue List<String> documentCode) {

        //HANDLE DOCUMENT CODE PARAMETERS
        filters = handleDocumentCodeArgument(filters, documentCode, "document.code");

        PageResponse<DocumentReceiptExtraDataDTO> result = documentService.getDocumentReceipts(pageAndSorting, filters);
        return BaseResponse.success(OK.getReason(), OK.getCode(), "getDocumentReceiptsByDocumentCode", result);
    }

    private Filter handleDocumentCodeArgument(Filter filters, List<String> documentCode, String argumentName) {
        //HANDLE DOCUMENT CODE PARAMETERS
        if (documentCode != null && documentCode.size() > 0) {
            Filter codeFilter = new com.petrotec.api.filter.logic.In(argumentName, documentCode.toArray());
            if (filters == null)
                filters = codeFilter;
            else
                filters = new com.petrotec.api.filter.logic.And(filters, codeFilter);
        }

        return filters;
    }
}
