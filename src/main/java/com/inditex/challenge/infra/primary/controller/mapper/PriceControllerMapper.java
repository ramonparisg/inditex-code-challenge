package com.inditex.challenge.infra.primary.controller.mapper;

import com.inditex.challenge.core.domain.Price;
import com.inditex.challenge.infra.primary.controller.dto.GetPricingResponseDto;
import lombok.Generated;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@Generated
public interface PriceControllerMapper {

    GetPricingResponseDto toGetPricingResponseDto(Price price);
}
