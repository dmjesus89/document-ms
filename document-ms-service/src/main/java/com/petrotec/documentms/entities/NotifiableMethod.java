package com.petrotec.documentms.entities;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Map;
import java.util.Objects;

import com.petrotec.service.converters.JpaConverterJson;

@Entity
@Table(name = "notifiable_method")
public class NotifiableMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private byte id;

	@Column(name = "code")
	private String code;

	@Convert(converter = JpaConverterJson.class)
	@Column(name = "description", columnDefinition = "json", nullable = true)
	private Map<String, String> description;

	@Column(name = "is_enabled")
	private boolean enabled;

	public NotifiableMethod() {
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
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
		if (!(o instanceof NotifiableMethod)) {
			return false;
		}
		NotifiableMethod notifiableMethod = (NotifiableMethod) o;
		return id == notifiableMethod.id && Objects.equals(code, notifiableMethod.code)
				&& Objects.equals(description, notifiableMethod.description) && enabled == notifiableMethod.enabled;
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

	// public List<NotifiableElementContact> getRefNotifiableElementContactEntities() {
	// return refNotifiableElementContactEntities;
	// }
	//
	// public void setRefNotifiableElementContactEntities(
	// List<NotifiableElementContact> refNotifiableElementContactEntities) {
	// this.refNotifiableElementContactEntities = refNotifiableElementContactEntities;
	// }
}
