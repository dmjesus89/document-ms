package com.petrotec.documentms.services;

import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.service.spec.SpecificationFilter;
import com.petrotec.documentms.dtos.site.ServiceModeDTO;
import com.petrotec.documentms.entities.ServiceMode;
import com.petrotec.documentms.interfaces.IServiceMode;
import com.petrotec.documentms.mappers.ServiceModeMapper;
import com.petrotec.documentms.repositories.interfaces.ServiceModeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * ServiceModeService
 */
@Singleton
@Transactional
public class DefaultServiceModeService implements IServiceMode {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultServiceModeService.class);

	private final SpecificationFilter<ServiceMode> specificationFilter;
	private final ServiceModeRepository serviceModeRepository;
	private final ServiceModeMapper serviceModeMapper;

	public DefaultServiceModeService(SpecificationFilter<ServiceMode> specificationFilter,
							  ServiceModeRepository serviceModeRepository, ServiceModeMapper serviceModeMapper) {
		this.specificationFilter = specificationFilter;
		this.serviceModeRepository = serviceModeRepository;
		this.serviceModeMapper = serviceModeMapper;
	}

	@Override
	public PageResponse<ServiceModeDTO> findAll(@Nullable PageAndSorting pageAndSorting,
			@Nullable Filter filterQuery) {
		return PageResponse.from(
				specificationFilter.findAll(filterQuery, ServiceMode.class, pageAndSorting).stream().map(serviceModeMapper::toDTO)
						.collect(Collectors.toList()), pageAndSorting,
				() -> specificationFilter.size(filterQuery, ServiceMode.class));
	}

	@Override
	public ServiceModeDTO get(String code) {
		return serviceModeMapper.toDTO(getEntityByCode(code));
	}

	@Override
	public ServiceModeDTO create(ServiceModeDTO serviceModeDTO) {
		if(Objects.isNull(serviceModeDTO.getCode())){
			serviceModeDTO.setCode(UUID.randomUUID().toString());
		}
		ServiceMode serviceModeSave = serviceModeRepository.save(serviceModeMapper.fromDTO(serviceModeDTO));
		return serviceModeMapper.toDTO(serviceModeSave);
	}

	@Override
	public ServiceModeDTO update(String code, ServiceModeDTO serviceModeDTO) {
		ServiceMode serviceMode = getEntityByCode(code);
		return serviceModeMapper.toDTO(serviceModeRepository.update(serviceModeMapper.fromCreate(serviceModeDTO, serviceMode)));
	}

	public ServiceModeDTO setServiceModeEnabled(@NotBlank String code, boolean enabled) {
		ServiceMode serviceMode = getEntityByCode(code);
		serviceMode.setEnabled(enabled);

		return serviceModeMapper.toDTO(serviceModeRepository.update(serviceMode));
	}

	public ServiceMode getEntityByCode(String code){
		Optional<ServiceMode> serviceModeCode = serviceModeRepository.findByCode(code);
		if (!serviceModeCode.isPresent()) {
			LOG.warn("Service with code {} not found", code);
			throw new EntityNotFoundException("service_mode.code_not_found","Service mode code not found");
		}

		return serviceModeCode.get();
	}
}
