package in.muktashastra.core.persistence.relationalstore.repo;

import in.muktashastra.core.util.filter.Operator;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@ToString
class WhereConditionContainer {
    private final String conditionString;
    private final List<Object> parameterValues;

    private WhereConditionContainer(String conditionString, List<Object> parameterValues) {
        this.conditionString = conditionString;
        this.parameterValues = parameterValues;
    }

    public static WhereConditionContainer generate(String tableAlias, String columnName, Operator operator, List<Object> values) {
        return switch(operator) {
            case EQUALS -> {
                String conditionString = String.format("%s.%s = ?", tableAlias, columnName);
                yield new WhereConditionContainer(conditionString, values);
            }
            case NOT_EQUALS -> {
                String conditionString = String.format("%s.%s <> ?", tableAlias, columnName);
                yield new WhereConditionContainer(conditionString, values);
            }
            case GREATER_THAN -> {
                String conditionString = String.format("%s.%s > ?", tableAlias, columnName);
                yield new WhereConditionContainer(conditionString, values);
            }
            case GREATER_THAN_OR_EQUAL_TO -> {
                String conditionString = String.format("%s.%s >= ?", tableAlias, columnName);
                yield new WhereConditionContainer(conditionString, values);
            }
            case LESS_THAN -> {
                String conditionString = String.format("%s.%s < ?", tableAlias, columnName);
                yield new WhereConditionContainer(conditionString, values);
            }
            case LESS_THAN_OR_EQUAL_TO -> {
                String conditionString = String.format("%s.%s <= ?", tableAlias, columnName);
                yield new WhereConditionContainer(conditionString, values);
            }
            case LIKE -> {
                String conditionString = String.format("LOWER(%s.%s) LIKE LOWER(?) ESCAPE '\\'", tableAlias, columnName);
                values.forEach(value -> escapeLikeSpecialChars(value.toString()));
                yield new WhereConditionContainer(conditionString, values);
            }
            case NOT_LIKE -> {
                String conditionString = String.format("LOWER(%s.%s) NOT LIKE LOWER(?) ESCAPE '\\'", tableAlias, columnName);
                values.forEach(value -> escapeLikeSpecialChars(value.toString()));
                yield new WhereConditionContainer(conditionString, values);
            }
            case STARTS_WITH -> {
                String conditionString = String.format("LOWER(%s.%s) LIKE LOWER(?) ESCAPE '\\'", tableAlias, columnName);
                List<Object> processedValues = values.stream().map(value -> escapeLikeSpecialChars(value.toString())).map(value -> (Object)(value + "%")).toList();
                yield new WhereConditionContainer(conditionString, processedValues);
            }
            case ENDS_WITH -> {
                String conditionString = String.format("LOWER(%s.%s) LIKE LOWER(?) ESCAPE '\\'", tableAlias, columnName);
                List<Object> processedValues = values.stream().map(value -> escapeLikeSpecialChars(value.toString())).map(value -> (Object)("%" + value)).toList();
                yield new WhereConditionContainer(conditionString, processedValues);
            }
            case CONTAINS_STRING -> {
                String conditionString = String.format("LOWER(%s.%s) LIKE LOWER(?) ESCAPE '\\'", tableAlias, columnName);
                List<Object> processedValues = values.stream().map(value -> escapeLikeSpecialChars(value.toString())).map(value -> (Object)("%" + value + "%")).toList();
                yield new WhereConditionContainer(conditionString, processedValues);
            }
            case IN -> {
                String placeholders = String.join(", ", Collections.nCopies(values.size(), "?"));
                String conditionString = String.format("%s.%s IN(%s)", tableAlias, columnName, placeholders);
                yield new WhereConditionContainer(conditionString, values);
            }
            case NOT_IN -> {
                String placeholders = String.join(", ", Collections.nCopies(values.size(), "?"));
                String conditionString = String.format("%s.%s NOT IN(%s)", tableAlias, columnName, placeholders);
                yield new WhereConditionContainer(conditionString, values);
            }
            case IS_NULL -> {
                String conditionString = String.format("%s.%s IS NULL", tableAlias, columnName);
                yield new WhereConditionContainer(conditionString, values);
            }
            case IS_NOT_NULL -> {
                String conditionString = String.format("%s.%s IS NOT NULL", tableAlias, columnName);
                yield new WhereConditionContainer(conditionString, values);
            }
        };
    }

    /**
     * Escapes %, _ and \ for safe LIKE matching.
     */
    private static String escapeLikeSpecialChars(String input) {
        return input
                .replace("\\", "\\\\")  // Escape backslashes first
                .replace("%", "\\%")    // Escape wildcard %
                .replace("_", "\\_");   // Escape wildcard _
    }
}
