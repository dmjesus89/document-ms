package com.petrotec.documentms.dtos.siteProfile;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

@Introspected
@Schema(description = "Site Profile data")
public class SiteProfileDTO {

	@NotNull
	@Size(min = 1, max = 45)
	@Schema(description = "unique profile code")
	private String code;

	@Nullable
	@Size(min = 1, max = 45)
	@Schema(description = "description for the current locale")
	private String description;

	@Nullable
	@Schema(description = "description for each locale")
	private Map<String,String> detailedDescription;

	@Schema(description = "profile status")
	private boolean enabled;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Nullable
	public String getDescription() {
		return description;
	}

	public void setDescription(@Nullable String description) {
		this.description = description;
	}

	@Nullable
	public Map<String, String> getDetailedDescription() {
		return detailedDescription;
	}

	public void setDetailedDescription(@Nullable Map<String, String> detailedDescription) {
		this.detailedDescription = detailedDescription;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
