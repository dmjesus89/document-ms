package com.petrotec.documentms.dtos.configuration;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Introspected
@Schema(description = "Fuelling Mode that can be associated to an FCC Configuration")
public class FuellingModeDTO {

    @Schema(description = "Unique fuelling mode code", maxLength = 45, accessMode = Schema.AccessMode.READ_ONLY)
    private String code;

    @Schema(description = "Checks if the current fuelling mode is enabled", defaultValue = "true")
    private boolean enabled;

    @NotBlank
    @Schema(description = "User friendly description", maxLength = 50)
    private String description;

    @NotNull
    @Max(value = 9999)
    @Schema(description = "Max fuelling time allowed before the fuelling reaches the minimum value.", maximum = "9999", minimum = "0")
    private Integer maxTimeToReachMinLimit;

    @NotNull
    @Max(value = 99)
    @Schema(description = "Max fuelling time without progress", maximum = "99", minimum = "0")
    private Integer maxTimeWithoutProgress;

    @NotNull
    @Max(value = 9999)
    @Schema(description = "Maximum fuelling time in seconds.", maximum = "9999", minimum = "0")
    private Integer maxFuellingTime;

    @NotNull
    @Max(value = 9999999)
    @Schema(description = "Maximum transaction Volume", maximum = "9999999", minimum = "0")
    private Integer maxTransactionVolume;

    @NotNull
    @Max(value = 9999999)
    @Schema(description = "Maximum transaction Amount", maximum = "9999999", minimum = "0")
    private Integer maxTransactionAmount;

    @NotNull
    @Max(value = 99)
    @Schema(description = "Maximum volume overrun (transaction over the amount preseted)", maximum = "99", minimum = "0")
    private Integer maxVolumeOverrun;

    @NotNull
    @Max(value = 9999)
    @Schema(description = "Clear display time", maximum = "9999", minimum = "0")
    private Integer clearDisplayTime;

    @Schema(description = "Clear display when transaction is cleared", defaultValue = "false")
    private boolean clearDisplayWhenTransactionIsCleared;

    @Schema(description = "Object creation date", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdOn;

    @Schema(description = "Last known update date", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedOn;

    public FuellingModeDTO() {

    }

    public FuellingModeDTO(String code, boolean enabled, String description, Integer maxTimeToReachMinLimit, Integer maxTimeWithoutProgress, Integer maxFuellingTime, Integer maxTransactionVolume, Integer maxTransactionAmount, Integer maxVolumeOverrun, Integer clearDisplayTime, boolean clearDisplayWhenTransactionIsCleared, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this.code = code;
        this.enabled = enabled;
        this.description = description;
        this.maxTimeToReachMinLimit = maxTimeToReachMinLimit;
        this.maxTimeWithoutProgress = maxTimeWithoutProgress;
        this.maxFuellingTime = maxFuellingTime;
        this.maxTransactionVolume = maxTransactionVolume;
        this.maxTransactionAmount = maxTransactionAmount;
        this.maxVolumeOverrun = maxVolumeOverrun;
        this.clearDisplayTime = clearDisplayTime;
        this.clearDisplayWhenTransactionIsCleared = clearDisplayWhenTransactionIsCleared;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxTimeToReachMinLimit() {
        return maxTimeToReachMinLimit;
    }

    public void setMaxTimeToReachMinLimit(Integer maxTimeToReachMinLimit) {
        this.maxTimeToReachMinLimit = maxTimeToReachMinLimit;
    }

    public Integer getMaxTimeWithoutProgress() {
        return maxTimeWithoutProgress;
    }

    public void setMaxTimeWithoutProgress(Integer maxTimeWithoutProgress) {
        this.maxTimeWithoutProgress = maxTimeWithoutProgress;
    }

    public Integer getMaxFuellingTime() {
        return maxFuellingTime;
    }

    public void setMaxFuellingTime(Integer maxFuellingTime) {
        this.maxFuellingTime = maxFuellingTime;
    }

    public Integer getMaxTransactionVolume() {
        return maxTransactionVolume;
    }

    public void setMaxTransactionVolume(Integer maxTransactionVolume) {
        this.maxTransactionVolume = maxTransactionVolume;
    }

    public Integer getMaxTransactionAmount() {
        return maxTransactionAmount;
    }

    public void setMaxTransactionAmount(Integer maxTransactionAmount) {
        this.maxTransactionAmount = maxTransactionAmount;
    }

    public Integer getMaxVolumeOverrun() {
        return maxVolumeOverrun;
    }

    public void setMaxVolumeOverrun(Integer maxVolumeOverrun) {
        this.maxVolumeOverrun = maxVolumeOverrun;
    }

    public Integer getClearDisplayTime() {
        return clearDisplayTime;
    }

    public void setClearDisplayTime(Integer clearDisplayTime) {
        this.clearDisplayTime = clearDisplayTime;
    }

    public boolean isClearDisplayWhenTransactionIsCleared() {
        return clearDisplayWhenTransactionIsCleared;
    }

    public void setClearDisplayWhenTransactionIsCleared(boolean clearDisplayWhenTransactionIsCleared) {
        this.clearDisplayWhenTransactionIsCleared = clearDisplayWhenTransactionIsCleared;
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
