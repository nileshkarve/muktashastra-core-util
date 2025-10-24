package in.muktashastra.core.persistence.relationalstore.metadata;

public interface QueryMetadata {
    String getQuery();
    int getNumberOfParameters();
    String getColumnNameOfPreparedStatementParamAt(int index);
}
