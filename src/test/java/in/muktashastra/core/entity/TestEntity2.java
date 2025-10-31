package in.muktashastra.core.entity;

import in.muktashastra.core.persistence.model.EntityId;
import in.muktashastra.core.persistence.model.PersistableEntity;
import in.muktashastra.core.persistence.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestEntity2 implements PersistableEntity {
    private EntityId id;
    private Status status;
}
