package com.petrotec.documentms.repositories;


import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.repositories.AbstractLazyRepository;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteDevicePos;
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
public abstract class SiteDevicePosRepository extends AbstractLazyRepository implements JpaRepository<SiteDevicePos, Long> {

    private static final String DEFAULT_QUERY = "SELECT e.* FROM site_device_pos as e";

    public SiteDevicePosRepository(@CurrentSession EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    public List<SiteDevicePos> findAll(@PositiveOrZero int offset, @PositiveOrZero int limit, @NotNull List<Sort> sort,
                                             @Nullable Filter filters) {
        return super.fetchResult(DEFAULT_QUERY, filters, offset, limit, sort, SiteDevicePos.class);
    }

    public Long count(Filter filters) {
        return super.countResult(DEFAULT_QUERY, filters);
    }

    public abstract Optional<SiteDevicePos> findByCode(@NotBlank String code);

    public abstract Page<SiteDevicePos> findBySiteCode(String siteCode, Pageable pageable);

    public abstract Optional<SiteDevicePos> findBySiteCodeAndNumber(String siteCode, Integer number);

    public abstract List<SiteDevicePos> findBySite(Site site);
}
