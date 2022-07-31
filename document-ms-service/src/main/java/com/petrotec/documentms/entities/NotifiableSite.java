package com.petrotec.documentms.entities;

import javax.persistence.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "notifiable_site")
public class NotifiableSite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "notifiable_code")
	private String notifiableCode;

	@Column(name = "site_code")
	private String siteCode;

	@Column(name = "created_on")
	@CreationTimestamp
	private LocalDateTime createdOn;

	public NotifiableSite() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNotifiableCode() {
		return notifiableCode;
	}

	public void setNotifiableCode(String notifiableCode) {
		this.notifiableCode = notifiableCode;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

}
