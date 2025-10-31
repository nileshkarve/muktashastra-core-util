package in.muktashastra.core.util.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@ToString
public class FilterTuple implements Serializable {
    private final String fieldName;
    @Singular
    private final List<Object> filterValues;
    private final Operator comparisonOperator;

    @JsonCreator
    public FilterTuple(@JsonProperty("fieldName") String fieldName, @JsonProperty("filterValues") List<Object> filterValues, @JsonProperty("comparisonOperator") Operator comparisonOperator) {
        this.fieldName = fieldName;
        this.filterValues = filterValues;
        this.comparisonOperator = comparisonOperator;
    }
}
