package com.petrotec.documentms.common;

import com.petrotec.api.PCSConstants;
import com.petrotec.service.util.HttpUtilities;
import io.micronaut.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class AuditableBaseEntity extends BaseEntity {
	private static final Logger LOG = LoggerFactory.getLogger(AuditableBaseEntity.class);

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	public AuditableBaseEntity() {
	}

	@PrePersist
	public void prePersist() {
		createdBy = getUsername();
		updatedBy = getUsername();
	}

	@PreUpdate
	public void preUpdate() {
		updatedBy = getUsername();
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Grabs the current PCS authentication username.
	 *
	 * @return Authenticated PCS username
	 */
	private String getUsername() {
		try {
			String username = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_USERNAME);
			if (StringUtils.isEmpty(username)) {
				throw new Exception();
			}
			else {
				return username;
			}
		}
		catch (Exception e){
			LOG.warn(
					"Could not get auth username. Check if this is information is being filled at API GATEWAY service.");
			return "NO_USERNAME_FOUND";
		}
	}
}
