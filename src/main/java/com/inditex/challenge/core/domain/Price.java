package com.inditex.challenge.core.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data // todo ver si puedo dejar esto como @Value y como object values
public class Price {

    private Integer brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long priceId;
    private String productId;
    private Integer priority;
    private BigDecimal price;
    private String currency;

}
