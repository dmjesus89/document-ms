package com.petrotec.documentms.repositories;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.repositories.AbstractLazyRepository;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteDeviceWarehouse;
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
public abstract class SiteDeviceWarehouseRepository extends AbstractLazyRepository implements JpaRepository<SiteDeviceWarehouse, Long> {

    private static final String DEFAULT_QUERY = "SELECT e.* FROM site_device_warehouse as e";

    public SiteDeviceWarehouseRepository(@CurrentSession EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    public List<SiteDeviceWarehouse> findAll(@PositiveOrZero int offset, @PositiveOrZero int limit, @NotNull List<Sort> sort,
                                 @Nullable Filter filters) {
        return super.fetchResult(DEFAULT_QUERY, filters, offset, limit, sort, SiteDeviceWarehouse.class);
    }

    public Long count(Filter filters) {
        return super.countResult(DEFAULT_QUERY, filters);
    }

    public abstract Optional<SiteDeviceWarehouse> findByCode(@NotBlank String code);

    public abstract Optional<SiteDeviceWarehouse> findBySiteCodeAndWarehouseCode(@NotBlank String siteCode, @NotBlank String warehouseCode);

    public abstract Page<SiteDeviceWarehouse> findBySiteCode(String siteCode, Pageable pageable);

    public abstract List<SiteDeviceWarehouse> findBySite(Site site);

}
