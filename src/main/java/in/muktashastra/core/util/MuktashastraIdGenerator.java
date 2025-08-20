package in.muktashastra.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Implementation of IdGenerator that creates unique identifiers using UUID and timestamp.
 * 
 * @author Nilesh
 */
public class MuktashastraIdGenerator implements IdGenerator {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.TIMESTAMP_FORMAT);

    @Override
    public String generateId(String entityName) {
        return entityName + UUID.randomUUID() + LocalDateTime.now().format(formatter);
    }

    @Override
    public String generateId(PersistentEntity entity) {
        return generateId(entity.getEntityName());
    }

    @Override
    public void generateAndSetId(PersistentEntity entity) {
        generateAndSetIds(List.of(entity));
    }

    @Override
    public <T extends PersistentEntity> void generateAndSetIds(List<T> entities) {
        entities.forEach(entity -> {
            entity.setId(generateId(entity.getEntityName()));
        });
    }

    @Override
    public List<String> generateIds(String entityName, int count) {
        List<String> ids = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            ids.add(generateId(entityName));
        }
        return ids;
    }
}
