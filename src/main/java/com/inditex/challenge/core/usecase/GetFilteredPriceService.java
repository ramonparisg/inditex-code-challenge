package com.inditex.challenge.core.usecase;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.domain.criteria.CriteriaDomain;
import com.inditex.challenge.core.domain.criteria.CriteriaFilter;
import com.inditex.challenge.core.domain.criteria.CriteriaOrder;
import com.inditex.challenge.core.domain.criteria.filters.PriceFieldFilter;
import com.inditex.challenge.core.ports.PricePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.inditex.challenge.core.domain.criteria.CriteriaOperator.*;
import static com.inditex.challenge.core.domain.criteria.CriteriaOrder.OrderType.ASC;
import static com.inditex.challenge.core.domain.criteria.filters.PriceFieldFilter.*;

@Service
@RequiredArgsConstructor
public class GetFilteredPriceService implements GetFilteredPriceUseCase{

    private final PricePersistencePort pricePersistencePort;

    @Override
    public Mono<Price> getPriceByFilter(FilterCmd filterCmd) {
        final var criteria = buildCriteria(filterCmd);

        return pricePersistencePort
                .matching(criteria)
                .reduce(GetFilteredPriceService::getHighestPriority);
    }

    private static Price getHighestPriority(Price p1, Price p2) {
        return p1.getPriority() > p2.getPriority() ? p1 : p2;
    }

    private static CriteriaDomain<PriceFieldFilter> buildCriteria(FilterCmd filterCmd) {
        final var brandFilter = new CriteriaFilter<>(BRAND_ID, EQUALS, filterCmd.getBrandId());
        final var startDateFilter = new CriteriaFilter<>(START_DATE, LESS_THAN_EQUALS, filterCmd.getApplicationDate());
        final var endDateFilter = new CriteriaFilter<>(END_DATE, GREATER_THAN_EQUALS, filterCmd.getApplicationDate());
        final var productFilter = new CriteriaFilter<>(PRODUCT_ID, EQUALS, filterCmd.getProductId());

        return new CriteriaDomain<>(List.of(brandFilter, startDateFilter, endDateFilter, productFilter), 1, new CriteriaOrder<>(PRIORITY, ASC));
    }
}
