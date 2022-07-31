package com.petrotec.documentms.clients;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageResponse;
import com.petrotec.product.api.dtos.ProductExtendedDTO;
import com.petrotec.product.api.dtos.ProductGroupExtendedDTO;
import com.petrotec.product.api.dtos.inventory.InventoryMovementExtendedDTO;
import com.petrotec.product.api.dtos.inventory.InventoryStockExtendedDTO;
import com.petrotec.product.api.dtos.stockManagement.deliveries.StockDeliveryDTO;
import com.petrotec.documentms.dtos.SiteProductDTO;
import com.petrotec.ws.binder.PageAndSortingArgumentBinder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

import javax.validation.constraints.NotNull;
import java.util.List;

@Client(path = "/api/v1", id = "product", errorType = BaseResponse.class)
public interface ProductClient {

    /* Inventory */
    @Get("/inventories/stocks/byWarehouse/{warehouseCode}?limit=999")
    BaseResponse<PageResponse<InventoryStockExtendedDTO>> getWarehouseInventories(@Header("locale") @Nullable String locale,
                                                                                  @Header("tenant_id") String tenantId,
                                                                                  @Header("rank_order") String rankOrder,
                                                                                  @Header("entity_code") String entityCode,
                                                                                  @NotNull String warehouseCode);

    @Post("/inventories/movements")
    BaseResponse<InventoryMovementExtendedDTO> insertStockMovement(@Body StockDeliveryDTO delivery);

    /* Product */
    @Get("/products/groups")
    BaseResponse<PageResponse<ProductGroupExtendedDTO>> getProductGroups(@Nullable Integer offset, @Nullable Integer limit,
                                                                         @QueryValue(value = PageAndSortingArgumentBinder.REAL_SIZE_PARAMETER) @Nullable Boolean realSize,
                                                                         @QueryValue(value = PageAndSortingArgumentBinder.SORT_PARAMETER) @Nullable List<String> sort);

    @Get("/products{?real_size,offset,limit,sort}")
    BaseResponse<PageResponse<ProductExtendedDTO>> getProductList(@Header(name = "query") @Nullable String query,
                                                                  @Nullable Integer offset, @Nullable Integer limit,
                                                                  @QueryValue(value = PageAndSortingArgumentBinder.REAL_SIZE_PARAMETER) @Nullable Boolean realSize,
                                                                  @QueryValue(value = PageAndSortingArgumentBinder.SORT_PARAMETER) @Nullable List<String> sort);

    @Get("/siteProducts{?real_size,offset,limit,sort}")
    BaseResponse<PageResponse<SiteProductDTO>> getSiteProductList(@Nullable Integer offset, @Nullable Integer limit,
                                                                  @QueryValue(value = PageAndSortingArgumentBinder.REAL_SIZE_PARAMETER) @Nullable Boolean realSize,
                                                                  @QueryValue(value = PageAndSortingArgumentBinder.SORT_PARAMETER) @Nullable List<String> sort);

}
