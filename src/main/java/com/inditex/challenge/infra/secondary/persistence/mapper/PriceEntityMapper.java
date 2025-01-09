package com.inditex.challenge.infra.secondary.persistence.mapper;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.infra.secondary.persistence.dto.PriceEntityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

//    @Mapping(source = "brandId", target = "brandId")
    Price toDomain(PriceEntityDto entityDto);
}
