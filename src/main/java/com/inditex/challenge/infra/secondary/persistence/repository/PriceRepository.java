package com.inditex.challenge.infra.secondary.persistence.repository;

import com.inditex.challenge.infra.secondary.persistence.dto.PriceEntityDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends ReactiveCrudRepository<PriceEntityDto, Long> {
}
