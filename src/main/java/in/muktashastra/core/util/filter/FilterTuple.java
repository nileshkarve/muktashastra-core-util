package in.muktashastra.core.util.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterTuple implements Serializable {
    private String fieldName;
    private Object value;
    private ComparisonOperator comparisonOperator;
}
