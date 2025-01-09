package com.inditex.challenge.infra.secondary.persistence;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.ports.PricePersistencePort;
import com.inditex.challenge.core.usecase.GetFilteredPriceUseCase;
import com.inditex.challenge.infra.secondary.persistence.mapper.PriceEntityMapper;
import com.inditex.challenge.infra.secondary.persistence.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
@Slf4j
public class PricePersistenceAdapter implements PricePersistencePort {

    private final PriceRepository priceRepository;
    private final PriceEntityMapper priceEntityMapper;

    // todo acoplar FilterCmd acá está mal. Buscar workaround
    @Override
    public Mono<Price> getPriceByFilter(GetFilteredPriceUseCase.FilterCmd filterCmd) {
        return priceRepository.findById(1L)
                .map(priceEntityMapper::toDomain);
    }
}
