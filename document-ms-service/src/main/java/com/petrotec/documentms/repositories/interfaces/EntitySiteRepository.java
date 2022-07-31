package com.petrotec.documentms.repositories.interfaces;

import com.petrotec.documentms.entities.EntitySite;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * EntityRepository
 */

@Repository
public interface EntitySiteRepository extends JpaRepository<EntitySite, Integer> {

    Collection<EntitySite> findAllByEntityCode(String entityCode);
}
