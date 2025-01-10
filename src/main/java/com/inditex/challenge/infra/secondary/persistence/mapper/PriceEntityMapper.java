package com.inditex.challenge.infra.secondary.persistence.mapper;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.infra.secondary.persistence.dto.PriceEntityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

    @Mapping(source = "priceList", target = "priceId")
    @Mapping(source = "curr", target = "currency")
    Price toDomain(PriceEntityDto entityDto);
}
