package com.petrotec.documentms.mappers.receipt;

import com.petrotec.documentms.dtos.receipt.ReceiptLayoutDTO;
import com.petrotec.documentms.entities.receipt.ReceiptLayout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface ReceiptLayoutMapper {

    ReceiptLayoutDTO toDTO(ReceiptLayout entity);

    ReceiptLayout toEntity(ReceiptLayoutDTO receiptLayoutDTO);
}
