package com.petrotec.documentms.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "notifiable_element")
public class NotifiableElement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "notifiable_id", referencedColumnName = "id")
	private Notifiable notifiable;

	@Column(name = "name")
	private String name;

	@Column(name = "description", nullable = false)
    private String description;

	@Column(name = "locale_code")
	private String localeCode;

	@OneToMany(mappedBy = "notifiableElement", cascade = CascadeType.ALL)
	private List<NotifiableElementContact> notifiableElementContacts;

	@Column(name = "created_on")
	@CreationTimestamp
	private LocalDateTime createdOn;

	@Column(name = "updated_on")
	@UpdateTimestamp
	private LocalDateTime updatedOn;

	public NotifiableElement() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Notifiable getNotifiable() {
		return notifiable;
	}

	public void setNotifiable(Notifiable notifiable) {
		this.notifiable = notifiable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public List<NotifiableElementContact> getNotifiableElementContacts() {
		return notifiableElementContacts;
	}

	public void setNotifiableElementContacts(List<NotifiableElementContact> notifiableElementContacts) {
		this.notifiableElementContacts = notifiableElementContacts;
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
	
	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof NotifiableElement)) {
			return false;
		}
		NotifiableElement notifiableElement = (NotifiableElement) o;

		return id == notifiableElement.id
				&& Objects.equals(Optional.ofNullable(notifiable).map(Notifiable::getId).orElse(null),
						Optional.ofNullable(notifiableElement.notifiable).map(Notifiable::getId).orElse(null))
				&& Objects.equals(name, notifiableElement.name)
				&& Objects.equals(localeCode, notifiableElement.localeCode)
				&& Objects.equals(notifiableElementContacts, notifiableElement.notifiableElementContacts)
				&& Objects.equals(createdOn, notifiableElement.createdOn)
				&& Objects.equals(updatedOn, notifiableElement.updatedOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, Optional.ofNullable(notifiable).map(Notifiable::getId).orElse(null), name, localeCode,
				notifiableElementContacts, createdOn, updatedOn);
	}

	@Override
	public String toString() {
		// @formatter:off
		return "{" +
			" id='" + getId() + "'" +
			", name='" + getName() + "'" +
			", notifiableId='" + Optional.ofNullable(notifiable).map(Notifiable::getId).orElse(null) + "'" +
			", localeCode='" + getLocaleCode() + "'" +
			", notifiableElementContacts='" + getNotifiableElementContacts() + "'" +
			", createdOn='" + getCreatedOn() + "'" +
			", updatedOn='" + getUpdatedOn() + "'" +
			"}";
		// @formatter:on
	}

}
