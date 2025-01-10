package com.inditex.challenge.infra.secondary.persistence.mapper;

import com.inditex.challenge.core.domain.criteria.CriteriaDomain;
import com.inditex.challenge.core.domain.criteria.CriteriaFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.stream.Collectors;

@Slf4j
public class CriteriaToH2Converter {

    public static DatabaseClient.GenericExecuteSpec convert(DatabaseClient databaseClient, String tableName, CriteriaDomain<?> criteria) {
        var sql = String.format("SELECT * FROM %s", tableName);

        final var hasFilters = criteria != null && !criteria.getFilters().isEmpty();

        if (!hasFilters) {
            return databaseClient.sql(sql);
        }

        sql = appendWhereClause(criteria, sql);
        return bindParameters(databaseClient, criteria, sql);
    }

    private static DatabaseClient.GenericExecuteSpec bindParameters(DatabaseClient databaseClient, CriteriaDomain<?> criteria, String sql) {
        var executeSpec = databaseClient.sql(sql);
        for (CriteriaFilter<?> filter : criteria.getFilters()) {
            executeSpec = executeSpec.bind(filter.getField().toString(), filter.getValue());
        }
        return executeSpec;
    }


    private static String appendWhereClause(CriteriaDomain<?> criteria, String sql) {
        final String whereClause = criteria.getFilters()
                .stream()
                .map(filter -> String.format("%s %s :%s", filter.getField(), filter.getOperator().getValue(), filter.getField()))
                .collect(Collectors.joining(" AND "));

        sql = sql +
                " WHERE " +
                whereClause;
        return sql;
    }
}
