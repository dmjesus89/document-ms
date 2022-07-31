package com.petrotec.documentms.services;

import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.api.queue.MessageOperationEnum;
import com.petrotec.queue.annotations.MqttPublish;
import com.petrotec.queue.annotations.MqttPublisher;
import com.petrotec.service.exceptions.InvalidDataException;
import com.petrotec.documentms.dtos.grade.SiteGradeDTO;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteGrade;
import com.petrotec.documentms.mappers.SiteGradeMapper;
import com.petrotec.documentms.repositories.SiteGradeRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Singleton
@MqttPublisher(topicName = "pcs/document-ms/siteGrade")
public class SiteGradeService {
    private static final Logger LOG = LoggerFactory.getLogger(SiteGradeService.class);

    private final SiteGradeRepository siteGradeRepository;
    private final SiteGradeMapper siteGradeMapper;
    private final SiteService siteService;
    private final GradeService gradeService;
    private final SiteDeviceFuelPointService siteDeviceFuelPointService;

    public SiteGradeService(SiteGradeRepository siteGradeRepository, SiteGradeMapper siteGradeMapper, SiteService siteService, GradeService gradeService, SiteDeviceFuelPointService siteDeviceFuelPointService) {
        this.siteGradeRepository = siteGradeRepository;
        this.siteGradeMapper = siteGradeMapper;
        this.siteService = siteService;
        this.gradeService = gradeService;
        this.siteDeviceFuelPointService = siteDeviceFuelPointService;
    }

    public List<SiteGrade> listSiteGradesBySite(String siteCode) {
        return siteGradeRepository.findAllBySiteCode(siteCode);
    }

    public List<SiteGradeDTO> listSiteGradesBySiteDTO(String siteCode, String locale) {
        return siteGradeMapper.toDTO(this.listSiteGradesBySite(siteCode), locale);
    }

    public PageResponse<SiteGradeDTO> listSiteGradesDTO(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filters, String locale) {
        List<SiteGradeDTO> dtoList = siteGradeMapper.toDTO(this.getSiteGrades(pageAndSorting, filters).getContent(), locale);
        return PageResponse.from(dtoList, pageAndSorting, () -> siteGradeRepository.count());
    }

    public Page<SiteGrade> getSiteGrades(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery) {
        return siteGradeRepository.findAll(Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));
    }

    public SiteGradeDTO createSiteGradeDTO(SiteGradeDTO dto, String rankOrder, String locale) {
        SiteGrade siteGrade = siteGradeMapper.fromDTO(dto, locale);
        siteGrade.setGrade(gradeService.getByCode(dto.getGradeCode(), rankOrder, locale));
        siteGrade.setSite(siteService.getSiteByCode(dto.getSiteCode()));
        SiteGrade savedSiteGrade = createSiteGrade(siteGrade);
        return siteGradeMapper.toDTO(savedSiteGrade, locale);
    }

    //Use the siteCode given by param and force that association(used for bulk associations)
    public SiteGradeDTO createSiteGradeDTO(SiteGradeDTO dto, String siteCode, String rankOrder, String locale) {
        SiteGrade siteGrade = siteGradeMapper.fromDTO(dto, locale);
        siteGrade.setGrade(gradeService.getByCode(dto.getGradeCode(), rankOrder, locale));
        siteGrade.setSite(siteService.getSiteByCode(siteCode));
        SiteGrade savedSiteGrade = createSiteGrade(siteGrade);
        return siteGradeMapper.toDTO(savedSiteGrade, locale);
    }

    @MqttPublish(operationType = MessageOperationEnum.CREATED)
    public SiteGrade createSiteGrade(SiteGrade entity) {
        Optional<SiteGrade> foundSiteGrade = siteGradeRepository.findBySiteCodeAndGradeCode(entity.getSite().getCode(), entity.getGrade().getCode());



        SiteGrade savedEntity = null;
        if (foundSiteGrade.isPresent()) {
            //Update it
            SiteGrade siteProductToUpdate = foundSiteGrade.get();
            siteProductToUpdate.setGrade(entity.getGrade());
            siteProductToUpdate.setSite(entity.getSite());
            siteProductToUpdate.setFccGradeCode(entity.getFccGradeCode());
            savedEntity = siteGradeRepository.update(siteProductToUpdate);
        } else {
            //validate if FccGrade code already exists for this siteCode
            if (siteGradeRepository.existsBySiteCodeAndFccGradeCode(entity.getSite().getCode(), entity.getFccGradeCode())) {
                throw new InvalidDataException("site_ms_ws.site_grade_service.duplicated_fcc_grade_code", "FccGradeCode already assign to Site.");
            }
            //create it
            savedEntity = siteGradeRepository.save(entity);
        }

        return savedEntity;
    }

    @MqttPublish(operationType = MessageOperationEnum.DELETED)
    public SiteGradeDTO removeSiteGrade(String siteCode, String gradeCode, String locale) {
        SiteGrade siteGrade = siteGradeRepository.findBySiteCodeAndGradeCode(siteCode, gradeCode)
                .orElseThrow(() -> new com.petrotec.service.exceptions.EntityNotFoundException("site_ms_ws.site_grade_service.not_found", "siteGrade not found"));

        //validate if siteGrade is associated with a nozzle
        if (siteDeviceFuelPointService.isSiteGradeAssociatedToAnyNozzle(siteGrade)) {
            throw new InvalidDataException("site_ms_ws.site_grade_service.cannot_remove_site_grade_associated_with_nozzle", "SiteGrade with gradeCode " + siteGrade.getGrade().getCode() + " is already associated to a FuelPoint Nozzle.");
        }

        siteGradeRepository.delete(siteGrade);
        return siteGradeMapper.toDTO(siteGrade, locale);
    }

    public List<SiteGradeDTO> associateSiteGrades(String siteCode, List<SiteGradeDTO> siteGradeList, String rankOrder, String locale) {
        if (siteGradeList == null)
            throw new InvalidDataException("site_ms_ws.site_grade_service.empty_list", "Empty list received.");

        Site site = siteService.getSiteByCode(siteCode);

        Set<SiteGradeDTO> createdSiteGrades = new HashSet<>();
        for (SiteGradeDTO sp : siteGradeList) {
            SiteGradeDTO siteGradeDTO = createSiteGradeDTO(sp, siteCode, rankOrder, locale);
            createdSiteGrades.add(siteGradeDTO);
        }

        //check with current siteGrades (remove if not in received list)
        List<String> createdGradesAssociation = createdSiteGrades.stream().map(SiteGradeDTO::getGradeCode).collect(Collectors.toList());
        for (SiteGrade sg : site.getSiteGrades()) {
            String gradeCode = sg.getGrade().getCode();
            if (!createdGradesAssociation.contains(gradeCode)) removeSiteGrade(siteCode, gradeCode, locale);
        }

        return siteGradeMapper.toDTO(siteGradeRepository.findBySiteCode(siteCode), locale);
    }
}
