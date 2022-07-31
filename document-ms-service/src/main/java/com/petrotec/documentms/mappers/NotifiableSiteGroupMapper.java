package com.petrotec.documentms.mappers;

import com.petrotec.documentms.dtos.NotifiableSiteGroupItemDTO;
import com.petrotec.documentms.entities.NotifiableSiteGroupItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jsr330")
public abstract class NotifiableSiteGroupMapper {

	public abstract NotifiableSiteGroupItemDTO toDTO(NotifiableSiteGroupItem notifiableSiteGroupItem);

	public abstract List<NotifiableSiteGroupItemDTO> toDTO(List<NotifiableSiteGroupItem> notifiableSiteGroupItem);

}
