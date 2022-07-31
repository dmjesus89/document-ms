package com.petrotec.documentms.interfaces;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageResponse;

/**
 * EntityApiSpec
 */
public interface IEntityApi {
    BaseResponse<PageResponse<String>> getEntitySitesCode(String entityCode);
}