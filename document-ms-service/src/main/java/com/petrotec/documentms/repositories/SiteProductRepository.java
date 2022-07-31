package com.petrotec.documentms.repositories;

import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteProduct;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class SiteProductRepository implements JpaRepository<SiteProduct, Integer> {

    public abstract Optional<SiteProduct> findByProductCodeAndSiteCode(String productCode, String siteCode);

    public abstract List<SiteProduct> findBySite(Site site);
}
