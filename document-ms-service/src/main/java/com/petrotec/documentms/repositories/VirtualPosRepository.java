package com.petrotec.documentms.repositories;

import com.petrotec.documentms.entities.VirtualPos;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public abstract class VirtualPosRepository implements JpaRepository<VirtualPos, Long> {

    private final EntityManager entityManager;

    public VirtualPosRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public abstract Optional<VirtualPos> findByCode(@NotBlank String code);

    public abstract Optional<VirtualPos> findByNumberAndSiteDevicePosSiteCode(Integer number, String siteCode);


}
