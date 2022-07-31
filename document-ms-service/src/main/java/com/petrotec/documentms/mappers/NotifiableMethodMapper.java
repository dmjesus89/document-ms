package com.petrotec.documentms.mappers;

import java.util.List;
import java.util.Map;

import com.petrotec.documentms.dtos.NotifiableMethodDTO;
import com.petrotec.documentms.dtos.NotifiableMethodTranslatedDTO;
import com.petrotec.documentms.entities.NotifiableMethod;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "jsr330")
public abstract class NotifiableMethodMapper {

	public abstract List<NotifiableMethodDTO> toDTO(List<NotifiableMethod> notifiableMethods);

	@Mapping(target = "description", source = "description", qualifiedByName = "descriptionForLocale")
	public abstract NotifiableMethodTranslatedDTO toTranslatedDTO(NotifiableMethod notifiableMethod,
			@Context String locale);

	public abstract List<NotifiableMethodTranslatedDTO> toTranslatedDTO(List<NotifiableMethod> notifiableMethods,
			@Context String locale);

	@Named(value = "descriptionForLocale")
	public String getDescriptionForLocale(Map<String, String> description, @Context String locale) {
		return description != null ? description.get(locale) : null;
	}
}
