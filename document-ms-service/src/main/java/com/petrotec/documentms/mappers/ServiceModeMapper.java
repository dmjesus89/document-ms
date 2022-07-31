package com.petrotec.documentms.mappers;

import com.petrotec.documentms.dtos.site.ServiceModeDTO;
import com.petrotec.documentms.entities.ServiceMode;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class ServiceModeMapper {

    public ServiceModeDTO toDTO(ServiceMode entity) {
        if (entity == null) {
            return null;
        }

        return ServiceModeDTO.builder()
                .authorizeModeSelection(entity.isAuthorizeModeSelection())
                .autoAuthorizeLimit(entity.getAutoAuthorizeLimit())
                .autoClearTransDelayTime(entity.getAutoClearTransDelayTime())
                .autoUnlockTransDelayTime(entity.getAutoUnlockTransDelayTime())
                .code(entity.getCode())
                .description(entity.getDescription())
                .enabled(entity.isEnabled())
                .maxNoOfConsecutiveZeroTrans(entity.getMaxNoOfConsecutiveZeroTrans())
                .maxNozzleDownTime(entity.getMaxNozzleDownTime())
                .maxPreAuthorizeTime(entity.getMaxPreAuthorizeTime())
                .minTransAmount(entity.getMinTransAmount())
                .minTransVolume(entity.getMinTransVolume())
                .pumpLightMode(entity.getPumpLightMode())
                .moneyInTransBufferStatus(entity.isMoneyInTransBufferStatus())
                .stopFuellingPointVehicleTag(entity.isStopFuellingPointVehicleTag())
                .storePreAuthorize(entity.isStorePreAuthorize())
                .supTransBufferSize(entity.getSupTransBufferSize())
                .unSupTransBufferSize(entity.getUnSupTransBufferSize())
                .useVehicleTagReadingButton(entity.isUseVehicleTagReadingButton())
                .volumeInTransBufferStatus(entity.isVolumeInTransBufferStatus())
                .zeroTransToPos(entity.isZeroTransToPos())
                .build();
    }

    public ServiceMode fromDTO(ServiceModeDTO dto) {
        if (dto == null) {
            return null;
        }
        ServiceMode mode = fromCreate(dto, new ServiceMode());
        mode.setCode(UUID.randomUUID().toString());
        return mode;
    }

    public ServiceMode fromCreate(ServiceModeDTO dto, ServiceMode serviceMode) {
        if (dto == null || serviceMode == null) {
            return null;
        }
        serviceMode.setAuthorizeModeSelection(dto.isAuthorizeModeSelection());
        serviceMode.setAutoAuthorizeLimit(dto.getAutoAuthorizeLimit());
        serviceMode.setAutoClearTransDelayTime(dto.getAutoClearTransDelayTime());
        serviceMode.setAutoUnlockTransDelayTime(dto.getAutoUnlockTransDelayTime());
        serviceMode.setDescription(dto.getDescription());
        serviceMode.setEnabled(dto.isEnabled());
        serviceMode.setMaxNoOfConsecutiveZeroTrans(dto.getMaxNoOfConsecutiveZeroTrans());
        serviceMode.setMaxNozzleDownTime(dto.getMaxNozzleDownTime());
        serviceMode.setMaxPreAuthorizeTime(dto.getMaxPreAuthorizeTime());
        serviceMode.setMinTransAmount(dto.getMinTransAmount());
        serviceMode.setMinTransVolume(dto.getMinTransVolume());
        serviceMode.setPumpLightMode(dto.getPumpLightMode());
        serviceMode.setMoneyInTransBufferStatus(dto.isMoneyInTransBufferStatus());
        serviceMode.setStopFuellingPointVehicleTag(dto.isStopFuellingPointVehicleTag());
        serviceMode.setStorePreAuthorize(dto.isStorePreAuthorize());
        serviceMode.setSupTransBufferSize(dto.getSupTransBufferSize());
        serviceMode.setUnSupTransBufferSize(dto.getUnSupTransBufferSize());
        serviceMode.setUseVehicleTagReadingButton(dto.isUseVehicleTagReadingButton());
        serviceMode.setVolumeInTransBufferStatus(dto.isVolumeInTransBufferStatus());
        serviceMode.setZeroTransToPos(dto.isZeroTransToPos());

        return serviceMode;
    }

}
