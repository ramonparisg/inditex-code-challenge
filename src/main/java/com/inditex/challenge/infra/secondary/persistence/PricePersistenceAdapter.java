package com.inditex.challenge.infra.secondary.persistence;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.domain.criteria.CriteriaDomain;
import com.inditex.challenge.core.domain.criteria.filters.PriceFieldFilter;
import com.inditex.challenge.core.ports.PricePersistencePort;
import com.inditex.challenge.infra.secondary.persistence.dto.PriceEntityDto;
import com.inditex.challenge.infra.secondary.persistence.mapper.CriteriaToH2Converter;
import com.inditex.challenge.infra.secondary.persistence.mapper.PriceEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
@Slf4j
public class PricePersistenceAdapter implements PricePersistencePort {

    private final PriceEntityMapper priceEntityMapper;
    private final DatabaseClient databaseClient;

    private static final String PRICE_TABLE_NAME = "PRICES";

    @Override
    public Flux<Price> matching(CriteriaDomain<PriceFieldFilter> criteriaDomain) {


        final var sql = CriteriaToH2Converter.convert(databaseClient, PRICE_TABLE_NAME, criteriaDomain);

        return sql
                .mapProperties(PriceEntityDto.class)
                .all()
                .map(priceEntityMapper::toDomain);
    }
}
