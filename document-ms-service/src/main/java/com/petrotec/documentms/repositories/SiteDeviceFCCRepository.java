package com.petrotec.documentms.repositories;

import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteDeviceFCC;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class SiteDeviceFCCRepository implements JpaRepository<SiteDeviceFCC, Long> {

	public abstract Optional<SiteDeviceFCC> findByCode(String code);

	public abstract Page<SiteDeviceFCC> findByDeviceTypeCode(String fcc, Pageable from);

	public abstract Page<SiteDeviceFCC> findBySiteCodeAndDeviceTypeCode(String siteCode, String fcc, Pageable from);

    public abstract List<SiteDeviceFCC> findBySite(Site site);
}
