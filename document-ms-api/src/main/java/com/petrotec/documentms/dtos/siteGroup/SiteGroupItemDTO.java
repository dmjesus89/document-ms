package com.petrotec.documentms.dtos.siteGroup;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.micronaut.core.annotation.Introspected;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Introspected
public class SiteGroupItemDTO {

	@JsonInclude(Include.NON_NULL)
//	@JsonBackReference
	@Nullable
	private SiteGroupItemDTO parent;

	@JsonInclude(Include.NON_NULL)
	@Nullable
	private List<SiteGroupItemDTO> children;

	@NotNull
	@Size(min = 1, max = 45)
	private String code;

	@NotNull
	private Boolean leaf;

	@JsonInclude(Include.NON_NULL)
	private List<SiteGroupItemSiteDTO> itemSites;

	private String description;

	private Map<String, String> detailedDescription;

	private LocalDateTime updatedOn;

	private LocalDateTime createdOn;

	private SiteGroupDTO siteGroup;


	@Nullable
	public SiteGroupItemDTO getParent() {
		return parent;
	}

	public void setParent(@Nullable SiteGroupItemDTO parent) {
		this.parent = parent;
	}

	@Nullable
	public List<SiteGroupItemDTO> getChildren() {
		return children;
	}

	public void setChildren(@Nullable List<SiteGroupItemDTO> children) {
		this.children = children;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public List<SiteGroupItemSiteDTO> getItemSites() {
		return itemSites;
	}

	public void setItemSites(List<SiteGroupItemSiteDTO> itemSites) {
		this.itemSites = itemSites;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getDetailedDescription() {
		return detailedDescription;
	}

	public void setDetailedDescription(Map<String, String> detailedDescription) {
		this.detailedDescription = detailedDescription;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public SiteGroupDTO getSiteGroup() {
		return siteGroup;
	}

	public void setSiteGroup(SiteGroupDTO siteGroup) {
		this.siteGroup = siteGroup;
	}
}
