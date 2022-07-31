package com.petrotec.documentms.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NotifiableDTO {

	private String code;
	private String entityCode;
	private int entityRankOrder;
	private boolean enabled;
	private List<NotifiableElementDTO> notifiableElements;
	private String description;
	private Map<String, String> descriptionJson;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEntityCode() {
		return this.entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public int getEntityRankOrder() {
		return this.entityRankOrder;
	}

	public void setEntityRankOrder(int entityRankOrder) {
		this.entityRankOrder = entityRankOrder;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<NotifiableElementDTO> getNotifiableElements() {
		return this.notifiableElements;
	}

	public void setNotifiableElements(List<NotifiableElementDTO> notifiableElements) {
		this.notifiableElements = notifiableElements;
	}

	public LocalDateTime getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
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

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof NotifiableDTO)) {
			return false;
		}
		NotifiableDTO notifiableDTO = (NotifiableDTO) o;
		return Objects.equals(code, notifiableDTO.code) && Objects.equals(entityCode, notifiableDTO.entityCode)
				&& entityRankOrder == notifiableDTO.entityRankOrder && enabled == notifiableDTO.enabled
				&& Objects.equals(notifiableElements, notifiableDTO.notifiableElements)
				&& Objects.equals(createdOn, notifiableDTO.createdOn)
				&& Objects.equals(updatedOn, notifiableDTO.updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, entityCode, entityRankOrder, enabled, notifiableElements, createdOn, updatedOn);
	}

	@Override
	public String toString() {
		// @formatter:off
		return "{" +
			" code='" + getCode() + "'" +
			", entityCode='" + getEntityCode() + "'" +
			", entityRankOrder='" + getEntityRankOrder() + "'" +
			", enabled='" + isEnabled() + "'" +
			", notifiableElements='" + getNotifiableElements() + "'" +
			", createdOn='" + getCreatedOn() + "'" +
			", updatedOn='" + getUpdatedOn() + "'" +
			"}";
		// @formatter:on
	}

}
