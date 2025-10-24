package in.muktashastra.core.controller;

import in.muktashastra.core.entity.TestEntity;
import in.muktashastra.core.persistence.relationalstore.repo.CorePersistableEntityRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testEntity")
public class TestEntityController extends CorePersistableEntityController<TestEntity> {

    private final CorePersistableEntityRepo<TestEntity> testEntityRepo;

    public TestEntityController(CorePersistableEntityRepo<TestEntity> repo) {
        super(repo, TestEntity.class);
        this.testEntityRepo = repo;
    }
}
