package in.muktashastra.core.controller;

import in.muktashastra.core.util.Filter;
import in.muktashastra.core.util.PagedResponse;
import in.muktashastra.core.util.PersistentEntity;

import java.util.List;

/**
 * Generic controller interface for CRUD operations on persistent entities.
 * 
 * @param <T> the type of persistent entity
 * @author Nilesh
 */
public interface PersistentEntityController<T extends PersistentEntity> {

    /**
     * Retrieves an entity by its ID.
     * 
     * @param id the entity ID
     * @return the entity
     */
    T get(String id);

    /**
     * Retrieves all entities with filtering and pagination.
     * 
     * @param filter filter criteria including pagination
     * @return paged response containing entities
     */
    PagedResponse<T> getAll(Filter filter);

    /**
     * Inserts a new entity.
     * 
     * @param persistentEntityToAdd the entity to insert
     * @return the inserted entity
     */
    T insert(T persistentEntityToAdd);

    /**
     * Updates an existing entity.
     * 
     * @param persistentEntityToUpdate the entity to update
     * @return the updated entity
     */
    T update(T persistentEntityToUpdate);

    /**
     * Deletes an entity by ID.
     * 
     * @param persistentEntityIdToDelete the ID of entity to delete
     * @return the deleted entity
     */
    T delete(String persistentEntityIdToDelete);

    /**
     * Inserts multiple entities.
     * 
     * @param persistentEntitiesToAdd list of entities to insert
     * @return list of inserted entities
     */
    List<T> insertAll(List<T> persistentEntitiesToAdd);

    /**
     * Updates multiple entities.
     * 
     * @param persistentEntitiesToUpdate list of entities to update
     * @return list of updated entities
     */
    List<T> updateAll(List<T> persistentEntitiesToUpdate);

    /**
     * Deletes multiple entities by their IDs.
     * 
     * @param persistentEntityIdsToDelete list of entity IDs to delete
     * @return list of deleted entities
     */
    List<T> deleteAll(List<String> persistentEntityIdsToDelete);
}
