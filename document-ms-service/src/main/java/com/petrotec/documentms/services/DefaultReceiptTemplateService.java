package com.petrotec.documentms.services;

import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.spec.SpecificationFilter;
import com.petrotec.documentms.dtos.receipt.ReceiptBlockTypeDTO;
import com.petrotec.documentms.dtos.receipt.ReceiptTemplateDTO;
import com.petrotec.documentms.dtos.receipt.ReceiptTemplateTypeDTO;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteReceiptTemplate;
import com.petrotec.documentms.entities.receipt.ReceiptLine;
import com.petrotec.documentms.entities.receipt.ReceiptTemplateEntity;
import com.petrotec.documentms.entities.receipt.ReceiptTemplateType;
import com.petrotec.documentms.mappers.receipt.ReceiptBlockTypeMapper;
import com.petrotec.documentms.mappers.receipt.ReceiptTemplateMapper;
import com.petrotec.documentms.mappers.receipt.ReceiptTemplateTypeMapper;
import com.petrotec.documentms.repositories.SiteRepository;
import com.petrotec.documentms.repositories.receipt.*;
import com.petrotec.documentms.services.interfaces.ReceiptTemplate;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@Transactional
public class DefaultReceiptTemplateService implements ReceiptTemplate {

    private final SpecificationFilter<ReceiptTemplateType> specFilterTemplateType;
    private final ReceiptTemplateTypeMapper receiptTemplateTypeMapper;
    private final ReceiptTemplateMapper receiptTemplateMapper;
    private final ReceiptBlockTypeMapper receiptBlockTypeMapper;
    private final ReceiptBlockTypeRepository receiptBlockTypeRepository;
    private final ReceiptTemplateRepository receiptTemplateRepository;
    private final ReceiptTemplateTypeRepository receiptTemplateTypeRepository;
    private final ReceiptLayoutRepository receiptLayoutRepository;
    private final SiteReceiptTemplateRepository siteReceiptTemplateRepository;
    private final SiteRepository siteRepository;
    private final ReceiptLineRepository receiptLineRepository;

    public DefaultReceiptTemplateService(SpecificationFilter<ReceiptTemplateType> specFilterTemplateType, ReceiptTemplateTypeMapper receiptTemplateTypeMapper,
                                         ReceiptTemplateMapper receiptTemplateMapper, ReceiptBlockTypeMapper receiptBlockTypeMapper, ReceiptBlockTypeRepository receiptBlockTypeRepository,
                                         ReceiptTemplateRepository receiptTemplateRepository, ReceiptTemplateTypeRepository receiptTemplateTypeRepository, ReceiptLayoutRepository receiptLayoutRepository,
                                         SiteReceiptTemplateRepository siteReceiptTemplateRepository, SiteRepository siteRepository, ReceiptLineRepository receiptLineRepository) {
        this.specFilterTemplateType = specFilterTemplateType;
        this.receiptTemplateTypeMapper = receiptTemplateTypeMapper;
        this.receiptTemplateMapper = receiptTemplateMapper;
        this.receiptBlockTypeMapper = receiptBlockTypeMapper;
        this.receiptBlockTypeRepository = receiptBlockTypeRepository;
        this.receiptTemplateRepository = receiptTemplateRepository;
        this.receiptTemplateTypeRepository = receiptTemplateTypeRepository;
        this.receiptLayoutRepository = receiptLayoutRepository;
        this.siteReceiptTemplateRepository = siteReceiptTemplateRepository;
        this.siteRepository = siteRepository;
        this.receiptLineRepository = receiptLineRepository;
    }


    @Override
    public PageResponse<ReceiptTemplateTypeDTO> getTemplateType(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery) {
        return PageResponse.from(
                specFilterTemplateType.findAll(filterQuery, ReceiptTemplateType.class, pageAndSorting).stream().map(receiptTemplateTypeMapper::toDTO)
                        .collect(Collectors.toList()), pageAndSorting,
                () -> specFilterTemplateType.size(filterQuery, ReceiptTemplateType.class));
    }

    @Override
    public PageResponse<ReceiptBlockTypeDTO> getBlockTypeByTypeCode(String templateCode) {
        return PageResponse.from(receiptBlockTypeMapper.toDTO(receiptBlockTypeRepository.findByReceiptTemplateTypeCode(templateCode)), null, receiptBlockTypeRepository::count);
    }

    @Override
    public PageResponse<ReceiptTemplateDTO> getTemplates() {
        return PageResponse.from(receiptTemplateRepository.findAll()
                .stream().map(receiptTemplateMapper::toDTO)
                .collect(Collectors.toList()), null, receiptTemplateRepository::count);
    }

    @Override
    public ReceiptTemplateDTO getTemplatesByCode(String templateCode) {
        return receiptTemplateRepository.findByCode(templateCode).map(receiptTemplateMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Not found receipt template with code : " + templateCode));
    }

    @Override
    public ReceiptTemplateDTO createTemplateByCode(String templateCode, ReceiptTemplateDTO receiptTemplateDTO) {
        if (Objects.isNull(templateCode)) {
            receiptTemplateDTO.setCode(UUID.randomUUID().toString());
        } else {
            return receiptTemplateMapper.toDTO(update(templateCode, receiptTemplateDTO));
        }

        ReceiptTemplateEntity receiptTemplate = receiptTemplateMapper.toEntity(receiptTemplateDTO);
        receiptTemplate.setReceiptTemplateType(receiptTemplateTypeRepository.findByCode(receiptTemplateDTO.getReceiptTemplateTypeCode())
                .orElseThrow(() -> new EntityNotFoundException("Not found receipt template type with code : " + receiptTemplateDTO.getReceiptTemplateTypeCode())));
        ReceiptTemplateEntity save = receiptTemplateRepository.update(receiptTemplate);
        save.setReceiptLines(createReceiptLine(receiptTemplateDTO, save));
        return receiptTemplateMapper.toDTO(receiptTemplateRepository.update(save));
    }

    private ReceiptTemplateEntity update(String templateCode, ReceiptTemplateDTO receiptTemplateDTO) {
        ReceiptTemplateEntity entity = receiptTemplateMapper.fromCreate(getReceiptTemplateEntityByCode(templateCode), receiptTemplateDTO);
        entity.setReceiptTemplateType(receiptTemplateTypeRepository.findByCode(receiptTemplateDTO.getReceiptTemplateTypeCode())
                .orElseThrow(() -> new EntityNotFoundException("Not found receipt template layout type with code : " + receiptTemplateDTO.getReceiptTemplateTypeCode())));
        entity.setDescription(receiptTemplateDTO.getDescription());
        receiptLineRepository.deleteAll(entity.getReceiptLines());
        entity.setReceiptLines(createReceiptLine(receiptTemplateDTO, entity));
        return receiptTemplateRepository.update(entity);
    }

    private List<ReceiptLine> createReceiptLine(ReceiptTemplateDTO receiptTemplateDTO, ReceiptTemplateEntity entity) {
        List<ReceiptLine> receiptLines = new ArrayList<>();
        receiptTemplateDTO.getReceiptLineDTO().forEach(p -> {
            ReceiptLine receiptLine = new ReceiptLine();
            receiptLine.setLineNo(p.getLineNo());
            receiptLine.setLineData(p.getLineData());
            receiptLine.setReceiptBlockTypes(receiptBlockTypeRepository.findByCode(p.getReceiptBlockTypeCode())
                    .orElseThrow(() -> new EntityNotFoundException("Not found receipt template block type with code : " + p.getReceiptBlockTypeCode())));
            receiptLine.setReceiptLayout(receiptLayoutRepository.findByCode(p.getReceiptLayoutCode())
                    .orElseThrow(() -> new EntityNotFoundException("Not found receipt template layout type with code : " + p.getReceiptLayoutCode())));
            receiptLine.setReceiptTemplateEntity(entity);
            receiptLines.add(receiptLine);
        });
        return receiptLines;
    }

    @Override
    public Boolean deleteTemplateByCode(String templateCode) {
        try {
            receiptTemplateRepository.delete(getReceiptTemplateEntityByCode(templateCode));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ReceiptTemplateDTO> getTemplatesBySiteCode(String siteCode) {
        List<SiteReceiptTemplate> bySite = siteReceiptTemplateRepository.findBySite(getSite(siteCode));
        List<ReceiptTemplateDTO> receiptTemplateDTOList = new ArrayList<>();
        bySite.forEach(p -> receiptTemplateDTOList.add(receiptTemplateMapper.toDTO(getReceiptTemplateEntityByCode(p.getReceiptTemplate().getCode()))));
        return receiptTemplateDTOList;
    }

    @Override
    public Boolean associatedTemplateBySite(String templateCode, String siteCode) {
        try {
            siteReceiptTemplateRepository.save(
                    SiteReceiptTemplate.builder()
                            .site(getSite(siteCode))
                            .receiptTemplate(getReceiptTemplateEntityByCode(templateCode))
                            .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteAssociateTemplateBySite(String templateCode, String siteCode) {
        try {
            siteReceiptTemplateRepository.deleteAll(siteReceiptTemplateRepository.findBySiteAndReceiptTemplate(getSite(siteCode), getReceiptTemplateEntityByCode(templateCode)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private ReceiptTemplateEntity getReceiptTemplateEntityByCode(String p) {
        return receiptTemplateRepository.findByCode(p)
                .orElseThrow(() -> new EntityNotFoundException("Not found receipt template with code : " + p));
    }

    private Site getSite(String siteCode) {
        return siteRepository.findByCodeAndEnabled(siteCode)
                .orElseThrow(() -> new EntityNotFoundException("Not found site with code : " + siteCode));
    }

}
