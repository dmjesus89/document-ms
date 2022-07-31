package com.petrotec.documentms.repositories.documents;

import com.petrotec.api.Sort;
import com.petrotec.api.filter.Filter;
import com.petrotec.multitenant.repository.MultitenantLazyRepository;
import com.petrotec.multitenant.resolver.MultitenantResolver;
import com.petrotec.documentms.entities.documents.PaymentMovement;
import io.micronaut.context.BeanLocator;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

@Repository
@Singleton
public abstract class PaymentMovementRepository extends MultitenantLazyRepository implements PageableRepository<PaymentMovement, Integer> {

    private static final String DEFAULT_QUERY = "SELECT pm.* FROM payment_movement as pm";

    public PaymentMovementRepository(BeanLocator beanLocator, MultitenantResolver multitenantResolver) {
        super(beanLocator, multitenantResolver);
    }

    public List<PaymentMovement> findAll(@PositiveOrZero int offset, @PositiveOrZero int limit, @NotNull List<Sort> sort,
                                  @Nullable Filter filters) {
        return super.fetchResult(DEFAULT_QUERY, filters, offset, limit, sort, PaymentMovement.class);
    }

    public Long count(Filter filters) {
        return super.countResult(DEFAULT_QUERY, filters);
    }

    public abstract Optional<PaymentMovement> findByCode(@NotBlank String code);

}
