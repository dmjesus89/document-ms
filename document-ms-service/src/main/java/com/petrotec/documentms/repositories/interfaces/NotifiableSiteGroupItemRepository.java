package com.petrotec.documentms.repositories.interfaces;

import com.petrotec.documentms.entities.NotifiableSiteGroupItem;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotifiableSiteGroupItemRepository extends JpaRepository<NotifiableSiteGroupItem, Long> {

	Optional<NotifiableSiteGroupItem> findByNotifiableCodeAndSiteGroupCodeAndSiteGroupItemCode(String notifiableCode,
			String siteGroupCode, String siteGroupItemCode);

	@Query("select nsgi from NotifiableSiteGroupItem nsgi join SiteGroup sg on (sg.code = nsgi.siteGroupCode) where nsgi.notifiableCode = :notifiableCode and nsgi.siteGroupCode = :siteGroupCode and nsgi.siteGroupItemCode = :siteGroupItemCode and sg.enabled = true")
	Optional<NotifiableSiteGroupItem> findByNotifiableCodeAndSiteGroupCodeEnabledAndSiteGroupItemCode(String notifiableCode,
			String siteGroupCode, String siteGroupItemCode);

	List<NotifiableSiteGroupItem> findByNotifiableCodeAndSiteGroupCode(String notifiableCode,
			String siteGroupCode);

	@Query("select nsgi from NotifiableSiteGroupItem nsgi join SiteGroup sg on (sg.code = nsgi.siteGroupCode) where nsgi.notifiableCode = :notifiableCode and nsgi.siteGroupCode = :siteGroupCode and sg.enabled = true")
	List<NotifiableSiteGroupItem> findByNotifiableCodeAndSiteGroupCodeEnabled(String notifiableCode,
			String siteGroupCode);

	@Query("select nsgi from NotifiableSiteGroupItem nsgi join SiteGroup sg on (sg.code = nsgi.siteGroupCode) where nsgi.notifiableCode = :notifiableCode")
	List<NotifiableSiteGroupItem> findByNotifiableCodeWithSiteGroupCode(String notifiableCode);

	@Query("select nsgi from NotifiableSiteGroupItem nsgi join SiteGroup sg on (sg.code = nsgi.siteGroupCode) where nsgi.notifiableCode = :notifiableCode and sg.enabled = true")
	List<NotifiableSiteGroupItem> findByNotifiableCodeWithSiteGroupCodeEnabled(String notifiableCode);
}
