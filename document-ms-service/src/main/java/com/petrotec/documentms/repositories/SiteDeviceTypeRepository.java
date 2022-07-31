package com.petrotec.documentms.repositories;

import com.petrotec.documentms.entities.SiteDeviceType;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public abstract class SiteDeviceTypeRepository implements JpaRepository<SiteDeviceType, Long> {
    public abstract Optional<SiteDeviceType> findByCode(@NotBlank String code);
}
