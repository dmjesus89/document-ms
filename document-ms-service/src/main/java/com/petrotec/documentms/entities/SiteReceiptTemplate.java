package com.petrotec.documentms.entities;

import com.petrotec.documentms.entities.receipt.ReceiptTemplateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "site_receipt_template")
public class SiteReceiptTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    private Site site;

    @ManyToOne
    @JoinColumn(name = "receipt_template_id", referencedColumnName = "id")
    private ReceiptTemplateEntity receiptTemplate;

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

    public ReceiptTemplateEntity getReceiptTemplate() {
        return receiptTemplate;
    }

    public void setReceiptTemplate(ReceiptTemplateEntity receiptTemplate) {
        this.receiptTemplate = receiptTemplate;
    }
}
