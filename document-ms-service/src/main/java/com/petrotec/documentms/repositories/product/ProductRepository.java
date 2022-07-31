package com.petrotec.documentms.repositories.product;

import com.petrotec.documentms.entities.product.Product;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Returns whether an entity with the given code exists.
     *
     * @param code must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws javax.validation.ConstraintViolationException if the code is {@literal null}.
     */
    boolean existsByCode(@NotNull @NonNull String code);

    Optional<Product> findByCode(String code);
}
