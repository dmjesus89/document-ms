package com.petrotec.documentms.repositories;

import com.petrotec.documentms.entities.SiteDeviceSubtype;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class SiteDeviceSubtypeRepository implements JpaRepository<SiteDeviceSubtype, Long> {

    public abstract Optional<SiteDeviceSubtype> findByCode(@NotBlank String code);
    public abstract List<SiteDeviceSubtype> findBySiteDeviceTypeCode(String siteDeviceTypeCode);

    public abstract Optional<SiteDeviceSubtype> findByCodeAndSiteDeviceTypeCode(@NotBlank String code, @NotBlank String siteDeviceTypeCode);

}
