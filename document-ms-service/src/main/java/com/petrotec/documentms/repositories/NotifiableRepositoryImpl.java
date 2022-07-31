package com.petrotec.documentms.repositories;

import javax.inject.Singleton;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Optional;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.multitenant.repository.MultitenantLazyRepository;
import com.petrotec.multitenant.resolver.MultitenantResolver;
import com.petrotec.documentms.entities.Notifiable;
import com.petrotec.documentms.entities.NotifiableCustom;
import com.petrotec.documentms.repositories.interfaces.NotifiableRepository;
import io.micronaut.context.BeanLocator;


@Transactional
@Singleton
public class NotifiableRepositoryImpl extends MultitenantLazyRepository implements NotifiableRepository {

	private static final String DEFAULT_QUERY = "SELECT n.id, n.code, n.description, n.created_on, n.updated_on, n.is_enabled as enabled, n.entity_code, n.entity_rank_order FROM notifiable n";

	public NotifiableRepositoryImpl(BeanLocator beanLocator, MultitenantResolver multitenantResolver) {
		super(beanLocator, multitenantResolver);
	}

	@Override
	public List<NotifiableCustom> findAll(Filter filters, @PositiveOrZero int offset, @PositiveOrZero int limit,
			@NotNull List<Sort> sorting) {
		return fetchResult(DEFAULT_QUERY, filters, offset, limit, sorting, NotifiableCustom.class);
	}

	@Override
	public long count(Filter filters) {
		return countResult(DEFAULT_QUERY, filters);
	}

	@Override
	public Optional<Notifiable> findByCode(String code) {
		try {
			Notifiable notifiable = getEntityManager()
					.createQuery("From com.petrotec.sitems.entities.Notifiable WHERE code = :code", Notifiable.class)
					.setParameter("code", code).getSingleResult();
			return Optional.ofNullable(notifiable);
		} catch (NoResultException noResultException) {
			return Optional.empty();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public Notifiable save(@NotBlank Notifiable notifiable) {
		getEntityManager().persist(notifiable);
		getEntityManager().flush();
		return notifiable;
	}

	@Override
	public Notifiable update(@NotNull Notifiable notifiable) {
		return getEntityManager().merge(notifiable);
	}

	@Override
	public void delete(@NotBlank Notifiable notifiable) {
		getEntityManager().remove(notifiable);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notifiable> findBySiteCodeOrAtSiteGroupOrEntityCode(String siteCode, String entityCode) {
		List<Notifiable> notifiables = getEntityManager().createNativeQuery(
				"select n.* from notifiable_site ns join notifiable n on (ns.notifiable_code = n.code) JOIN notifiable_entity ne ON (ne.notifiable_code = n.code) where ns.site_code = :siteCode OR ne.entity_code = :entityCode"
						+ " union all"
						+ " select n.* from notifiable_site_group_item nsgi join site_group_item sgi on (sgi.code = nsgi.site_group_item_code)"
						+ " join site_group_item_site sgis on (sgi.id = sgis.site_group_item_id)"
						+ " join site s on(s.id = sgis.site_id)"
						+ " join notifiable n on (nsgi.notifiable_code = n.code)" + "where s.code = :siteCode",
				Notifiable.class).setParameter("siteCode", siteCode).setParameter("entityCode", entityCode)
				.getResultList();
		return notifiables;
	}

}
