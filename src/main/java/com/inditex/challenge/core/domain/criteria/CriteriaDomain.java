package com.inditex.challenge.core.domain.criteria;

import lombok.Value;

import java.util.List;

@Value
public class CriteriaDomain<T> {
    List<CriteriaFilter<T>> filters;
    Integer limit;
    CriteriaOrder<T> order;



}
