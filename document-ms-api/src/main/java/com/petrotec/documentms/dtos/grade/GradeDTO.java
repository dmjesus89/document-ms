package com.petrotec.documentms.dtos.grade;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Introspected
@Schema(description = "Represent a grade that can be associated to a product")
public class GradeDTO {

	@Schema(description = "unique code")
	private String code;

	@Schema(description = "description for the current locale")
	private String description;

	@Schema(description = "description for all locales")
	private Map<String, String> detailedDescription;

	@Schema(description = "color")
	private String color;

	@Schema(description = "grade unit price net")
	private BigDecimal unitPriceNet;

	@Schema(description = "grade product composition. Composition of all products in a grade")
	private List<GradeProductDTO> products;

	@Schema(description = "site status")
	private boolean enabled = true;

	@Schema(description = "Creation Datetime")
	private LocalDateTime createdOn;

	@Schema(description = "Last update Datetime")
	private LocalDateTime updatedOn;

	public GradeDTO() {
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

	public Map<String, String> getDetailedDescription() {
		return detailedDescription;
	}

	public void setDetailedDescription(Map<String, String> detailedDescription) {
		this.detailedDescription = detailedDescription;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public BigDecimal getUnitPriceNet() {
		return unitPriceNet;
	}

	public void setUnitPriceNet(BigDecimal unitPriceNet) {
		this.unitPriceNet = unitPriceNet;
	}

	public List<GradeProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<GradeProductDTO> products) {
		this.products = products;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
}
