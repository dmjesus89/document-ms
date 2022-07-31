package com.petrotec.documentms.dtos;

public class FuelPointResponseDTO {
	private final String result;
	private final String message;

	public FuelPointResponseDTO(String result, String message) {
		this.result = result;
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}
}
