package com.inditex.challenge.core.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Price {

    private Integer brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceId;
    private Integer productId;
    private Integer priority;
    private BigDecimal price;
    private String currency;

}
