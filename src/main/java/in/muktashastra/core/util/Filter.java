package in.muktashastra.core.util;

import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Filter class for querying entities with pagination and custom criteria.
 * 
 * @author Nilesh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Filter implements Serializable {
    /** Map of filter property names to their values */
    private Map<String, Object> filterPropertyValueMap;
    
    /** Page number for pagination (0-based) */
    private Integer page;
    
    /** Number of items per page */
    private Integer size;
    
    /** Entity status filter */
    private ENTITY_STATUS status;

    /**
     * Retrieves the value for a specific filter property.
     * 
     * @param filterPropertyName the property name
     * @return the property value or null if not found
     */
    public Object getFilterPropertyValue(String filterPropertyName) {
        if(null == filterPropertyValueMap) {
            return null;
        }
        return filterPropertyValueMap.get(filterPropertyName);
    }

    /**
     * Adds or updates a filter property value.
     * 
     * @param parameterName the property name
     * @param parameterValue the property value
     */
    public void putFilterPropertyValue(String parameterName, Object parameterValue) {
        if(null == filterPropertyValueMap) {
            filterPropertyValueMap = new HashMap<>();
        }
        filterPropertyValueMap.put(parameterName, parameterValue);
    }
}
