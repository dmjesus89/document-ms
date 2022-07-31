package com.petrotec.documentms.repositories.receipt;

import com.petrotec.documentms.entities.receipt.ReceiptTemplateEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface ReceiptTemplateRepository extends JpaRepository<ReceiptTemplateEntity, Long> {

    Optional<ReceiptTemplateEntity> findByCode(String templateCode);
}
