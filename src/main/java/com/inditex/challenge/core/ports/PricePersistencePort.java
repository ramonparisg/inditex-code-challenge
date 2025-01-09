package com.inditex.challenge.core.ports;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.usecase.GetFilteredPriceUseCase;
import reactor.core.publisher.Mono;

public interface PricePersistencePort {

    Mono<Price> getPriceByFilter(GetFilteredPriceUseCase.FilterCmd filterCmd);
}
