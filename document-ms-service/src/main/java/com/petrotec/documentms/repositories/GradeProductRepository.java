package com.petrotec.documentms.repositories;

import com.petrotec.documentms.entities.GradeProduct;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public abstract class GradeProductRepository implements JpaRepository<GradeProduct, Long> {
    public abstract Optional<GradeProduct> findByGradeCodeAndProductCode(String gradeCode, String productCode);
}
