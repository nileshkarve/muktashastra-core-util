package in.muktashastra.core.persistence.repo;

import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.persistence.EntityId;
import in.muktashastra.core.persistence.PersistableEntity;
import in.muktashastra.core.persistence.Status;
import in.muktashastra.core.persistence.util.EntityMetadata;
import in.muktashastra.core.persistence.util.RelationalDatabaseEntityMetadataCache;
import in.muktashastra.core.util.CoreUtil;
import in.muktashastra.core.util.PagedResponse;
import in.muktashastra.core.util.filter.ComparisonOperator;
import in.muktashastra.core.util.filter.Filter;
import in.muktashastra.core.util.filter.FilterTuple;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class PersistableEntityRepoImpl<T extends PersistableEntity> implements PersistableEntityRepo<T> {

    private final JdbcTemplate applicationJdbcTemplate;
    private final CoreUtil coreUtil;
    private final int maximumParametersPerBatchQuery;

    @Override
    public T save(@NonNull T entity) throws CoreException {
        return saveAll(List.of(entity)).getFirst();
    }

    @Override
    public List<T> saveAll(@NonNull List<T> entities) throws CoreException {
        EntityMetadata entityMetaData = getEntityMetadata(entities);
        EntityMetadata.QueryMetadata insertQueryMetadata = entityMetaData.getInsertQueryMetadata();
        executeBatchUpdate(entities, entityMetaData, insertQueryMetadata);
        return entities;
    }

    @Override
    public Optional<T> get(@NonNull EntityId id, @NonNull String entityName) {
        EntityMetadata metaData = RelationalDatabaseEntityMetadataCache.getEntityMetaData(entityName);
        String selectSql = metaData.getSelectQueryMetadata().getQuery().concat(" WHERE id = ?");
        List<T> objects = applicationJdbcTemplate.query(selectSql, new EntityRowMapper<>(metaData), (Object) id.toBytes());
        return objects.isEmpty() ? Optional.empty() : Optional.of(objects.getFirst());
    }

    @Override
    public PagedResponse<T> getAll(Filter filter) throws CoreException {
        EntityMetadata metaData = RelationalDatabaseEntityMetadataCache.getEntityMetaData(filter.getEntityName());
        String selectSql = metaData.getSelectQueryMetadata().getQuery();
        List<Object> preparedStatementParameterValues = new ArrayList<>();
        String whereClause = buildWhereClauseAndPopulatePreparedStatementParamValues(preparedStatementParameterValues, filter, metaData);

        // Get total count
        String countSql = "SELECT COUNT(*) FROM " + metaData.getTableName() + whereClause;
        Long totalElements = applicationJdbcTemplate.queryForObject(countSql, Long.class, preparedStatementParameterValues.toArray());
        
        // Get paginated results
        String paginatedSql = selectSql.concat(whereClause).concat(" LIMIT ? OFFSET ?");
        List<Object> paginatedParams = new ArrayList<>(preparedStatementParameterValues);
        paginatedParams.add(filter.getSize());
        paginatedParams.add(filter.getPage() * filter.getSize());
        List<T> objects = applicationJdbcTemplate.query(paginatedSql, new EntityRowMapper<>(metaData), paginatedParams.toArray());
        return new PagedResponse<>(objects, filter.getPage(), filter.getSize(), totalElements);
    }

    @Override
    public void delete(@NonNull EntityId id, @NonNull String entityName) throws CoreException {
        T entity = get(id, entityName).orElseThrow(() -> new CoreException("Entity not found"));
        deleteAll(List.of(entity));
    }

    @Override
    public void deleteAll(@NonNull List<T> entities) throws CoreException {
        EntityMetadata entityMetaData = getEntityMetadata(entities);
        EntityMetadata.QueryMetadata deleteQueryMetadata = entityMetaData.getDeleteQueryMetadata();
        executeBatchUpdate(entities, entityMetaData, deleteQueryMetadata);
    }

    @Override
    public T update(@NonNull T entity) throws CoreException {
        return updateAll(List.of(entity)).getFirst();
    }

    @Override
    public List<T> updateAll(@NonNull List<T> entities) throws CoreException {
        EntityMetadata entityMetaData = getEntityMetadata(entities);
        EntityMetadata.QueryMetadata updateQueryMetadata = entityMetaData.getUpdateQueryMetadata();
        executeBatchUpdate(entities, entityMetaData, updateQueryMetadata);
        return entities;
    }

    private String buildWhereClauseAndPopulatePreparedStatementParamValues(List<Object> preparedStatementParameterValues, Filter filter, EntityMetadata metaData) throws CoreException {
        List<String> filterClauses = new ArrayList<>();
        populatePrimaryKeyFilter(filterClauses, preparedStatementParameterValues, filter, metaData);
        populateIndexedColumnFilters(filterClauses, preparedStatementParameterValues, filter, metaData);
        populateOtherColumnFilters(filterClauses, preparedStatementParameterValues, filter, metaData);
        return " WHERE " + String.join(System.lineSeparator().concat(" AND "), filterClauses);
    }

    private void populateOtherColumnFilters(List<String> filterClauses, List<Object> preparedStatementParameterValues, Filter filter, EntityMetadata metaData) {
        Map<String, List<FilterTuple>> filteredFieldTupleMap = groupFilterTuplesByFieldName(filter);
        List<String> nonIndexedColumns = metaData.getFieldMetadataList().stream().filter(fieldMetaData -> !fieldMetaData.primaryKey() && fieldMetaData.indexSequenceNumber()<1 && filteredFieldTupleMap.containsKey(fieldMetaData.field().getName())).map(EntityMetadata.FieldMetadata::columnName).toList();
        for(String nonIndexedColumn : nonIndexedColumns) {
            EntityMetadata.FieldMetadata fieldMetadata = metaData.getFieldMetadata(nonIndexedColumn);
            List<FilterTuple> filterTuples = filteredFieldTupleMap.get(fieldMetadata.field().getName());
            List<String> filterClausesForField = new ArrayList<>();
            for(FilterTuple filterTuple : filterTuples) {
                ComparisonOperator.PreparedStatementClause statementClause = filterTuple.getComparisonOperator().generatePreparedStatementClause(nonIndexedColumn, filterTuple.getFilterValues());
                filterClausesForField.add(statementClause.getPreparedStatementSql());
                preparedStatementParameterValues.addAll(statementClause.getValues());
            }
            filterClauses.add(String.join(" AND ", filterClausesForField));
        }
    }

    private void populateIndexedColumnFilters(List<String> filterClauses, List<Object> preparedStatementParameterValues, Filter filter, EntityMetadata metaData) {
        Map<String, List<FilterTuple>> filteredFieldTupleMap = groupFilterTuplesByFieldName(filter);
        List<String> indexedColumns = metaData.getFieldMetadataList().stream().filter(fieldMetaData -> fieldMetaData.indexSequenceNumber()>0 && filteredFieldTupleMap.containsKey(fieldMetaData.field().getName())).sorted(Comparator.comparing(EntityMetadata.FieldMetadata::indexSequenceNumber)).map(EntityMetadata.FieldMetadata::columnName).toList();
        for(String indexedColumn : indexedColumns) {
            EntityMetadata.FieldMetadata fieldMetadata = metaData.getFieldMetadata(indexedColumn);
            List<FilterTuple> filterTuples = filteredFieldTupleMap.get(fieldMetadata.field().getName());
            List<String> filterClausesForField = new ArrayList<>();
            for(FilterTuple filterTuple : filterTuples) {
                ComparisonOperator.PreparedStatementClause statementClause = filterTuple.getComparisonOperator().generatePreparedStatementClause(indexedColumn, filterTuple.getFilterValues());
                filterClausesForField.add(statementClause.getPreparedStatementSql());
                preparedStatementParameterValues.addAll(statementClause.getValues());
            }
            filterClauses.add(String.join(" AND ", filterClausesForField));
        }
    }

    private Map<String, List<FilterTuple>> groupFilterTuplesByFieldName(Filter filter) {
        return filter.getFilterTuples().stream().collect(Collectors.groupingBy(FilterTuple::getFieldName));
    }

    private void populatePrimaryKeyFilter(List<String> filterClauses, List<Object> preparedStatementParameterValues, Filter filter, EntityMetadata metaData) throws CoreException {
        String primaryKeyFieldName = metaData.getPrimaryKeyFieldName();
        List<FilterTuple> primaryColumnFilters = filter.getFilterTuples().stream().filter(filterTuple -> filterTuple.getFieldName().equals(primaryKeyFieldName)).toList();
        if(!primaryColumnFilters.isEmpty()) {
            List<String> filterClausesForField = new ArrayList<>();
            primaryColumnFilters.forEach(filterTuple -> {
                List<Object> idValues = filterTuple.getFilterValues().stream().map(value -> EntityId.fromString(value.toString())).map(EntityId::toBytes).collect(Collectors.toList());
                ComparisonOperator.PreparedStatementClause statementClause = filterTuple.getComparisonOperator().generatePreparedStatementClause(metaData.getPrimaryKeyColumn(), idValues);
                filterClausesForField.add(statementClause.getPreparedStatementSql());
                preparedStatementParameterValues.addAll(statementClause.getValues());

            });
            filterClauses.add(String.join(" AND ", filterClausesForField));
        }
    }

    private void executeBatchUpdate(List<T> entities, EntityMetadata entityMetaData, EntityMetadata.QueryMetadata queryMetadata) {
        List<List<T>> entityBatches = coreUtil.createBatches(entities, maximumParametersPerBatchQuery/queryMetadata.getNumberOfParameters());
        for(List<T> entityBatch : entityBatches) {
            applicationJdbcTemplate.batchUpdate(queryMetadata.getQuery(), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    T entity = entityBatch.get(i);
                    try {
                        for(int j = 1; j <= queryMetadata.getNumberOfParameters(); j++) {
                            Object value = deriveFieldValue(entityMetaData.getFieldMetadata(queryMetadata.getPreparedStatementParamAtIndex(j)), entity);
                            if (value instanceof byte[]) {
                                ps.setBytes(j, (byte[]) value);
                            } else {
                                ps.setObject(j, value);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public int getBatchSize() {
                    return entityBatch.size();
                }
            });
        }
    }

    private Object deriveFieldValue(EntityMetadata.FieldMetadata fieldMetadata, T entity) throws IllegalAccessException {
        return switch (fieldMetadata.javaConversionType()) {
            case ENTITY_ID -> {
                EntityId id = (EntityId) fieldMetadata.field().get(entity);
                yield id.toBytes();
            }
            case STRING, BIG_DECIMAL, INTEGER, LOCAL_DATE, INSTANT -> fieldMetadata.field().get(entity);
            case ENTITY_STATUS -> ((Status) fieldMetadata.field().get(entity)).name();
            case BOOLEAN -> ((Boolean) fieldMetadata.field().get(entity)).toString();
        };
    }

    private EntityMetadata getEntityMetadata(List<T> entities) throws CoreException {
        if (entities.isEmpty()) {
            throw new CoreException("Entity list to be saved cannot be empty");
        }
        return RelationalDatabaseEntityMetadataCache.getEntityMetaData(entities.getFirst().getEntityName());
    }
}
