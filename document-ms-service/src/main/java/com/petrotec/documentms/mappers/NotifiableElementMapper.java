package com.petrotec.documentms.mappers;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrotec.documentms.dtos.NotifiableElementContactDTO;
import com.petrotec.documentms.dtos.NotifiableElementDTO;
import com.petrotec.documentms.entities.Notifiable;
import com.petrotec.documentms.entities.NotifiableElement;
import com.petrotec.documentms.entities.NotifiableElementContact;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.core.util.CollectionUtils;

@Mapper(componentModel = "jsr330", uses = {NotifiableElementContactMapper.class, NotifiableMapper.class })
public abstract class NotifiableElementMapper {

	private static final Logger LOG = LoggerFactory.getLogger(NotifiableMapper.class);

	@Inject
	public ObjectMapper objectMapper;
	
	@Inject
	private NotifiableElementContactMapper contactMapper;

	public abstract List<NotifiableElementDTO> toDTO(List<NotifiableElement> notifiableElements, @Context String locale, @Context boolean detailed);

	@Mapping(target = "code", source = "name")
	@Mapping(target = "description", source = "notifiableElement.description", qualifiedByName = "deserializeJson")
    @Mapping(target = "descriptionJson", source = "notifiableElement.description", qualifiedByName = "deserializeJsonMap")
	public abstract NotifiableElementDTO toDTO(NotifiableElement notifiableElement, @Context String locale, @Context boolean detailed);

	public List<NotifiableElement> fromDTO(List<NotifiableElementDTO> notifiableElements,
			@Context Notifiable notifiable, String locale) {
		if (CollectionUtils.isEmpty(notifiableElements)) return null;
		return notifiableElements.stream().map(dto -> fromDTO(dto, notifiable, locale)).collect(Collectors.toList());
	}

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdOn", ignore = true)
	@Mapping(target = "updatedOn", ignore = true)
	@Mapping(target = "name", expression = "java(getItemId(notifiableElementDTO.getCode()))")
	@Mapping(target = "description", expression = "java(serializeJson(notifiableElementDTO, notifiableElement, locale))")
	@Mapping(target = "notifiableElementContacts", expression = "java(notifiableElementContactMapper.fromDTO(notifiableElementDTO.getNotifiableElementContacts(), notifiableElement))")
	public abstract NotifiableElement fromDTO(NotifiableElementDTO notifiableElementDTO, Notifiable notifiable, @Context String locale);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdOn", ignore = true)
	@Mapping(target = "updatedOn", ignore = true)
	//@Mapping(target = "notifiableElement.name", source= "dto.code")
	@Mapping(target = "name", expression = "java(getItemId(dto.getCode()))")
	@Mapping(target = "description", expression = "java(serializeJson(dto, notifiableElement, locale))")
	@Mapping(target = "notifiableElementContacts", expression = "java(updateNotifiableElementsContacts(dto, notifiableElement))")
	public abstract void update(@MappingTarget NotifiableElement notifiableElement, NotifiableElementDTO dto, @Context String locale);

	protected List<NotifiableElementContact> updateNotifiableElementsContacts(NotifiableElementDTO dto,
			@Context NotifiableElement notifiableElement) {

		if (dto.getNotifiableElementContacts() == null) {
			return null;
		}
		List<NotifiableElementContact> list = Optional.ofNullable(notifiableElement.getNotifiableElementContacts())
				.orElse(new ArrayList<>());

		for (NotifiableElementContactDTO contactDTO : dto.getNotifiableElementContacts()) {
			String contactValue = contactDTO.getContact();
			Optional<NotifiableElementContact> matchedEntity = list.stream()
					.filter(e -> e.getContact().equals(contactValue)).findFirst();
			if (matchedEntity.isPresent()) {
				contactMapper.update(matchedEntity.get(), contactDTO);
			}
			else {
				list.add(contactMapper.fromDTO(contactDTO, notifiableElement));
			}
		}
		return list;
	}

	@Named("getItemId")
	String getItemId(String currentID){
		LOG.info("[getItemId] --> Going to getItemId");

		if (currentID == null)
		{
			LOG.info("[getItemId] --> creating a new one.");
			return UUID.randomUUID().toString();
		}

		LOG.info("[getItemId] --> returning currentID -->" + currentID);
		return currentID;
	}


	@Named("serializeJson")
    String serializeJson(NotifiableElementDTO dto, @Context NotifiableElement entity,  @Context String locale) {
        try {
            if (dto != null && dto.getDescriptionJson() != null)
                return objectMapper.writeValueAsString(dto.getDescriptionJson());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            TypeReference<HashMap<String, String>> typeReference = new TypeReference<HashMap<String, String>>() {};
            Map<String, String> descriptionMap =new HashMap<String, String>();

            if (entity != null && entity.getDescription() != null)
            {
                try{
                    
                    Map<String, String> jsonMap = objectMapper.readValue(entity.getDescription(), typeReference);
                    if (jsonMap != null)
                    {
                        descriptionMap = jsonMap;
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                
            }

            descriptionMap.put(locale, dto.getDescription());
            return objectMapper.writeValueAsString(descriptionMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
