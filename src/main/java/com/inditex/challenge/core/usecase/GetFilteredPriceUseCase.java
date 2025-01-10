package com.inditex.challenge.core.usecase;

import com.inditex.challenge.core.domain.Price;
import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface GetFilteredPriceUseCase {

    Mono<Price> getPriceByFilter(FilterCmd filterCmd);

    @Data
    @Builder
    class FilterCmd {
        private LocalDateTime applicationDate;
        private Integer productId;
        private Integer brandId;
    }
}
