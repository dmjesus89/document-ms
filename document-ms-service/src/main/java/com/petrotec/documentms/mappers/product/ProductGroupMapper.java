package com.petrotec.documentms.mappers.product;


import com.petrotec.product.api.dtos.ProductGroupExtendedDTO;
import com.petrotec.product.api.dtos.ProductGroupSimpleDTO;
import com.petrotec.service.mappers.BaseMapper;
import com.petrotec.documentms.entities.product.ProductGroup;
import org.mapstruct.Context;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class ProductGroupMapper extends BaseMapper<ProductGroupExtendedDTO, ProductGroup> {
    @Override
    public ProductGroupExtendedDTO toDTO(ProductGroup entity, String locale) {
        if (entity == null) {
            return null;
        }

        ProductGroupExtendedDTO productGroupExtendedDTO = new ProductGroupExtendedDTO();
        Map<String, String> map = entity.getDescription();
        if (map != null) {
            productGroupExtendedDTO.setDetailedDescription(new HashMap<>(map));
        }
        productGroupExtendedDTO.setCode(entity.getCode());
        productGroupExtendedDTO.setEnabled(entity.isEnabled());
        productGroupExtendedDTO.setColor(entity.getColor());
        productGroupExtendedDTO.setCreatedOn(entity.getCreatedOn());
        productGroupExtendedDTO.setUpdatedOn(entity.getUpdatedOn());

        return productGroupExtendedDTO;
    }

    public ProductGroupSimpleDTO toSimpleDTO(ProductGroup entity) {
        if (entity == null) {
            return null;
        }
        ProductGroupSimpleDTO dto = new ProductGroupSimpleDTO();
        dto.setCode(entity.getCode());
        dto.setColor(entity.getColor());
        dto.setEnabled(entity.isEnabled());
        Map<String, String> map = entity.getDescription();
        if (map != null) {
            dto.setDetailedDescription(new HashMap<>(map));
        }

        return dto;
    }

    @Override
    public List<ProductGroupExtendedDTO> toDTO(List<ProductGroup> entities, @Context String locale) {
        if (entities == null) {
            return Collections.emptyList();
        }

        List<ProductGroupExtendedDTO> list = new ArrayList<>(entities.size());
        for (ProductGroup entity : entities) {
            list.add(toDTO(entity, locale));
        }

        return list;
    }

    @Override
    public ProductGroup fromDTO(ProductGroupExtendedDTO dto, String locale) {
        ProductGroup entity = new ProductGroup();
        entity.setCode(dto.getCode());
        Map<String, String> hashMap = dto.getDetailedDescription();
        if (hashMap != null) {
            entity.setDescription(new HashMap<>(hashMap));
        }
        entity.setEnabled(dto.isEnabled());
        entity.setColor(dto.getColor());

        if(dto.getCreatedOn() != null) entity.setCreatedOn(dto.getCreatedOn());
        if(dto.getUpdatedOn() != null) entity.setUpdatedOn(dto.getUpdatedOn());
        return entity;
    }

    public ProductGroup fromSimpleDTO(ProductGroupSimpleDTO dto){
        ProductGroup entity = new ProductGroup();
        entity.setCode(dto.getCode());
        Map<String, String> hashMap = dto.getDetailedDescription();
        if (hashMap != null) {
            entity.setDescription(new HashMap<>(hashMap));
        }
        entity.setColor(dto.getColor());
        entity.setEnabled(dto.isEnabled());
        return entity;
    }

    @Override
    public void updateEntity(ProductGroup entity, ProductGroupExtendedDTO dto, String locale) {
        if (dto == null) {
            return;
        }

        if (entity.getDescription() != null) {
            Map<String, String> hashMap = dto.getDetailedDescription();
            if (hashMap != null) {
                entity.getDescription().clear();
                entity.getDescription().putAll(hashMap);
            }
        }

        entity.setColor(dto.getColor());
        entity.setEnabled(dto.isEnabled());

        if(dto.getCreatedOn() != null) entity.setCreatedOn(dto.getCreatedOn());
        if(dto.getUpdatedOn() != null) entity.setUpdatedOn(dto.getUpdatedOn());
    }
}
