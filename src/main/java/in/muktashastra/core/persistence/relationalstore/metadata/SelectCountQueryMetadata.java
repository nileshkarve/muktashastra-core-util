package in.muktashastra.core.persistence.relationalstore.metadata;

import lombok.Getter;

class SelectCountQueryMetadata implements QueryMetadata {

    @Getter
    private final String query;

    SelectCountQueryMetadata(String entityName, String tableName) {
        this.query = String.format("SELECT COUNT(*) FROM  %s %s", tableName, entityName);
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
