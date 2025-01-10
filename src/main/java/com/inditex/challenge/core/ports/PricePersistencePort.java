package com.inditex.challenge.core.ports;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.domain.criteria.CriteriaDomain;
import com.inditex.challenge.core.domain.criteria.filters.PriceFieldFilter;
import reactor.core.publisher.Flux;

public interface PricePersistencePort {

    Flux<Price> matching(CriteriaDomain<PriceFieldFilter> criteriaDomain);
}
