package com.petrotec.documentms.services;

import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.api.queue.MessageOperationEnum;
import com.petrotec.queue.annotations.MqttPublish;
import com.petrotec.queue.annotations.MqttPublisher;
import com.petrotec.service.exceptions.EntityAlreadyExistsException;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.grade.GradeDTO;
import com.petrotec.documentms.dtos.grade.GradeViewDTO;
import com.petrotec.documentms.entities.Grade;
import com.petrotec.documentms.entities.GradeProduct;
import com.petrotec.documentms.entities.SiteGrade;
import com.petrotec.documentms.entities.product.Product;
import com.petrotec.documentms.mappers.GradeMapper;
import com.petrotec.documentms.repositories.GradeRepository;
import com.petrotec.documentms.repositories.SiteGradeRepository;
import com.petrotec.documentms.services.product.ProductService;
import io.micronaut.core.util.StringUtils;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Singleton
@MqttPublisher(topicName = "pcs/document-ms/grade")
public class GradeService {
    private static final Logger LOG = LoggerFactory.getLogger(GradeService.class);

    private final GradeRepository gradeRepository;
    private final SiteGradeRepository siteGradeRepository;
    private final GradeMapper gradeMapper;
    private final ProductService productService;

    public GradeService(GradeRepository gradeRepository, SiteGradeRepository siteGradeRepository, GradeMapper gradeMapper, ProductService productService) {
        this.gradeRepository = gradeRepository;
        this.siteGradeRepository = siteGradeRepository;
        this.gradeMapper = gradeMapper;
        this.productService = productService;
    }


    public Page<Grade> list(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String rankOrder) {
        LOG.debug("Searching entities with pageAndSorting {} and filters {}", pageAndSorting, filterQuery);
        int limit = pageAndSorting.getLimit() + 1;
        // Retrieve all with pagination and filtering
//        return siteDevicePosRepository
//                .findAll(pageAndSorting.getOffset(), limit, pageAndSorting.getSorting(), filterQuery);
        return gradeRepository.findAll(Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));
    }

    public Grade getByCode(@NotEmpty String code, String rankOrder, String locale) {
        return gradeRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No Grade found for code.");
            return new EntityNotFoundException("No Grade found for code.");
        });
    }

    public Grade details(@NotBlank String code, String rankOrder, String locale) {
        LOG.debug("get Grade Details");
        Grade entity = getByCode(code, rankOrder, locale);
        LOG.debug("Found Grade with code {}: {}", code, entity);
        return entity;
    }

    public GradeDTO detailsDTO(@NotBlank String code, String rankOrder, String locale) {
        return gradeMapper.toDTO(details(code, rankOrder, locale), locale);
    }

    @MqttPublish(operationType = MessageOperationEnum.CREATED)
    public GradeDTO createDTO(GradeDTO dto, String rankOrder, String locale) {
        Grade entity = gradeMapper.fromDTO(dto, locale);

        //Associate products if present
        if (!dto.getProducts().isEmpty()) {
            dto.getProducts().forEach(gradeProductDTO -> {
                Product productToLinkToGrade = productService.getByCode(gradeProductDTO.getProductCode(), rankOrder, locale);
                GradeProduct newGradeProduct = new GradeProduct();
                newGradeProduct.setGrade(entity);
                newGradeProduct.setProduct(productToLinkToGrade);
                newGradeProduct.setProductPercentage(gradeProductDTO.getProductPercentage());
                entity.getGradeProducts().add(newGradeProduct);
            });
        }

        return gradeMapper.toDTO(this.create(entity, rankOrder, locale), locale);
    }

    public Grade create(Grade entity, String rankOrder, String locale) {
        LOG.debug("create Grade");

        if (StringUtils.isNotEmpty(entity.getCode())) {
            gradeRepository.findByCode(entity.getCode()).ifPresent(grade -> {
                throw new EntityAlreadyExistsException("site_ms_ws.grade_service.grade_code_already_exists", "Grade with code " + grade.getCode() + " already exists.");
            });
        } else {
            entity.setCode(UUID.randomUUID().toString());
        }

        Grade savedEntity = gradeRepository.save(entity);
        LOG.debug("Successfully created Grade to {}", savedEntity);
        return savedEntity;
    }

    @MqttPublish(operationType = MessageOperationEnum.UPDATED)
    public GradeDTO updateDTO(@NotBlank String code, GradeDTO dto, String rankOrder, String locale) {
        Grade entity = getByCode(code, rankOrder, locale);
        gradeMapper.updateEntity(entity, dto, locale);

        List<String> existingProducts = entity.getGradeProducts().stream().map(g -> g.getProduct().getCode()).collect(Collectors.toList());

        //Associate products if present
        if (!dto.getProducts().isEmpty()) {
            dto.getProducts().forEach(gradeProductDTO -> {

                Optional<GradeProduct> gp = entity.getGradeProducts().stream().filter(g -> g.getProduct().getCode().equals(gradeProductDTO.getProductCode())).findFirst();
                if (gp.isPresent()) {
                    gp.get().setProductPercentage(gradeProductDTO.getProductPercentage());
                    existingProducts.remove(gradeProductDTO.getProductCode());
                } else {
                    Product productToLinkToGrade = productService.getByCode(gradeProductDTO.getProductCode(), rankOrder, locale);
                    GradeProduct newGradeProduct = new GradeProduct();
                    newGradeProduct.setGrade(entity);
                    newGradeProduct.setProduct(productToLinkToGrade);
                    newGradeProduct.setProductPercentage(gradeProductDTO.getProductPercentage());
                    entity.getGradeProducts().add(newGradeProduct);
                }
            });
        }

        entity.getGradeProducts().removeIf(g -> existingProducts.contains(g.getProduct().getCode()));

        Grade result = update(code, entity, rankOrder, locale);
        return gradeMapper.toDTO(result, locale);
    }

    public Grade update(@NotBlank String code, Grade entity, String rankOrder, String locale) {
        LOG.debug("update Grade");
        Grade savedEntity = gradeRepository.update(entity);
        gradeRepository.getEntityManager().flush();
        gradeRepository.getEntityManager().refresh(savedEntity);
        LOG.debug("Successfully updated Grade to {}", savedEntity);
        return savedEntity;
    }

    public Page<SiteGrade> getSiteGrades(@Nullable PageAndSorting pageAndSorting, @Nullable Filter filterQuery, String siteCode) {
        return siteGradeRepository.findBySiteCode(siteCode, Pageable.from(pageAndSorting.getOffset(), pageAndSorting.getLimit()));
    }

    public PageResponse<GradeViewDTO> listView(PageAndSorting pageAndSorting, Filter filterQuery, String rankOrder) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        return gradeRepository.findAll(pageAndSorting, filterQuery, locale);
    }
}
