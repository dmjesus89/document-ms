package com.petrotec.documentms.mappers;

import com.petrotec.documentms.dtos.NotifiableSiteDTO;
import com.petrotec.documentms.entities.NotifiableSite;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jsr330")
public interface NotifiableSiteMapper {
	NotifiableSiteDTO toDTO(NotifiableSite notifiableSite);
	List<NotifiableSiteDTO> toDTO(List<NotifiableSite> notifiableSites);
}
