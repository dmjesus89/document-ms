package com.petrotec.documentms.interfaces;

import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.site.ServiceModeDTO;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ServiceMode
 */
public interface IServiceMode {

	/**
	 * Obtains available service mode in Page chunks and allows ServiceModeDTO fields to be filtered and sorted
	 * @param pageAndSorting
	 * @param filterQuery
	 * @return
	 */
	PageResponse<ServiceModeDTO> findAll(@Nullable PageAndSorting pageAndSorting,@Nullable Filter filterQuery);

	/**
	 * Retrieves an existing service mode.
	 * Throws EntityDoesNotExist if a service mode with the provided code does not exist
	 *
	 * @param code service mode uuid
	 * @return
	 */
	ServiceModeDTO get(String code);

	/**
	 * Creates a new service mode.
	 * Retrieves the created service mode with the code (uuid) that was associated to the entity and will be used to identify the service mode
	 * @param serviceModeDTO
	 * @return
	 */
	ServiceModeDTO create(@NotNull ServiceModeDTO serviceModeDTO);

	/**
	 * Updates an existing service mode.
	 * Returns the updated service mode
	 *
	 * Throws ExceptionDoesNotExist if a service mode with the provided code does not exist
	 * Throws EntityDoesNotExist if a service mode is set to Disable and there are enabled
	 *
	 * @param code service mode uuid
	 * @param serviceModeDTO
	 * @return
	 */
	ServiceModeDTO update(String code, @NotNull ServiceModeDTO serviceModeDTO);

	/**
	 * update to active or not active service mode
	 * @param code
	 * @param enabled
	 * @return
	 */
	ServiceModeDTO setServiceModeEnabled(@NotBlank String code, boolean enabled);
}
