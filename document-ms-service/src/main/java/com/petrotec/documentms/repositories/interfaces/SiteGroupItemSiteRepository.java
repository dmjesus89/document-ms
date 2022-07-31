package com.petrotec.documentms.repositories.interfaces;

import com.petrotec.documentms.entities.SiteGroupItemSite;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Repository
@Singleton
public interface SiteGroupItemSiteRepository extends JpaRepository<SiteGroupItemSite, Integer> {

    @Query("SELECT sgis FROM SiteGroupItemSite sgis INNER JOIN sgis.siteGroupItem sgi INNER JOIN sgi.siteGroup sg INNER JOIN sgis.site s WHERE sg.code = :groupCode AND sgi.code = :itemCode AND s.code= :siteCode")
    Optional<SiteGroupItemSite> findBy(String groupCode, String itemCode, String siteCode);

    @Query("SELECT sgis FROM SiteGroupItemSite sgis INNER JOIN sgis.siteGroupItem sgi INNER JOIN sgi.siteGroup sg INNER JOIN sgis.site s WHERE sg.code = :groupCode AND sgi.code = :itemCode")
    List<SiteGroupItemSite> findAllBy(String groupCode, String itemCode);

    @Query("SELECT sgis FROM SiteGroupItemSite sgis INNER JOIN sgis.siteGroupItem sgi INNER JOIN sgi.siteGroup sg INNER JOIN sgis.site s WHERE sg.code = :groupCode AND sgi.code = :itemCode AND s.enabled = true")
    List<SiteGroupItemSite> findAllWithSiteEnabledBy(String groupCode, String itemCode);
}
