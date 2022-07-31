package com.petrotec.documentms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "notifiable_site_group_item")
public class NotifiableSiteGroupItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "notifiable_code")
	private String notifiableCode;

	@Column(name = "site_group_code")
	private String siteGroupCode;

	@Column(name = "site_group_item_code")
	private String siteGroupItemCode;

	@Column(name = "created_on")
	@CreationTimestamp
	private LocalDateTime createdOn;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNotifiableCode() {
		return this.notifiableCode;
	}

	public void setNotifiableCode(String notifiableCode) {
		this.notifiableCode = notifiableCode;
	}

	public String getSiteGroupCode() {
		return this.siteGroupCode;
	}

	public void setSiteGroupCode(String siteGroupCode) {
		this.siteGroupCode = siteGroupCode;
	}

	public String getSiteGroupItemCode() {
		return this.siteGroupItemCode;
	}

	public void setSiteGroupItemCode(String siteGroupItemCode) {
		this.siteGroupItemCode = siteGroupItemCode;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		// @formatter:off
		return "{" +
			" id='" + getId() + "'" +
			", notifiableCode='" + getNotifiableCode() + "'" +
			", siteGroupCode='" + getSiteGroupCode() + "'" +
			", siteGroupItemCode='" + getSiteGroupItemCode() + "'" +
			", createdOn='" + getCreatedOn() + "'" +
			"}";
		// @formatter:on
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof NotifiableSiteGroupItem)) {
			return false;
		}
		NotifiableSiteGroupItem notifiableSiteGroupItem = (NotifiableSiteGroupItem) o;
		return id == notifiableSiteGroupItem.id
				&& Objects.equals(notifiableCode, notifiableSiteGroupItem.notifiableCode)
				&& Objects.equals(siteGroupCode, notifiableSiteGroupItem.siteGroupCode)
				&& Objects.equals(siteGroupItemCode, notifiableSiteGroupItem.siteGroupItemCode)
				&& Objects.equals(createdOn, notifiableSiteGroupItem.createdOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, notifiableCode, siteGroupCode, siteGroupItemCode, createdOn);
	}

}
