package com.inditex.challenge.infra.secondary.persistence;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.domain.criteria.Criteria;
import com.inditex.challenge.core.domain.criteria.CriteriaFilter;
import com.inditex.challenge.core.domain.criteria.CriteriaOrder;
import com.inditex.challenge.core.domain.criteria.filters.PriceFieldFilter;
import com.inditex.challenge.infra.secondary.persistence.dto.PriceEntityDto;
import com.inditex.challenge.infra.secondary.persistence.mapper.CriteriaToH2Converter;
import com.inditex.challenge.infra.secondary.persistence.mapper.PriceEntityMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.r2dbc.core.DatabaseClient.GenericExecuteSpec;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.inditex.challenge.core.domain.criteria.CriteriaOperator.EQUALS;
import static com.inditex.challenge.core.domain.criteria.CriteriaOrder.OrderType.ASC;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricePersistenceAdapterTest {

    @Mock
    private PriceEntityMapper priceEntityMapper;

    @Mock
    private CriteriaToH2Converter criteriaConverter;

    @Mock
    private GenericExecuteSpec executeSpec;

    @Mock
    private RowsFetchSpec<PriceEntityDto> rowsFetchSpec;

    @InjectMocks
    private PricePersistenceAdapter pricePersistenceAdapter;

    @Test
    @DisplayName("Should correctly query and map prices matching criteria")
    void shouldQueryAndMapPricesMatchingCriteria() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Criteria<PriceFieldFilter> criteria = new Criteria<>(
                List.of(new CriteriaFilter<>(PriceFieldFilter.BRAND_ID, EQUALS, 1)),
                10,
                new CriteriaOrder<>(PriceFieldFilter.PRIORITY, ASC)
        );

        PriceEntityDto entityDto = PriceEntityDto.builder()
                .brandId(1)
                .startDate(now)
                .endDate(now.plusDays(1))
                .priceList(1)
                .productId(35455)
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .curr("EUR")
                .build();

        Price expectedPrice = Price.builder()
                .brandId(1)
                .startDate(now)
                .endDate(now.plusDays(1))
                .priceId(1)
                .productId(35455)
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .currency("EUR")
                .build();

        when(criteriaConverter.convert(eq(criteria), eq("PRICES"))).thenReturn(executeSpec);
        when(executeSpec.mapProperties(PriceEntityDto.class)).thenReturn(rowsFetchSpec);
        when(rowsFetchSpec.all()).thenReturn(Flux.just(entityDto));
        when(priceEntityMapper.toDomain(entityDto)).thenReturn(expectedPrice);

        // When
        Flux<Price> result = pricePersistenceAdapter.matching(criteria);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedPrice)
                .verifyComplete();

        verify(criteriaConverter).convert(criteria, "PRICES");
        verify(priceEntityMapper).toDomain(entityDto);
    }

    @Test
    @DisplayName("Should return empty flux when no prices match criteria")
    void shouldReturnEmptyFluxWhenNoPricesMatch() {
        // Given
        Criteria<PriceFieldFilter> criteria = new Criteria<>(
                List.of(new CriteriaFilter<>(PriceFieldFilter.BRAND_ID, EQUALS, 999)),
                10,
                new CriteriaOrder<>(PriceFieldFilter.PRIORITY, ASC)
        );

        when(criteriaConverter.convert(eq(criteria), eq("PRICES"))).thenReturn(executeSpec);
        when(executeSpec.mapProperties(PriceEntityDto.class)).thenReturn(rowsFetchSpec);
        when(rowsFetchSpec.all()).thenReturn(Flux.empty());

        // When
        Flux<Price> result = pricePersistenceAdapter.matching(criteria);

        // Then
        StepVerifier.create(result)
                .verifyComplete();

        verify(criteriaConverter).convert(criteria, "PRICES");
    }

    @Test
    @DisplayName("Should handle multiple prices in result")
    void shouldHandleMultiplePricesInResult() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Criteria<PriceFieldFilter> criteria = new Criteria<>(
                List.of(new CriteriaFilter<>(PriceFieldFilter.BRAND_ID, EQUALS, 1)),
                10,
                new CriteriaOrder<>(PriceFieldFilter.PRIORITY, ASC)
        );

        PriceEntityDto entityDto1 = PriceEntityDto.builder()
                .brandId(1)
                .startDate(now)
                .endDate(now.plusDays(1))
                .priceList(1)
                .productId(35455)
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .curr("EUR")
                .build();

        PriceEntityDto entityDto2 = PriceEntityDto.builder()
                .brandId(1)
                .startDate(now)
                .endDate(now.plusDays(1))
                .priceList(2)
                .productId(35455)
                .priority(1)
                .price(BigDecimal.valueOf(25.45))
                .curr("EUR")
                .build();

        Price price1 = Price.builder()
                .brandId(1)
                .startDate(now)
                .endDate(now.plusDays(1))
                .priceId(1)
                .productId(35455)
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .currency("EUR")
                .build();

        Price price2 = Price.builder()
                .brandId(1)
                .startDate(now)
                .endDate(now.plusDays(1))
                .priceId(2)
                .productId(35455)
                .priority(1)
                .price(BigDecimal.valueOf(25.45))
                .currency("EUR")
                .build();

        when(criteriaConverter.convert(eq(criteria), eq("PRICES"))).thenReturn(executeSpec);
        when(executeSpec.mapProperties(PriceEntityDto.class)).thenReturn(rowsFetchSpec);
        when(rowsFetchSpec.all()).thenReturn(Flux.just(entityDto1, entityDto2));
        when(priceEntityMapper.toDomain(entityDto1)).thenReturn(price1);
        when(priceEntityMapper.toDomain(entityDto2)).thenReturn(price2);

        // When
        Flux<Price> result = pricePersistenceAdapter.matching(criteria);

        // Then
        StepVerifier.create(result)
                .expectNext(price1, price2)
                .verifyComplete();

        verify(criteriaConverter).convert(criteria, "PRICES");
        verify(priceEntityMapper).toDomain(entityDto1);
        verify(priceEntityMapper).toDomain(entityDto2);
    }
}