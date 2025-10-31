Feature: CorePersistableEntityRepo Null Parameter Validation
  Test null parameter validation for all CRUD operations in CorePersistableEntityRepo
  All methods with @NonNull annotations should throw appropriate exceptions when null parameters are passed

  Background:
    Given the H2 database is initialized
    And the test entity table exists with data populated from schema.sql

  Scenario: Insert null entity should throw exception
    When I attempt to insert null entity
    Then a NullPointerException should be thrown with message containing "entity is marked non-null but is null"

  Scenario: Insert null entity list should throw exception
    When I attempt to insert null entity list
    Then a NullPointerException should be thrown with message containing "entities is marked non-null but is null"

  Scenario: Insert empty entity list should throw CoreException
    When I attempt to insert empty entity list
    Then a CoreException should be thrown with message "Entity list cannot be empty"

  Scenario: Get entity with null ID should throw exception
    When I attempt to get entity with null ID
    Then a NullPointerException should be thrown with message containing "id is marked non-null but is null"

  Scenario: Get all paged with null filter should throw exception
    When I attempt to get all paged with null filter
    Then a NullPointerException should be thrown with message containing "filter is marked non-null but is null"

  Scenario: Get all with null filter should throw exception
    When I attempt to get all with null filter
    Then a NullPointerException should be thrown with message containing "filter is marked non-null but is null"

  Scenario: Delete entity with null ID should throw exception
    When I attempt to delete entity with null ID
    Then a NullPointerException should be thrown with message containing "id is marked non-null but is null"

  Scenario: Delete null entity list should throw exception
    When I attempt to delete null entity list
    Then a NullPointerException should be thrown with message containing "entities is marked non-null but is null"

  Scenario: Delete empty entity list should throw CoreException
    When I attempt to delete empty entity list
    Then a CoreException should be thrown with message "Entity list cannot be empty"

  Scenario: Update null entity should throw exception
    When I attempt to update null entity
    Then a NullPointerException should be thrown with message containing "entity is marked non-null but is null"

  Scenario: Update null entity list should throw exception
    When I attempt to update null entity list
    Then a NullPointerException should be thrown with message containing "entities is marked non-null but is null"

  Scenario: Update empty entity list should throw CoreException
    When I attempt to update empty entity list
    Then a CoreException should be thrown with message "Entity list cannot be empty"