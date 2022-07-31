package com.petrotec.documentms.dtos;

import java.time.LocalDateTime;

public class NotifiableElementContactDTO {

	private String notifiableMethodCode;
	private String alarmSeverityCode;
	private String contact;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;

	public NotifiableElementContactDTO() {
	}

	public String getNotifiableMethodCode() {
		return notifiableMethodCode;
	}

	public void setNotifiableMethodCode(String notifiableMethodCode) {
		this.notifiableMethodCode = notifiableMethodCode;
	}

	public String getAlarmSeverityCode() {
		return alarmSeverityCode;
	}

	public void setAlarmSeverityCode(String alarmSeverityCode) {
		this.alarmSeverityCode = alarmSeverityCode;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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
