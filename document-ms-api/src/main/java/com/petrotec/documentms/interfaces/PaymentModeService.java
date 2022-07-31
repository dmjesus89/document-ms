package com.petrotec.documentms.interfaces;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.documents.PaymentModeDTO;
import com.petrotec.documentms.dtos.siteDevice.SimpleSitePaymentModeDTO;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface PaymentModeService {

	/**
	 * Obtains available payment mode in Page chunks and allows PaymentModeDTO fields to be filtered and sorted
	 * @param pageAndSorting
	 * @param filterQuery
	 * @return
	 */
	PageResponse<PaymentModeDTO> findAll(@Nullable PageAndSorting pageAndSorting,@Nullable Filter filterQuery);

	/**
	 * Retrieves an existing payment mode.
	 * Throws EntityDoesNotExist if a payment mode with the provided code does not exist
	 *
	 * @param code payment mode uuid
	 * @return
	 */
	PaymentModeDTO get(String code);

	/**
	 * Creates a new payment mode.
	 * Retrieves the created payment mode with the code (uuid).
	 * @param paymentModeDTO
	 * @return
	 */
	PaymentModeDTO create(@NotNull PaymentModeDTO paymentModeDTO);

	/**
	 * Updates an existing payment mode.
	 * Returns the updated payment mode
	 *
	 * Throws ExceptionDoesNotExist if a payment mode with the provided code does not exist
	 * Throws EntityDoesNotExist if a payment mode is set to Disable and there are enabled
	 *
	 * @param code payment mode uuid
	 * @param paymentModeDTO
	 * @return
	 */
	PaymentModeDTO update(String code, @NotNull PaymentModeDTO paymentModeDTO);

	/**
	 * Payment mode associated the site.
	 * @param siteCode
	 * @param paymentCode
	 * @return
	 */
	SimpleSitePaymentModeDTO createSitePaymentMode(String siteCode, String paymentCode);

	/**
	 * Returns the payments' mode associated by site.
	 * @param siteNumber
	 * @return
	 */
	List<SimpleSitePaymentModeDTO> findBySiteCode(String siteNumber);

	/**
	 * Delete site payment mode by siteCode.
	 * @param siteCode
	 */
	BaseResponse<Void> deleteSitePaymentMode(String siteCode);
}
