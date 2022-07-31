package com.petrotec.documentms.repositories.documents;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.multitenant.repository.MultitenantLazyRepository;
import com.petrotec.multitenant.resolver.MultitenantResolver;
import com.petrotec.documentms.entities.documents.DocumentType;
import io.micronaut.context.BeanLocator;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class DocumentTypeRepository extends MultitenantLazyRepository implements PageableRepository<DocumentType, Integer> {

    private static final String DEFAULT_QUERY = "SELECT dt.* FROM document_type as dt";

    public DocumentTypeRepository(BeanLocator beanLocator, MultitenantResolver multitenantResolver) {
        super(beanLocator, multitenantResolver);
    }

    public List<DocumentType> findAll(@PositiveOrZero int offset, @PositiveOrZero int limit, @NotNull List<Sort> sort,
                                      @Nullable Filter filters) {
        return super.fetchResult(DEFAULT_QUERY, filters, offset, limit, sort, DocumentType.class);
    }

    public Long count(Filter filters) {
        return super.countResult(DEFAULT_QUERY, filters);
    }

    public abstract Optional<DocumentType> findByCode(@NotBlank String code);

}
