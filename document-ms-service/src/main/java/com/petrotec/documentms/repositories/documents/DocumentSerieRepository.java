package com.petrotec.documentms.repositories.documents;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.multitenant.repository.MultitenantLazyRepository;
import com.petrotec.multitenant.resolver.MultitenantResolver;
import com.petrotec.documentms.entities.documents.DocumentSerie;
import io.micronaut.context.BeanLocator;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

@Repository
@Singleton
public abstract class DocumentSerieRepository extends MultitenantLazyRepository implements PageableRepository<DocumentSerie, Integer> {

    private static final String DEFAULT_QUERY = "SELECT ds.* FROM document_serie as ds";
    private static final String INNER_JOIN_QUERY = "select ds.*\n" +
            "from document_serie ds \n" +
            "inner join site on site.id = ds.site_id \n" +
            "inner join site_device_pos on site_device_pos.id = ds.site_device_pos_id \n" +
            "inner join site_device on site_device_pos.id = site_device.id\n" +
            "inner join document_type on ds.document_type = document_type.id \n" +
            "where  site.code = :siteCode and site_device.code = :deviceCode and document_type.code = :documentTypeCode";

    public DocumentSerieRepository(BeanLocator beanLocator, MultitenantResolver multitenantResolver) {
        super(beanLocator, multitenantResolver);
    }

    public List<DocumentSerie> findAll(@PositiveOrZero int offset, @PositiveOrZero int limit, @NotNull List<Sort> sort,
                                       @Nullable Filter filters) {
        return super.fetchResult(DEFAULT_QUERY, filters, offset, limit, sort, DocumentSerie.class);
    }

    public Long count(Filter filters) {
        return super.countResult(DEFAULT_QUERY, filters);
    }

    public abstract Optional<DocumentSerie> findByCode(@NotBlank String code);


    @Query(value = INNER_JOIN_QUERY, nativeQuery = true)
    public abstract Optional<DocumentSerie> findSerieForSale(String siteCode, String deviceCode, String documentTypeCode);

}
