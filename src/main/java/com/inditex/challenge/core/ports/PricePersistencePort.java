package com.inditex.challenge.core.ports;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.domain.criteria.Criteria;
import com.inditex.challenge.core.domain.criteria.filters.PriceFieldFilter;
import reactor.core.publisher.Flux;

public interface PricePersistencePort {

    Flux<Price> matching(Criteria<PriceFieldFilter> criteria);
}
