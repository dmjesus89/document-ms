package com.petrotec.documentms.repositories.documents;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.multitenant.repository.MultitenantLazyRepository;
import com.petrotec.multitenant.resolver.MultitenantResolver;
import com.petrotec.service.repositories.CodeGenerator;
import com.petrotec.documentms.entities.documents.Document;
import io.micronaut.context.BeanLocator;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

@Repository
@Singleton
public abstract class DocumentRepository extends MultitenantLazyRepository implements PageableRepository<Document,Long>, CodeGenerator<String> {

    private static final String DEFAULT_QUERY = "SELECT d.* FROM document as d";

    public DocumentRepository(BeanLocator beanLocator, MultitenantResolver multitenantResolver) {
        super(beanLocator, multitenantResolver);
    }

    @Override
    public String getTableName() {
        return "document";
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    public List<Document> findAll(@PositiveOrZero int offset, @PositiveOrZero int limit, @NotNull List<Sort> sort,
                                  @Nullable Filter filters) {
        return super.fetchResult(DEFAULT_QUERY, filters, offset, limit, sort, Document.class);
    }

    public Long count(Filter filters) {
        return super.countResult(DEFAULT_QUERY, filters);
    }

    public abstract Optional<Document> findByCode(@NotBlank String code);

    public abstract Optional<Document> findByCodeAndEntityRankOrder(@NotBlank String code, @NotBlank Short entityRankOrder);
}
