package com.inditex.challenge.infra.secondary.persistence.mapper;

import com.inditex.challenge.core.domain.criteria.Criteria;
import com.inditex.challenge.core.domain.criteria.CriteriaFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * The CriteriaToSqlConverter class is responsible for converting a {@code Criteria} object into a SQL query
 * that can be executed using a {@code DatabaseClient}.
 *
 * This class takes a criteria object, which represents a set of filtering conditions, and a table name.
 * It processes the criteria filters to generate a SQL query with an optional WHERE clause, and binds
 * the necessary parameters for execution.
 */
@Slf4j
@Component
public class CriteriaToSqlConverter {

    private final DatabaseClient databaseClient;

    public CriteriaToSqlConverter(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public DatabaseClient.GenericExecuteSpec convert(Criteria<?> criteria, String tableName) {
        var sql = String.format("SELECT * FROM %s", tableName);

        final var hasFilters = criteria != null && !criteria.getFilters().isEmpty();

        if (!hasFilters) {
            return databaseClient.sql(sql);
        }

        sql = appendWhereClause(criteria, sql);
        return bindParameters(databaseClient, criteria, sql);
    }

    private static DatabaseClient.GenericExecuteSpec bindParameters(DatabaseClient databaseClient, Criteria<?> criteria, String sql) {
        var executeSpec = databaseClient.sql(sql);
        for (CriteriaFilter<?> filter : criteria.getFilters()) {
            executeSpec = executeSpec.bind(filter.getField().toString(), filter.getValue());
        }
        return executeSpec;
    }


    private static String appendWhereClause(Criteria<?> criteria, String sql) {
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
