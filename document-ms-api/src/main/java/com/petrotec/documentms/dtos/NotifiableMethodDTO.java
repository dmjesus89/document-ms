package com.petrotec.documentms.dtos;

import java.util.Map;
import java.util.Objects;

public class NotifiableMethodDTO {

	private String code;
	private Map<String, String> description;
	private boolean enabled;

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
		if (!(o instanceof NotifiableMethodDTO)) {
			return false;
		}
		NotifiableMethodDTO notifiableMethodDTO = (NotifiableMethodDTO) o;
		return Objects.equals(code, notifiableMethodDTO.code)
				&& Objects.equals(description, notifiableMethodDTO.description)
				&& enabled == notifiableMethodDTO.enabled;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, description, enabled);
	}

	@Override
	public String toString() {
		// @formatter:off
		return "{" +
			" code='" + code + "'" +
			", description='" + description + "'" +
			", enabled='" + enabled + "'" +
			"}";
		// @formatter:on
	}

}
