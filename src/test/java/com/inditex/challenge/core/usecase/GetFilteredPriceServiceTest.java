package com.inditex.challenge.core.usecase;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.domain.criteria.Criteria;
import com.inditex.challenge.core.domain.criteria.filters.PriceFieldFilter;
import com.inditex.challenge.core.ports.PricePersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFilteredPriceServiceTest {

    @Mock
    private PricePersistencePort pricePersistencePort;

    @InjectMocks
    private GetFilteredPriceService getFilteredPriceService;

    @Captor
    private ArgumentCaptor<Criteria<PriceFieldFilter>> criteriaCaptor;

    @Test
    @DisplayName("Should return empty when no prices match the criteria")
    void shouldReturnEmptyWhenNoPricesMatch() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        GetFilteredPriceUseCase.FilterCmd filterCmd = GetFilteredPriceUseCase.FilterCmd.builder()
                .brandId(1)
                .productId(35455)
                .applicationDate(now)
                .build();

        when(pricePersistencePort.matching(any())).thenReturn(Flux.empty());

        // When
        Mono<Price> result = getFilteredPriceService.getPriceByFilter(filterCmd);

        // Then
        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Should return price with highest priority when multiple prices match")
    void shouldReturnHighestPriorityPrice() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        GetFilteredPriceUseCase.FilterCmd filterCmd = GetFilteredPriceUseCase.FilterCmd.builder()
                .brandId(1)
                .productId(35455)
                .applicationDate(now)
                .build();

        Price lowPriorityPrice = Price.builder()
                .brandId(1)
                .startDate(now.minusDays(1))
                .endDate(now.plusDays(1))
                .priceId(1)
                .productId(35455)
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .currency("EUR")
                .build();

        Price highPriorityPrice = Price.builder()
                .brandId(1)
                .startDate(now.minusDays(1))
                .endDate(now.plusDays(1))
                .priceId(2)
                .productId(35455)
                .priority(1)
                .price(BigDecimal.valueOf(25.45))
                .currency("EUR")
                .build();

        when(pricePersistencePort.matching(any()))
                .thenReturn(Flux.just(lowPriorityPrice, highPriorityPrice));

        // When
        Mono<Price> result = getFilteredPriceService.getPriceByFilter(filterCmd);

        // Then
        StepVerifier.create(result)
                .expectNext(highPriorityPrice)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Should build criteria with correct filters")
    void shouldBuildCriteriaCorrectly() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        GetFilteredPriceUseCase.FilterCmd filterCmd = GetFilteredPriceUseCase.FilterCmd.builder()
                .brandId(1)
                .productId(35455)
                .applicationDate(now)
                .build();

        when(pricePersistencePort.matching(criteriaCaptor.capture()))
                .thenReturn(Flux.empty());

        // When
        Mono<Price> result = getFilteredPriceService.getPriceByFilter(filterCmd);

        // Then
        Criteria<?> capturedCriteria = criteriaCaptor.getValue();
        assertThat(capturedCriteria.getFilters()).hasSize(4);
        assertThat(capturedCriteria.getLimit()).isEqualTo(1);
        assertThat(capturedCriteria.getOrder()).isNotNull();

        // Verify all required filters are present
        assertThat(capturedCriteria.getFilters())
                .extracting("field")
                .containsExactlyInAnyOrder(
                        PriceFieldFilter.BRAND_ID,
                        PriceFieldFilter.START_DATE,
                        PriceFieldFilter.END_DATE,
                        PriceFieldFilter.PRODUCT_ID
                );


        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return single price when only one price matches")
    void shouldReturnSingleMatchingPrice() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        GetFilteredPriceUseCase.FilterCmd filterCmd = GetFilteredPriceUseCase.FilterCmd.builder()
                .brandId(1)
                .productId(35455)
                .applicationDate(now)
                .build();

        Price expectedPrice = Price.builder()
                .brandId(1)
                .startDate(now.minusDays(1))
                .endDate(now.plusDays(1))
                .priceId(1)
                .productId(35455)
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .currency("EUR")
                .build();

        when(pricePersistencePort.matching(any()))
                .thenReturn(Flux.just(expectedPrice));

        // When
        Mono<Price> result = getFilteredPriceService.getPriceByFilter(filterCmd);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedPrice)
                .expectComplete()
                .verify();

        verify(pricePersistencePort).matching(any());
    }
}