package com.inditex.challenge.core.domain.criteria;

public class CriteriaOrder<T> {
    private T by;
    private OrderType order;

    public CriteriaOrder(T by, OrderType order) {
        this.by = by;
        this.order = order;
    }


    public enum OrderType {
        ASC, DESC
    }
}
