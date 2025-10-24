package in.muktashastra.core.persistence.relationalstore.metadata;

import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.persistence.util.JavaConversionType;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class EntityMetadata {

    @Getter
    private final Class<?> type;
    @Getter
    private final String tableName;
    @Getter
    private final String entityName;
    @Getter
    private final QueryMetadata insertQueryMetadata;
    @Getter
    private final QueryMetadata updateQueryMetadata;
    @Getter
    private final QueryMetadata deleteQueryMetadata;
    @Getter
    private final QueryMetadata selectQueryMetadata;
    @Getter
    private final QueryMetadata selectCountQueryMetadata;

    private final Map<String, FieldMetadata> columnMetadataMap;
    private final Map<String, FieldMetadata> fieldMetadataMap;

    EntityMetadata(Class<?> type, String tableName, Map<String, FieldMetadata> columnMetadataMap, Map<String, FieldMetadata> fieldMetadataMap) throws CoreException {
        this.type = type;
        this.entityName = type.getSimpleName();
        this.tableName = tableName;
        this.columnMetadataMap = columnMetadataMap;
        this.fieldMetadataMap = fieldMetadataMap;
        this.insertQueryMetadata = new InsertQueryMetadata(tableName, columnMetadataMap);
        this.updateQueryMetadata = new UpdateQueryMetadata(tableName, columnMetadataMap);
        this.deleteQueryMetadata = new DeleteQueryMetadata(tableName, columnMetadataMap);
        this.selectQueryMetadata = new SelectQueryMetadata(type.getSimpleName(), tableName, columnMetadataMap);
        this.selectCountQueryMetadata = new SelectCountQueryMetadata(type.getSimpleName(), tableName);
    }

    public JavaConversionType getJavaConversionTypeOfColumn(String columnName) {
        return columnMetadataMap.get(columnName).javaConversionType();
    }

    public JavaConversionType getJavaConversionTypeOfField(String fieldName) {
        return fieldMetadataMap.get(fieldName).javaConversionType();
    }

    public Field getFieldForColumn(String columnName) {
        return columnMetadataMap.get(columnName).field();
    }

    public String getPrimaryKeyFieldName() {
        return fieldMetadataMap.values().stream().filter(FieldMetadata::primaryKey).map(fieldMetadata -> fieldMetadata.field().getName()).findFirst().orElse(null);
    }

    public String getColumnNameForField(String fieldName) throws CoreException {
        if(fieldMetadataMap.containsKey(fieldName))
            return fieldMetadataMap.get(fieldName).columnName();
        else throw new CoreException("Unable to find column name for field name : " + fieldName);
    }

    public int getIndexSequenceNumberForField(String fieldName) {
        return fieldMetadataMap.get(fieldName).indexSequenceNumber();
    }

    public Set<String> getAllColumnNames() {
        return columnMetadataMap.keySet();
    }
}
