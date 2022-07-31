package com.petrotec.documentms.mappers.devices;

import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteDevice.*;
import com.petrotec.documentms.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "jsr330", uses = {
		TranslateMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SimpleMapper {

	public abstract SimpleFuellingModeDTO toSimpleFuellingModeDTO(FuellingMode entity);

	public abstract SimpleServiceModeDTO toSimpleServiceModeDTO(ServiceMode entity);

	public abstract SimplePriceSignDTO toSimplePriceSignDTO(SiteDevicePriceSign entity);

	public abstract SimpleSiteDeviceTlgDTO toSimpleTlgDTO(SiteDeviceTankLevelGauges entity);

	public abstract SimpleSiteDevicePosDTO toSimpleSiteDevicePos(SiteDevicePos entity);
}
