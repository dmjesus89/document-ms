package com.petrotec.documentms.dtos.grade;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Introspected
@Schema(description = "Represent a grade view with associated properties. This dto is searchable")
public class GradeViewDTO {

	@Schema(description = "grade unique code")
	private String code;

	@Schema(description = "grade description for the current locale")
	private String description;

	@Schema(description = "grade color")
	private String color;

	@Schema(description = "grade unit price net")
	private BigDecimal unitPriceNet;

	@Schema(description = "grade checks if the grade is enabled")
	private boolean enabled;

	@Schema(description = "grade created on")
	private LocalDateTime createdOn;

	@Schema(description = "grade updated on")
	private LocalDateTime updatedOn;

	@Schema(description = "grade main product data. (Product with higher percentage)")
	private GradeViewProductDTO product;

	@Schema(description = "grade main product group data. (Product with higher percentage)")
	private GradeViewProductGroupDTO productGroup;

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

	public GradeViewProductDTO getProduct() {
		return product;
	}

	public void setProduct(GradeViewProductDTO product) {
		this.product = product;
	}

	public GradeViewProductGroupDTO getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(GradeViewProductGroupDTO productGroup) {
		this.productGroup = productGroup;
	}
}
