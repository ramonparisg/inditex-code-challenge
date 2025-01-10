package com.inditex.challenge.core.ports;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.usecase.GetFilteredPriceUseCase;
import reactor.core.publisher.Flux;

public interface PricePersistencePort {

    Flux<Price> matching(GetFilteredPriceUseCase.FilterCmd filterCmd);
}
