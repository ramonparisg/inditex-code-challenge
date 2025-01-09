package com.inditex.challenge.infra.primary.controller;

import com.inditex.challenge.core.usecase.GetFilteredPriceUseCase;
import com.inditex.challenge.infra.primary.controller.dto.GetPricingResponseDto;
import com.inditex.challenge.infra.primary.controller.mapper.PriceControllerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class PricingController {

    private final GetFilteredPriceUseCase getFilteredPriceUseCase;
    private final PriceControllerMapper priceControllerMapper;

    @GetMapping("prices")
    Mono<ResponseEntity<GetPricingResponseDto>> pricing(@RequestParam Integer brandId, @RequestParam LocalDateTime datetime, @RequestParam String productId) {
        return getFilteredPriceUseCase
                .getPriceByCriteria(null)
                .map(priceControllerMapper::toGetPricingResponseDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
