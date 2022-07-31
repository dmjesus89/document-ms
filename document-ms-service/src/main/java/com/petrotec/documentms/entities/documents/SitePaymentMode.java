package com.petrotec.documentms.entities.documents;

import com.petrotec.documentms.entities.Site;

import javax.persistence.*;

@Entity
@Table(name = "site_payment_mode")
public class SitePaymentMode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "site_id", referencedColumnName = "id")
	private Site site;

	@ManyToOne
	@JoinColumn(name = "payment_mode_id", referencedColumnName = "id")
	private PaymentMode paymentMode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}
}
