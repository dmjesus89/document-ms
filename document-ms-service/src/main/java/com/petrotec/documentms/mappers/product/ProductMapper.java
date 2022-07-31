package com.petrotec.documentms.mappers.product;


import com.petrotec.product.api.dtos.ProductExtendedDTO;
import com.petrotec.service.mappers.BaseMapper;
import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.entities.product.Product;
import org.mapstruct.Context;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class ProductMapper extends BaseMapper<ProductExtendedDTO, Product> {

    @Inject
    private TranslateMapper translateMapper;

    @Inject
    private ProductGroupMapper productGroupMapper;

    @Override
    public ProductExtendedDTO toDTO(Product entity, String locale) {
        if (entity == null) {
            return null;
        }

        ProductExtendedDTO dto = new ProductExtendedDTO();
        dto.setProductGroup(productGroupMapper.toSimpleDTO(entity.getProductGroup()));
        dto.setCode(entity.getCode());
        dto.setDescription(translateMapper.translatedDescription(entity.getDescription(), locale));
        dto.setShortDescription(translateMapper.translatedDescription(entity.getShortDescription(), locale));
        dto.setEnabled(entity.isEnabled());
        dto.setIsFuel(entity.isFuel());
        dto.setColor(entity.getColor());
        dto.setReferenceUnitPrice(entity.getReferenceUnitPrice());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setCreatedBy(entity.getCreatedBy());

        return dto;
    }

    @Override
    public List<ProductExtendedDTO> toDTO(List<Product> entities, @Context String locale) {
        if (entities == null) {
            return Collections.emptyList();
        }

        List<ProductExtendedDTO> list = new ArrayList<>(entities.size());
        for (Product entity : entities) {
            list.add(toDTO(entity, locale));
        }

        return list;
    }

    @Override
    public Product fromDTO(ProductExtendedDTO dto, String locale) {
        if (dto == null) {
            return null;
        }

        Product entity = new Product();
        entity.setCode(dto.getCode());

        if (dto.getProductGroup() != null)
            entity.setProductGroup(productGroupMapper.fromSimpleDTO(dto.getProductGroup()));

        entity.setDescription(translateMapper.setDescription(dto.getDescription(), dto.getDetailedDescription(), locale));
        entity.setShortDescription(translateMapper.setDescription(dto.getShortDescription(), dto.getDetailedDescription(), locale));
        fillDTO(dto, entity);


        return entity;
    }

    @Override
    public void updateEntity(Product entity, ProductExtendedDTO dto, String locale) {
        if (dto == null) {
            return;
        }

        entity.setDescription(translateMapper.setDescription(dto.getDescription(), dto.getDetailedDescription(), locale));
        entity.setShortDescription(translateMapper.setDescription(dto.getShortDescription(), dto.getDetailedShortDescription(), locale));
        fillDTO(dto, entity);
    }

    private void fillDTO(ProductExtendedDTO dto, Product entity) {
        entity.setEnabled(dto.isEnabled());
        entity.setColor(dto.getColor());
        entity.setFuel(dto.getIsFuel());
        if(dto.getReferenceUnitPrice() != null) entity.setReferenceUnitPrice(dto.getReferenceUnitPrice());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setUpdatedOn(dto.getUpdatedOn());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setCreatedBy(dto.getCreatedBy());
    }

}
