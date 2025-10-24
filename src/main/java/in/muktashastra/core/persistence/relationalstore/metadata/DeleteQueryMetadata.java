package in.muktashastra.core.persistence.relationalstore.metadata;

import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.persistence.model.Status;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

class DeleteQueryMetadata implements QueryMetadata {
    @Getter
    private final String query;
    private final Map<Integer, String> preparedStatementParamIndexColumnNameMap = new HashMap<>();
    
    DeleteQueryMetadata(String tableName, Map<String, FieldMetadata> columnMetadataMap) throws CoreException {
        String statusColumn = columnMetadataMap.values().stream().filter(FieldMetadata::statusColum).findFirst().map(FieldMetadata::columnName).orElseThrow(() -> new CoreException("Unable to find status column in entity in table name : " + tableName));
        String primaryKeyColumnName = columnMetadataMap.values().stream().filter(FieldMetadata::primaryKey).findFirst().map(FieldMetadata::columnName).orElseThrow(() -> new CoreException("Unable to find primary key column in table name : " + tableName));
        this.query = String.format("UPDATE %s SET %s = '%s' WHERE %s = ?", tableName, statusColumn, Status.DELETED.name(), primaryKeyColumnName);
        this.preparedStatementParamIndexColumnNameMap.put(1, primaryKeyColumnName);
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
