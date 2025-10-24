 Feature: CoreUtil Date and Time Operations
  CoreUtil provides utility methods for date/time operations and list batching

  Scenario: Get current LocalDateTime
    When I call getCurrentLocalDateTimes method
    Then the returned LocalDateTime should be in ISO format
    And the LocalDateTime should be close to current time

  Scenario: Get current LocalDateTime as string
    When I call getCurrentLocalDateTimesString method
    Then the returned string should match ISO LocalDateTime format "yyyy-MM-dd'T'HH:mm:ss"
    And the string should represent current time

  Scenario: Get current timestamp for ID generation
    When I call getCurrentTimestamp method
    Then the returned string should match timestamp format "yyyyMMddHHmmssSSS"
    And the timestamp should be 17 characters long

  Scenario: Get current LocalDate
    When I call getCurrentDate method
    Then the returned LocalDate should be in ISO format
    And the LocalDate should be today's date

  Scenario: Get current LocalDate as string
    When I call getCurrentDateString method
    Then the returned string should match ISO LocalDate format "yyyy-MM-dd"
    And the string should represent today's date

  Scenario: Parse LocalDate from string
    Given a valid date string "2024-12-25"
    When I call getLocalDateFromString method
    Then the returned LocalDate should be "2024-12-25"

  Scenario: Create batches from list
    Given a list with 10 elements
    When I call createBatches with batch size 3
    Then I should get 4 batches
    And the first 3 batches should have 3 elements each
    And the last batch should have 1 element

  Scenario: Create batches with exact division
    Given a list with 9 elements
    When I call createBatches with batch size 3
    Then I should get 3 batches
    And all batches should have 3 elements each

  Scenario: Create batches with empty list
    Given an empty list
    When I call createBatches with batch size 5
    Then I should get 0 batches

  Scenario: Create batches with single element
    Given a list with 1 element
    When I call createBatches with batch size 5
    Then I should get 1 batch
    And the batch should have 1 element