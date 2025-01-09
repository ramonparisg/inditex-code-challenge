package com.inditex.challenge.infra.primary.controller.mapper;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.infra.primary.controller.dto.GetPricingResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceControllerMapper {

    GetPricingResponseDto toGetPricingResponseDto(Price price);
}
