package com.petrotec.documentms.common;

import com.petrotec.service.converters.JpaConverterJson;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import java.util.Map;

@MappedSuperclass
public class EnumerableBaseEntity extends BaseEntity {

	@Column(name = "code", length = 20, nullable = false, unique = true, updatable = false)
	private String code;

	@Column(name = "description", columnDefinition = "json", nullable = true)
	@Convert(converter = JpaConverterJson.class)
	private Map<String, String> description;

	@Column(name = "is_enabled")
	private boolean enabled;

	public EnumerableBaseEntity() {
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
}
