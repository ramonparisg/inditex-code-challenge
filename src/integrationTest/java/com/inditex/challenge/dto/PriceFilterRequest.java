package com.inditex.challenge.dto;

public class PriceFilterRequest {

    private final String applicationDate;
    private final Integer productId;
    private final Integer brandId;

    public PriceFilterRequest(String applicationDate, Integer productId, Integer brandId) {
        this.applicationDate = applicationDate;
        this.productId = productId;
        this.brandId = brandId;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getBrandId() {
        return brandId;
    }
}
