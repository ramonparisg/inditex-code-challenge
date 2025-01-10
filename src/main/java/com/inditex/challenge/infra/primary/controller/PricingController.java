package com.inditex.challenge.infra.primary.controller;

import com.inditex.challenge.core.usecase.GetFilteredPriceUseCase;
import com.inditex.challenge.infra.primary.controller.dto.GetPricingResponseDto;
import com.inditex.challenge.infra.primary.controller.mapper.PriceControllerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("/v1/prices")
    Mono<ResponseEntity<GetPricingResponseDto>> getPrice(
            @RequestParam Integer brandId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datetime,
            @RequestParam Integer productId
    ) {

        final GetFilteredPriceUseCase.FilterCmd filterCmd = buildFilter(brandId, datetime, productId);

        return getFilteredPriceUseCase
                .getPriceByFilter(filterCmd)
                .map(priceControllerMapper::toGetPricingResponseDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private static GetFilteredPriceUseCase.FilterCmd buildFilter(Integer brandId, LocalDateTime datetime, Integer productId) {
        return GetFilteredPriceUseCase.FilterCmd.builder()
                .applicationDate(datetime)
                .brandId(brandId)
                .productId(productId)
                .build();
    }
}
