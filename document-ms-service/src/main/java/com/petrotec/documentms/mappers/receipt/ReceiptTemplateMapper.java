package com.petrotec.documentms.mappers.receipt;

import com.petrotec.documentms.dtos.receipt.ReceiptTemplateDTO;
import com.petrotec.documentms.entities.receipt.ReceiptTemplateEntity;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

@Singleton
public class ReceiptTemplateMapper {

   private final ReceiptTemplateTypeMapper receiptTemplateTypeMapper;
   private final ReceiptLineMapper receiptLineMapper;

   public ReceiptTemplateMapper(ReceiptTemplateTypeMapper receiptTemplateTypeMapper, ReceiptLineMapper receiptLineMapper) {
      this.receiptTemplateTypeMapper = receiptTemplateTypeMapper;
      this.receiptLineMapper = receiptLineMapper;
   }


   public ReceiptTemplateDTO toDTO(ReceiptTemplateEntity entity) {
      if ( entity == null ) {
         return null;
      }

      ReceiptTemplateDTO.ReceiptTemplateDTOBuilder receiptTemplateDTO = ReceiptTemplateDTO.builder();

      receiptTemplateDTO.code( entity.getCode() );
      receiptTemplateDTO.description( entity.getDescription() );
      receiptTemplateDTO.receiptTemplateTypeDTO(receiptTemplateTypeMapper.toDTO(entity.getReceiptTemplateType()));
      receiptTemplateDTO.receiptLineDTO(receiptLineMapper.toDTOList(entity.getReceiptLines()));
      receiptTemplateDTO.receiptTemplateTypeCode(entity.getReceiptTemplateType().getCode());

      return receiptTemplateDTO.build();
   }

   public ReceiptTemplateEntity toEntity(ReceiptTemplateDTO receiptTemplateDTO) {
      if ( receiptTemplateDTO == null ) {
         return null;
      }
      ReceiptTemplateEntity receiptTemplateEntity = new ReceiptTemplateEntity();
      receiptTemplateEntity.setCode( receiptTemplateDTO.getCode() );
      receiptTemplateEntity.setDescription( receiptTemplateDTO.getDescription() );
      return receiptTemplateEntity;
   }

    public ReceiptTemplateEntity fromCreate(ReceiptTemplateEntity entity, ReceiptTemplateDTO dto) {
       if (dto == null && entity == null) {
          return null;
       }

       return getReceiptTemplate(dto, entity);
    }

    @NotNull
   private ReceiptTemplateEntity getReceiptTemplate(ReceiptTemplateDTO dto, ReceiptTemplateEntity entity) {
      entity.setDescription(dto.getDescription());
      return entity;
   }
}
