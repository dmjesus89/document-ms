package com.petrotec.documentms.repositories.receipt;

import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteReceiptTemplate;
import com.petrotec.documentms.entities.receipt.ReceiptTemplateEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface SiteReceiptTemplateRepository extends JpaRepository<SiteReceiptTemplate, Long> {

    List<SiteReceiptTemplate> findBySite(Site site);

    List<SiteReceiptTemplate> findBySiteAndReceiptTemplate(Site site, ReceiptTemplateEntity receiptTemplateEntity);
}
