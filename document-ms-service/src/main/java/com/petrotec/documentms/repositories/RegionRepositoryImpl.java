package com.petrotec.documentms.repositories;

import javax.inject.Singleton;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Optional;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.multitenant.repository.MultitenantLazyRepository;
import com.petrotec.multitenant.resolver.MultitenantResolver;
import com.petrotec.documentms.entities.Region;
import com.petrotec.documentms.entities.RegionCustom;
import com.petrotec.documentms.repositories.interfaces.RegionRepository;
import io.micronaut.context.BeanLocator;


/** RegionRepositoryImpl */
@Transactional
@Singleton
public class RegionRepositoryImpl extends MultitenantLazyRepository implements RegionRepository {
    private static final String DEFAULT_ALL_QUERY = "SELECT id, code, description as description, is_enabled as enabled FROM region";

    public RegionRepositoryImpl(BeanLocator beanLocator, MultitenantResolver multitenantResolver) {
        super(beanLocator, multitenantResolver);
    }

    @Override
    public List<RegionCustom> findAllForLocale(String locale, Filter filters, @PositiveOrZero int offset,
            @PositiveOrZero int limit, @NotNull List<Sort> sorting) {
        String query = "SELECT id, code, JSON_UNQUOTE(JSON_EXTRACT(description, ?)) as description, is_enabled as enabled FROM region";
        return fetchResult(query, filters, offset, limit, sorting, RegionCustom.class, "$.\"" + locale + "\"");
    }

    @Override
    public List<RegionCustom> findAll(Filter filters, @PositiveOrZero int offset, @PositiveOrZero int limit,
            @NotNull List<Sort> sorting) {
        return fetchResult(DEFAULT_ALL_QUERY, filters, offset, limit, sorting, RegionCustom.class);
    }

    @Override
    public Optional<Region> findByCode(String code) {
        try {
            Region region = getEntityManager()
                    .createQuery("From com.petrotec.sitems.entities.Region WHERE code = :code", Region.class)
                    .setParameter("code", code).getSingleResult();
            return Optional.ofNullable(region);
        } catch (NoResultException noResultException) {
            return Optional.empty();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Region> findById(@Positive int id) {
        return Optional.ofNullable(getEntityManager().find(Region.class, id));
    }

    @Override
    public long count(Filter filters) {
        return countResult(DEFAULT_ALL_QUERY, filters);
    }

    @Override
    public Optional<Region> insertRegion(Region region) {
        getEntityManager().persist(region);
        getEntityManager().flush();
        return Optional.of(region);
    }

    @Override
    public Optional<Region> updateRegion(Region region) {
        Region updatedRegion = getEntityManager().merge(region);
        return Optional.of(updatedRegion);
    }

}
