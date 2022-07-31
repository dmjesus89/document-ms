package com.petrotec.documentms.repositories.receipt;

import com.petrotec.documentms.entities.receipt.ReceiptTemplateType;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface ReceiptTemplateTypeRepository extends JpaRepository<ReceiptTemplateType, Long> {

    Optional<ReceiptTemplateType> findByCode(String receiptTemplateTypeCode);
}
