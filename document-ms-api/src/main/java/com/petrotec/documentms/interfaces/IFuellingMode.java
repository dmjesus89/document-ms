package com.petrotec.documentms.interfaces;

import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.dtos.configuration.FuellingModeDTO;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * FuellingModeSpec
 */
public interface IFuellingMode {

    /**
     * Obtains available fuelling modes in Page chunks and allows FuellingModeDTO fields to be filtered and sorted
     * @param pageAndSorting
     * @param filterQuery
     * @return
     */
    PageResponse<FuellingModeDTO>  findAll(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery);

    /**
     * Retrieves an existing fuelling mode.
     * Throws EntityDoesNotExist if a fuelling mode with the provided code does not exist
     *
     * @param code fuelling mode uuid
     * @return
     */
    FuellingModeDTO getByCode(String code);

    /**
     * Creates a new fuelling mode.
     * Retrieves the created fuelling mode with the code (uuid) that was associated to the entity and will be used to identify the fuelling mode
     * @param fuellingModeDTO
     * @return
     */
    FuellingModeDTO create(@NotNull FuellingModeDTO fuellingModeDTO);

    /**
     * Updates an existing fuelling mode.
     * Returns the updated fuelling mode
     *
     * Throws ExceptionDoesNotExist if a fuelling mode with the provided code does not exist
     * Throws EntityDoesNotExist if a fuelling mode is set to Disable and there are enabled FCC's set to work in that Fuelling Mode
     *
     * @param code fuelling mode uuid
     * @param fuellingModeDTO
     * @return
     */
    FuellingModeDTO update(String code, @NotNull FuellingModeDTO fuellingModeDTO);


}