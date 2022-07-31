package com.petrotec.documentms.mappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrotec.documentms.dtos.NotifiableDTO;
import com.petrotec.documentms.dtos.NotifiableElementDTO;
import com.petrotec.documentms.entities.Notifiable;
import com.petrotec.documentms.entities.NotifiableCustom;
import com.petrotec.documentms.entities.NotifiableElement;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import io.micronaut.core.util.StringUtils;

import javax.inject.Inject;

@Mapper(componentModel = "jsr330", uses = NotifiableElementMapper.class)
public abstract class NotifiableMapper {

    private static final Logger LOG = LoggerFactory.getLogger(NotifiableMapper.class);

	@Inject
	public ObjectMapper objectMapper;
	
	@Inject
	private NotifiableElementMapper notifiableElementMapper;

	public abstract List<NotifiableDTO> toDTO(List<Notifiable> notifiables, @Context String locale, @Context boolean detailed);

	@Mapping(target = "description", source = "notifiable.description", qualifiedByName = "deserializeJson")
    @Mapping(target = "descriptionJson", source = "notifiable.description", qualifiedByName = "deserializeJsonMap")
	public abstract NotifiableDTO toDTO(Notifiable notifiable, @Context String locale, @Context boolean detailed);

	public abstract List<Notifiable> customToEntities(List<NotifiableCustom> customNotifiables);

	@Mapping(target = "createdOn", ignore = true)
	@Mapping(target = "updatedOn", ignore = true)
	@Mapping(target = "description", expression = "java(serializeJson(notifiableDTO, notifiable, locale))")
	@Mapping(target = "notifiableElements", expression = "java(notifiableElementMapper.fromDTO(notifiableDTO.getNotifiableElements(), notifiable, locale))")
	public abstract Notifiable fromDTO(NotifiableDTO notifiableDTO, @Context String locale);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "code", ignore = true)
	@Mapping(target = "createdOn", ignore = true)
	@Mapping(target = "updatedOn", ignore = true)
	@Mapping(target = "notifiableElements", expression = "java(updateNotifiableElements(notifiable, notifiableDTO, locale))")
	@Mapping(target = "description", expression = "java(serializeJson(notifiableDTO, notifiable, locale))")
	public abstract void update(@MappingTarget Notifiable notifiable, NotifiableDTO notifiableDTO, @Context String locale);

	/** Updates existing or creates new notifiable elements from a notifiable dto into notifiable entity
	 * 
	 * @param notifiable entity
	 * @param dto dto
	 * @return created/updated notifiable elements */
	protected List<NotifiableElement> updateNotifiableElements(Notifiable notifiable, NotifiableDTO dto, String locale) {
		if (dto.getNotifiableElements() == null) {
			return null;
		}
		List<NotifiableElement> list = Optional.ofNullable(notifiable.getNotifiableElements())
				.orElse(new ArrayList<>());

		for (NotifiableElementDTO elementDTO : dto.getNotifiableElements()) {
			String elementName = elementDTO.getCode();
			Optional<NotifiableElement> matchedEntity = list.stream().filter(e -> e.getName().equals(elementName))
					.findFirst();
			if (matchedEntity.isPresent()) {
				notifiableElementMapper.update(matchedEntity.get(), elementDTO, locale);
			}
			else {
				list.add(notifiableElementMapper.fromDTO(elementDTO, notifiable,locale));
			}
		}
		return list;
	}

	@Named("deserializeJson")
    String deserializeJson(String description, @Context String locale) {
        if (StringUtils.isEmpty(description)) {
            return null;
        }
        try {
            TypeReference<HashMap<String, String>> typeReference = new TypeReference<HashMap<String, String>>() {
            };
            Map<String, String> jsonMap = objectMapper.readValue(description, typeReference);
            if (jsonMap.containsKey(locale))
                return jsonMap.get(locale);
            else if (jsonMap.containsKey("default"))
                return jsonMap.get("default");
            else if (jsonMap.containsKey("en-en"))
                return jsonMap.get("en-en");
            else if(jsonMap.size() > 0)
            {
                //impossible to determine default value, going to return first value
                return jsonMap.get(jsonMap.keySet().iterator().next());
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	@Named("deserializeJsonMap")
    Map<String, String> deserializeJsonMap(String description, @Context boolean detailed) {
        if (!detailed){
            return null;
        }
        if (StringUtils.isEmpty(description)) {
            return new HashMap<>();
        }
        try {
            TypeReference<HashMap<String, String>> typeReference = new TypeReference<HashMap<String, String>>() {
            };
            return objectMapper.readValue(description, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    @Named("serializeJsonMap")
    String serializeJsonMap(Map<String, String> description) {
        try {
            return objectMapper.writeValueAsString(description);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
	}
	
	@Named("serializeJson")
    String serializeJson(NotifiableDTO dto, @Context Notifiable entity,  @Context String locale) {
        try {
            LOG.info("[serializeJson] --> Going to serialize");
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
