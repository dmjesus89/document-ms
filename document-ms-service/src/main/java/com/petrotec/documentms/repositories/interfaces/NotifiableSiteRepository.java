package com.petrotec.documentms.repositories.interfaces;

import com.petrotec.documentms.entities.NotifiableSite;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotifiableSiteRepository extends JpaRepository<NotifiableSite, Long> {
	Optional<NotifiableSite> findByNotifiableCodeAndSiteCode(String notifiableCode, String siteCode);
	List<NotifiableSite> findByNotifiableCodeAndSiteCodeIn(String notifiableCode, List<String> siteCodes);
	List<NotifiableSite> findByNotifiableCode(String notifiableCode);
}
