package com.petrotec.documentms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "notifiable_element_contact")
public class NotifiableElementContact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notifiable_element_id", referencedColumnName = "id")
	private NotifiableElement notifiableElement;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notifiable_method_id", referencedColumnName = "id")
	private NotifiableMethod notifiableMethod;

	@Column(name = "alarm_severity_code")
	private String alarmSeverityCode;

	@Column(name = "contact")
	private String contact;

	@Column(name = "created_on")
	@CreationTimestamp
	private LocalDateTime createdOn;

	@Column(name = "updated_on")
	@UpdateTimestamp
	private LocalDateTime updatedOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public NotifiableElement getNotifiableElement() {
		return notifiableElement;
	}

	public void setNotifiableElement(NotifiableElement notifiableElement) {
		this.notifiableElement = notifiableElement;
	}

	public NotifiableMethod getNotifiableMethod() {
		return notifiableMethod;
	}

	public void setNotifiableMethod(NotifiableMethod notifiableMethod) {
		this.notifiableMethod = notifiableMethod;
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

	public String localeCode(){
		return notifiableElement.getLocaleCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof NotifiableElementContact)) {
			return false;
		}
		NotifiableElementContact notifiableElementContact = (NotifiableElementContact) o;
		return id == notifiableElementContact.id
				&& Objects.equals(Optional.ofNullable(notifiableElement).map(NotifiableElement::getId).orElse(null),
						Optional.ofNullable(notifiableElementContact.notifiableElement).map(NotifiableElement::getId)
								.orElse(null))
				&& Objects.equals(notifiableMethod, notifiableElementContact.notifiableMethod)
				&& Objects.equals(alarmSeverityCode, notifiableElementContact.alarmSeverityCode)
				&& Objects.equals(contact, notifiableElementContact.contact)
				&& Objects.equals(createdOn, notifiableElementContact.createdOn)
				&& Objects.equals(updatedOn, notifiableElementContact.updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, Optional.ofNullable(notifiableElement).map(NotifiableElement::getId).orElse(null),
				notifiableMethod, alarmSeverityCode, contact, createdOn, updatedOn);
	}

	@Override
	public String toString() {
		// @formatter:off
		return "{" +
			" id='" + getId() + "'" +
			", notifiableElement='" + Optional.ofNullable(notifiableElement).map(NotifiableElement::getId).orElse(null) + "'" +
			", alarmSeverityCode='" + getAlarmSeverityCode() + "'" +
			", contact='" + getContact() + "'" +
			", createdOn='" + getCreatedOn() + "'" +
			", updatedOn='" + getUpdatedOn() + "'" +
			"}";
		// @formatter:on
	}

}
