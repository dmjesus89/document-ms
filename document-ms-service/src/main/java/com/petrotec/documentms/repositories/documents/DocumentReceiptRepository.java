package com.petrotec.documentms.repositories.documents;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.multitenant.repository.MultitenantLazyRepository;
import com.petrotec.multitenant.resolver.MultitenantResolver;
import com.petrotec.documentms.entities.documents.DocumentReceipt;
import io.micronaut.context.BeanLocator;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

@Repository
@Singleton
public abstract class DocumentReceiptRepository extends MultitenantLazyRepository implements PageableRepository<DocumentReceipt, Long> {

    private static final String DEFAULT_QUERY = "SELECT dr.*, d.code as `document.code` FROM document_receipt as dr INNER JOIN document as d ON (d.id = dr.document_id)";

    public DocumentReceiptRepository(BeanLocator beanLocator, MultitenantResolver multitenantResolver) {
        super(beanLocator, multitenantResolver);
    }

    public List<DocumentReceipt> findAll(@PositiveOrZero int offset, @PositiveOrZero int limit, @NotNull List<Sort> sort,
                                  @Nullable Filter filters) {
        return super.fetchResult(DEFAULT_QUERY, filters, offset, limit, sort, DocumentReceipt.class);
    }

    public Long count(Filter filters) {
        return super.countResult(DEFAULT_QUERY, filters);
    }


    @Query(value = "SELECT dr.* FROM document_receipt as dr INNER JOIN document as d ON (d.id = dr.document_id) WHERE document.code = :documentCode", nativeQuery = true)
    public abstract Optional<DocumentReceipt> findReceiptByDocumentCode(String documentCode);


}
