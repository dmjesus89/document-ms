package com.petrotec.documentms.services.configuration;

import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.service.exceptions.InvalidDataException;
import com.petrotec.service.spec.SpecificationFilter;
import com.petrotec.documentms.dtos.configuration.FuellingModeDTO;
import com.petrotec.documentms.entities.FuellingMode;
import com.petrotec.documentms.interfaces.IFuellingMode;
import com.petrotec.documentms.mappers.configuration.FuellingModeMapper;
import com.petrotec.documentms.repositories.configuration.FuellingModeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@Transactional
public class IFuellingModeService implements IFuellingMode {

    private static final Logger LOG = LoggerFactory.getLogger(IFuellingModeService.class);

    private final FuellingModeRepository fuellingModeRepository;
    private final FuellingModeMapper fuellingModeMapper;
    private final SpecificationFilter<FuellingMode> specificationFilter;

    public IFuellingModeService(final FuellingModeRepository fuellingModeRepository, final FuellingModeMapper fuellingModeMapper, final SpecificationFilter<FuellingMode> specificationFilter) {
        this.fuellingModeRepository = fuellingModeRepository;
        this.fuellingModeMapper = fuellingModeMapper;
        this.specificationFilter = specificationFilter;
    }

    @Override
    public PageResponse<FuellingModeDTO> findAll(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery) {
        LOG.debug("Finding all fuelling modes. Contains filters? {} PageAndSorting: {}", filterQuery != null, pageAndSorting);
        try {
            return PageResponse.from(
                    specificationFilter.findAll(filterQuery, FuellingMode.class, pageAndSorting).stream().map(fuellingModeMapper::toDTO)
                            .collect(Collectors.toList()), pageAndSorting,
                    () -> specificationFilter.size(filterQuery, FuellingMode.class));
        } catch (Exception ex) {
            throw new InvalidDataException("site_ms_ws.fuelling_mode_service.finding_all_error", "Error finding all fueling modes");
        }
    }

    @Override
    public FuellingModeDTO getByCode(@NotEmpty String code) {
        LOG.debug("Searching for existing fueling mode with code: {} ", code);
        Optional<FuellingMode> fuelingMode = fuellingModeRepository.findByCode(code);
        if (!fuelingMode.isPresent()) {
            throw new EntityNotFoundException("site_ms_ws.fuelling_mode_service.fuelling_mode_not_found", String.format("No fueling mode found for code: [%s]", code));
        }
        LOG.debug("Found fueling mode with code {}: {}", code, fuelingMode.get());
        return fuellingModeMapper.toDTO(fuelingMode.get());
    }

    @Override
    public FuellingModeDTO create(FuellingModeDTO fuellingModeDTO) {
        LOG.debug("creating fueling mode");

        if (Optional.ofNullable(fuellingModeDTO.getCode()).isPresent()) {
            LOG.debug("Searching for existing fueling mode with code {} ", fuellingModeDTO.getCode());
            Optional<FuellingMode> fuelingMode = fuellingModeRepository.findByCode(fuellingModeDTO.getCode());
            if (fuelingMode.isPresent()) {
                throw new EntityNotFoundException("site_ms_ws.fuelling_mode_service.fuelling_mode_found", String.format("Fueling mode found for code: [%s]", fuellingModeDTO.getCode()));
            }
        }

        try {

            FuellingMode fuellingMode = fuellingModeMapper.toEntity(fuellingModeDTO);
            fuellingMode.setCode(UUID.randomUUID().toString());
            return fuellingModeMapper.toDTO(fuellingModeRepository.save(fuellingMode));
        } catch (Exception ex) {
            throw new InvalidDataException("site_ms_ws.fuelling_mode_service.create_erro", "Error creating fueling mode");
        }
    }

    @Override
    public FuellingModeDTO update(String code, @NotNull FuellingModeDTO fuellingModeDTO) {
        LOG.debug("updating fueling mode");
        return updateDTO(code, fuellingModeDTO);
    }

    public FuellingModeDTO updateDTO(String code, FuellingModeDTO fuellingModeDTO) {
        LOG.debug("updating dto fueling mode");

        FuellingMode fuellingMode = fuellingModeRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException("site_ms_ws.fuelling_mode_service.fuelling_mode_not_found", String.format("No fueling mode found for code: [%s]", code)));

        LOG.debug("Updating stored fuelling mode element ({}) with fuelling mode properties ({})", fuellingMode, fuellingModeDTO);
        fuellingModeMapper.update(fuellingMode, fuellingModeDTO);

        LOG.debug("Updating contact element entity: {}", fuellingMode);
        fuellingMode = fuellingModeRepository.update(fuellingMode);
        LOG.debug("Updated entity succesfully stored");
        return fuellingModeMapper.toDTO(fuellingMode);
    }


}
