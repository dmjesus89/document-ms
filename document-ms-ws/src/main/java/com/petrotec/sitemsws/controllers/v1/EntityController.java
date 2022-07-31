package com.petrotec.sitemsws.controllers.v1;

import static io.micronaut.http.HttpStatus.OK;

import java.util.Collection;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageResponse;
import com.petrotec.documentms.services.EntityService;
import com.petrotec.documentms.interfaces.IEntityApi;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;


/**
 * EntityController
 */
@Controller("/api/v1/entity")
public class EntityController implements IEntityApi {

    protected final EntityService entityService;

    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @Get("/{entityCode}/sites")
    public BaseResponse<PageResponse<String>> getEntitySitesCode(String entityCode) {
        Collection<String> siteCodes = entityService.getEntitySiteCodes(entityCode);
        PageResponse<String> pageResponse;
        if (siteCodes.isEmpty()) {
            pageResponse = PageResponse.empty();
        }
        else {
            pageResponse = new PageResponse<String>(siteCodes, siteCodes.size(), true);
        }
        return BaseResponse.success(OK.getReason(), OK.getCode(), "List of site codes for given entity", pageResponse);
    }
}