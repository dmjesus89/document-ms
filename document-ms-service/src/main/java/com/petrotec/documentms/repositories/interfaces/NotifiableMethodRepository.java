package com.petrotec.documentms.repositories.interfaces;

import com.petrotec.documentms.entities.NotifiableMethod;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotifiableMethodRepository extends JpaRepository<NotifiableMethod, Byte> {
	Optional<NotifiableMethod> findByCode(String code);

	Optional<NotifiableMethod> findByCodeAndEnabledTrue(String code);

	List<NotifiableMethod> findAllByEnabledTrue();
}
