package com.petrotec.documentms.repositories.receipt;

import com.petrotec.documentms.entities.receipt.ReceiptLine;
import com.petrotec.documentms.entities.receipt.ReceiptTemplateEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ReceiptLineRepository extends JpaRepository<ReceiptLine, Long> {

    List<ReceiptLine> findByReceiptTemplateEntity(ReceiptTemplateEntity receiptTemplateEntity);
}
