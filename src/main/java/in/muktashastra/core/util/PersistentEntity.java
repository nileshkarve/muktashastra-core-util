package in.muktashastra.core.util;

import java.io.Serializable;

/**
 * @author Nilesh
 *
 */
public interface PersistentEntity extends Serializable {
    void setId(String id);
    default String getEntityName() {
        return this.getClass().getSimpleName();
    }
}
