package com.inditex.challenge.core.domain.criteria;

import lombok.Value;

@Value
public class CriteriaFilter<T> {

    T field;
    CriteriaOperator operator;
    Object value;
}
