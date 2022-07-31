package com.petrotec.documentms.clients;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageResponse;
import com.petrotec.api.dtos.categories.CategoryDTO;
import com.petrotec.api.dtos.properties.PropertyDTO;
import com.petrotec.documentms.dtos.documents.PromptDTO;
import com.petrotec.ws.binder.PageAndSortingArgumentBinder;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import javax.annotation.Nullable;
import java.util.List;

@Client(path = "/api/v1", id = "party", errorType = BaseResponse.class)
public interface PartyClient {

	@Get("/prompts{?real_size,offset,limit,sort}")
	BaseResponse<PageResponse<PromptDTO>> getPromptList(@Header(name = "query") @Nullable String query, @Nullable Integer offset,
			@Nullable Integer limit,
			@QueryValue(value = PageAndSortingArgumentBinder.REAL_SIZE_PARAMETER) @Nullable Boolean realSize,
			@QueryValue(value = PageAndSortingArgumentBinder.SORT_PARAMETER) @Nullable List<String> sort);

	@Get("/categories/elements{?real_size,offset,limit,sort}")
	BaseResponse<PageResponse<CategoryDTO>> getCategories(@Header(name = "query") @Nullable String query,
			@Parameter @Nullable Integer offset, @Parameter @Nullable Integer limit,
			@QueryValue(value = PageAndSortingArgumentBinder.REAL_SIZE_PARAMETER) @Nullable Boolean realSize,
			@QueryValue(value = PageAndSortingArgumentBinder.SORT_PARAMETER) @Nullable List<String> sort);

	@Get("/properties{?real_size,offset,limit,sort}")
	BaseResponse<PageResponse<PropertyDTO>> getProperties(@Header(name = "query") @Nullable String query,
			@Nullable Integer offset, @Nullable Integer limit,
			@QueryValue(value = PageAndSortingArgumentBinder.REAL_SIZE_PARAMETER) @Nullable Boolean realSize,
			@QueryValue(value = PageAndSortingArgumentBinder.SORT_PARAMETER) @Nullable List<String> sort);
}
