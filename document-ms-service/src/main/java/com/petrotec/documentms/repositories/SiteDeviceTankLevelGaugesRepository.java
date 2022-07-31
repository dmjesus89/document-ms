package com.petrotec.documentms.repositories;

import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteDeviceTankLevelGauges;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteDeviceTankLevelGaugesRepository extends JpaRepository<SiteDeviceTankLevelGauges, Long> {

	Optional<SiteDeviceTankLevelGauges> findByCode(String code);

	Page<SiteDeviceTankLevelGauges> findBySiteCode(String siteCode, Pageable pageable);

	List<SiteDeviceTankLevelGauges> findBySite(Site site);

	Optional<SiteDeviceTankLevelGauges> findByTlgCode(Short tlgCode);

	Optional<SiteDeviceTankLevelGauges> findByTlgCodeAndSiteCode(Short tlgCode, String siteCode);

}