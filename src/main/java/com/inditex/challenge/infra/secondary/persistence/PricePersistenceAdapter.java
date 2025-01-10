package com.inditex.challenge.infra.secondary.persistence;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.domain.criteria.Criteria;
import com.inditex.challenge.core.domain.criteria.filters.PriceFieldFilter;
import com.inditex.challenge.core.ports.PricePersistencePort;
import com.inditex.challenge.infra.secondary.persistence.dto.PriceEntityDto;
import com.inditex.challenge.infra.secondary.persistence.mapper.CriteriaToH2Converter;
import com.inditex.challenge.infra.secondary.persistence.mapper.PriceEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
@RequiredArgsConstructor
public class PricePersistenceAdapter implements PricePersistencePort {

    private final PriceEntityMapper priceEntityMapper;
    private final CriteriaToH2Converter criteriaConverter;

    private static final String PRICE_TABLE_NAME = "PRICES";

    @Override
    public Flux<Price> matching(Criteria<PriceFieldFilter> criteria) {

        final var sqlExec = criteriaConverter.convert(criteria, PRICE_TABLE_NAME);

        return sqlExec
                .mapProperties(PriceEntityDto.class)
                .all()
                .map(priceEntityMapper::toDomain);
    }
}
