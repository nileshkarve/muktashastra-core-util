package in.muktashastra.core.controller;

import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.persistence.model.EntityId;
import in.muktashastra.core.persistence.model.PersistableEntity;
import in.muktashastra.core.util.filter.Filter;
import in.muktashastra.core.util.filter.PaginationFilter;
import in.muktashastra.core.util.model.PagedResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Generic controller interface for CRUD operations on persistent entities.
 * 
 * @param <T> the type of persistent entity
 * @author Nilesh
 */
public interface PersistableEntityController<T extends PersistableEntity> {

    /**
     * Retrieves an entity by its EntityId.
     * 
     * @param id the entity EntityId
     * @return the entity
     */
    ResponseEntity<T> get(EntityId id);

    /**
     * Retrieves all entities with filtering and pagination.
     * 
     * @param filter filter criteria including pagination
     * @return paged response containing entities
     */
    ResponseEntity<PagedResponse<T>> getAllPaged(PaginationFilter filter) throws CoreException;

    /**
     * Retrieves all entities with filtering.
     *
     * @param filter filter criteria including pagination
     * @return list containing entities
     */
    ResponseEntity<List<T>> getAll(Filter filter) throws CoreException;

    /**
     * Inserts a new entity.
     * 
     * @param persistentEntityToAdd the entity to insert
     * @return the inserted entity
     */
    ResponseEntity<T> insert(T persistentEntityToAdd) throws CoreException;

    /**
     * Updates an existing entity.
     * 
     * @param persistentEntityToUpdate the entity to update
     * @return the updated entity
     */
    ResponseEntity<T> update(T persistentEntityToUpdate) throws CoreException;

    /**
     * Deletes an entity by ID.
     * 
     * @param persistentEntityIdToDelete the ID of entity to delete
     */
    ResponseEntity<T> delete(EntityId persistentEntityIdToDelete) throws CoreException;

    /**
     * Inserts multiple entities.
     * 
     * @param persistentEntitiesToAdd list of entities to insert
     * @return list of inserted entities
     */
    ResponseEntity<List<T>> insertAll(List<T> persistentEntitiesToAdd) throws CoreException;

    /**
     * Updates multiple entities.
     * 
     * @param persistentEntitiesToUpdate list of entities to update
     * @return list of updated entities
     */
    ResponseEntity<List<T>> updateAll(List<T> persistentEntitiesToUpdate) throws CoreException;

    /**
     * Deletes multiple entities by their IDs.
     * 
     * @param persistentEntityIdsToDelete list of entity IDs to delete
     */
    ResponseEntity<List<T>> deleteAll(List<T> persistentEntityIdsToDelete) throws CoreException;
}
