package in.muktashastra.core.persistence.relationalstore.metadata;

import in.muktashastra.core.exception.CoreException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

class UpdateQueryMetadata implements QueryMetadata {
    @Getter
    private final String query;
    private final Map<Integer, String> preparedStatementParamIndexColumnNameMap = new HashMap<>();

    UpdateQueryMetadata(String tableName, Map<String, FieldMetadata> columnMetadataMap) throws CoreException {
        StringBuilder builder = new StringBuilder();
        int indexCounter = 1;
        for(FieldMetadata field : columnMetadataMap.values()) {
            if(field.primaryKey())
                continue;
            if(!builder.isEmpty()) {
                builder.append(", ");
            }
            builder.append(field.columnName()).append(" = ?");
            this.preparedStatementParamIndexColumnNameMap.put(indexCounter, field.columnName());
            indexCounter++;
        }
        String primaryKeyColumnName = columnMetadataMap.values().stream().filter(FieldMetadata::primaryKey).findFirst().map(FieldMetadata::columnName).orElseThrow(() -> new CoreException("Unable to find primary key column in table name : " + tableName));
        this.query = String.format("UPDATE %s SET %s WHERE %s = ?", tableName, builder, primaryKeyColumnName);
        this.preparedStatementParamIndexColumnNameMap.put(indexCounter, primaryKeyColumnName);
    }

    @Override
    public int getNumberOfParameters() {
        return this.preparedStatementParamIndexColumnNameMap.size();
    }

    @Override
    public String getColumnNameOfPreparedStatementParamAt(int index) {
        return preparedStatementParamIndexColumnNameMap.get(index);
    }
}
