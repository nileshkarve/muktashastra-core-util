package in.muktashastra.core.util;

import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nilesh
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Filter implements Serializable {
    private Map<String, Object> filterPropertyValueMap;
    private Integer page;
    private Integer size;
    private ENTITY_STATUS status;

    public Object getFilterPropertyValue(String filterPropertyName) {
        if(null == filterPropertyValueMap) {
            return null;
        }
        return filterPropertyValueMap.get(filterPropertyName);
    }

    public void putFilterPropertyValue(String parameterName, Object parameterValue) {
        if(null == filterPropertyValueMap) {
            filterPropertyValueMap = new HashMap<>();
        }
        filterPropertyValueMap.put(parameterName, parameterValue);
    }
}
