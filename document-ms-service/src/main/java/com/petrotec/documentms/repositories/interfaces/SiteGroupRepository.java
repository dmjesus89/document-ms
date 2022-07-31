package com.petrotec.documentms.repositories.interfaces;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.entities.SiteGroup;
import com.petrotec.documentms.entities.SiteGroupCustom;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

public interface SiteGroupRepository {
	List<SiteGroupCustom> findAll(Filter filters, @PositiveOrZero int offset, @PositiveOrZero int limit,
			@NotNull List<Sort> sorting);

	Optional<SiteGroup> findByCode(String code);

	List<SiteGroup> findByCode(List<String> codes);

	SiteGroup save(SiteGroup siteGroup);

	SiteGroup update(SiteGroup siteGroup);

	long count(Filter filters);

}
