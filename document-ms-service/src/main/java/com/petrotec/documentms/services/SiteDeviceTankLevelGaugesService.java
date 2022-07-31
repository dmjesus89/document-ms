package com.petrotec.documentms.services;

import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.exceptions.EntityAlreadyExistsException;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.service.exceptions.InvalidDataException;
import com.petrotec.service.spec.SpecificationFilter;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.SiteDeviceTankLevelGaugesDTO;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteDeviceTankLevelGauges;
import com.petrotec.documentms.interfaces.ITankLevelGauges;
import com.petrotec.documentms.mappers.SiteDeviceTankLevelGaugesMapper;
import com.petrotec.documentms.repositories.*;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@Transactional
public class SiteDeviceTankLevelGaugesService implements ITankLevelGauges {

	private static final Logger LOG = LoggerFactory.getLogger(SiteDeviceTankLevelGaugesService.class);

	private final SpecificationFilter<SiteDeviceTankLevelGauges> specificationFilter;
	private final SiteDeviceTankLevelGaugesRepository siteDeviceTankLevelGaugesRepository;
	private final SiteRepository siteRepository;
	private final SiteDeviceWarehouseRepository siteDeviceWarehouseRepository;
	private final CommunicationMethodRepository communicationMethodRepository;
	private final SiteDeviceTankLevelGaugesMapper siteDeviceTankLevelGaugesMapper;
	private final SiteDeviceSubtypeRepository siteDeviceSubtypeRepository;
	private final SiteDeviceTypeRepository siteDeviceTypeRepository;

	public SiteDeviceTankLevelGaugesService(SpecificationFilter<SiteDeviceTankLevelGauges> specificationFilter,
                                            SiteDeviceTankLevelGaugesRepository siteDeviceTankLevelGaugesRepository, SiteRepository siteRepository,
                                            SiteDeviceWarehouseRepository siteDeviceWarehouseRepository,
                                            CommunicationMethodRepository communicationMethodRepository, SiteDeviceTankLevelGaugesMapper siteDeviceTankLevelGaugesMapper,
                                            SiteDeviceSubtypeRepository siteDeviceSubtypeRepository, SiteDeviceTypeRepository siteDeviceTypeRepository) {
		this.specificationFilter = specificationFilter;
		this.siteDeviceTankLevelGaugesRepository = siteDeviceTankLevelGaugesRepository;
		this.siteRepository = siteRepository;
		this.siteDeviceWarehouseRepository = siteDeviceWarehouseRepository;
		this.communicationMethodRepository = communicationMethodRepository;
		this.siteDeviceTankLevelGaugesMapper = siteDeviceTankLevelGaugesMapper;
		this.siteDeviceSubtypeRepository = siteDeviceSubtypeRepository;
		this.siteDeviceTypeRepository = siteDeviceTypeRepository;
	}

	@Override
	public PageResponse<SiteDeviceTankLevelGaugesDTO> findAll(@Nullable PageAndSorting pageAndSorting,
			@Nullable Filter filterQuery) {
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		return PageResponse.from(
				specificationFilter.findAll(filterQuery, SiteDeviceTankLevelGauges.class, pageAndSorting).stream()
						.map(p -> siteDeviceTankLevelGaugesMapper.toDTO(p, locale)).collect(Collectors.toList()), pageAndSorting,
				() -> specificationFilter.size(filterQuery, SiteDeviceTankLevelGauges.class));
	}

	@Override
	public SiteDeviceTankLevelGaugesDTO getByCode(String code) {
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		return siteDeviceTankLevelGaugesMapper.toExtendedDTO(getEntityByCode(code), locale);
	}

	@Override
	public SiteDeviceTankLevelGaugesDTO create(SiteDeviceTankLevelGaugesDTO dto) {
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		if(Objects.isNull(dto.getCode())) {
			dto.setCode(UUID.randomUUID().toString());
		}

		if (Objects.nonNull(dto.getTlgCode())) {
			Optional<SiteDeviceTankLevelGauges> byTlgCode = siteDeviceTankLevelGaugesRepository.findByTlgCodeAndSiteCode(dto.getTlgCode(), dto.getSiteCode());
			if (byTlgCode.isPresent()) {
				LOG.debug("TankLevelGauge already exists in this site for tlg code {}", dto.getTlgCode());
				throw new EntityAlreadyExistsException("site_ms_ws.site.site_device_tank_level_gauges_service.tank_level_gauges.code_already_exists", "Tlg code already exists in this site for code: " + dto.getTlgCode());
			}
		}

		SiteDeviceTankLevelGauges entity = siteDeviceTankLevelGaugesMapper.fromDTO(dto);
		entity.setDeviceType(siteDeviceTypeRepository.findByCode("TLG").orElseThrow(
				() -> new EntityNotFoundException("site_ms_ws.site_device_service.site_device_type_code_not_found", "No siteDeviceType found for code." + "TLG")
		));

		Optional<Site> site = siteRepository.findByCode(dto.getSiteCode());
		if (!site.isPresent()) {
			LOG.warn("Site with code {} not found", dto.getSiteCode());
			throw new EntityNotFoundException("site.code_not_found","Site code not found");
		}

		entity.setDeviceSubtype(siteDeviceSubtypeRepository.findByCodeAndSiteDeviceTypeCode(dto.getProtocolTypeCode(),"TLG").orElseThrow(
				() -> new InvalidDataException("site_ms_ws.fuel_point_service.pump_protocol_not_found", "FuelPoint with protocol code " + dto.getProtocolTypeCode() + " not found.")
		));
		entity.setSite(site.get());
		entity.setSiteDeviceWarehouse(siteDeviceWarehouseRepository.findByCode(dto.getWarehouseCode()).orElseThrow(
				() -> new EntityNotFoundException("site_device_warehouse.code_not_found", "Site device warehouse code not found")));
		entity.setCommunicationMethod(communicationMethodRepository.findByCode(dto.getCommunicationMethodTypeCode()).orElseThrow(
				() -> new EntityNotFoundException("communication_method.code_not_found","Communication Method code not found")
		));

		return siteDeviceTankLevelGaugesMapper.toDTO(siteDeviceTankLevelGaugesRepository.save(entity), locale);
	}

	@Override
	public SiteDeviceTankLevelGaugesDTO update(String code, SiteDeviceTankLevelGaugesDTO dto) {
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

		SiteDeviceTankLevelGauges existingEntity = getEntityByCode(code);

		//Validates if newTlgCode is different then also validate if another tlg has tlgCode on that specifiedSite
		if (!existingEntity.getTlgCode().equals(dto.getTlgCode())) {
			Optional<SiteDeviceTankLevelGauges> byTlgCode = siteDeviceTankLevelGaugesRepository.findByTlgCodeAndSiteCode(dto.getTlgCode(), dto.getSiteCode());
			if (byTlgCode.isPresent()) {
				LOG.debug("TankLevelGauge already exists in this site for tlg code {}", dto.getTlgCode());
				throw new EntityAlreadyExistsException("site_ms_ws.site.site_device_tank_level_gauges_service.tank_level_gauges.code_already_exists", "Tlg code already exists in this site for code: " + dto.getTlgCode());
			}
		}

		SiteDeviceTankLevelGauges entity =  siteDeviceTankLevelGaugesMapper.fromCreate(dto, existingEntity);
		if(!dto.getWarehouseCode().equals(entity.getSiteDeviceWarehouse().getCode())) {
			entity.setSiteDeviceWarehouse(siteDeviceWarehouseRepository.findByCode(dto.getWarehouseCode()).orElseThrow(
					() -> new EntityNotFoundException("site_device_warehouse.code_not_found", "Site device warehouse code not found")));
		}
		entity.setDeviceSubtype(siteDeviceSubtypeRepository.findByCodeAndSiteDeviceTypeCode(dto.getProtocolTypeCode(),"TLG").orElseThrow(
				() -> new InvalidDataException("site_ms_ws.fuel_point_service.pump_protocol_not_found", "FuelPoint with protocol code " + dto.getProtocolTypeCode() + " not found.")
		));
		entity.setCommunicationMethod(communicationMethodRepository.findByCode(dto.getCommunicationMethodTypeCode())
				.orElseThrow(() -> new EntityNotFoundException("communication_method.code_not_found", "Communication Method code not found")));

		return siteDeviceTankLevelGaugesMapper.toExtendedDTO(entity, locale);
	}

	@Override
	public SiteDeviceTankLevelGaugesDTO setTankLevelGaugesDTOEnabled(String code, boolean enabled) {
		SiteDeviceTankLevelGauges tank = getEntityByCode(code);
		tank.setEnabled(enabled);
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		return siteDeviceTankLevelGaugesMapper.toDTO(siteDeviceTankLevelGaugesRepository.update(tank), locale);
	}

	public Page<SiteDeviceTankLevelGauges> listTankLevelGauges(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery,String siteCode) {
		LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);
		int offSet = Objects.nonNull(pageAndSorting) ? pageAndSorting.getOffset() : 0;
		int limit = Objects.nonNull(pageAndSorting) ? pageAndSorting.getLimit() : 30;
		return siteDeviceTankLevelGaugesRepository.findBySiteCode(siteCode, Pageable.from(offSet, limit));
	}

	public PageResponse<SiteDeviceTankLevelGaugesDTO> listTankLevelGaugesDTO(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String siteCode) {
		String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
		Page<SiteDeviceTankLevelGauges> result = this.listTankLevelGauges(pageAndSorting, filterQuery, siteCode);
		return PageResponse
				.from(siteDeviceTankLevelGaugesMapper.toExtendedDTO(result.getContent(), locale),
						pageAndSorting, () -> Long.valueOf(result.getSize()));
	}

	public SiteDeviceTankLevelGauges getEntityByCode(String code){
		Optional<SiteDeviceTankLevelGauges> TankLevelGaugesCode = siteDeviceTankLevelGaugesRepository.findByCode(code);
		if (!TankLevelGaugesCode.isPresent()) {
			LOG.warn("Tank level gauges with code {} not found", code);
			throw new EntityNotFoundException("tank_level_gauges.code_not_found","Tank level gauges code not found");
		}

		return TankLevelGaugesCode.get();
	}
}
