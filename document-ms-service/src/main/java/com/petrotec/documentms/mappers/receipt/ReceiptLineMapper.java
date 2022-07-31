package com.petrotec.documentms.mappers.receipt;

import com.petrotec.documentms.dtos.receipt.ReceiptLineDTO;
import com.petrotec.documentms.entities.receipt.ReceiptLine;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ReceiptLineMapper {

    private final ReceiptBlockTypeMapper receiptBlockTypeMapper;
    private final ReceiptLayoutMapper receiptLayoutMapper;

    public ReceiptLineMapper(ReceiptBlockTypeMapper receiptBlockTypeMapper, ReceiptLayoutMapper receiptLayoutMapper) {
        this.receiptBlockTypeMapper = receiptBlockTypeMapper;
        this.receiptLayoutMapper = receiptLayoutMapper;
    }

    public List<ReceiptLineDTO> toDTOList(List<ReceiptLine> entities) {
        if ( entities == null ) {
            return null;
        }
        List<ReceiptLineDTO> list = new ArrayList<ReceiptLineDTO>( entities.size() );
        for ( ReceiptLine receiptLine : entities ) {
            list.add( toDTO( receiptLine ) );
        }
        return list;
    }

    public ReceiptLineDTO toDTO(ReceiptLine entity) {
        if ( entity == null ) {
            return null;
        }
        ReceiptLineDTO receiptLineDTO = new ReceiptLineDTO();
        receiptLineDTO.setLineNo( entity.getLineNo() );
        receiptLineDTO.setLineData( entity.getLineData() );
        receiptLineDTO.setReceiptBlockTypeDTO(receiptBlockTypeMapper.toDTO(entity.getReceiptBlockTypes()));
        receiptLineDTO.setReceiptLayoutDTO(receiptLayoutMapper.toDTO(entity.getReceiptLayout()));
        receiptLineDTO.setReceiptBlockTypeCode(entity.getReceiptBlockTypes().getCode());
        receiptLineDTO.setReceiptLayoutCode(entity.getReceiptLayout().getCode());
        return receiptLineDTO;
    }

    public ReceiptLine toEntity(ReceiptLineDTO receiptLineDTO) {
        if ( receiptLineDTO == null ) {
            return null;
        }
        ReceiptLine receiptLine = new ReceiptLine();
        receiptLine.setLineNo( receiptLineDTO.getLineNo() );
        receiptLine.setLineData( receiptLineDTO.getLineData() );
        receiptLine.setReceiptBlockTypes(receiptBlockTypeMapper.toEntity(receiptLineDTO.getReceiptBlockTypeDTO()));
        receiptLine.setReceiptLayout(receiptLayoutMapper.toEntity(receiptLineDTO.getReceiptLayoutDTO()));
        return receiptLine;
    }

    public List<ReceiptLine> toEntityList(List<ReceiptLineDTO> entitiesDTO) {
        if ( entitiesDTO == null ) {
            return null;
        }
        List<ReceiptLine> list = new ArrayList<ReceiptLine>( entitiesDTO.size() );
        for ( ReceiptLineDTO receiptLineDTO : entitiesDTO ) {
            list.add( toEntity( receiptLineDTO ) );
        }
        return list;
    }
}
