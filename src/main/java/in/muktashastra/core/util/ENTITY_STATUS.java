package in.muktashastra.core.util;

/**
 * Enumeration representing the various states of an entity lifecycle.
 * 
 * @author Nilesh
 */
public enum ENTITY_STATUS {
    /** Entity is newly created */
    NEW,
    
    /** Entity has been approved */
    APPROVED,
    
    /** Entity is active and operational */
    ACTIVE,
    
    /** Entity is temporarily inactive */
    INACTIVE,
    
    /** Entity has been rejected */
    REJECTED,
    
    /** Entity is disabled */
    DISABLED,
    
    /** Entity is marked for deletion */
    DELETED
}
