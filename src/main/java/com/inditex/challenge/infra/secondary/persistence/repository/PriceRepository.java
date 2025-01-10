package com.inditex.challenge.infra.secondary.persistence.repository;

import com.inditex.challenge.infra.secondary.persistence.dto.PriceEntityDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface PriceRepository extends ReactiveCrudRepository<PriceEntityDto, Long> {



    Flux<PriceEntityDto> findAllByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateLessThanEqual(Integer brandId, Integer productId, LocalDateTime startDate, LocalDateTime endDate);

}
