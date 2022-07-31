package com.petrotec.documentms.services;

import com.petrotec.queue.annotations.MqttPublisher;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceTypeDTO;
import com.petrotec.documentms.entities.SiteDevice;
import com.petrotec.documentms.entities.SiteDeviceType;
import com.petrotec.documentms.mappers.devices.SiteDeviceTypeMapper;
import com.petrotec.documentms.repositories.SiteDevicePosRepository;
import com.petrotec.documentms.repositories.SiteDevicePriceSignRepository;
import com.petrotec.documentms.repositories.SiteDeviceRepository;
import com.petrotec.documentms.repositories.SiteDeviceTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Singleton
@MqttPublisher(topicName = "pcs/document-ms/siteDevice")
@Transactional
public class SiteDeviceService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteDeviceService.class);

    private final SiteDeviceRepository siteDeviceRepository;
    private final SiteDevicePriceSignRepository siteDevicePriceSignsRepository;
    private final SiteDevicePosRepository siteDevicePosRepository;
    private final SiteDeviceTypeRepository siteDeviceTypeRepository;

    private final SiteDeviceTypeMapper siteDeviceTypeMapper;

    public SiteDeviceService(SiteDeviceRepository siteDeviceRepository,
            SiteDevicePosRepository siteDevicePosRepository,
            SiteDeviceTypeRepository siteDeviceTypeRepository, SiteDeviceTypeMapper siteDeviceTypeMapper,
            SiteDevicePriceSignRepository siteDevicePriceSignsRepository) {
        this.siteDeviceRepository = siteDeviceRepository;
        this.siteDevicePosRepository = siteDevicePosRepository;
        this.siteDeviceTypeRepository = siteDeviceTypeRepository;
        this.siteDeviceTypeMapper = siteDeviceTypeMapper;
        this.siteDevicePriceSignsRepository = siteDevicePriceSignsRepository;
    }

    public SiteDeviceType getSiteDeviceTypeByCode(@NotEmpty String code) {
        return siteDeviceTypeRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No siteDeviceType found for code.");
            return new EntityNotFoundException("site_ms_ws.site_device_service.site_device_type_code_not_found", "No siteDeviceType found for code." + code);
        });
    }

    public List<SiteDeviceTypeDTO> listSiteDeviceTypes(String locale) {
        return siteDeviceTypeMapper.toDTO(siteDeviceTypeRepository.findAll(), locale);
    }

    public List<SiteDevice> listAllFccDevicesBySiteDTO(String siteCode) {
        return siteDeviceRepository.findBySiteCode(siteCode);
    }

}
