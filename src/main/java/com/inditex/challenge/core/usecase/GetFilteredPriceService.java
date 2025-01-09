package com.inditex.challenge.core.usecase;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.core.ports.PricePersistencePort;
import com.inditex.challenge.infra.secondary.persistence.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetFilteredPriceService implements GetFilteredPriceUseCase{

    private final PricePersistencePort persistencePort;

    @Override
    public Mono<Price> getPriceByCriteria(FilterCmd filterCmd) {
        return persistencePort.getPriceByFilter(filterCmd);
    }
}
