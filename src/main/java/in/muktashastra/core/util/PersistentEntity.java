package in.muktashastra.core.util;

import java.io.Serializable;

/**
 * Base interface for all persistent entities in the system.
 * Provides common functionality for entity identification.
 * 
 * @author Nilesh
 */
public interface PersistentEntity extends Serializable {
    
    /**
     * Sets the unique identifier for this entity.
     * 
     * @param id the unique identifier
     */
    void setId(String id);
    
    /**
     * Gets the entity name, defaults to simple class name.
     * 
     * @return the entity name
     */
    default String getEntityName() {
        return this.getClass().getSimpleName();
    }
}
