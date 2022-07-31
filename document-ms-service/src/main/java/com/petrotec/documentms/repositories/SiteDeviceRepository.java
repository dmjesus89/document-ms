package com.petrotec.documentms.repositories;

import com.petrotec.documentms.entities.SiteDevice;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class SiteDeviceRepository implements JpaRepository<SiteDevice, Long> {

    private final EntityManager entityManager;


    public SiteDeviceRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    public abstract Optional<SiteDevice> findByCode(@NotBlank String code);

    public abstract Page<SiteDevice> findBySiteCode(String siteCode, Pageable pageable);

    public abstract List<SiteDevice> findBySiteCode(String siteCode);

    public abstract Page<SiteDevice> findByCode(String code, Pageable pageable);

    public abstract Page<SiteDevice> findBySiteCodeAndDeviceTypeCode(String sideCode, String deviceTypeCode, Pageable pageable);

}
