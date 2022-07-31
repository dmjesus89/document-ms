package com.petrotec.documentms.repositories;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.multitenant.repository.MultitenantLazyRepository;
import com.petrotec.multitenant.resolver.MultitenantResolver;
import com.petrotec.documentms.entities.SiteProfile;
import com.petrotec.documentms.entities.SiteProfileCustom;
import com.petrotec.documentms.repositories.interfaces.SiteProfileRepository;
import io.micronaut.context.BeanLocator;

import javax.inject.Singleton;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

/** SiteProfileImpl */
@Transactional
@Singleton
public class SiteProfileRepositoryImpl extends MultitenantLazyRepository implements SiteProfileRepository {

	private static final String DEFAULT_QUERY = "SELECT id, code, description as description, is_enabled as enabled FROM site_profile";

	public SiteProfileRepositoryImpl(BeanLocator beanLocator, MultitenantResolver multitenantResolver) {
		super(beanLocator, multitenantResolver);
	}

	@Override
	public Optional<SiteProfile> findByCode(@NotEmpty String code) {
		try {
			SiteProfile result = getEntityManager()
					.createQuery("SELECT sp FROM com.petrotec.documentms.entities.SiteProfile sp WHERE code = :code",
							SiteProfile.class)
					.setParameter("code", code).getSingleResult();
			return Optional.ofNullable(result);
		} catch (NoResultException noResultException) {
			return Optional.empty();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Optional.empty();
		}

	}

	@Override
	public Optional<SiteProfile> findById(@Positive int profileId) {
		return Optional.ofNullable(getEntityManager().find(SiteProfile.class, profileId));
	}

	@Override
	public List<SiteProfileCustom> findAllForLocale(String locale, Filter filters, @PositiveOrZero int offset,
			@PositiveOrZero int limit, @NotNull List<Sort> sorting) {
		String sql = "SELECT id, code, JSON_UNQUOTE(JSON_EXTRACT(description,?)) AS description, is_enabled as enabled FROM site_profile";
		return fetchResult(sql, filters, offset, limit, sorting, SiteProfileCustom.class, "$.\"" + locale + "\"");
	}

	@Override
	public List<SiteProfileCustom> findAll(Filter filters, @PositiveOrZero int offset, @PositiveOrZero int limit,
			@NotNull List<Sort> sorting) {
		return fetchResult(DEFAULT_QUERY, filters, offset, limit, sorting, SiteProfileCustom.class);
	}

	@Override
	public long count(Filter filters) {
		return countResult(DEFAULT_QUERY, filters);
	}

	@Override
	public Optional<SiteProfile> insertSiteProfile(SiteProfile siteProfile) {
		getEntityManager().persist(siteProfile);
		getEntityManager().flush();
		return Optional.of(siteProfile);
	}

	@Override
	public Optional<SiteProfile> updateSiteProfile(SiteProfile siteProfile) {
		SiteProfile updateSiteProfile = getEntityManager().merge(siteProfile);
		return Optional.of(updateSiteProfile);
	}

}
