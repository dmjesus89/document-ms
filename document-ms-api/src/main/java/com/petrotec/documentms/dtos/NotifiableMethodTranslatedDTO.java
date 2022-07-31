package com.petrotec.documentms.dtos;

public class NotifiableMethodTranslatedDTO {

	private String code;
	private String description;
	private boolean isEnabled;

	public NotifiableMethodTranslatedDTO() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean enabled) {
		isEnabled = enabled;
	}
}
