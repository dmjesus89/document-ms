package com.petrotec.documentms.repositories;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.multitenant.repository.MultitenantLazyRepository;
import com.petrotec.multitenant.resolver.MultitenantResolver;
import com.petrotec.documentms.entities.SiteGroup;
import com.petrotec.documentms.entities.SiteGroupCustom;
import com.petrotec.documentms.repositories.interfaces.SiteGroupRepository;
import io.micronaut.context.BeanLocator;
import io.micronaut.data.annotation.Repository;


import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** SiteGroupRepositoryImpl */
@Transactional
@Repository
public class SiteGroupRepositoryImpl extends MultitenantLazyRepository implements SiteGroupRepository {
	private static final String DEFAULT_QUERY = "SELECT id, code, description, entity_code, entity_rank_order, is_enabled as enabled, created_on, updated_on FROM site_group";

	public SiteGroupRepositoryImpl(BeanLocator beanLocator, MultitenantResolver multitenantResolver) {
		super(beanLocator, multitenantResolver);
	}

	@Override
	public List<SiteGroupCustom> findAll(Filter filters, @PositiveOrZero int offset, @PositiveOrZero int limit,
			@NotNull List<Sort> sorting) {
		return fetchResult(DEFAULT_QUERY, filters, offset, limit, sorting, SiteGroupCustom.class);
	}

	@Override
	public Optional<SiteGroup> findByCode(String code) {
		try {
			return Optional.ofNullable(getEntityManager()
					.createQuery("SELECT sg FROM com.petrotec.documentms.entities.SiteGroup sg WHERE code = :code",
							SiteGroup.class)
					.setParameter("code", code).getSingleResult());
		} catch (NoResultException noResultException) {
			return Optional.empty();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public List<SiteGroup> findByCode(List<String> codes) {
		try {
			return getEntityManager()
					.createQuery("SELECT sg FROM com.petrotec.documentms.entities.SiteGroup sg WHERE code in :codes",
							SiteGroup.class)
					.setParameter("codes", codes).getResultList();
		} catch (NoResultException noResultException) {
			return new ArrayList<>();
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public SiteGroup save(SiteGroup siteGroup) {
		getEntityManager().persist(siteGroup);
		return siteGroup;
	}

	@Override
	public SiteGroup update(SiteGroup siteGroup) {
		return getEntityManager().merge(siteGroup);
	}

	@Override
	public long count(Filter filters) {
		return countResult(DEFAULT_QUERY, filters);
	}

}
