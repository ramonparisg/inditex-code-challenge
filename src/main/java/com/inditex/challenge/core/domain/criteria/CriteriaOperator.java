package com.inditex.challenge.core.domain.criteria;

import lombok.Getter;

@Getter
public enum CriteriaOperator {
    EQUALS("="),
    NOT_EQUALS("!="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_THAN_EQUALS(">="),
    LESS_THAN_EQUALS("<=");

    private final String value;

    CriteriaOperator(final String value) {
        this.value = value;
    }

}