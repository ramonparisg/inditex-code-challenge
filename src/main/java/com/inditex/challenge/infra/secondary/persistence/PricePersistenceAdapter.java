package com.inditex.challenge.infra.secondary.persistence;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.ports.PricePersistencePort;
import com.inditex.challenge.core.usecase.GetFilteredPriceUseCase;
import com.inditex.challenge.infra.secondary.persistence.mapper.PriceEntityMapper;
import com.inditex.challenge.infra.secondary.persistence.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
@Slf4j
public class PricePersistenceAdapter implements PricePersistencePort {

    private final PriceRepository priceRepository;
    private final PriceEntityMapper priceEntityMapper;
    private final DatabaseClient databaseClient;

    @Override
    public Flux<Price> matching(GetFilteredPriceUseCase.FilterCmd filterCmd) {
        Criteria criteria = Criteria.where("BRAND_ID").is(filterCmd.getBrandId());

        Query query = Query.query(criteria);

        return priceRepository.findAllByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateLessThanEqual(
                filterCmd.getBrandId(), filterCmd.getProductId(), filterCmd.getApplicationDate(), filterCmd.getApplicationDate())
                .map(priceEntityMapper::toDomain);

//        return priceRepository.findAllByFilter(filterCmd.getBrandId(), filterCmd.getApplicationDate(), filterCmd.getProductId())
//                .map(priceEntityMapper::toDomain);

//        return databaseClient
//                .from(PriceEntityDto.class)
//                .matching(query)
//                .all()
//                .map(priceEntityMapper::toDomain);
//                ;
    }
}
