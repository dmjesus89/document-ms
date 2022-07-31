package com.petrotec.documentms.mappers.devices;

import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceFuelPointDTO;
import com.petrotec.documentms.dtos.siteDevice.SiteDeviceFuelPointNozzleDTO;
import com.petrotec.documentms.entities.Grade;
import com.petrotec.documentms.entities.SiteDeviceFuelPointNozzle;
import com.petrotec.documentms.entities.SiteDeviceWarehouse;
import com.petrotec.documentms.entities.SiteGrade;
import com.petrotec.documentms.repositories.SiteGradeRepository;
import com.petrotec.documentms.services.GradeService;
import com.petrotec.documentms.services.SiteDeviceService;
import com.petrotec.documentms.services.SiteService;
import com.petrotec.documentms.services.WarehouseService;
import io.micronaut.data.exceptions.EmptyResultException;
import org.mapstruct.*;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;

@Mapper(componentModel = "jsr330", uses = {TranslateMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SiteDeviceFuelPointNozzleMapper {

    @Inject
    private TranslateMapper translateMapper;

    @Inject
    private Provider<SiteDeviceService> siteDeviceService;

    @Inject
    private Provider<WarehouseService> warehouseService;

    @Inject
    private Provider<GradeService> gradeService;

    @Inject
    private Provider<SiteService> siteService;

    @Inject
    private SiteGradeRepository siteGradeRepository;

    @IterableMapping(qualifiedByName = "toDTO")
    public abstract List<SiteDeviceFuelPointNozzleDTO> toDTO(List<SiteDeviceFuelPointNozzle> entityList, @Context String locale);

    @Named("nozzleFromDTO")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "warehouse", source = "warehouseCode")
    @Mapping(target = "siteGrade", source = "gradeCode")
    public abstract SiteDeviceFuelPointNozzle fromDTO(SiteDeviceFuelPointNozzleDTO dto, @Context SiteDeviceFuelPointDTO fuelPointDTO, @Context String locale);

    @IterableMapping(qualifiedByName = "nozzleFromDTO")
    public abstract List<SiteDeviceFuelPointNozzle> fromDTO(List<SiteDeviceFuelPointNozzleDTO> dto, @Context SiteDeviceFuelPointDTO fuelPointDTO, @Context String locale);

    @Named("updateNozzleFromDTO")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "warehouse", source = "warehouseCode")
    @Mapping(target = "siteGrade", source = "gradeCode")
    public abstract void updateEntity(@MappingTarget SiteDeviceFuelPointNozzle entity, SiteDeviceFuelPointNozzleDTO dto, @Context SiteDeviceFuelPointDTO fuelPointDTO, @Context String locale);

    public abstract void updateEntity(@MappingTarget List<SiteDeviceFuelPointNozzle> entity, List<SiteDeviceFuelPointNozzleDTO> dto, @Context SiteDeviceFuelPointDTO fuelPointDTO, @Context String locale);

    public SiteDeviceWarehouse warehouseFromCode(String code){
        return warehouseService.get().getWarehouseByCode(code);
    }

    public SiteGrade siteGradeFromCode(String code, @Context SiteDeviceFuelPointDTO fuelPointDTO, @Context String locale){
        SiteGrade grade = siteGradeRepository.findBySiteCodeAndGradeCode(fuelPointDTO.getSiteCode(), code).orElse(null);
        if(grade == null){
            Grade gradeEntity = gradeService.get().getByCode(code,"1",locale);
            grade = new SiteGrade();
            grade.setGrade(gradeEntity);
            grade.setSite(siteService.get().getSiteByCode(fuelPointDTO.getSiteCode()));

            Integer maxFcc;
            try {
                maxFcc = siteGradeRepository.findBySiteCode(fuelPointDTO.getSiteCode()).stream().mapToInt(v -> v.getFccGradeCode() == null ? 0 : v.getFccGradeCode())
                        .max().orElse(0);
            } catch(EmptyResultException e){
                maxFcc = 0;
            }

            grade.setFccGradeCode((short) (maxFcc + 1));

            grade = siteGradeRepository.saveAndFlush(grade);
        }
        return grade;
    }

    @Named("toDTO")
    public SiteDeviceFuelPointNozzleDTO toDTO(SiteDeviceFuelPointNozzle entity, @Context String locale) {
        if (entity == null) return null;

        SiteDeviceFuelPointNozzleDTO deviceDto = new SiteDeviceFuelPointNozzleDTO();
        deviceDto.setNozzleNumber(entity.getNozzleNumber());
        deviceDto.setLastTotalizer(entity.getLastTotalizer());
        deviceDto.setLastTotalizerOn(entity.getLastTotalizerOn());

        if (entity.getSiteGrade() != null && entity.getSiteGrade().getGrade() != null) {
            SiteDeviceFuelPointNozzleDTO.NozzleGradeDTO gradeDto = new SiteDeviceFuelPointNozzleDTO.NozzleGradeDTO();
            deviceDto.setGrade(gradeDto);
            gradeDto.setCode(entity.getSiteGrade().getGrade().getCode());
            gradeDto.setColor(entity.getSiteGrade().getGrade().getColor());
            gradeDto.setDescription(translateMapper.translatedDescription(entity.getSiteGrade().getGrade().getDescription(), locale));

            if (entity.getSiteGrade().getFccGradeCode() != null)
                gradeDto.setFccCode((int) entity.getSiteGrade().getFccGradeCode());
        }

        if (entity.getWarehouse() != null) {
            SiteDeviceFuelPointNozzleDTO.NozzleWarehouseDTO warehouseDTO = new SiteDeviceFuelPointNozzleDTO.NozzleWarehouseDTO();
            deviceDto.setWarehouse(warehouseDTO);
            warehouseDTO.setCode(entity.getWarehouse().getCode());
            warehouseDTO.setWarehouseTypeCode(entity.getWarehouse().getWarehouseType().getCode());
            warehouseDTO.setFccWarehouseCode(entity.getWarehouse().getWarehouseCode());
            warehouseDTO.setDescription(entity.getWarehouse().getDescription());
        }

        return deviceDto;
    }

}
