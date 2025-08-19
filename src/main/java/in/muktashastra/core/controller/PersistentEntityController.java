package in.muktashastra.core.controller;

import in.muktashastra.core.util.Filter;
import in.muktashastra.core.util.PagedResponse;
import in.muktashastra.core.util.PersistentEntity;

import java.util.List;

/**
 * @author Nilesh
 *
 */
public interface PersistentEntityController<T extends PersistentEntity> {

    T get(String id);

    PagedResponse<T> getAll(Filter filter);

    T insert(T persistentEntityToAdd);

    T update(T persistentEntityToUpdate);

    T delete(String persistentEntityIdToDelete);

    List<T> insertAll(List<T> persistentEntitiesToAdd);

    List<T> updateAll(List<T> persistentEntitiesToUpdate);

    List<T> deleteAll(List<String> persistentEntityIdsToDelete);
}
