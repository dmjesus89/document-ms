package com.petrotec.documentms.repositories.interfaces;

import com.petrotec.documentms.entities.ServiceMode;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface ServiceModeRepository extends CrudRepository<ServiceMode, Long> {

	Optional<ServiceMode> findByCode(String code);
}
