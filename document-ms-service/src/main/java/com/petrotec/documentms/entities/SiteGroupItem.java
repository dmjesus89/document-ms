package com.petrotec.documentms.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.petrotec.service.converters.JpaConverterJson;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "site_group_item")
public class SiteGroupItem implements Serializable {

	private static final long serialVersionUID = 7865221449375442704L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_group_id")
	private SiteGroup siteGroup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private SiteGroupItem parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SiteGroupItem> children = new ArrayList<>();

	@OneToMany(mappedBy = "siteGroupItem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SiteGroupItemSite> siteGroupItemSite = new ArrayList<>();

	@Column(name = "code", nullable = false, updatable = false)
	private String code;

	@Column(name = "description", columnDefinition = "json")
	@Convert(converter = JpaConverterJson.class)
	private Map<String,String> description;

	@Column(name = "is_leaf")
	private Boolean isLeaf;

	@Column(name = "updated_on", nullable = false, insertable = false)
	@UpdateTimestamp
	private LocalDateTime updatedOn;

	@Column(name = "created_on", nullable = false, insertable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdOn;

	public SiteGroupItem() {
	}

	public List<SiteGroupItemSite> getSiteGroupItemSite() {
		return siteGroupItemSite;
	}

	public void setSiteGroupItemSite(List<SiteGroupItemSite> siteGroupItemSite) {
		this.siteGroupItemSite = siteGroupItemSite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SiteGroup getSiteGroup() {
		return siteGroup;
	}

	public void setSiteGroup(SiteGroup siteGroup) {
		this.siteGroup = siteGroup;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public SiteGroupItem getParent() {
		return parent;
	}

	public void setParent(SiteGroupItem parent) {
		this.parent = parent;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getLeaf() {
		return isLeaf;
	}

	public void setLeaf(Boolean leaf) {
		isLeaf = leaf;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public List<SiteGroupItem> getChildren() {
		return children;
	}

	public void setChildren(List<SiteGroupItem> children) {
		this.children = children;
	}

	public Map<String, String> getDescription() {
		return description;
	}

	public void setDescription(Map<String, String> description) {
		this.description = description;
	}
}
