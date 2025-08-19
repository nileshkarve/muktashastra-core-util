package in.muktashastra.core.util;

import java.util.List;

/**
 * Interface for generating unique identifiers for entities.
 * 
 * @author Nilesh
 */
public interface IdGenerator {
    
    String generateId(String entityName);
    
    String generateId(PersistentEntity entity);
    
    void generateAndSetId(PersistentEntity entity);
    
    <T extends PersistentEntity> void generateAndSetIds(List<T> entities);
    
    List<String> generateIds(String entityName, int count);
}