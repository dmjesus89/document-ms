package com.petrotec.documentms.repositories.interfaces;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Optional;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.entities.SiteProfile;
import com.petrotec.documentms.entities.SiteProfileCustom;

/** SiteProfileRepository */
public interface SiteProfileRepository {

    List<SiteProfileCustom> findAllForLocale(String locale, Filter filters, @PositiveOrZero int offset,
            @PositiveOrZero int limit, @NotNull List<Sort> sorts);

    List<SiteProfileCustom> findAll(Filter filters, @PositiveOrZero int offset, @PositiveOrZero int limit,
            @NotNull List<Sort> sorting);

    Optional<SiteProfile> findByCode(@NotEmpty String code);

    Optional<SiteProfile> findById(@Positive int profileId);

    long count(Filter filters);

    Optional<SiteProfile> insertSiteProfile(SiteProfile siteProfile);

    Optional<SiteProfile> updateSiteProfile(SiteProfile siteProfile);
}
