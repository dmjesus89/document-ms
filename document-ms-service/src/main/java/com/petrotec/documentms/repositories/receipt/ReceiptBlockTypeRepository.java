package com.petrotec.documentms.repositories.receipt;

import com.petrotec.documentms.entities.receipt.ReceiptBlockType;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptBlockTypeRepository extends JpaRepository<ReceiptBlockType, Long> {

    @Query(value = " select e.* from receipt_block_type e  where e.receipt_template_type_id  = :code ", nativeQuery = true)
    List<ReceiptBlockType> findByReceiptTemplateTypeCode(String code);

    Optional<ReceiptBlockType> findByCode(String receiptBlockTypeCode);
}
