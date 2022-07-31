package com.petrotec.documentms.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class NotifiableElementDTO {

	@Deprecated
	private String code;
	private String description;
	private Map<String, String> descriptionJson;
	private String localeCode;
	private List<NotifiableElementContactDTO> notifiableElementContacts;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;

	public NotifiableElementDTO() {
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public List<NotifiableElementContactDTO> getNotifiableElementContacts() {
		return notifiableElementContacts;
	}

	public void setNotifiableElementContacts(List<NotifiableElementContactDTO> notifiableElementContacts) {
		this.notifiableElementContacts = notifiableElementContacts;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String,String> getDescriptionJson() {
		return this.descriptionJson;
	}

	public void setDescriptionJson(Map<String,String> descriptionJson) {
		this.descriptionJson = descriptionJson;
	}
}
