package in.muktashastra.core.persistence.repo;

import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.persistence.model.EntityId;
import in.muktashastra.core.persistence.model.PersistableEntity;
import in.muktashastra.core.util.model.PagedResponse;
import in.muktashastra.core.util.filter.Filter;

import java.util.List;
import java.util.Optional;

public interface PersistableEntityRepo<T extends PersistableEntity> {
    T insert(T entity) throws CoreException;
    List<T> insertAll(List<T> entities) throws CoreException;
    Optional<T> get(EntityId id);
    PagedResponse<T> getAllPaged(Filter filter) throws CoreException;
    List<T> getAll(Filter filter) throws CoreException;
    T delete(EntityId id) throws CoreException;
    List<T> deleteAll(List<T> entities) throws CoreException;
    T update(T entity) throws CoreException;
    List<T> updateAll(List<T> entities) throws CoreException;
}
