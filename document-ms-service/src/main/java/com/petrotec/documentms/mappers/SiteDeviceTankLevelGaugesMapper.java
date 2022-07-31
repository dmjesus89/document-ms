package com.petrotec.documentms.mappers;

import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.SimpleWarehouseDeviceDTO;
import com.petrotec.documentms.dtos.SiteDeviceTankLevelGaugesDTO;
import com.petrotec.documentms.entities.SiteDeviceTankLevelGauges;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class SiteDeviceTankLevelGaugesMapper {

	private final TranslateMapper translateMapper;
	private final SiteMapper siteMapper;

	public SiteDeviceTankLevelGaugesMapper(TranslateMapper translateMapper, SiteMapper siteMapper) {
		this.translateMapper = translateMapper;
		this.siteMapper = siteMapper;
	}

	public SiteDeviceTankLevelGaugesDTO toDTO(SiteDeviceTankLevelGauges entity, String locale) {
		SiteDeviceTankLevelGaugesDTO dto = new SiteDeviceTankLevelGaugesDTO();
		dto.setCode(entity.getCode());
		dto.setCommunicationMethodData(entity.getCommunicationMethodData());
		dto.setSiteCode(entity.getSite().getCode());
		dto.setDescription(entity.getDescription());
		SimpleWarehouseDeviceDTO simpleWarehouseDeviceDTO = new SimpleWarehouseDeviceDTO();
		simpleWarehouseDeviceDTO.setWarehouseCode(entity.getSiteDeviceWarehouse().getWarehouseCode());
		simpleWarehouseDeviceDTO.setSiteDeviceCode(entity.getSiteDeviceWarehouse().getCode());
		simpleWarehouseDeviceDTO.setSiteDeviceDescription(entity.getSiteDeviceWarehouse().getDescription());
		dto.setSimpleTankDeviceDTO(simpleWarehouseDeviceDTO);
		dto.setTlgCode(entity.getTlgCode());
		dto.setProtocolTypeCode(entity.getDeviceSubtype().getCode());
		dto.setEnabled(entity.isEnabled());

		Map<String, Object> map = entity.getCommunicationMethodData();
		if ( map != null ) {
			dto.setCommunicationMethodData( new HashMap<String, Object>( map ) );
		}

		if (entity.getCommunicationMethod() != null){
			dto.setCommunicationMethodTypeCode(entity.getCommunicationMethod().getCode());
			dto.setCommunicationMethodTypeDescription(translateMapper.translatedDescription(entity.getCommunicationMethod().getDescription(), locale));
		}

		return dto;
	}

	public SiteDeviceTankLevelGauges fromDTO(SiteDeviceTankLevelGaugesDTO dto){
		SiteDeviceTankLevelGauges entity = new SiteDeviceTankLevelGauges();
		entity.setCode(dto.getCode());
		entity.setTlgCode(dto.getTlgCode());
		entity.setDescription(dto.getDescription());
		entity.setEnabled(dto.isEnabled());
		Map<String, Object> map = dto.getCommunicationMethodData();
		if ( map != null ) {
			entity.setCommunicationMethodData( new HashMap<String, Object>( map ) );
		}

		return entity;
	}

	public SiteDeviceTankLevelGaugesDTO toExtendedDTO(SiteDeviceTankLevelGauges entity, String locale){
		SiteDeviceTankLevelGaugesDTO dto = new SiteDeviceTankLevelGaugesDTO();

		dto.setSite(siteMapper.toSimpleDTO(entity.getSite(), locale));
		dto.setCode(entity.getCode());
		dto.setCommunicationMethodData(entity.getCommunicationMethodData());
		dto.setDescription(entity.getDescription());
		SimpleWarehouseDeviceDTO simpleWarehouseDeviceDTO = new SimpleWarehouseDeviceDTO();
		simpleWarehouseDeviceDTO.setWarehouseCode(entity.getSiteDeviceWarehouse().getWarehouseCode());
		simpleWarehouseDeviceDTO.setSiteDeviceCode(entity.getSiteDeviceWarehouse().getCode());
		simpleWarehouseDeviceDTO.setSiteDeviceDescription(entity.getSiteDeviceWarehouse().getDescription());
		dto.setSimpleTankDeviceDTO(simpleWarehouseDeviceDTO);
		dto.setTlgCode(entity.getTlgCode());
		dto.setProtocolTypeCode(entity.getDeviceSubtype().getCode());
		dto.setEnabled(entity.isEnabled());

		Map<String, Object> map = entity.getCommunicationMethodData();
		if ( map != null ) {
			dto.setCommunicationMethodData( new HashMap<String, Object>( map ) );
		}

		if (entity.getCommunicationMethod() != null){
			dto.setCommunicationMethodTypeCode(entity.getCommunicationMethod().getCode());
			dto.setCommunicationMethodTypeDescription(translateMapper.translatedDescription(entity.getCommunicationMethod().getDescription(), locale));
		}

		return dto;
	}

	public List<SiteDeviceTankLevelGaugesDTO> toExtendedDTO(List<SiteDeviceTankLevelGauges> entity, String locale) {

		if ( entity == null ) {
			return null;
		}

		List<SiteDeviceTankLevelGaugesDTO> list = new ArrayList<SiteDeviceTankLevelGaugesDTO>( entity.size() );
		for ( SiteDeviceTankLevelGauges item : entity ) {
			list.add( toExtendedDTO( item, locale ) );
		}

		return list;
	}

	public SiteDeviceTankLevelGauges fromCreate(SiteDeviceTankLevelGaugesDTO dto, SiteDeviceTankLevelGauges entity) {
		if (dto == null && entity == null) {
			return null;
		}
		entity.setTlgCode(dto.getTlgCode());
		Map<String, Object> map = dto.getCommunicationMethodData();
		if ( map != null ) {
			entity.setCommunicationMethodData( new HashMap<String, Object>( map ) );
		}
		entity.setEnabled(dto.isEnabled());

		entity.setSite(siteMapper.getSiteFromCode(dto.getSiteCode()));

		return entity;
	}
}
