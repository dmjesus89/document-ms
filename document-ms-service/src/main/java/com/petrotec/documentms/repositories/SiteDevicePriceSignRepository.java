package com.petrotec.documentms.repositories;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.repositories.AbstractLazyRepository;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteDevicePriceSign;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class SiteDevicePriceSignRepository extends AbstractLazyRepository implements JpaRepository<SiteDevicePriceSign, Long> {

    private static final String DEFAULT_QUERY = "SELECT e.* FROM site_device_price_sign as e";

    public SiteDevicePriceSignRepository(@CurrentSession EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    public List<SiteDevicePriceSign> findAll(@PositiveOrZero int offset, @PositiveOrZero int limit, @NotNull List<Sort> sort,
                                 @Nullable Filter filters) {
        return super.fetchResult(DEFAULT_QUERY, filters, offset, limit, sort, SiteDevicePriceSign.class);
    }

    public Long count(Filter filters) {
        return super.countResult(DEFAULT_QUERY, filters);
    }

    public abstract Optional<SiteDevicePriceSign> findByCode(@NotBlank String code);

    public abstract Page<SiteDevicePriceSign> findBySiteCode(String siteCode, Pageable pageable);

    public abstract Optional<SiteDevicePriceSign> findBySiteCodeAndPriceSignCode(String siteCode, String priceSignCode);

    public abstract List<SiteDevicePriceSign> findBySite(Site site);
}
