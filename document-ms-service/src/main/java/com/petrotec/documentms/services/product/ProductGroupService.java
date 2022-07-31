package com.petrotec.documentms.services.product;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageResponse;
import com.petrotec.api.queue.BaseMessage;
import com.petrotec.product.api.dtos.ProductGroupExtendedDTO;
import com.petrotec.queue.annotations.MqttSubscribe;
import com.petrotec.service.annotations.PetroScheduled;
import com.petrotec.documentms.clients.ProductClient;
import com.petrotec.documentms.entities.product.ProductGroup;
import com.petrotec.documentms.mappers.product.ProductGroupMapper;
import com.petrotec.documentms.repositories.product.ProductGroupRepository;
import io.micronaut.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
@Transactional
public class ProductGroupService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductGroupService.class);

    private final ProductGroupRepository productGroupRepository;
    private final ProductGroupMapper productGroupMapper;
    private final ProductClient productClient;

    public ProductGroupService(ProductGroupRepository productGroupRepository, ProductGroupMapper productGroupMapper, ProductClient productClient) {
        this.productGroupRepository = productGroupRepository;
        this.productGroupMapper = productGroupMapper;
        this.productClient = productClient;
    }

    @PetroScheduled(initialDelay = "20s", fixedDelay = "12h", backOff = "3")
    public boolean syncData() {
        int pageSize = 30;
        int offset = 0;

        while (true) {
            BaseResponse<PageResponse<ProductGroupExtendedDTO>> remoteDataResponse = this.productClient.getProductGroups(offset, pageSize, true, null);
            if (remoteDataResponse.getCode() == HttpStatus.OK.getCode() && remoteDataResponse.getResult() != null) {
                Set<String> localDataCodes = productGroupRepository.findAll().stream().map(ProductGroup::getCode).collect(Collectors.toSet());
                /*Update all existing PRODUCT GROUPS that exists in syncRequest (apenas update de remote updatedOn > local updatedOn)*/
                remoteDataResponse.getResult().getItems().iterator().forEachRemaining(remoteItem -> {
                    if (localDataCodes.contains(remoteItem.getCode())) {
                        this.updateDTO(remoteItem.getCode(), remoteItem, null, null); // TODO question how do microservice talk to other microservice without tenant id?
                    } else if (!localDataCodes.contains(remoteItem.getCode())) {
                        this.createDTO(remoteItem, null, null); // TODO question how do microservice talk to other microservice without tenant id?
                    }
                });

                if (remoteDataResponse.getResult().isLast())
                    return true;
            } else
                return false;
            offset = offset + pageSize;

        }
    }

    public ProductGroup getByCode(@NotEmpty String code, String rankOrder, String locale) {
        return productGroupRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No ProductGroup found for code.");
            return new EntityNotFoundException("No ProductGroup found for code.");
        });
    }

    public ProductGroup create(ProductGroup entity) {
        LOG.debug("create ProductGroup");
        ProductGroup savedEntity = productGroupRepository.save(entity);
        LOG.debug("Successfully created ProductGroup to {}", savedEntity);
        return savedEntity;
    }

    public ProductGroup update(@NotBlank String code, ProductGroup entity) {
        LOG.debug("update ProductGroup");
        ProductGroup savedEntity = productGroupRepository.update(entity);
        LOG.debug("Successfully updated ProductGroup to {}", savedEntity);
        return savedEntity;
    }

    public ProductGroupExtendedDTO createDTO(ProductGroupExtendedDTO dto, String locale, String rankOrder) {
        return productGroupMapper.toDTO(create(productGroupMapper.fromDTO(dto, locale)), locale);
    }

    public ProductGroupExtendedDTO updateDTO(@NotBlank String code, ProductGroupExtendedDTO dto, String locale, String rankOrder) {
        ProductGroup entity = getByCode(code, rankOrder, locale);
        productGroupMapper.updateEntity(entity, dto, locale);
        ProductGroup result = update(code, entity);
        return productGroupMapper.toDTO(result, locale);
    }

    @MqttSubscribe(topicName = "pcs/product-ms/productGroup")
    public void handleReceivedMessage(BaseMessage<ProductGroupExtendedDTO> baseMessage) {
        switch (baseMessage.getOperation()) {
            case CREATED:
            case UPDATED:
                if (productGroupRepository.existsByCode(baseMessage.getResult().getCode())) {
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
