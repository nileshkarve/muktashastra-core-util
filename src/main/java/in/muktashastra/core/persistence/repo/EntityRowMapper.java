package in.muktashastra.core.persistence.repo;

import in.muktashastra.core.exception.CoreRuntimeException;
import in.muktashastra.core.persistence.EntityId;
import in.muktashastra.core.persistence.Status;
import in.muktashastra.core.persistence.util.EntityMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class EntityRowMapper<T> implements RowMapper<T> {

    private final EntityMetadata entityMetaData;

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            @SuppressWarnings("unchecked")
            T instance = (T) entityMetaData.getType().getDeclaredConstructor().newInstance();

            entityMetaData.getFieldMetadataList().forEach(fieldMetadata -> {
                try {
                    setField(instance, rs, fieldMetadata);
                } catch (IllegalAccessException | SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return instance;
        } catch (Exception e) {
            throw new CoreRuntimeException("Failed to map row to entity " + entityMetaData.getType().getName(), e);
        }
    }

    private void setField(T instance, ResultSet rs, EntityMetadata.FieldMetadata fieldMetadata) throws SQLException, IllegalAccessException {
        switch (fieldMetadata.javaConversionType()) {
            case ENTITY_ID -> {
                fieldMetadata.field().set(instance, EntityId.fromBytes(rs.getBytes(fieldMetadata.columnName())));
            }
            case STRING -> {
                fieldMetadata.field().set(instance, rs.getString(fieldMetadata.columnName()));
            }
            case BIG_DECIMAL -> {
                fieldMetadata.field().set(instance, rs.getBigDecimal(fieldMetadata.columnName()));
            }
            case INTEGER -> {
                fieldMetadata.field().set(instance, rs.getInt(fieldMetadata.columnName()));
            }
            case LOCAL_DATE -> {
                fieldMetadata.field().set(instance, rs.getDate(fieldMetadata.columnName()).toLocalDate());
            }
            case INSTANT -> {
                fieldMetadata.field().set(instance, rs.getTimestamp(fieldMetadata.columnName()).toInstant());
            }
            case ENTITY_STATUS -> {
                fieldMetadata.field().set(instance, Status.valueOf(rs.getString(fieldMetadata.columnName())));
            }
            case BOOLEAN -> {
                fieldMetadata.field().set(instance, Boolean.valueOf(rs.getString(fieldMetadata.columnName())));
            }
        }
    }
}
