package com.petrotec.documentms.services.interfaces;

import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.receipt.ReceiptBlockTypeDTO;
import com.petrotec.documentms.dtos.receipt.ReceiptTemplateDTO;
import com.petrotec.documentms.dtos.receipt.ReceiptTemplateTypeDTO;

import java.util.List;

public interface ReceiptTemplate {

    PageResponse<ReceiptTemplateTypeDTO> getTemplateType(PageAndSorting pageAndSorting, Filter filterQuery);

    PageResponse<ReceiptBlockTypeDTO> getBlockTypeByTypeCode(String receiptTypeCode);

    PageResponse<ReceiptTemplateDTO>getTemplates();

    ReceiptTemplateDTO createTemplateByCode(String templateCode, ReceiptTemplateDTO receiptTemplateDTO);

    Boolean deleteTemplateByCode(String templateCode);

    List<ReceiptTemplateDTO> getTemplatesBySiteCode(String siteCode);

    Boolean associatedTemplateBySite(String templateCode, String siteCode);

    Boolean deleteAssociateTemplateBySite(String templateCode, String siteCode);

    ReceiptTemplateDTO getTemplatesByCode(String templateCode);
}
