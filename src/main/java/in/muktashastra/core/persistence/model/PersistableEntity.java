package in.muktashastra.core.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Base interface for all persistable entities in the system.
 * Provides common functionality for entity identification.
 * 
 * @author Nilesh
 */
public interface PersistableEntity extends Serializable {
    
    /**
     * Sets the unique identifier for this entity.
     * 
     * @param id the unique identifier
     */
    void setId(EntityId id);
    
    /**
     * Sets the status for this entity.
     * 
     * @param status the status
     */
    void setStatus(Status status);

    /**
     * Gets the entity name, defaults to simple class name.
     * 
     * @return the entity name
     */
    @JsonIgnore
    default String getEntityName() {
        return this.getClass().getSimpleName();
    }
}
