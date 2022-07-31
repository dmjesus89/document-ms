package com.petrotec.documentms.repositories;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.repositories.AbstractLazyRepository;
import com.petrotec.documentms.entities.SiteDeviceFuelPointNozzle;
import com.petrotec.documentms.entities.SiteGrade;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Repository
public abstract class SiteDeviceFuelPointNozzleRepository extends AbstractLazyRepository implements JpaRepository<SiteDeviceFuelPointNozzle, Integer> {

    private static final String DEFAULT_QUERY = "SELECT e.* FROM site_device_fuel_point_nozzle as e";

    public SiteDeviceFuelPointNozzleRepository(@CurrentSession EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    public List<SiteDeviceFuelPointNozzle> findAll(@PositiveOrZero int offset, @PositiveOrZero int limit, @NotNull List<Sort> sort,
                                                   @Nullable Filter filters) {
        return super.fetchResult(DEFAULT_QUERY, filters, offset, limit, sort, SiteDeviceFuelPointNozzle.class);
    }

    public Long count(Filter filters) {
        return super.countResult(DEFAULT_QUERY, filters);
    }

    public abstract boolean existsBySiteGrade(SiteGrade siteGrade);
}
