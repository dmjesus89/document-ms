package com.petrotec.documentms.repositories.configuration;

import com.petrotec.documentms.entities.FuellingMode;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface FuellingModeRepository extends CrudRepository<FuellingMode, Long> {

    Optional<FuellingMode> findByCode(String code);

    Optional<FuellingMode> findByCodeAndEnabled(String code, boolean enabled);
}
