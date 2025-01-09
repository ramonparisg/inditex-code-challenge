package com.inditex.challenge.core.usecase;

import com.inditex.challenge.core.domain.Price;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface GetFilteredPriceUseCase {

    Mono<Price> getPriceByCriteria(FilterCmd filterCmd);

    class FilterCmd {
        private LocalDateTime applicationDate;
        private String productId;
        private String brandId;
    }
}
