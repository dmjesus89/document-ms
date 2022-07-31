package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.documents.PaymentModeDTO;
import com.petrotec.documentms.dtos.siteDevice.SimpleSitePaymentModeDTO;
import com.petrotec.documentms.interfaces.PaymentModeService;
import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.List;

import static io.micronaut.http.HttpStatus.CREATED;
import static io.micronaut.http.HttpStatus.OK;

@Controller("/api/v1/paymentModes")
public class PaymentController {

	private final PaymentModeService defaultPaymentModeService;

	public PaymentController(PaymentModeService defaultPaymentModeService) {
		this.defaultPaymentModeService = defaultPaymentModeService;
	}

	@Get
	public BaseResponse<PageResponse<PaymentModeDTO>> getAll(@Nullable Filter filter,
			@Nullable PageAndSorting pageAndSorting) {
		return BaseResponse.success(OK.getReason(), OK.getCode(), "List of payment mode",
				defaultPaymentModeService.findAll(pageAndSorting, filter));
	}

	@Get("/{code}")
	public BaseResponse<PaymentModeDTO> get(@NotBlank String code) {
		return BaseResponse.success(OK.getReason(), OK.getCode(), "payment mode details", defaultPaymentModeService.get(code));
	}

	@Put("/{code}")
	public BaseResponse<PaymentModeDTO> update(@NotBlank String code,
			@Body @Valid PaymentModeDTO paymentModeDTO) {
		return BaseResponse.success(OK.getReason(), OK.getCode(), "payment mode : " + code + " successfully updated",
				defaultPaymentModeService.update(code, paymentModeDTO));
	}

	@Post
	public BaseResponse<PaymentModeDTO> create(@Body @Valid PaymentModeDTO paymentModeDTO) {
		return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "payment mode saved with success",
				defaultPaymentModeService.create(paymentModeDTO));
	}

	@Post("/paymentModeConfiguration/{siteCode}/{paymentCode}")
	public BaseResponse<SimpleSitePaymentModeDTO> createSitePaymentMode(@PathVariable String siteCode, @PathVariable String paymentCode) {
		return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Payment mode associated the site with success",
				defaultPaymentModeService.createSitePaymentMode(siteCode, paymentCode));
	}

	@Get("/paymentModeConfiguration/bySiteCode/{siteCode}")
	public BaseResponse<List<SimpleSitePaymentModeDTO>> getSitePaymentMode(@PathVariable String siteCode) {
		return BaseResponse.success(CREATED.getReason(), CREATED.getCode(), "Payment mode associated the site with success",
				defaultPaymentModeService.findBySiteCode(siteCode));
	}

	@Delete("/paymentModeConfiguration/bySiteCode/{siteCode}")
	public BaseResponse<Void> deleteSitePaymentMode(@PathVariable String siteCode) {
		return defaultPaymentModeService.deleteSitePaymentMode(siteCode);
	}
}
