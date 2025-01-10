package com.inditex.challenge.infra.primary.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPricingResponseDto {
    private String productId;
    private String brandId;
    private String startDate;
    private String endDate;
    private String priceId;
    private BigDecimal price;
    private String currency;
}
