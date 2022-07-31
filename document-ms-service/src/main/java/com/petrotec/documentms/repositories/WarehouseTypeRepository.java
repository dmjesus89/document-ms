package com.petrotec.documentms.repositories;

import com.petrotec.documentms.entities.WarehouseType;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import java.util.Optional;

@Repository
public abstract class WarehouseTypeRepository implements JpaRepository<WarehouseType, Long> {

    public abstract Page<WarehouseType> findByCode(String code, Pageable pageable);
    public abstract Optional<WarehouseType> findByCode(String code);
}
