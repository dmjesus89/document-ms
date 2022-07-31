package com.petrotec.documentms.dtos;

public class RefuelDTO {
	private String gradeId;
	private String presetType;
	private String presetValue;
	private String authToken;

	public RefuelDTO() {
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getPresetType() {
		return presetType;
	}

	public void setPresetType(String presetType) {
		this.presetType = presetType;
	}

	public String getPresetValue() {
		return presetValue;
	}

	public void setPresetValue(String presetValue) {
		this.presetValue = presetValue;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
