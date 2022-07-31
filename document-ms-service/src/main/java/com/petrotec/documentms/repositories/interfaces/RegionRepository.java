package com.petrotec.documentms.repositories.interfaces;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Optional;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.documentms.entities.RegionCustom;
import com.petrotec.documentms.entities.Region;

/**
 * RegionRepository
 */
public interface RegionRepository {

        List<RegionCustom> findAllForLocale(String locale, Filter filters, @PositiveOrZero int offset,
                        @PositiveOrZero int limit, @NotNull List<Sort> sorting);

        List<RegionCustom> findAll(Filter filters, @PositiveOrZero int offset, @PositiveOrZero int limit,
                        @NotNull List<Sort> sorting);

        Optional<Region> findById(@Positive int id);

        Optional<Region> findByCode(@NotEmpty String code);

        Optional<Region> insertRegion(Region region);

        Optional<Region> updateRegion(Region region);

        long count(Filter filters);

}