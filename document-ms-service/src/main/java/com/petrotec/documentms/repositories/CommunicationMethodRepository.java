package com.petrotec.documentms.repositories;

import com.petrotec.documentms.entities.CommunicationMethod;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public abstract class CommunicationMethodRepository implements JpaRepository<CommunicationMethod, Long> {

    public abstract Optional<CommunicationMethod> findByCode(@NotBlank String code);

}
