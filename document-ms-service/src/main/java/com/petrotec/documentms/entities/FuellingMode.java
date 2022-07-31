package com.petrotec.documentms.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "fuelling_mode")
public class FuellingMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Schema(description = "id", required = true, accessMode = Schema.AccessMode.WRITE_ONLY)
    private long id;

    @Column(name = "code")
    private String code;

    @Column(name = "is_enabled")
    private boolean enabled;

    @Column(name = "description")
    private String description;

    @Column(name = "max_time_to_reach_min_limit")
    private Integer maxTimeToReachMinLimit;

    @Column(name = "max_time_without_progress")
    private Integer maxTimeWithoutProgress;

    @Column(name = "max_fuelling_time")
    private Integer maxFuellingTime;

    @Column(name = "max_transaction_volume")
    private Integer maxTransactionVolume;

    @Column(name = "max_transaction_amount")
    private Integer maxTransactionAmount;

    @Column(name = "max_volume_overrun")
    private Integer maxVolumeOverrun;

    @Column(name = "clear_display_time")
    private Integer clearDisplayTime;

    @Column(name = "clear_display_when_transaction_is_cleared")
    private boolean clearDisplayWhenTransactionIsCleared;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

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
