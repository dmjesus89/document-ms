package com.petrotec.documentms.repositories.receipt;

import com.petrotec.documentms.entities.receipt.ReceiptLayout;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface ReceiptLayoutRepository extends JpaRepository<ReceiptLayout, Long> {

    Optional<ReceiptLayout> findByCode(String code);

}
