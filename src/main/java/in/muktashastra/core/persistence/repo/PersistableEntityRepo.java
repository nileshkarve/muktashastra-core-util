package in.muktashastra.core.persistence.repo;

import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.persistence.EntityId;
import in.muktashastra.core.persistence.PersistableEntity;
import in.muktashastra.core.util.filter.Filter;
import in.muktashastra.core.util.PagedResponse;

import java.util.List;
import java.util.Optional;

public interface PersistableEntityRepo<T extends PersistableEntity> {
    T save(T entity) throws CoreException;
    List<T> saveAll(List<T> entities) throws CoreException;
    Optional<T> get(EntityId id, String entityName);
    PagedResponse<T> getAll(Filter filter) throws CoreException;
    void delete(EntityId id, String entityName) throws CoreException;
    void deleteAll(List<T> entities) throws CoreException;
    T update(T entity) throws CoreException;
    List<T> updateAll(List<T> entities) throws CoreException;
}
