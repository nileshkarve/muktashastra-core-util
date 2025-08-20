package in.muktashastra.core.util;

import java.util.List;

/**
 * Interface for generating unique identifiers for entities.
 * 
 * @author Nilesh
 */
public interface IdGenerator {
    
    /**
     * Generates a unique ID for the specified entity name.
     * 
     * @param entityName the name of the entity
     * @return unique identifier string
     */
    String generateId(String entityName);
    
    /**
     * Generates a unique ID for the given entity.
     * 
     * @param entity the persistent entity
     * @return unique identifier string
     */
    String generateId(PersistentEntity entity);
    
    /**
     * Generates and sets a unique ID for the entity.
     * 
     * @param entity the entity to set ID for
     */
    void generateAndSetId(PersistentEntity entity);
    
    /**
     * Generates and sets unique IDs for multiple entities.
     * 
     * @param <T> the entity type
     * @param entities list of entities to set IDs for
     */
    <T extends PersistentEntity> void generateAndSetIds(List<T> entities);
    
    /**
     * Generates multiple unique IDs for the specified entity name.
     * 
     * @param entityName the name of the entity
     * @param count number of IDs to generate
     * @return list of unique identifier strings
     */
    List<String> generateIds(String entityName, int count);
}