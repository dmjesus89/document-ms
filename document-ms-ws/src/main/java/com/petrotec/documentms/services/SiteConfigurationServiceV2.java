package com.petrotec.documentms.services;

import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.documentms.dtos.siteConfiguration.v2.SiteConfigurationDTO;
import com.petrotec.documentms.dtos.siteDevice.WarehouseTypeDTO;
import com.petrotec.documentms.entities.*;
import com.petrotec.documentms.mappers.ServiceModeMapper;
import com.petrotec.documentms.mappers.configuration.FuellingModeMapper;
import com.petrotec.documentms.repositories.*;
import io.micronaut.core.util.StringUtils;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@Transactional
public class SiteConfigurationServiceV2 {

    private final SiteRepository siteRepository;
    private final SiteProductRepository productRepository;
    private final SiteGradeRepository gradeRepository;
    private final SiteDeviceFuelPointRepository fuelPointRepository;
    private final SiteDeviceWarehouseRepository warehouseRepository;
    private final SiteDeviceTankLevelGaugesRepository tankLevelGaugesRepository;
    private final SiteDevicePriceSignRepository priceSignRepository;
    private final SiteDeviceDispenserRepository fuelDispenserRepository;
    private final SiteDevicePosRepository posRepository;
    private final SiteDeviceFCCRepository fccRepository;
    private final ServiceModeMapper serviceModeMapper;
    private final FuellingModeMapper fuellingModeMapper;

    public SiteConfigurationServiceV2(SiteRepository siteRepository, SiteProductRepository productRepository, SiteGradeRepository gradeRepository, SiteDeviceFuelPointRepository fuelPointRepository, SiteDeviceWarehouseRepository warehouseRepository, SiteDeviceTankLevelGaugesRepository tankLevelGaugesRepository, SiteDevicePriceSignRepository priceSignRepository, SiteDeviceDispenserRepository fuelDispenserRepository, SiteDevicePosRepository posRepository, SiteDeviceFCCRepository fccRepository, ServiceModeMapper serviceModeMapper, FuellingModeMapper fuellingModeMapper) {
        this.siteRepository = siteRepository;
        this.productRepository = productRepository;
        this.gradeRepository = gradeRepository;
        this.fuelPointRepository = fuelPointRepository;
        this.warehouseRepository = warehouseRepository;
        this.tankLevelGaugesRepository = tankLevelGaugesRepository;
        this.priceSignRepository = priceSignRepository;
        this.fuelDispenserRepository = fuelDispenserRepository;
        this.posRepository = posRepository;
        this.fccRepository = fccRepository;
        this.serviceModeMapper = serviceModeMapper;
        this.fuellingModeMapper = fuellingModeMapper;
    }

    public SiteConfigurationDTO getSiteConfiguration(String siteNumber) {
        Site site = siteRepository.findBySiteNumber(siteNumber).orElseThrow(() -> new EntityNotFoundException("document-ms-ws.site_configuration_service.site_number_not_found", "Couldn not find site with site number:" + siteNumber));

        List<SiteProduct> products = productRepository.findBySite(site);
        List<SiteGrade> grades = gradeRepository.findBySite(site);
        List<SiteDeviceFuelPoint> fuelPoints = fuelPointRepository.findBySite(site);
        List<SiteDeviceWarehouse> warehouses = warehouseRepository.findBySite(site);
        List<SiteDeviceTankLevelGauges> tankLevelGauges = tankLevelGaugesRepository.findBySite(site);
        List<SiteDevicePriceSign> priceSigns = priceSignRepository.findBySite(site);
        List<SiteDeviceDispenser> dispensers = fuelDispenserRepository.findBySite(site);
        List<SiteDevicePos> posList = posRepository.findBySite(site);
        List<SiteDeviceFCC> fccList = fccRepository.findBySite(site);


        SiteConfigurationDTO dto = SiteConfigurationDTO.builder()
                .site(siteDtoDTO(site))
                .products(productsToDto(products))
                .grades(gradeToDto(grades))
                .fuelPoints(fuelPointsToDTO(fuelPoints))
                .warehouses(warehousesToDTO(warehouses))
                .pos(posToDTO(posList))
                .fcc(fccToDto(fccList))
                .fuelDispensers(fuelDispensersToDTO(dispensers))
                .tankLevelGauges(tlgToDTO(tankLevelGauges))
                .priceSigns(priceSignsToDTO(priceSigns))
                .build();

        return dto;
    }

    private SiteConfigurationDTO.SiteConfigurationSiteDTO siteDtoDTO(Site site) {
        return SiteConfigurationDTO.SiteConfigurationSiteDTO.builder()
                .code(site.getCode())
                .enabled(site.isEnabled())
                .description(site.getDescription())
                .entityCode(site.getEntityCode())
                .latitude(site.getLatitude())
                .longitude(site.getLongitude())
                .regionCode(site.getRegion().getCode())
                .regionDescription(site.getRegion().getDescription())
                .siteNumber(site.getSiteNumber())
                .siteProfileCode(site.getSiteProfile() == null ? null : site.getSiteProfile().getCode())
                .siteProfileDescription(site.getSiteProfile() == null ? null : site.getSiteProfile().getDescription())
                .build();
    }

    public Map<String, SiteConfigurationDTO.SiteConfigurationProductDTO> productsToDto(List<SiteProduct> product) {
        HashMap<String, SiteConfigurationDTO.SiteConfigurationProductDTO> res = new HashMap<>();

        if (product == null) return res;

        product.stream()
                .filter(p -> StringUtils.isNotEmpty(p.getFccProductCode())).forEach(p ->
                        res.put(p.getProduct().getCode(), SiteConfigurationDTO.SiteConfigurationProductDTO.builder()
                                .code(p.getProduct().getCode())
                                .unitPrice(p.getProduct().getReferenceUnitPrice())
                                .color(p.getProduct().getColor())
                                .fccProductCode(Integer.valueOf(p.getFccProductCode()))
                                .productGroupCode(p.getProduct().getProductGroup() == null ? null : p.getProduct().getProductGroup().getCode())
                                .productGroupColor(p.getProduct().getProductGroup() == null ? null : p.getProduct().getProductGroup().getColor())
                                .description(p.getProduct().getDescription())
                                .build()));

        return res;
    }

    public Map<String, SiteConfigurationDTO.SiteConfigurationGradeDTO> gradeToDto(List<SiteGrade> grades) {
        HashMap<String, SiteConfigurationDTO.SiteConfigurationGradeDTO> res = new HashMap<>();

        if (grades == null) return res;

        grades.forEach(g ->
                res.put(g.getGrade().getCode(), SiteConfigurationDTO.SiteConfigurationGradeDTO.builder()
                        .code(g.getGrade().getCode())
                        .fccGradeCode(Integer.valueOf(g.getFccGradeCode()))
                        .color(g.getGrade().getColor())
                        .unitPrice(g.getGrade().getUnitPriceNet())
                        .productCode(g.getGrade().getMainGradeProduct().getProduct().getCode())
                        .productPercentage(new BigDecimal(g.getGrade().getMainGradeProduct().getProductPercentage()).multiply(new BigDecimal(100)).intValue())
                        .description(g.getGrade().getDescription())
                        .build()));

        return res;
    }

    public Map<String, SiteConfigurationDTO.SiteConfigurationWarehouseDTO> warehousesToDTO(List<SiteDeviceWarehouse> warehouses) {
        HashMap<String, SiteConfigurationDTO.SiteConfigurationWarehouseDTO> res = new HashMap<>();

        if (warehouses == null) return res;

        warehouses.forEach(w ->
                res.put(w.getCode(), SiteConfigurationDTO.SiteConfigurationWarehouseDTO.builder()
                        .code(w.getCode())
                        .enabled(w.isEnabled())
                        .warehouseCode(Integer.valueOf(w.getWarehouseCode()))
                        .productCode(w.getProduct() == null ? null : w.getProduct().getCode())
                        .description(w.getDescription())
                        .warehouseTypeCode(WarehouseTypeDTO.WAREHOUSE_TYPE.valueOf(w.getWarehouseType().getCode()))
                        .build()));

        return res;
    }


    public Map<String, SiteConfigurationDTO.SiteConfigurationFuelPointDTO> fuelPointsToDTO(List<SiteDeviceFuelPoint> fuelPoints) {
        HashMap<String, SiteConfigurationDTO.SiteConfigurationFuelPointDTO> res = new HashMap<>();

        if (fuelPoints == null) return res;

        fuelPoints.forEach(fp ->
                res.put(fp.getCode(), SiteConfigurationDTO.SiteConfigurationFuelPointDTO.builder()
                        .code(fp.getCode())
                        .enabled(fp.isEnabled())
                        .description(fp.getDescription())
                        .fuelDispenserCode(fp.getDispenser() == null ? null : fp.getDispenser().getCode())
                        .fccCode(fp.getSiteDeviceFCC() == null ? null : fp.getSiteDeviceFCC().getCode())
                        .communicationMethodTypeCode(fp.getCommunicationMethod().getCode())
                        .communicationMethodData(fp.getCommunicationMethodData())
                        .protocolTypeCode(fp.getDeviceSubtype().getCode())
                        .pumpNumber(fp.getPumpNumber())
                        .nozzles(fuelNozzlesToDTO(fp.getNozzles()))
                        .build()));

        return res;
    }

    public List<SiteConfigurationDTO.SiteConfigurationFuelPointGradeDTO> fuelNozzlesToDTO(List<SiteDeviceFuelPointNozzle> nozzles) {
        List<SiteConfigurationDTO.SiteConfigurationFuelPointGradeDTO> res = new ArrayList<>();

        if (nozzles == null) return res;

        nozzles.forEach(n ->
                res.add(SiteConfigurationDTO.SiteConfigurationFuelPointGradeDTO.builder()
                        .code(n.getNozzleNumber() + "")
                        .nozzleNumber(n.getNozzleNumber())
                        .gradeCode(n.getSiteGrade().getGrade().getCode())
                        .productCode(n.getSiteGrade().getGrade().getMainGradeProduct().getProduct().getCode())
                        .warehouseCode(n.getWarehouse().getCode())
                        .build()));

        return res;
    }

    public Map<String, SiteConfigurationDTO.SiteConfigurationPosDTO> posToDTO(List<SiteDevicePos> pos) {
        Map<String, SiteConfigurationDTO.SiteConfigurationPosDTO> res = new HashMap<>();

        if (pos == null) return res;

        pos.forEach(p ->
                res.put(p.getCode(), SiteConfigurationDTO.SiteConfigurationPosDTO.builder()
                        .code(p.getCode())
                        .enabled(p.isEnabled())
                        .description(p.getDescription())
                        .posNumber(p.getNumber())
                        .communicationMethodTypeCode(p.getCommunicationMethod().getCode())
                        .communicationMethodData(p.getCommunicationMethodData())
                        .fuelPoints(p.getFuelPoints().stream().map(f -> f.getCode()).collect(Collectors.toList()))
                        .protocolTypeCode(p.getDeviceSubtype().getCode())
                        .description(p.getDescription())
                        .virtualPOS(virtualPosToDTO(p.getVirtualPos()))
                        .build()));

        return res;
    }

    public List<SiteConfigurationDTO.SiteConfigurationPosVirtualDTO> virtualPosToDTO(List<VirtualPos> virtualPos) {
        List<SiteConfigurationDTO.SiteConfigurationPosVirtualDTO> res = new ArrayList<>();

        if (virtualPos == null) return res;

        virtualPos.forEach(n ->
                res.add(SiteConfigurationDTO.SiteConfigurationPosVirtualDTO.builder()
                        .code(n.getCode())
                        .virtualPosNumber(n.getNumber())
                        .description(n.getDescription())
                        .hasPrinter(n.isHasPrinter())
                        .fuelPoints(n.getFuelPoints().stream().map(fp -> fp.getCode()).collect(Collectors.toList()))
                        .build()));

        return res;
    }

    public Map<String, SiteConfigurationDTO.SiteConfigurationFccDTO> fccToDto(List<SiteDeviceFCC> fcc) {
        Map<String, SiteConfigurationDTO.SiteConfigurationFccDTO> res = new HashMap<>();

        if (fcc == null) return res;

        fcc.forEach(p ->
                res.put(p.getCode(), SiteConfigurationDTO.SiteConfigurationFccDTO.builder()
                        .code(p.getCode())
                        .enabled(p.isEnabled())
                        .description(p.getDescription())
                        .additionalData(p.getAdditionalData())
                        .protocolTypeCode(p.getDeviceSubtype().getCode())
                        .communicationMethodTypeCode(p.getCommunicationMethod().getCode())
                        .communicationMethodData(p.getCommunicationMethodData())
                        .fuelPoints(p.getFuelPoints().stream().map(f -> f.getCode()).collect(Collectors.toList()))
                        .pos(p.getSiteDevicePos().stream().map(f -> f.getCode()).collect(Collectors.toList()))
                        .tlgs(p.getSiteDeviceTankLevelGauges().stream().map(f -> f.getCode()).collect(Collectors.toList()))
                        .priceSigns(p.getGradePriceSigns().stream().map(f -> f.getCode()).collect(Collectors.toList()))
                        .fuellingMode(fuellingModeMapper.toDTO(p.getFuellingMode()))
                        .serviceMode(serviceModeMapper.toDTO(p.getServiceMode()))
                        .build()));

        return res;
    }

    public Map<String, SiteConfigurationDTO.SiteConfigurationFuelDispenserDTO> fuelDispensersToDTO(List<SiteDeviceDispenser> dispensers) {
        Map<String, SiteConfigurationDTO.SiteConfigurationFuelDispenserDTO> res = new HashMap<>();

        if (dispensers == null) return res;

        dispensers.forEach(p ->
                res.put(p.getCode(), SiteConfigurationDTO.SiteConfigurationFuelDispenserDTO.builder()
                        .code(p.getCode())
                        .enabled(p.isEnabled())
                        .description(p.getDescription())
                        .dispenserNumber(p.getDispenserNumber())
                        .fuelPoints(p.getFuelPoints().stream().map(f -> f.getCode()).collect(Collectors.toList()))
                        .build()));

        return res;
    }

    public Map<String, SiteConfigurationDTO.SiteConfigurationTankLevelGaugesDTO> tlgToDTO(List<SiteDeviceTankLevelGauges> tlgs) {
        Map<String, SiteConfigurationDTO.SiteConfigurationTankLevelGaugesDTO> res = new HashMap<>();

        if (tlgs == null) return res;

        tlgs.forEach(tlg ->
                res.put(tlg.getCode(), SiteConfigurationDTO.SiteConfigurationTankLevelGaugesDTO.builder()
                        .code(tlg.getCode())
                        .enabled(tlg.isEnabled())
                        .description(tlg.getDescription())
                        .tlgCode(tlg.getTlgCode())
                        .protocolTypeCode(tlg.getDeviceSubtype().getCode())
                        .warehouseCode(tlg.getSiteDeviceWarehouse() == null ? null : tlg.getSiteDeviceWarehouse().getCode())
                        .communicationMethodTypeCode(tlg.getCommunicationMethod().getCode())
                        .communicationMethodData(tlg.getCommunicationMethodData())
                        .build()));

        return res;
    }

    public Map<String, SiteConfigurationDTO.SiteConfigurationPriceSignsDTO> priceSignsToDTO(List<SiteDevicePriceSign> signs) {
        Map<String, SiteConfigurationDTO.SiteConfigurationPriceSignsDTO> res = new HashMap<>();

        if (signs == null) return res;

        signs.forEach(ps ->
                res.put(ps.getCode(), SiteConfigurationDTO.SiteConfigurationPriceSignsDTO.builder()
                        .code(ps.getCode())
                        .enabled(ps.isEnabled())
                        .description(ps.getDescription())
                        .protocolTypeCode(ps.getDeviceSubtype().getCode())
                        .communicationMethodTypeCode(ps.getCommunicationMethod().getCode())
                        .communicationMethodData(ps.getCommunicationMethodData())
                        .grades(priceSignGradesToDTO(ps.getGrades()))
                        .build()));

        return res;
    }

    public Map<Integer, String> priceSignGradesToDTO(List<GradePriceSign> grades) {
        Map<Integer, String> res = new HashMap<>();

        if (grades == null) return res;

        grades.forEach(g -> res.put(g.getGradeOrder(), g.getGrade().getCode()));

        return res;
    }


}
