package in.muktashastra.core.controller;

import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.persistence.model.EntityId;
import in.muktashastra.core.persistence.model.PersistableEntity;
import in.muktashastra.core.persistence.repo.PersistableEntityRepo;
import in.muktashastra.core.util.filter.PaginationFilter;
import in.muktashastra.core.util.model.PagedResponse;
import in.muktashastra.core.util.filter.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CorePersistableEntityController<T extends PersistableEntity> implements PersistableEntityController<T> {

    private final PersistableEntityRepo<T> repo;
    private final Class<T> entityClass;


    @GetMapping("/{id}")
    @Override
    public ResponseEntity<T> get(@PathVariable EntityId id) {
        log.info("Fetching entity of type {} with id {}", getPersistableEntityName(), id.toString());
        T entity = repo.get(id).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

    @PostMapping(path = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<PagedResponse<T>> getAllPaged(@RequestBody PaginationFilter filter) throws CoreException {
        log.info("Fetching paged list of entities of type {} with filter {}", getPersistableEntityName(), filter);
        PagedResponse<T> entities = repo.getAllPaged(filter);
        ResponseEntity<PagedResponse<T>> e = ResponseEntity.status(entities == null || null == entities.getContent() || entities.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(entities);
        return ResponseEntity.status(HttpStatus.OK).body(entities);
    }

    @PostMapping(path = "/getAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<T>> getAll(@RequestBody Filter filter) throws CoreException {
        log.info("Fetching list of entities of type {} with filter {}", getPersistableEntityName(), filter);
        List<T> entities = repo.getAll(filter);
        return ResponseEntity.status(HttpStatus.OK).body(entities);
    }

    @PostMapping(path = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<T> insert(@RequestBody T persistableEntityToAdd) throws CoreException {
        log.info("Inserting persistable entity of type {} : {}", getPersistableEntityName(), persistableEntityToAdd);
        T entity = repo.insert(persistableEntityToAdd);
        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

    @PostMapping("/update")
    @Override
    public ResponseEntity<T> update(@RequestBody T persistableEntityToUpdate) throws CoreException {
        log.info("Updating persistable entity of type {} : {}", getPersistableEntityName(), persistableEntityToUpdate);
        T entity = repo.update(persistableEntityToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

    @PostMapping("/delete/{persistableEntityIdToDelete}")
    @Override
    public ResponseEntity<T> delete(@PathVariable EntityId persistableEntityIdToDelete) throws CoreException {
        log.info("Deleting persistable entity of type {} : {}", getPersistableEntityName(), persistableEntityIdToDelete);
        T entity = repo.delete(persistableEntityIdToDelete);
        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

    @PostMapping("/insertAll")
    @Override
    public ResponseEntity<List<T>> insertAll(@RequestBody List<T> persistableEntitiesToAdd) throws CoreException {
        log.info("Total persistable entities being added of type {} : {}", getPersistableEntityName(), persistableEntitiesToAdd.size());
        List<T> entities = repo.insertAll(persistableEntitiesToAdd);
        return ResponseEntity.status(HttpStatus.OK).body(entities);
    }

    @PostMapping("/updateAll")
    @Override
    public ResponseEntity<List<T>> updateAll(@RequestBody List<T> persistableEntitiesToUpdate) throws CoreException {
        log.info("Total persistable entities being updated of type {} : {}", getPersistableEntityName(), persistableEntitiesToUpdate.size());
        List<T> entities = repo.updateAll(persistableEntitiesToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(entities);
    }

    @PostMapping("/deleteAll")
    @Override
    public ResponseEntity<List<T>> deleteAll(@RequestBody List<T> persistableEntityIdsToDelete) throws CoreException {
        log.info("Total persistable entities being deleted of type {} : {}", getPersistableEntityName(), persistableEntityIdsToDelete.size());
        List<T> entities = repo.deleteAll(persistableEntityIdsToDelete);
        return ResponseEntity.status(HttpStatus.OK).body(entities);
    }

    private String getPersistableEntityName() {
        return entityClass.getSimpleName();
    }
}
