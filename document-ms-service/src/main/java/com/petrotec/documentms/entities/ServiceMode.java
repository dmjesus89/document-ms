package com.petrotec.documentms.entities;

import com.petrotec.documentms.enums.PumpLightMode;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_mode")
@Schema(description = "Entity Config of pump", accessMode = Schema.AccessMode.READ_ONLY)
public class ServiceMode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@Schema(description = "id", required = true, accessMode = Schema.AccessMode.WRITE_ONLY)
	private long id;

	@Column(name = "code")
	@Schema(description = "Unique service mode code", maxLength = 45, accessMode = Schema.AccessMode.READ_ONLY)
	private String code;

	@Column(name = "enabled")
	@Schema(description = "Checks if the current service mode is enabled", defaultValue = "true")
	private boolean enabled;

	@NotBlank
	@Column(name = "description")
	@Schema(description = "User friendly description", maxLength = 50)
	private String description;

	@Column(name = "auto_authorize_limit")
	@Schema(description = "Auto authorize limit", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0,  maxLength = 3)
	private int autoAuthorizeLimit;

	@Column(name = "max_pre_authorize_time", length = 9999)
	@Schema(description = "Max pre authorize time (seconds)", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 9999)
	private int maxPreAuthorizeTime;

	@Column(name = "max_nozzle_down_time", length = 99)
	@Schema(description = "Max nozzle lay down time (seconds)", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 9999)
	private int maxNozzleDownTime;

	@Column(name = "zero_trans_to_pos", length = 1)
	@Schema(description = "Zero trans to pos", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false")
	private boolean zeroTransToPos;

	@Column(name = "money_trans_buffer_status", length = 1)
	@Schema(description = "Money due in trans buffer status", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false")
	private boolean moneyInTransBufferStatus;

	@Column(name = "min_trans_volume", length = 10)
	@Schema(description = "Min trans volume", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 10)
	private BigDecimal minTransVolume;

	@Column(name = "min_trans_amount", length = 50)
	@Schema(description = "Min trans amount", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 50)
	private BigDecimal minTransAmount;

	@Column(name = "sup_trans_buffer_size", length = 99)
	@Schema(description = "Sup Trans Buffer Size", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 99)
	private int supTransBufferSize;

	@Column(name = "unsup_trans_buffer_size", length = 99)
	@Schema(description = "Sup Trans Buffer Size", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 99)
	private int unSupTransBufferSize;

	@Column(name = "store_pre_authorize", length = 1)
	@Schema(description = "Store at pre authorize", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false")
	private boolean storePreAuthorize;

	@Column(name = "volume_trans_buffer_status", length = 1)
	@Schema(description = "Volume in trans buffer status", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false")
	private boolean volumeInTransBufferStatus;

	@Column(name = "authorize_mode_selection", length = 1)
	@Schema(description = "Authorize mode selection", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false")
	private boolean authorizeModeSelection;

	@Column(name = "max_no_consecutive_zero_trans", length = 99)
	@Schema(description = "Max no of consecutive zero trans", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 99)
	private int maxNoOfConsecutiveZeroTrans;

	@Column(name = "auto_clear_trans_delay_time", length = 9999)
	@Schema(description = "Auto clear trans delay time (Seconds)", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 9999)
	private int autoClearTransDelayTime;

	@Column(name = "auto_unlock_trans_delay_time", length = 9999)
	@Schema(description = "Auto unlock trans delay time (Seconds)", accessMode = Schema.AccessMode.READ_WRITE, minLength = 0, maxLength = 9999)
	private int autoUnlockTransDelayTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "pump_light_mode")
	@Schema(description = "Pump light mode", accessMode = Schema.AccessMode.READ_WRITE, discriminatorProperty = "INACTIVE, ON_AT_AUTHORIZE, BLINK_AT_AUTHORIZE, ON_AT_RESERVE, BLINK_ON_RESERVE")
	private PumpLightMode pumpLightMode;

	@Column(name = "stop_fuellingpoint_vehicle_tag", length = 1)
	@Schema(description = "Stop fuelling point on vehicle tag", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false")
	private boolean stopFuellingPointVehicleTag;

	@Column(name = "use_vehicle_tag_reading_button", length = 1)
	@Schema(description = "Use vehicle tag reading button", accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false")
	private boolean useVehicleTagReadingButton;

	@Column(name = "created_on")
	@CreationTimestamp
	private LocalDateTime createdOn;

	@Column(name = "updated_on")
	@UpdateTimestamp
	private LocalDateTime updatedOn;


	public ServiceMode() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
