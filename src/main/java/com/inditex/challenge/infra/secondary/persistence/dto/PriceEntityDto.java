package com.inditex.challenge.infra.secondary.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Table("PRICES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceEntityDto {

    @Column("PRICE_LIST")
//    @Id
    private Integer priceList;

    @Column("BRAND_ID")
    private Integer brandId;

    @Column("START_DATE")
    private LocalDateTime startDate;

    @Column("END_DATE")
    private LocalDateTime endDate;

    @Column("PRODUCT_ID")
    private Integer productId;

    @Column("PRIORITY")
    private Integer priority;

    @Column("PRICE")
    private BigDecimal price;

    @Column("CURR")
    private String curr;
}
