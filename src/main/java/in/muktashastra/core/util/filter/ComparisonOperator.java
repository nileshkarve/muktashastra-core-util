package in.muktashastra.core.util.filter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
@RequiredArgsConstructor
public class ComparisonOperator implements Serializable {

    @Getter
    public enum Operator {
        EQUALS("="),
        NOT_EQUALS("<>"),
        GREATER_THAN(">"),
        GREATER_THAN_OR_EQUAL_TO(">="),
        LESS_THAN("<"),
        LESS_THAN_OR_EQUAL_TO("<="),
        LIKE("LIKE"),
        NOT_LIKE("NOT LIKE"),
        IN("IN"),
        NOT_IN("NOT IN"),
        IS_NULL("IS NULL"),
        IS_NOT_NULL("IS NOT NULL");

        private final String operatorSqlString;

        Operator(String operatorSqlString) {
            this.operatorSqlString = operatorSqlString;
        }
    }
    private final Operator operator;

    public PreparedStatementClause generatePreparedStatementClause(String columnName, List<Object> values) {
        return switch(operator) {
            case IN, NOT_IN -> {
                String placeholders = String.join(", ", Collections.nCopies(values.size(), "?"));
                String clause = String.format("%s %s (%s)", columnName, this.operator.getOperatorSqlString(), placeholders);
                yield new PreparedStatementClause(clause, values);
            }
            case IS_NULL, IS_NOT_NULL -> {
                String clause = String.format("%s %s", columnName, this.operator.getOperatorSqlString());
                yield new PreparedStatementClause(clause, new ArrayList<>());
            }
            default -> {
                String clause = String.format("%s %s ?", columnName, this.operator.getOperatorSqlString());
                yield new PreparedStatementClause(clause, values.getFirst());
            }
        };
    }

    @Getter
    public static class PreparedStatementClause {
        private final String preparedStatementSql;
        private final List<Object> values;

        private PreparedStatementClause(String preparedStatementSql, List<Object> values) {
            this.preparedStatementSql = preparedStatementSql;
            this.values = values;
        }

        private PreparedStatementClause(String preparedStatementSql, Object value) {
            this.preparedStatementSql = preparedStatementSql;
            this.values = List.of(value);
        }
    }
}
