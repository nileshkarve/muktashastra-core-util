package in.muktashastra.core.persistence.relationalstore;

import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.persistence.relationalstore.metadata.EntityMetadata;
import in.muktashastra.core.persistence.relationalstore.metadata.EntityMetadataGenerator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RelationalDatabaseEntityMetadataCache {

    private static final Map<String, EntityMetadata> entityFieldCache = new ConcurrentHashMap<>();

    public static void loadEntityMetaData(Class<?> clazz) throws CoreException {
        EntityMetadata metaData = generateEntityMetaData(clazz);
        entityFieldCache.put(metaData.getEntityName(), metaData);
    }

    public static EntityMetadata getEntityMetaData(String entityName) {
        return entityFieldCache.get(entityName);
    }

    private static EntityMetadata generateEntityMetaData(Class<?> clazz) throws CoreException {
        return EntityMetadataGenerator.generate(clazz);
    }
}