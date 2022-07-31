package com.petrotec.documentms.services.product;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageResponse;
import com.petrotec.api.queue.BaseMessage;
import com.petrotec.product.api.dtos.ProductExtendedDTO;
import com.petrotec.queue.annotations.MqttSubscribe;
import com.petrotec.service.annotations.PetroScheduled;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.documentms.clients.ProductClient;
import com.petrotec.documentms.entities.product.Product;
import com.petrotec.documentms.mappers.product.ProductMapper;
import com.petrotec.documentms.repositories.product.ProductRepository;
import io.micronaut.context.annotation.Context;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.stream.Collectors;

@Context
@Transactional
public class ProductService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductClient productClient;

    private final ProductGroupService productGroupService;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, ProductClient productClient,
                          ProductGroupService productGroupService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productClient = productClient;
        this.productGroupService = productGroupService;
    }

    @PetroScheduled(initialDelay = "60s", fixedDelay = "12h", backOff = "3")
    public boolean syncData() {
        int pageSize = 30;
        int offset = 0;

        while (true) {
            BaseResponse<PageResponse<ProductExtendedDTO>> remoteDataResponse = productClient.getProductList(null, offset, pageSize, true, null);
            if (remoteDataResponse.getCode() == HttpStatus.OK.getCode() && remoteDataResponse.getResult() != null) {

                Set<String> localDataCodes = productRepository.findAll().stream().map(Product::getCode).collect(Collectors.toSet()); //Get all local data codes
                for (ProductExtendedDTO remoteItem : remoteDataResponse.getResult().getItems()) {
                    if (localDataCodes.contains(remoteItem.getCode())) {
                        /* TODO question how do microservice talk to other microservice without tenant id? */
                        this.updateDTO(remoteItem.getCode(), remoteItem, null, null);
                    } else if (!localDataCodes.contains(remoteItem.getCode())) {
                        /*TODO question how do microservice talk to other microservice without tenant id? locale? rankOrder*/
                        this.createDTO(remoteItem, null, null);
                    }
                }
                if (remoteDataResponse.getResult().isLast())
                    return true;

            } else {
                return false;
            }
            offset = offset + pageSize;
        }
    }

    public Product getByCode(@NotEmpty String code, String rankOrder, String locale) {
        return productRepository.findByCode(code).orElseThrow(() ->
                new EntityNotFoundException("site_ms_ws.product_service.product_code_not_found", "No Product found for code." + code)
        );
    }

    public Product create(Product entity) {
        Product savedEntity = productRepository.save(entity);
        return savedEntity;
    }

    public Product update(@NotBlank String code, Product entity, String rankOrder, String locale) {
        LOG.debug("Update Product");
        Product savedEntity = productRepository.update(entity);
        LOG.debug("Successfully updated Product to {}", savedEntity);
        return savedEntity;
    }

    public ProductExtendedDTO createDTO(ProductExtendedDTO dto, String locale, String rankOrder) {
        Product entity = productMapper.fromDTO(dto, locale);
        if (dto.getProductGroup() != null && !StringUtils.isEmpty(dto.getProductGroup().getCode())) {
            entity.setProductGroup(productGroupService.getByCode(dto.getProductGroup().getCode(), locale, rankOrder));
        }
        return productMapper.toDTO(create(entity), locale);
    }

    public ProductExtendedDTO updateDTO(@NotBlank String code, ProductExtendedDTO dto, String locale, String rankOrder) {
        Product entity = getByCode(code, rankOrder, locale);
        productMapper.updateEntity(entity, dto, locale);
        if (dto.getProductGroup() != null && !StringUtils.isEmpty(dto.getProductGroup().getCode())) {
            entity.setProductGroup(productGroupService.getByCode(dto.getProductGroup().getCode(), locale, rankOrder));
        }
        Product result = update(code, entity, rankOrder, locale);
        return productMapper.toDTO(result, locale);
    }


    @MqttSubscribe(topicName = "pcs/product-ms/product")
    public void handleReceivedMessage(BaseMessage<ProductExtendedDTO> baseMessage) {
        switch (baseMessage.getOperation()) {
            case CREATED:
            case UPDATED:
                if (productRepository.existsByCode(baseMessage.getResult().getCode())) {
                    this.updateDTO(baseMessage.getResult().getCode(), baseMessage.getResult(), null, null);
                } else {
                    this.createDTO(baseMessage.getResult(), null, null);
                }
                break;

            case DELETED:
                LOG.error("WHAT ARE YOU SYNCING ABOUT");
                break;
        }
    }

}
