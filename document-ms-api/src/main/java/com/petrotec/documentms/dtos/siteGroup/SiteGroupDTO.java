package com.petrotec.documentms.dtos.siteGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SiteGroupDTO {

	@NotNull
	@Size(min = 1, max = 45)
	private String code;

	@NotNull
	private Boolean enabled;

	private String entityCode;

	private String description;
	
	private Map<String, String> detailedDescription;

	private int entityRankOrder;

	private List<SiteGroupItemDTO> items;

	private LocalDateTime updatedOn;

	private LocalDateTime createdOn;

	public int getEntityRankOrder() {
		return entityRankOrder;
	}

	public List<SiteGroupItemDTO> getItems() {
		return items;
	}

	public void setItems(List<SiteGroupItemDTO> items) {
		this.items = items;
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

	public void setEntityRankOrder(int entityRankOrder) {
		this.entityRankOrder = entityRankOrder;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String,String> getDetailedDescription() {
		return this.detailedDescription;
	}

	public void setDetailedDescription(Map<String,String> detailedDescription) {
		this.detailedDescription = detailedDescription;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof SiteGroupDTO)) {
			return false;
		}
		SiteGroupDTO siteGroupDTO = (SiteGroupDTO) o;
		return Objects.equals(code, siteGroupDTO.code) && Objects.equals(enabled, siteGroupDTO.enabled)
				&& Objects.equals(entityCode, siteGroupDTO.entityCode)
				&& Objects.equals(updatedOn, siteGroupDTO.updatedOn)
				&& Objects.equals(createdOn, siteGroupDTO.createdOn) && entityRankOrder == siteGroupDTO.entityRankOrder;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, enabled, entityCode, entityRankOrder, updatedOn, createdOn);
	}

	@Override
	public String toString() {
		// @formatter:off
		return "{" +
			" code='" + getCode() + "'" +
			" description='" + getDescription() + "'" +
			", enabled='" + isEnabled() + "'" +
			", entityCode='" + getEntityCode() + "'" +
			", entityRankOrder='" + getEntityRankOrder() + "'" +
			", updatedOn='" + getUpdatedOn() + "'" +
			", createdOn='" + getCreatedOn() + "'" +
			"}";
		// @formatter:on
	}

}
