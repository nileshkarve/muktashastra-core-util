package in.muktashastra.core.util.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterTuple implements Serializable {
    private String fieldName;
    @Singular
    private List<Object> filterValues;
    private ComparisonOperator comparisonOperator;
}
