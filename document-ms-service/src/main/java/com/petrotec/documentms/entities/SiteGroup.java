package com.petrotec.documentms.entities;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.petrotec.service.converters.JpaConverterJson;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "site_group")
public class SiteGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "code", nullable = false, updatable = false)
	private String code;

	@Column(name = "is_enabled")
	private Boolean enabled;

	@Column(name = "entity_code")
	private String entityCode;

	@Column(name = "description", columnDefinition = "json")
	@Convert(converter = JpaConverterJson.class)
	private Map<String,String> description;

	@Column(name = "entity_rank_order")
	private Integer entityRankOrder;

	@OneToMany(mappedBy = "siteGroup", cascade = CascadeType.ALL)
	@Where(clause = "parent_id IS NULL")
	private List<SiteGroupItem> items;

	@Column(name = "updated_on")
	@UpdateTimestamp
	private LocalDateTime updatedOn;

	@Column(name = "created_on")
	@CreationTimestamp
	private LocalDateTime createdOn;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Map<String, String> getDescription() {
		return description;
	}

	public void setDescription(Map<String, String> description) {
		this.description = description;
	}

	public Integer getEntityRankOrder() {
		return entityRankOrder;
	}

	public void setEntityRankOrder(Integer entityRankOrder) {
		this.entityRankOrder = entityRankOrder;
	}

	public List<SiteGroupItem> getItems() {
		return items;
	}

	public void setItems(List<SiteGroupItem> items) {
		this.items = items;
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
}
