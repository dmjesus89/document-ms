package com.petrotec.documentms.mappers;

import com.petrotec.documentms.dtos.NotifiableElementContactDTO;
import com.petrotec.documentms.entities.NotifiableElement;
import com.petrotec.documentms.entities.NotifiableElementContact;
import com.petrotec.documentms.entities.FlatNotifiableElementContact;
import com.petrotec.documentms.entities.NotifiableMethod;
import com.petrotec.documentms.repositories.interfaces.NotifiableMethodRepository;
import io.micronaut.core.util.StringUtils;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "jsr330")
public abstract class NotifiableElementContactMapper {

	@Inject
	private NotifiableMethodRepository methodRepository;

	@Mapping(target = "notifiableMethodCode", source = "notifiableMethod.code")
	public abstract NotifiableElementContactDTO toDTO(NotifiableElementContact notifiableElementContact);

	public List<NotifiableElementContactDTO> toDTOFromFlat(List<FlatNotifiableElementContact> flatContacts) {
		if (flatContacts == null) return null;
		if (flatContacts.stream().allMatch(this::isEmptyContact)) return null;
		return flatContacts.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public boolean isEmptyContact(FlatNotifiableElementContact contact){
		return Stream
				.of(contact.getNotifiableMethodCode(), contact.getAlarmSeverityCode(), contact.getContact(), contact.getCreatedOn(), contact.getUpdatedOn())
				.allMatch(Objects::isNull);
	}

	public abstract NotifiableElementContactDTO toDTO(FlatNotifiableElementContact flatContact);

	public abstract List<NotifiableElementContactDTO> toDTO(List<NotifiableElementContact> notifiableElementContacts);

	/** Converts DTO into entity object. It also maps notifiable method from code to entity.
	 * 
	 * @param notifiableElementContactDTO contact dto object
	 * @param notifiableElement           element this contact belongs to
	 * @return NotifiableElementContact */
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdOn", ignore = true)
	@Mapping(target = "updatedOn", ignore = true)
	@Mapping(target = "notifiableElement", expression = "java(notifiableElement)")
	@Mapping(target = "notifiableMethod", source = "notifiableElementContactDTO.notifiableMethodCode")
	public abstract NotifiableElementContact fromDTO(NotifiableElementContactDTO notifiableElementContactDTO,
			@Context NotifiableElement notifiableElement);

	public abstract List<NotifiableElementContact> fromDTO(List<NotifiableElementContactDTO> notifiableElementContacts,
			@Context NotifiableElement notifiableElement);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdOn", ignore = true)
	@Mapping(target = "updatedOn", ignore = true)
	@Mapping(target = "notifiableMethod", source = "notifiableMethodCode")
	public abstract void update(@MappingTarget NotifiableElementContact entity, NotifiableElementContactDTO dto);

	protected NotifiableMethod fromMethodCode(String methodCode) {
		if (StringUtils.isEmpty(methodCode)) {
			throw new IllegalArgumentException("Invalid method code received: " + methodCode);
		}
		return methodRepository.findByCode(methodCode)
				.orElseThrow(() -> new EntityNotFoundException("Invalid method code received: " + methodCode));
	}
}
