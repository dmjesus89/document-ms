package com.petrotec.documentms.repositories;

import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.documents.SitePaymentMode;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface SitePaymentModeRepository extends JpaRepository<SitePaymentMode, Long> {

    List<SitePaymentMode> findBySite(Site site);
}
