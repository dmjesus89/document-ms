package com.petrotec.documentms.repositories.interfaces;

import java.util.Optional;

import com.petrotec.documentms.entities.NotifiableElement;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface NotifiableElementRepository extends JpaRepository<NotifiableElement, Long> {

	@Query(value = "select ne.* from notifiable_element as ne join notifiable as n on (n.id = ne.notifiable_id) where ne.name = :notifiableElementName and n.code = :notifiableCode", nativeQuery = true)
	Optional<NotifiableElement> findByNameAndNotifiableCode( String notifiableElementName, String notifiableCode);

}
