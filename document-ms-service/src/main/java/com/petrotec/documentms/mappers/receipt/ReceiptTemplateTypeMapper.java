package com.petrotec.documentms.mappers.receipt;

import com.petrotec.documentms.dtos.receipt.ReceiptTemplateTypeDTO;
import com.petrotec.documentms.entities.receipt.ReceiptTemplateType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface ReceiptTemplateTypeMapper {

   ReceiptTemplateTypeDTO toDTO(ReceiptTemplateType entity);

   ReceiptTemplateType toEntity(ReceiptTemplateTypeDTO dto);

}
