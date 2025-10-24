package in.muktashastra.core.persistence.relationalstore.metadata;

import lombok.Getter;

import java.util.Map;

class SelectQueryMetadata implements QueryMetadata {

    @Getter
    private final String query;

    SelectQueryMetadata(String entityName, String tableName, Map<String, FieldMetadata> columnMetadataMap) {
        StringBuilder builder = new StringBuilder();
        for(FieldMetadata field : columnMetadataMap.values()) {
            if(!builder.isEmpty()) {
                builder.append(",").append(System.lineSeparator());
            }
            builder.append(entityName).append(".").append(field.columnName()).append(" AS ").append(field.field().getName());
        }
        this.query = String.format("SELECT %s FROM %s %s", builder, tableName, entityName);
    }

    @Override
    public int getNumberOfParameters() {
        return 0;
    }

    @Override
    public String getColumnNameOfPreparedStatementParamAt(int index) {
        return null;
    }
}
