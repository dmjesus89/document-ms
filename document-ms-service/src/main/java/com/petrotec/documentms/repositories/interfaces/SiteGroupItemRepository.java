package com.petrotec.documentms.repositories.interfaces;

import com.petrotec.documentms.entities.SiteGroup;
import com.petrotec.documentms.entities.SiteGroupItem;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Repository
@Singleton
public interface SiteGroupItemRepository extends JpaRepository<SiteGroupItem, Long> {

	Optional<SiteGroupItem> findByCodeAndSiteGroup(String code, SiteGroup siteGroup);

	@Query("Select sgi FROM SiteGroupItem sgi INNER JOIN sgi.siteGroup sg WHERE sg.code = :siteGroup AND sgi.code = :code")
	Optional<SiteGroupItem> findByCodeAndSiteGroup(String code, String siteGroup);

	@Query("Select sgi FROM SiteGroupItem sgi INNER JOIN sgi.siteGroup sg WHERE sg.code = :siteGroup AND sgi.code = :code AND sg.enabled = true")
	Optional<SiteGroupItem> findByCodeAndSiteGroupEnabled(String code, String siteGroup);

	List<SiteGroupItem> findBySiteGroupId(long siteGroupId);

	@Query("select sgi from SiteGroupItem sgi inner join sgi.siteGroup sg where sg.code = :siteGroupCode")
	List<SiteGroupItem> findBySiteGroupCode(long siteGroupCode);

	List<SiteGroupItem> findBySiteGroupIdAndParentIsNull(long siteGroupId);

	@Query("select sgi from SiteGroupItem sgi inner join sgi.siteGroup sg where sg.code = :siteGroupCode AND entity_rank_order >= :rankOrder")
	List<SiteGroupItem> findBySiteGroupCodeAndRankOrderBiggerThan(String siteGroupCode, int rankOrder);

	@Query("select sgi from SiteGroupItem sgi where sgi.code IN :siteGroupItemCodes")
	List<SiteGroupItem> findByCodes(List<String> siteGroupItemCodes);
}
