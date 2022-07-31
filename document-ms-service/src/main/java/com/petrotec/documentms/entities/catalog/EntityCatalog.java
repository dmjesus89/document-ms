package com.petrotec.documentms.entities.catalog;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "entity", catalog="catalog", schema="catalog")
public class EntityCatalog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;
    
    @Column(name="tenant_id")
    private long tenantId;
    
    @Column(name="user_suffix")
    private String userSuffix;
    
    @Column(name="max_allowed_user")
    private Integer maxAllowedUser;
    
    @Column(name="is_enabled")
    private Boolean isEnabled;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getTenantId() {
		return tenantId;
	}

	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserSuffix() {
		return userSuffix;
	}

	public void setUserSuffix(String userSuffix) {
		this.userSuffix = userSuffix;
	}

	public Integer getMaxAllowedUser() {
		return maxAllowedUser;
	}

	public void setMaxAllowedUser(Integer maxAllowedUser) {
		this.maxAllowedUser = maxAllowedUser;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
