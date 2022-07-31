package com.petrotec.documentms.mappers.receipt;

import com.petrotec.documentms.dtos.receipt.ReceiptBlockTypeDTO;
import com.petrotec.documentms.entities.receipt.ReceiptBlockType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jsr330")
public interface ReceiptBlockTypeMapper {

    List<ReceiptBlockTypeDTO> toDTO(List<ReceiptBlockType> entityList);

    ReceiptBlockTypeDTO toDTO(ReceiptBlockType entity);

    ReceiptBlockType toEntity(ReceiptBlockTypeDTO dto);
}
