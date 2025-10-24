package in.muktashastra.core.persistence.relationalstore.repo;

import in.muktashastra.core.exception.CoreRuntimeException;
import in.muktashastra.core.persistence.model.EntityId;
import in.muktashastra.core.persistence.model.Status;
import in.muktashastra.core.persistence.relationalstore.metadata.EntityMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class EntityRowMapper<T> implements RowMapper<T> {

    private final EntityMetadata entityMetaData;

    @Override
    public T mapRow(ResultSet rs, int rowNum) {
        try {
            @SuppressWarnings("unchecked")
            T instance = (T) entityMetaData.getType().getDeclaredConstructor().newInstance();

            entityMetaData.getAllColumnNames().forEach(columnName -> {
                try {
                    setField(instance, rs, columnName);
                } catch (IllegalAccessException | SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return instance;
        } catch (Exception e) {
            throw new CoreRuntimeException("Failed to map row to entity " + entityMetaData.getType().getName(), e);
        }
    }

    private void setField(T instance, ResultSet rs, String columnName) throws SQLException, IllegalAccessException {
        Field field = entityMetaData.getFieldForColumn(columnName);
        switch (entityMetaData.getJavaConversionTypeOfColumn(columnName)) {
            case ENTITY_ID -> {
                field.set(instance, EntityId.fromBytes(rs.getBytes(columnName)));
            }
            case STRING -> {
                field.set(instance, rs.getString(columnName));
            }
            case BIG_DECIMAL -> {
                field.set(instance, rs.getBigDecimal(columnName));
            }
            case INTEGER -> {
                field.set(instance, rs.getInt(columnName));
            }
            case LOCAL_DATE -> {
                field.set(instance, rs.getDate(columnName).toLocalDate());
            }
            case INSTANT -> {
                field.set(instance, rs.getTimestamp(columnName).toInstant());
            }
            case ENTITY_STATUS -> {
                field.set(instance, Status.valueOf(rs.getString(columnName)));
            }
            case BOOLEAN -> {
                field.set(instance, Boolean.valueOf(rs.getString(columnName)));
            }
        }
    }
}
