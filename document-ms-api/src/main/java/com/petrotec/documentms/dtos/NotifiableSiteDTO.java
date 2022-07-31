package com.petrotec.documentms.dtos;

import java.util.Objects;

public class NotifiableSiteDTO {

	private String notifiableCode;
	private String siteCode;
	private String createdOn;

	public String getNotifiableCode() {
		return this.notifiableCode;
	}

	public void setNotifiableCode(String notifiableCode) {
		this.notifiableCode = notifiableCode;
	}

	public String getSiteCode() {
		return this.siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof NotifiableSiteDTO)) {
			return false;
		}
		NotifiableSiteDTO notifiableSiteDTO = (NotifiableSiteDTO) o;
		return Objects.equals(notifiableCode, notifiableSiteDTO.notifiableCode)
				&& Objects.equals(siteCode, notifiableSiteDTO.siteCode)
				&& Objects.equals(createdOn, notifiableSiteDTO.createdOn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(notifiableCode, siteCode, createdOn);
	}

	@Override
	public String toString() {
		// @formatter:off
		return "{" +
			" notifiableCode='" + getNotifiableCode() + "'" +
			", siteCode='" + getSiteCode() + "'" +
			", createdOn='" + getCreatedOn() + "'" +
			"}";
				// @formatter:on
	}

}
