package in.muktashastra.core.util.filter;

import in.muktashastra.core.persistence.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public final class Filter implements Serializable {

    /** Page number for pagination (0-based) */
    private Integer page;

    /** Number of items per page */
    private Integer size;

    /** Entity status filter */
    private Status status;

    /** Entity on which filter is to be applied */
    private String entityName;

    @Singular
    private List<FilterTuple> filterTuples;
}
