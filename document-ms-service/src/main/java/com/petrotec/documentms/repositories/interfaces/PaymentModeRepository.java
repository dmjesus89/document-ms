package com.petrotec.documentms.repositories.interfaces;

import com.petrotec.documentms.entities.documents.PaymentMode;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface PaymentModeRepository extends JpaRepository<PaymentMode, Integer> {

	Optional<PaymentMode> findByCode(String code);

    @Query(value = " SELECT * FROM payment_mode p WHERE p.payment_mode_ref_id = :id", nativeQuery = true)
    Optional<PaymentMode> findByPaymentModeReference(Integer id);
}
