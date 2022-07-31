package com.petrotec.documentms.entities;

import com.petrotec.service.converters.JpaConverterJson;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

/** SiteProfile */
@Entity
public class SiteProfileCustom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "description", columnDefinition = "json")
	@Convert(converter = JpaConverterJson.class)
	private Map<String,String> description;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, String> getDescription() {
		return description;
	}

	public void setDescription(Map<String, String> description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof SiteProfileCustom)) {
			return false;
		}
		SiteProfileCustom siteProfile = (SiteProfileCustom) o;
		return id == siteProfile.id && enabled == siteProfile.enabled && Objects.equals(code, siteProfile.code)
				&& Objects.equals(description, siteProfile.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, code, description, enabled);
	}

	@Override
	public String toString() {
		// @formatter:off
        return "{" +
            " id='" + getId() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
        // @formatter:on
	}

}
