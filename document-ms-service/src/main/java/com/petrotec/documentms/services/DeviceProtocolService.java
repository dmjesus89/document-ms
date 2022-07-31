package com.petrotec.documentms.services;

import com.petrotec.documentms.dtos.siteDevice.SiteDeviceSubtypeDTO;
import com.petrotec.documentms.mappers.devices.SiteDeviceSubtypeMapper;
import com.petrotec.documentms.repositories.SiteDeviceSubtypeRepository;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;


@Singleton
@Transactional
public class DeviceProtocolService {

    private final SiteDeviceSubtypeRepository siteDeviceSubtypeRepository;
    private final SiteDeviceSubtypeMapper siteDeviceSubtypeMapper;

    public DeviceProtocolService(SiteDeviceSubtypeRepository siteDeviceSubtypeRepository, SiteDeviceSubtypeMapper siteDeviceSubtypeMapper) {
        this.siteDeviceSubtypeRepository = siteDeviceSubtypeRepository;
        this.siteDeviceSubtypeMapper = siteDeviceSubtypeMapper;
    }


    public List<SiteDeviceSubtypeDTO> listSiteDeviceSubtypes(String siteDeviceTypeCode, String locale) {
        List<SiteDeviceSubtypeDTO> result;
        if (siteDeviceTypeCode != null) {
            result = siteDeviceSubtypeMapper.toDTO(siteDeviceSubtypeRepository.findBySiteDeviceTypeCode(siteDeviceTypeCode), locale);
        } else {
            result = siteDeviceSubtypeMapper.toDTO(siteDeviceSubtypeRepository.findAll(), locale);
        }
        return result;
    }

    public List<SiteDeviceSubtypeDTO> listPosTypes(String locale) {
        return siteDeviceSubtypeMapper.toDTO(siteDeviceSubtypeRepository.findBySiteDeviceTypeCode("POS"), locale);
    }

    public List<SiteDeviceSubtypeDTO> listPumpTypes(String locale) {
        return siteDeviceSubtypeMapper.toDTO(siteDeviceSubtypeRepository.findBySiteDeviceTypeCode("FUEL_POINT"), locale);
    }

    public List<SiteDeviceSubtypeDTO> listFccTypes(String locale) {
        return siteDeviceSubtypeMapper.toDTO(siteDeviceSubtypeRepository.findBySiteDeviceTypeCode("FCC"), locale);
    }

    public List<SiteDeviceSubtypeDTO> listPriceSignsTypes(String locale) {
        return siteDeviceSubtypeMapper.toDTO(siteDeviceSubtypeRepository.findBySiteDeviceTypeCode("PRICE_SIGN"), locale);
    }

    public List<SiteDeviceSubtypeDTO> listTLGTypes(String locale) {
        return siteDeviceSubtypeMapper.toDTO(siteDeviceSubtypeRepository.findBySiteDeviceTypeCode("TLG"), locale);
    }
}
