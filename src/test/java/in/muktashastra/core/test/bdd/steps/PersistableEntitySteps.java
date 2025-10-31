package in.muktashastra.core.test.bdd.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.muktashastra.core.entity.TestEntity;
import in.muktashastra.core.util.filter.Filter;
import in.muktashastra.core.util.filter.FilterTuple;
import in.muktashastra.core.util.filter.PaginationFilter;
import in.muktashastra.core.util.model.ErrorResponse;
import in.muktashastra.core.util.model.PagedResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@RequiredArgsConstructor
public class PersistableEntitySteps {

    public static final String HTTP_LOCALHOST_8081 = "http://localhost:8081";
    private final JdbcTemplate jdbcTemplate;
    private final TestRestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private int lastResponseCode;
    private TestEntity savedEntity;
    private List<TestEntity> savedEntities;
    private PagedResponse<TestEntity> pagedResponse;
    private List<TestEntity> listResponse;
    private String lastExceptionMessage;

    @Given("the H2 database is initialized")
    public void theH2DatabaseIsInitialized() {
        assertNotNull(jdbcTemplate);
    }

    @Given("the test entity table exists with data populated from schema.sql")
    public void theTestEntityTableExists() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TEST_ENTITY'", Integer.class);
        assertEquals(1, count);
        Integer countRecords = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM test_entity", Integer.class);
        assertNotNull(countRecords);
        assertTrue(countRecords >= 10);
    }

    @When("I DELETE entity on {string}")
    public void iDeleteEntityOn(String endpoint) {
        ResponseEntity<TestEntity> responseEntity = restTemplate.postForEntity(HTTP_LOCALHOST_8081 + endpoint, null, TestEntity.class);
        lastResponseCode = responseEntity.getStatusCode().value();
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            try {
                savedEntity = responseEntity.getBody();
            } catch (Exception e) {
                log.warn("Failed to parse response as TestEntity: {}", e.getMessage());
            }
        }
    }

    @When("I POST entity on {string}")
    public void iPostEntityOn(String endpoint, DataTable dataTable) {
        TestEntity entity = convertDataTableToTestEntity(dataTable);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TestEntity> request = new HttpEntity<>(entity, headers);
        ResponseEntity<TestEntity> responseEntity = restTemplate.postForEntity(HTTP_LOCALHOST_8081 + endpoint, request, TestEntity.class);
        lastResponseCode = responseEntity.getStatusCode().value();
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            savedEntity = responseEntity.getBody();
        }
    }

    @When("I POST multiple entities on {string}")
    public void iPostEntitiesOn(String endpoint, DataTable dataTable) {
        List<TestEntity> entities = convertDataTableToTestEntities(dataTable);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<TestEntity>> request = new HttpEntity<>(entities, headers);
        ResponseEntity<List<TestEntity>> responseEntity = restTemplate.exchange(HTTP_LOCALHOST_8081 + endpoint, HttpMethod.POST, request, new ParameterizedTypeReference<>() {});
        lastResponseCode = responseEntity.getStatusCode().value();
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            savedEntities = responseEntity.getBody();
        }
    }

    @When("I GET entity on {string}")
    public void iGetEntityOn(String endpoint) {
        ResponseEntity<TestEntity> responseEntity = restTemplate.getForEntity(HTTP_LOCALHOST_8081 + endpoint, TestEntity.class);
        lastResponseCode = responseEntity.getStatusCode().value();
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            savedEntity = responseEntity.getBody();
        }
    }

    @When("I POST filter with entity name {string} page {int} size {int} on {string}")
    public void iPostFilterWithEntityNamePageSizeOn(String entityName, int page, int size, String endpoint, DataTable dataTable) {
        List<FilterTuple> filterTuples = convertDataTableToFilterTuples(dataTable);
        PaginationFilter filter = new PaginationFilter(entityName,page,size,filterTuples);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Filter> request = new HttpEntity<>(filter, headers);
        ResponseEntity<PagedResponse<TestEntity>> responseEntity = restTemplate.exchange(HTTP_LOCALHOST_8081 + endpoint, HttpMethod.POST, request, new ParameterizedTypeReference<>() {});
        lastResponseCode = responseEntity.getStatusCode().value();
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            pagedResponse = responseEntity.getBody();
        }
    }

    @When("I POST filter with entity name {string} on {string}")
    public void iPostFilterWithEntityNameOn(String entityName, String endpoint, DataTable dataTable) {
        List<FilterTuple> filterTuples = convertDataTableToFilterTuples(dataTable);
        Filter filter = new Filter(entityName, filterTuples);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Filter> request = new HttpEntity<>(filter, headers);
        ResponseEntity<List<TestEntity>> responseEntity = restTemplate.exchange(HTTP_LOCALHOST_8081 + endpoint, HttpMethod.POST, request, new ParameterizedTypeReference<>() {});
        lastResponseCode = responseEntity.getStatusCode().value();
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            listResponse = responseEntity.getBody();
        }
    }

    @When("I POST multiple entities on {string} for error")
    public void iPostEntitiesOnForError(String endpoint, DataTable dataTable) {
        List<TestEntity> entities = convertDataTableToTestEntities(dataTable);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<TestEntity>> request = new HttpEntity<>(entities, headers);
        ResponseEntity<ErrorResponse> responseEntity = restTemplate.exchange(HTTP_LOCALHOST_8081 + endpoint, HttpMethod.POST, request, new ParameterizedTypeReference<>() {});
        lastResponseCode = responseEntity.getStatusCode().value();
        assertNotNull(responseEntity.getBody());
        lastExceptionMessage = responseEntity.getBody().getMessage();
    }

    @Then("the response status is {int}")
    public void theResponseStatusIs(int expectedStatus) {
        assertEquals(expectedStatus, lastResponseCode);
    }

    @Then("the response contains entity with generated id")
    public void theResponseContainsEntityWithGeneratedId() {
        assertNotNull(savedEntity);
        assertNotNull(savedEntity.getId());
    }

    @Then("the response contains entities with generated ids")
    public void theResponseContainsEntitiesWithGeneratedIds() {
        assertNotNull(savedEntities);
        assertFalse(savedEntities.isEmpty());
        savedEntities.forEach(entity -> assertNotNull(entity.getId()));
    }

    @Then("the response entity has id {string}")
    public void theResponseContainsEntityWithId(String expectedId) {
        assertNotNull(savedEntity);
        assertNotNull(savedEntity.getId());
        assertEquals(expectedId, savedEntity.getId().toString());
    }

    @Then("the response entity is null")
    public void theResponseEntityIsNull() {
        assertNull(savedEntity);
    }

    @Then("the response contains entity with {word} as {string} and {word} as {string}")
    public void theResponseContainsEntityWithFields(String field1, String value1, String field2, String value2) {
        assertNotNull(savedEntity);
        Map<String, Object> entityMap = objectMapper.convertValue(savedEntity, new TypeReference<>() {});
        assertEquals(value1, String.valueOf(entityMap.get(field1)));
        assertEquals(value2, String.valueOf(entityMap.get(field2)));
    }

    @Then("the response contains entity at id {string} with {word} as {string} and {word} as {string}")
    public void theResponseContainsEntityWithFields(String id, String field1, String value1, String field2, String value2) {
        TestEntity entity = savedEntities.stream().filter(e -> e.getId().toString().equals(id)).findFirst().orElse(null);
        assertNotNull(entity);
        Map<String, Object> entityMap = objectMapper.convertValue(entity, new TypeReference<>() {});
        assertEquals(value1, String.valueOf(entityMap.get(field1)));
        assertEquals(value2, String.valueOf(entityMap.get(field2)));
    }

    @Then("the response contains {int} page(s) and {int} element(s) in page number {int} with total element(s) {int}")
    public void theResponseContainsPageAndElementsInPageWithTotalElements(int page, int size, int currentPage, int totalElements) {
        assertNotNull(pagedResponse);
        assertEquals(page, pagedResponse.getTotalPages());
        assertEquals(size, pagedResponse.getContent().size());
        assertEquals(currentPage, pagedResponse.getPageNumber());
        assertEquals(totalElements, pagedResponse.getTotalElements());
    }

    @Then("the response contains total {int} elements")
    public void theResponseContainsTotalElements(int totalElements) {
        assertNotNull(listResponse);
        assertEquals(totalElements, listResponse.size());
    }

    @Then("error message is {string}")
    public void errorMessageIs(String expectedMessage) {
        assertNotNull(lastExceptionMessage);
        assertEquals(expectedMessage, lastExceptionMessage);
    }

    private TestEntity convertDataTableToTestEntity(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps(String.class, String.class).getFirst();
        return objectMapper.convertValue(data, TestEntity.class);
    }

    private List<TestEntity> convertDataTableToTestEntities(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        return data.stream().map(row -> objectMapper.convertValue(row, TestEntity.class)).toList();
    }

    private List<FilterTuple> convertDataTableToFilterTuples(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        return data.stream().map(this::convertToFieldListOfObjectsMap).map(row -> objectMapper.convertValue(row, FilterTuple.class)).toList();
    }

    private Map<String, Object> convertToFieldListOfObjectsMap(Map<String, String> filterTupleRow) {
        Map<String, Object> tupleRow = new HashMap<>();
        tupleRow.put("fieldName", filterTupleRow.get("fieldName"));
        tupleRow.put("comparisonOperator", filterTupleRow.get("comparisonOperator"));
        String valueStr = filterTupleRow.get("filterValues");
        tupleRow.put("filterValues", convertFilterValue(filterTupleRow.get("fieldName"), valueStr));
        return tupleRow;
    }

    private List<Object> convertFilterValue(String fieldName, String value) {
        if (value == null || value.isBlank()) return List.of();
        return switch (fieldName.toLowerCase()) {
            case "procuredate" -> List.of(LocalDate.parse(value));
            case "createtimestamp" -> List.of(Instant.parse(value));
            case "price" -> List.of(new BigDecimal(value));
            case "rating" -> List.of(Integer.parseInt(value));
            case "active" -> List.of(Boolean.parseBoolean(value));
            default -> value.contains(",") ? List.of(value.split(",")) : List.of(value);
        };
    }
}