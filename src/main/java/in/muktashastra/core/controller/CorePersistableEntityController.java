package in.muktashastra.core.controller;

import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.persistence.model.EntityId;
import in.muktashastra.core.persistence.model.PersistableEntity;
import in.muktashastra.core.persistence.repo.PersistableEntityRepo;
import in.muktashastra.core.util.model.PagedResponse;
import in.muktashastra.core.util.filter.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
    public T get(@PathVariable EntityId id) {
        log.info("Fetching entity of type {} with id {}", getPersistableEntityName(), id.toString());
        return repo.get(id).orElse(null);
    }

    @PostMapping("/search")
    @Override
    public PagedResponse<T> getAll(@RequestBody Filter filter) throws CoreException {
        log.info("Fetching paged list of entities of type {} with filter {}", getPersistableEntityName(), filter);
        return repo.getAllPaged(filter);
    }

    @PostMapping(path = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public T insert(@RequestBody T persistableEntityToAdd) throws CoreException {
        log.info("Inserting persistable entity of type {} : {}", getPersistableEntityName(), persistableEntityToAdd);
        return repo.insert(persistableEntityToAdd);
    }

    @PostMapping("/update")
    @Override
    public T update(@RequestBody T persistableEntityToUpdate) throws CoreException {
        log.info("Updating persistable entity of type {} : {}", getPersistableEntityName(), persistableEntityToUpdate);
        return repo.update(persistableEntityToUpdate);
    }

    @PostMapping("/delete/{persistableEntityIdToDelete}")
    @Override
    public T delete(@PathVariable EntityId persistableEntityIdToDelete) throws CoreException {
        log.info("Deleting persistable entity of type {} : {}", getPersistableEntityName(), persistableEntityIdToDelete);
        return repo.delete(persistableEntityIdToDelete);
    }

    @PostMapping("/insertAll")
    @Override
    public List<T> insertAll(@RequestBody List<T> persistableEntitiesToAdd) throws CoreException {
        log.info("Total persistable entities being added of type {} : {}", getPersistableEntityName(), persistableEntitiesToAdd.size());
        return repo.insertAll(persistableEntitiesToAdd);
    }

    @PostMapping("/updateAll")
    @Override
    public List<T> updateAll(@RequestBody List<T> persistableEntitiesToUpdate) throws CoreException {
        log.info("Total persistable entities being updated of type {} : {}", getPersistableEntityName(), persistableEntitiesToUpdate.size());
        return repo.updateAll(persistableEntitiesToUpdate);
    }

    @PostMapping("/deleteAll")
    @Override
    public List<T> deleteAll(@RequestBody List<T> persistableEntityIdsToDelete) throws CoreException {
        log.info("Total persistable entities being deleted of type {} : {}", getPersistableEntityName(), persistableEntityIdsToDelete.size());
        return repo.deleteAll(persistableEntityIdsToDelete);
    }

    private String getPersistableEntityName() {
        return entityClass.getSimpleName();
    }
}
