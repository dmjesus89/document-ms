package com.petrotec.documentms.dtos.site;

import com.petrotec.documentms.enums.PumpLightMode;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@Schema(description = "Config of pump", accessMode = Schema.AccessMode.READ_ONLY)
public class ServiceModeDTO {

    @Schema(description = "Unique service mode code", maxLength = 45, accessMode = Schema.AccessMode.READ_ONLY, required = true)
    private String code;

    @NotBlank
    @Schema(description = "Checks if the current service mode is enabled", defaultValue = "true", required = true)
    private boolean enabled;

    @NotBlank
    @Min(1)
    @Max(50)
    @Schema(description = "User friendly description", maxLength = 50)
    private String description;

    @Min(0)
    @Max(3)
    @Schema(description = "Auto authorize limit", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 3)
    private int autoAuthorizeLimit;

    @Min(0)
    @Max(9999)
    @Schema(description = "Max pre authorize time (seconds)", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 9999)
    private int maxPreAuthorizeTime;

    @Min(0)
    @Max(9999)
    @Schema(description = "Max nozzle lay down time (seconds)", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 9999)
    private int maxNozzleDownTime;

    @NotBlank
    @Schema(description = "Zero trans to pos", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false", required = true)
    private boolean zeroTransToPos;

    @NotBlank
    @Schema(description = "Money due in trans buffer status", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false", required = true)
    private boolean moneyInTransBufferStatus;

    @Min(0)
    @Max(10)
    @Schema(description = "Min trans volume", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 10)
    private BigDecimal minTransVolume;

    @Min(0)
    @Max(50)
    @Schema(description = "Min trans money", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 50)
    private BigDecimal minTransAmount;

    @Min(0)
    @Max(99)
    @Schema(description = "Sup Trans Buffer Size", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 99)
    private int supTransBufferSize;

    @Min(0)
    @Max(99)
    @Schema(description = "Sup Trans Buffer Size", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 99)
    private int unSupTransBufferSize;

    @NotBlank
    @Schema(description = "Store at pre authorize", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false", required = true)
    private boolean storePreAuthorize;

    @NotBlank
    @Schema(description = "Volume in trans buffer status", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false", required = true)
    private boolean volumeInTransBufferStatus;

    @NotBlank
    @Schema(description = "Authorize mode selection", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false", required = true)
    private boolean authorizeModeSelection;

    @Min(0)
    @Max(99)
    @Schema(description = "Max no of consecutive zero trans", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 99)
    private int maxNoOfConsecutiveZeroTrans;

    @Min(0)
    @Max(9999)
    @Schema(description = "Auto clear trans delay time (Seconds)", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 9999)
    private int autoClearTransDelayTime;

    @Min(0)
    @Max(9999)
    @Schema(description = "Auto unlock trans delay time (Seconds)", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 9999)
    private int autoUnlockTransDelayTime;

    @NotBlank
    @Schema(description = "Pump light mode", accessMode = Schema.AccessMode.READ_WRITE, discriminatorProperty = "INACTIVE, ON_AT_AUTHORIZE, BLINK_AT_AUTHORIZE, ON_AT_RESERVE, BLINK_ON_RESERVE", required = true)
    private PumpLightMode pumpLightMode;

    @NotBlank
    @Schema(description = "Stop fuelling point on vehicle tag", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false", required = true)
    private boolean stopFuellingPointVehicleTag;

    @NotBlank
    @Schema(description = "Use vehicle tag reading button", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false", required = true)
    private boolean useVehicleTagReadingButton;

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

    public int getAutoAuthorizeLimit() {
        return autoAuthorizeLimit;
    }

    public void setAutoAuthorizeLimit(int autoAuthorizeLimit) {
        this.autoAuthorizeLimit = autoAuthorizeLimit;
    }

    public int getMaxPreAuthorizeTime() {
        return maxPreAuthorizeTime;
    }

    public void setMaxPreAuthorizeTime(int maxPreAuthorizeTime) {
        this.maxPreAuthorizeTime = maxPreAuthorizeTime;
    }

    public int getMaxNozzleDownTime() {
        return maxNozzleDownTime;
    }

    public void setMaxNozzleDownTime(int maxNozzleDownTime) {
        this.maxNozzleDownTime = maxNozzleDownTime;
    }

    public boolean isZeroTransToPos() {
        return zeroTransToPos;
    }

    public void setZeroTransToPos(boolean zeroTransToPos) {
        this.zeroTransToPos = zeroTransToPos;
    }

    public boolean isMoneyInTransBufferStatus() {
        return moneyInTransBufferStatus;
    }

    public void setMoneyInTransBufferStatus(boolean moneyInTransBufferStatus) {
        this.moneyInTransBufferStatus = moneyInTransBufferStatus;
    }

    public BigDecimal getMinTransVolume() {
        return minTransVolume;
    }

    public void setMinTransVolume(BigDecimal minTransVolume) {
        this.minTransVolume = minTransVolume;
    }

    public BigDecimal getMinTransAmount() {
        return minTransAmount;
    }

    public void setMinTransAmount(BigDecimal minTransAmount) {
        this.minTransAmount = minTransAmount;
    }

    public int getSupTransBufferSize() {
        return supTransBufferSize;
    }

    public void setSupTransBufferSize(int supTransBufferSize) {
        this.supTransBufferSize = supTransBufferSize;
    }

    public int getUnSupTransBufferSize() {
        return unSupTransBufferSize;
    }

    public void setUnSupTransBufferSize(int unSupTransBufferSize) {
        this.unSupTransBufferSize = unSupTransBufferSize;
    }

    public boolean isStorePreAuthorize() {
        return storePreAuthorize;
    }

    public void setStorePreAuthorize(boolean storePreAuthorize) {
        this.storePreAuthorize = storePreAuthorize;
    }

    public boolean isVolumeInTransBufferStatus() {
        return volumeInTransBufferStatus;
    }

    public void setVolumeInTransBufferStatus(boolean volumeInTransBufferStatus) {
        this.volumeInTransBufferStatus = volumeInTransBufferStatus;
    }

    public boolean isAuthorizeModeSelection() {
        return authorizeModeSelection;
    }

    public void setAuthorizeModeSelection(boolean authorizeModeSelection) {
        this.authorizeModeSelection = authorizeModeSelection;
    }

    public int getMaxNoOfConsecutiveZeroTrans() {
        return maxNoOfConsecutiveZeroTrans;
    }

    public void setMaxNoOfConsecutiveZeroTrans(int maxNoOfConsecutiveZeroTrans) {
        this.maxNoOfConsecutiveZeroTrans = maxNoOfConsecutiveZeroTrans;
    }

    public int getAutoClearTransDelayTime() {
        return autoClearTransDelayTime;
    }

    public void setAutoClearTransDelayTime(int autoClearTransDelayTime) {
        this.autoClearTransDelayTime = autoClearTransDelayTime;
    }

    public int getAutoUnlockTransDelayTime() {
        return autoUnlockTransDelayTime;
    }

    public void setAutoUnlockTransDelayTime(int autoUnlockTransDelayTime) {
        this.autoUnlockTransDelayTime = autoUnlockTransDelayTime;
    }

    public PumpLightMode getPumpLightMode() {
        return pumpLightMode;
    }

    public void setPumpLightMode(PumpLightMode pumpLightMode) {
        this.pumpLightMode = pumpLightMode;
    }

    public boolean isStopFuellingPointVehicleTag() {
        return stopFuellingPointVehicleTag;
    }

    public void setStopFuellingPointVehicleTag(boolean stopFuellingPointVehicleTag) {
        this.stopFuellingPointVehicleTag = stopFuellingPointVehicleTag;
    }

    public boolean isUseVehicleTagReadingButton() {
        return useVehicleTagReadingButton;
    }

    public void setUseVehicleTagReadingButton(boolean useVehicleTagReadingButton) {
        this.useVehicleTagReadingButton = useVehicleTagReadingButton;
    }
}
