package com.petrotec.documentms.repositories.interfaces;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.entities.Notifiable;
import com.petrotec.documentms.entities.NotifiableCustom;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

public interface NotifiableRepository {

	List<NotifiableCustom> findAll(Filter filters, @PositiveOrZero int offset, @PositiveOrZero int limit,
			@NotNull List<Sort> sorting);

	long count(Filter filters);

	Optional<Notifiable> findByCode(String notifiableCode);

	Notifiable update(@NotNull Notifiable notifiable);

	Notifiable save(@NotNull Notifiable notifiable);

	void delete(@NotBlank Notifiable notifiable);

	List<Notifiable> findBySiteCodeOrAtSiteGroupOrEntityCode(String siteCode, String entityCode);
}
