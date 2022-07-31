package com.petrotec.documentms.interfaces;

import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.SiteDeviceTankLevelGaugesDTO;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface ITankLevelGauges {

	/**
	 * Obtains available tank level gauges in Page chunks and allows TankLevelGaugesDTO fields to be filtered and sorted
	 * @param pageAndSorting
	 * @param filterQuery
	 * @return
	 */
	PageResponse<SiteDeviceTankLevelGaugesDTO> findAll(@Nullable PageAndSorting pageAndSorting,@Nullable Filter filterQuery);

	/**
	 *
	 * Retrieves an existing tank level gauges mode.
	 * Throws EntityDoesNotExist if a tank level gauges with the provided code does not exist
	 * @param code
	 * @return
	 */
	SiteDeviceTankLevelGaugesDTO getByCode(String code);

	/**
	 * Creates a new tank level gauges.
	 * Retrieves the created tank level gauges with the code (uuid) that was associated to the entity and will be used to identify the tank level gauges
	 * @param siteDeviceTankLevelGaugesDTO
	 * @return
	 */
	SiteDeviceTankLevelGaugesDTO create(@NotNull SiteDeviceTankLevelGaugesDTO siteDeviceTankLevelGaugesDTO);

	/**
	 * Updates an existing tank level gauges.
	 * Returns the updated tank level gauges.
	 *
	 * Throws ExceptionDoesNotExist if a tank level gauges with the provided code does not exist
	 * Throws EntityDoesNotExist if a tank level gauges is set to Disable and there are enabled
	 * @param code
	 * @param siteDeviceTankLevelGaugesDTO
	 * @return
	 */
	SiteDeviceTankLevelGaugesDTO update(String code, @NotNull SiteDeviceTankLevelGaugesDTO siteDeviceTankLevelGaugesDTO);

	/**
	 * update to active or not active tank level gauges
	 * @param code
	 * @param enabled
	 * @return
	 */
	SiteDeviceTankLevelGaugesDTO setTankLevelGaugesDTOEnabled(@NotBlank String code, boolean enabled);

}
