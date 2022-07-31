package com.petrotec.documentms.dtos;

public class NotifiableSiteGroupItemDTO {

	private String notifiableCode;
	private String siteGroupItemCode;

	public NotifiableSiteGroupItemDTO() {
	}

	public NotifiableSiteGroupItemDTO(String notifiableCode, String siteGroupItemCode) {
		this.notifiableCode = notifiableCode;
		this.siteGroupItemCode = siteGroupItemCode;
	}

	public String getNotifiableCode() {
		return notifiableCode;
	}

	public void setNotifiableCode(String notifiableCode) {
		this.notifiableCode = notifiableCode;
	}

	public String getSiteGroupItemCode() {
		return siteGroupItemCode;
	}

	public void setSiteGroupItemCode(String siteGroupItemCode) {
		this.siteGroupItemCode = siteGroupItemCode;
	}
}
