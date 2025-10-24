package in.muktashastra.core.persistence.relationalstore.repo;

import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.persistence.model.EntityId;
import in.muktashastra.core.persistence.model.PersistableEntity;
import in.muktashastra.core.persistence.model.Status;
import in.muktashastra.core.persistence.relationalstore.RelationalDatabaseEntityMetadataCache;
import in.muktashastra.core.persistence.relationalstore.metadata.EntityMetadata;
import in.muktashastra.core.persistence.relationalstore.metadata.QueryMetadata;
import in.muktashastra.core.persistence.repo.PersistableEntityRepo;
import in.muktashastra.core.persistence.util.JavaConversionType;
import in.muktashastra.core.util.CoreUtil;
import in.muktashastra.core.util.filter.Filter;
import in.muktashastra.core.util.filter.FilterTuple;
import in.muktashastra.core.util.model.PagedResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class CorePersistableEntityRepo<T extends PersistableEntity> implements PersistableEntityRepo<T> {

    private final JdbcTemplate applicationJdbcTemplate;
    private final CoreUtil coreUtil;
    private final Class<T> entityClass;
    private final int maximumPreparedStatementParametersPerBatch;

    @Override
    public T insert(@NonNull T entity) throws CoreException {
        return insertAll(List.of(entity)).getFirst();
    }

    @Override
    public List<T> insertAll(@NonNull List<T> entities) throws CoreException {
        EntityMetadata entityMetaData = getEntityMetadata(entities);
        QueryMetadata insertQueryMetadata = entityMetaData.getInsertQueryMetadata();
        entities.forEach(persistableEntityToAdd -> persistableEntityToAdd.setId(EntityId.generate()));
        executeBatchUpdate(entities, entityMetaData, insertQueryMetadata);
        return entities;
    }

    @Override
    public Optional<T> get(@NonNull EntityId id) {
        EntityMetadata metaData = RelationalDatabaseEntityMetadataCache.getEntityMetaData(entityClass.getSimpleName());
        String selectSql = metaData.getSelectQueryMetadata().getQuery().concat(" WHERE id = ?");
        List<T> objects = applicationJdbcTemplate.query(selectSql, new EntityRowMapper<>(metaData), (Object)id.toBytes());
        return objects.isEmpty() ? Optional.empty() : Optional.of(objects.getFirst());
    }

    @Override
    public PagedResponse<T> getAllPaged(@NonNull Filter filter) throws CoreException {
        EntityMetadata metaData = RelationalDatabaseEntityMetadataCache.getEntityMetaData(filter.getEntityName());
        List<WhereConditionContainer> whereClauseContainers = buildWhereConditionContainers(filter, metaData);
        String whereClause = buildWhereClause(whereClauseContainers);
        List<Object> values = extractValuesOfPreparedStatementParameters(whereClauseContainers);

        // Get total count
        String countSql =  metaData.getSelectCountQueryMetadata().getQuery() + whereClause;
        Long totalElements = applicationJdbcTemplate.queryForObject(countSql, Long.class, values.toArray());

        // Get paginated results
        String selectSql = metaData.getSelectQueryMetadata().getQuery();
        String paginatedSql = selectSql.concat(whereClause).concat(System.lineSeparator()).concat("LIMIT ? OFFSET ?");
        List<Object> paginatedParams = new ArrayList<>(values);

        paginatedParams.add(filter.getSize());
        paginatedParams.add(filter.getPage() * filter.getSize());
        List<T> objects = applicationJdbcTemplate.query(paginatedSql, new EntityRowMapper<>(metaData), paginatedParams.toArray());
        return new PagedResponse<>(objects, filter.getPage(), filter.getSize(), totalElements);
    }

    private List<Object> extractValuesOfPreparedStatementParameters(List<WhereConditionContainer> whereClauseContainers) {
        return whereClauseContainers.stream().flatMap(container -> container.getParameterValues().stream()).toList();
    }

    @Override
    public List<T> getAll(@NonNull Filter filter) throws CoreException {
        EntityMetadata metaData = RelationalDatabaseEntityMetadataCache.getEntityMetaData(filter.getEntityName());
        List<WhereConditionContainer> whereClauseContainers = buildWhereConditionContainers(filter, metaData);
        String whereClause = buildWhereClause(whereClauseContainers);
        List<Object> values = extractValuesOfPreparedStatementParameters(whereClauseContainers);
        String selectSql = metaData.getSelectQueryMetadata().getQuery();
        return applicationJdbcTemplate.query(selectSql.concat(whereClause), new EntityRowMapper<>(metaData), values.toArray());
    }

    @Override
    public T delete(@NonNull EntityId id) throws CoreException {
        T entity = get(id).orElseThrow(() -> new CoreException("Entity not found"));
        return deleteAll(List.of(entity)).getFirst();
    }

    @Override
    public List<T> deleteAll(@NonNull List<T> entities) throws CoreException {
        entities.forEach(persistableEntityToDelete -> persistableEntityToDelete.setStatus(Status.DELETED));
        EntityMetadata entityMetaData = getEntityMetadata(entities);
        QueryMetadata deleteQueryMetadata = entityMetaData.getDeleteQueryMetadata();
        executeBatchUpdate(entities, entityMetaData, deleteQueryMetadata);
        return entities;
    }

    @Override
    public T update(@NonNull T entity) throws CoreException {
        return updateAll(List.of(entity)).getFirst();
    }

    @Override
    public List<T> updateAll(@NonNull List<T> entities) throws CoreException {
        EntityMetadata entityMetaData = getEntityMetadata(entities);
        QueryMetadata updateQueryMetadata = entityMetaData.getUpdateQueryMetadata();
        executeBatchUpdate(entities, entityMetaData, updateQueryMetadata);
        return entities;
    }

    private String buildWhereClause(List<WhereConditionContainer> whereClauseContainers) {
        StringBuilder builder = new StringBuilder();
        if(!whereClauseContainers.isEmpty()) {
            builder.append(System.lineSeparator()).append("WHERE ").append(whereClauseContainers.getFirst().getConditionString());
            for(int i=1;i<whereClauseContainers.size();i++) {
                WhereConditionContainer container = whereClauseContainers.get(i);
                builder.append(System.lineSeparator()).append("AND ").append(container.getConditionString());
            }
        }
        return builder.toString();
    }

    private List<WhereConditionContainer> buildWhereConditionContainers(Filter filter, EntityMetadata metaData) throws CoreException {
        List<WhereConditionContainer> primaryKeyFilters = generateFiltersOnPrimaryKey(filter, metaData);
        log.debug("Filter clauses generated for primary keys {}", primaryKeyFilters);
        List<WhereConditionContainer> filterClausesOnIndices = generateFiltersOnIndices(filter, metaData);
        log.debug("Filter clauses added for index columns {}", filterClausesOnIndices);
        List<WhereConditionContainer> filterClausesOnOtherColumns = generateFiltersOnNonPrimaryNonIndexColumns(filter, metaData);
        log.debug("Filter clauses added for non primary keys and non indexed columns {}", filterClausesOnOtherColumns);
        List<WhereConditionContainer> containers = new ArrayList<>(primaryKeyFilters.size()+filterClausesOnIndices.size()+filterClausesOnOtherColumns.size());
        containers.addAll(primaryKeyFilters);
        containers.addAll(filterClausesOnIndices);
        containers.addAll(filterClausesOnOtherColumns);
        return containers;
    }

    private List<WhereConditionContainer> generateFiltersOnNonPrimaryNonIndexColumns(Filter filter, EntityMetadata metaData) throws CoreException {
        List<FilterTuple> filterTuplesOnPrimaryKeyField = filter.getFilterTuples().stream().filter(filterTuple -> !metaData.getPrimaryKeyFieldName().equals(filterTuple.getFieldName()) && metaData.getIndexSequenceNumberForField(filterTuple.getFieldName())<1).toList();
        return generateWhereConditionContainer(filterTuplesOnPrimaryKeyField, metaData);
    }

    private List<WhereConditionContainer> generateFiltersOnIndices(Filter filter, EntityMetadata metaData) throws CoreException {
        List<FilterTuple> filterTuplesOnPrimaryKeyField = filter.getFilterTuples().stream().filter(filterTuple -> metaData.getIndexSequenceNumberForField(filterTuple.getFieldName())>0).sorted(Comparator.comparing(filterTuple -> metaData.getIndexSequenceNumberForField(filterTuple.getFieldName()))).toList();
        return generateWhereConditionContainer(filterTuplesOnPrimaryKeyField, metaData);
    }

    private List<WhereConditionContainer> generateFiltersOnPrimaryKey(Filter filter, EntityMetadata metaData) throws CoreException {
        String primaryKeyFieldName = metaData.getPrimaryKeyFieldName();
        List<FilterTuple> filterTuplesOnPrimaryKeyField = filter.getFilterTuples().stream().filter(filterTuple -> filterTuple.getFieldName().equals(primaryKeyFieldName)).toList();
        return generateWhereConditionContainer(filterTuplesOnPrimaryKeyField, metaData);
    }

    private List<WhereConditionContainer> generateWhereConditionContainer(List<FilterTuple> filterTuples, EntityMetadata metaData) throws CoreException {
        List<WhereConditionContainer> containers = new ArrayList<>();
        for(FilterTuple filterTuple : filterTuples) {
            String fieldName = filterTuple.getFieldName();
            List<Object> typeConvertedValues = filterTuple.getFilterValues().stream().map(filterTupleValue -> convertToJavaConversionTypeOfField(metaData.getJavaConversionTypeOfField(fieldName), filterTupleValue)).toList();
            containers.add(WhereConditionContainer.generate(metaData.getEntityName(), metaData.getColumnNameForField(fieldName), filterTuple.getComparisonOperator(), typeConvertedValues));
        }
        return containers;
    }

    private void executeBatchUpdate(List<T> entities, EntityMetadata entityMetaData, QueryMetadata queryMetadata) {
        List<List<T>> entityBatches = coreUtil.createBatches(entities, maximumPreparedStatementParametersPerBatch /queryMetadata.getNumberOfParameters());
        for(List<T> entityBatch : entityBatches) {
            applicationJdbcTemplate.batchUpdate(queryMetadata.getQuery(), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    T entity = entityBatch.get(i);
                    try {
                        for(int j = 1; j <= queryMetadata.getNumberOfParameters(); j++) {
                            String columnName = queryMetadata.getColumnNameOfPreparedStatementParamAt(j);
                            Object value = convertToJavaConversionTypeOfField(entityMetaData.getJavaConversionTypeOfColumn(columnName), entityMetaData.getFieldForColumn(columnName).get(entity));
                            if (value instanceof byte[]) {
                                ps.setBytes(j, (byte[]) value);
                            } else {
                                ps.setObject(j, value);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        throw new SQLException("Failed to access field value for batch update", e);
                    }
                }
                @Override
                public int getBatchSize() {
                    return entityBatch.size();
                }
            });
        }
    }

    private Object convertToJavaConversionTypeOfField(JavaConversionType javaConversionType, Object valueToConvert) {
        return switch (javaConversionType) {
            case ENTITY_ID -> EntityId.fromString(valueToConvert.toString()).toBytes();
            case BIG_DECIMAL -> new BigDecimal(valueToConvert.toString());
            case INTEGER -> Integer.parseInt(valueToConvert.toString());
            case LOCAL_DATE -> coreUtil.getLocalDateFromString(valueToConvert.toString());
            case INSTANT -> coreUtil.getTimeStampFromString(valueToConvert.toString());
            case ENTITY_STATUS -> Status.valueOf(valueToConvert.toString()).name();
            case BOOLEAN -> Boolean.valueOf(valueToConvert.toString());
            case STRING -> valueToConvert.toString();
        };
    }

    private EntityMetadata getEntityMetadata(List<T> entities) throws CoreException {
        if (entities.isEmpty()) {
            throw new CoreException("Entity list to be saved cannot be empty");
        }
        return RelationalDatabaseEntityMetadataCache.getEntityMetaData(entities.getFirst().getEntityName());
    }
}
