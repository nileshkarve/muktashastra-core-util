Feature: PersistableEntity REST API Operations
  Test CRUD operations through REST endpoints for entities implementing PersistableEntity interface
  All operations are performed via TestEntityController which extends CorePersistableEntityController

  Background:
    Given the H2 database is initialized
    And the test entity table exists with data populated from schema.sql

  Scenario: Insert entity via REST API
    When I POST entity on "/testEntity/insert"
      | name   | description   | procureDate | createTimestamp      | price  | status | active | category    | rating |
      | Laptop | Gaming laptop | 2025-10-09  | 2024-01-15T10:30:00Z | 999.99 | ACTIVE | true   | Electronics | 5      |
    Then the response status is 200
    And the response contains entity with generated id

  Scenario: Get entity by ID via REST API
    When I GET entity on "/testEntity/49fc5e08-8a05-4fbc-92bf-f5333453bab0"
    Then the response status is 200
    And the response entity has id "49fc5e08-8a05-4fbc-92bf-f5333453bab0"

  Scenario: Update entity via REST API
    When I POST entity on "/testEntity/update"
      | id                                   | name        | description   | procureDate | createTimestamp      | price   | status | active | category    | rating |
      | a04cfd25-2e35-45fd-abe0-9d7eea4c1a87 | Work Laptop | Office laptop | 2025-10-09  | 2024-01-15T10:30:00Z | 1099.99 | ACTIVE | true   | Electronics | 5      |
    Then the response status is 200
    And the response entity has id "a04cfd25-2e35-45fd-abe0-9d7eea4c1a87"
    And the response contains entity with name as "Work Laptop" and createTimestamp as "2024-01-15T10:30:00Z"

  Scenario: Delete entity via REST API
    When I DELETE entity on "/testEntity/delete/7db9a915-6dfc-45e6-9d9d-a349d4cdcc6c"
    Then the response status is 200
    And the response entity has id "7db9a915-6dfc-45e6-9d9d-a349d4cdcc6c"
    And the response contains entity with name as "Standard Laptop" and status as "DELETED"

  Scenario: Insert multiple entities via REST API
    When I POST multiple entities on "/testEntity/insertAll"
      | name   | description   | procureDate | createTimestamp      | price    | status | active | category    | rating |
      | Laptop | Gaming laptop | 2025-10-09  | 2024-01-10T10:30:00Z | 1299.99  | ACTIVE | true   | Electronics | 4     |
      | Laptop | Work laptop   | 2025-10-09  | 2024-03-14T10:30:00Z | 1199.99  | ACTIVE | true   | Electronics | 5      |
      | Laptop | Kid's laptop  | 2025-10-09  | 2024-02-15T10:30:00Z | 599.99   | ACTIVE | true   | Electronics | 2      |
      | Laptop | Backup laptop | 2025-10-09  | 2024-01-11T10:30:00Z | 799.99   | ACTIVE | true   | Electronics | 3      |
    Then the response status is 200
    And the response contains entities with generated ids

  Scenario: Update multiple entities via REST API
    When I POST multiple entities on "/testEntity/updateAll"
      | id                                   | name           | description   | procureDate | createTimestamp      | price   | status | active | category    | rating |
      | 9c488e54-8449-45f8-bed2-979807755ad5 | Premium Laptop | Work laptop   | 2025-10-12  | 2025-01-15T10:30:00Z | 1299.99 | ACTIVE | true   | Electronics | 5      |
      | 92a582b1-961f-43b1-ae53-8a40d710b8b6 | Smartphone     | Apple iPhone  | 2025-10-09  | 2025-02-15T10:30:00Z | 999.99  | ACTIVE | true   | Electronics | 3      |
      | 3d950bfc-0323-42dc-9a28-8ccad7486501 | Smartphone     | Android phone | 2025-10-09  | 2025-11-15T10:30:00Z | 1799.99 | ACTIVE | true   | Electronics | 1      |
    Then the response status is 200
    And the response contains entity at id "9c488e54-8449-45f8-bed2-979807755ad5" with name as "Premium Laptop" and procureDate as "2025-10-12"
    And the response contains entity at id "92a582b1-961f-43b1-ae53-8a40d710b8b6" with name as "Smartphone" and description as "Apple iPhone"
    And the response contains entity at id "3d950bfc-0323-42dc-9a28-8ccad7486501" with name as "Smartphone" and description as "Android phone"

  Scenario: Delete multiple entities via REST API
    When I POST multiple entities on "/testEntity/deleteAll"
      | id                                   | name           | description   | procureDate | createTimestamp      | price   | status | active | category    | rating |
      | 9c488e54-8449-45f8-bed2-979807755ad5 | Premium Laptop | Work laptop   | 2025-10-12  | 2025-01-15T10:30:00Z | 1299.99 | ACTIVE | true   | Electronics | 5      |
      | 92a582b1-961f-43b1-ae53-8a40d710b8b6 | Smartphone     | Apple iPhone  | 2025-10-09  | 2025-02-15T10:30:00Z | 999.99  | ACTIVE | true   | Electronics | 3      |
      | 3d950bfc-0323-42dc-9a28-8ccad7486501 | Smartphone     | Android phone | 2025-10-09  | 2025-11-15T10:30:00Z | 1799.99 | ACTIVE | true   | Electronics | 1      |
    Then the response status is 200
    And the response contains entity at id "9c488e54-8449-45f8-bed2-979807755ad5" with name as "Premium Laptop" and status as "DELETED"
    And the response contains entity at id "92a582b1-961f-43b1-ae53-8a40d710b8b6" with name as "Smartphone" and status as "DELETED"
    And the response contains entity at id "3d950bfc-0323-42dc-9a28-8ccad7486501" with name as "Smartphone" and status as "DELETED"

  Scenario: Select with EQUALS,IN comparison operators in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName  | comparisonOperator    | filterValues |
      | status     | EQUALS                | ACTIVE       |
      | price      | LESS_THAN_OR_EQUAL_TO | 899.99       |
    Then the response status is 200
    And the response contains 1 page and 4 element in page number 0 with total elements 4

  Scenario: Select with NOT_EQUALS operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName | comparisonOperator | filterValues |
      | status    | NOT_EQUALS         | DELETED      |
    Then the response status is 200
    And the response contains 2 pages and 10 elements in page number 0 with total elements 11

  Scenario: Select with GREATER_THAN operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName | comparisonOperator | filterValues |
      | price     | GREATER_THAN       | 500.00       |
    Then the response status is 200
    And the response contains 2 pages and 10 elements in page number 0 with total elements 13

  Scenario: Select with GREATER_THAN_OR_EQUAL_TO operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName | comparisonOperator     | filterValues |
      | price     | GREATER_THAN_OR_EQUAL_TO | 599.99       |
    Then the response status is 200
    And the response contains 2 pages and 10 elements in page number 0 with total elements 13

  Scenario: Select with LESS_THAN operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName | comparisonOperator | filterValues |
      | price     | LESS_THAN          | 1000.00      |
    Then the response status is 200
    And the response contains 1 page and 9 elements in page number 0 with total elements 9

  Scenario: Select with LIKE operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName | comparisonOperator | filterValues |
      | name      | LIKE               | %Laptop%     |
    Then the response status is 200
    And the response contains 1 page and 10 elements in page number 0 with total elements 10

  Scenario: Select with NOT_LIKE operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName | comparisonOperator | filterValues |
      | name      | NOT_LIKE           | %Phone%      |
    Then the response status is 200
    And the response contains 2 pages and 10 elements in page number 0 with total elements 11

  Scenario: Select with STARTS_WITH operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName | comparisonOperator | filterValues |
      | name      | STARTS_WITH        | Standard     |
    Then the response status is 200
    And the response contains 1 page and 1 element in page number 0 with total elements 1

  Scenario: Select with ENDS_WITH operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName | comparisonOperator | filterValues |
      | name      | ENDS_WITH          | Laptop       |
    Then the response status is 200
    And the response contains 1 page and 10 elements in page number 0 with total elements 10

  Scenario: Select with CONTAINS_STRING operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName | comparisonOperator | filterValues |
      | name      | CONTAINS_STRING    | Laptop       |
    Then the response status is 200
    And the response contains 1 page and 10 elements in page number 0 with total elements 10

  Scenario: Select with IN operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName | comparisonOperator | filterValues        |
      | status    | IN                 | ACTIVE,NEW          |
    Then the response status is 200
    And the response contains 2 pages and 10 elements in page number 0 with total elements 11

  Scenario: Select with NOT_IN operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName | comparisonOperator | filterValues |
      | status    | NOT_IN             | DELETED      |
    Then the response status is 200
    And the response contains 2 pages and 10 elements in page number 0 with total elements 11

  Scenario: Select with IS_NULL operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName   | comparisonOperator | filterValues |
      | description | IS_NULL            |              |
    Then the response status is 200
    And the response contains 0 page and 0 elements in page number 0 with total elements 0

  Scenario: Select with IS_NOT_NULL operator in filter
    When I POST filter with entity name "TestEntity" page 0 size 10 on "/testEntity/search"
      | fieldName   | comparisonOperator | filterValues |
      | description | IS_NOT_NULL        |              |
    Then the response status is 200
    And the response contains 2 pages and 10 elements in page number 0 with total elements 15