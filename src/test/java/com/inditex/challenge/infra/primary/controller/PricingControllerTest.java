package com.inditex.challenge.infra.primary.controller;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.usecase.GetFilteredPriceUseCase;
import com.inditex.challenge.infra.primary.controller.dto.GetPricingResponseDto;
import com.inditex.challenge.infra.primary.controller.mapper.PriceControllerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricingControllerTest {

    @Mock
    private GetFilteredPriceUseCase getFilteredPriceUseCase;

    @Mock
    private PriceControllerMapper priceControllerMapper;

    @InjectMocks
    private PricingController pricingController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToController(pricingController)
                .build();
    }

    @Test
    @DisplayName("Should return 200 OK with price when found")
    void getPriceShouldReturnPriceWhenFound() {
        // Given
        LocalDateTime requestDateTime = LocalDateTime.of(2024, 1, 10, 10, 0);
        Integer brandId = 1;
        Integer productId = 35455;

        Price price = Price.builder()
                .brandId(brandId)
                .startDate(requestDateTime.minusDays(1))
                .endDate(requestDateTime.plusDays(1))
                .productId(productId)
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .build();

        GetPricingResponseDto responseDto = GetPricingResponseDto.builder()
                .productId(String.valueOf(productId))
                .brandId(String.valueOf(brandId))
                .priceId("1")
                .startDate(price.getStartDate().format(DateTimeFormatter.ISO_DATE_TIME))
                .endDate(price.getEndDate().format(DateTimeFormatter.ISO_DATE_TIME))
                .price(price.getPrice())
                .build();

        // When
        when(getFilteredPriceUseCase.getPriceByFilter(any(GetFilteredPriceUseCase.FilterCmd.class)))
                .thenReturn(Mono.just(price));
        when(priceControllerMapper.toGetPricingResponseDto(price))
                .thenReturn(responseDto);

        // Then
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/prices")
                        .queryParam("brandId", brandId)
                        .queryParam("datetime", requestDateTime)
                        .queryParam("productId", productId)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetPricingResponseDto.class)
                .isEqualTo(responseDto);
    }

    @Test
    @DisplayName("Should return 404 Not Found when price not found")
    void getPriceShouldReturn404WhenNotFound() {
        // Given
        LocalDateTime requestDateTime = LocalDateTime.of(2024, 1, 10, 10, 0);
        Integer brandId = 1;
        Integer productId = 35455;

        // When
        when(getFilteredPriceUseCase.getPriceByFilter(any(GetFilteredPriceUseCase.FilterCmd.class)))
                .thenReturn(Mono.empty());

        // Then
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/prices")
                        .queryParam("brandId", brandId)
                        .queryParam("datetime", requestDateTime)
                        .queryParam("productId", productId)
                        .build())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Should return 400 Bad Request when datetime format is invalid")
    void getPriceShouldReturn400WhenDateTimeFormatInvalid() {
        // Given
        String invalidDateTime = "2024-01-10 10:00:00"; // Wrong format
        Integer brandId = 1;
        Integer productId = 35455;

        // Then
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/prices")
                        .queryParam("brandId", brandId)
                        .queryParam("datetime", invalidDateTime)
                        .queryParam("productId", productId)
                        .build())
                .exchange()
                .expectStatus().isBadRequest();
    }
}