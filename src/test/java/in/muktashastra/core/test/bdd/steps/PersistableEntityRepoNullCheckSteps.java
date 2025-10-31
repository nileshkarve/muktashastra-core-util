package in.muktashastra.core.test.bdd.steps;

import in.muktashastra.core.entity.TestEntity;
import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.persistence.relationalstore.repo.CorePersistableEntityRepo;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class PersistableEntityRepoNullCheckSteps {

    private final CorePersistableEntityRepo<TestEntity> testEntityRepoImpl;
    private Exception lastException;

    @When("I attempt to insert null entity")
    public void iAttemptToInsertNullEntity() {
        try {
            testEntityRepoImpl.insert(null);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("I attempt to insert null entity list")
    public void iAttemptToInsertNullEntityList() {
        try {
            testEntityRepoImpl.insertAll(null);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("I attempt to insert empty entity list")
    public void iAttemptToInsertEmptyEntityList() {
        try {
            testEntityRepoImpl.insertAll(List.of());
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("I attempt to get entity with null ID")
    public void iAttemptToGetEntityWithNullId() {
        try {
            testEntityRepoImpl.get(null);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("I attempt to get all paged with null filter")
    public void iAttemptToGetAllPagedWithNullFilter() {
        try {
            testEntityRepoImpl.getAllPaged(null);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("I attempt to get all with null filter")
    public void iAttemptToGetAllWithNullFilter() {
        try {
            testEntityRepoImpl.getAll(null);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("I attempt to delete entity with null ID")
    public void iAttemptToDeleteEntityWithNullId() {
        try {
            testEntityRepoImpl.delete(null);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("I attempt to delete null entity list")
    public void iAttemptToDeleteNullEntityList() {
        try {
            testEntityRepoImpl.deleteAll(null);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("I attempt to delete empty entity list")
    public void iAttemptToDeleteEmptyEntityList() {
        try {
            testEntityRepoImpl.deleteAll(List.of());
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("I attempt to update null entity")
    public void iAttemptToUpdateNullEntity() {
        try {
            testEntityRepoImpl.update(null);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("I attempt to update null entity list")
    public void iAttemptToUpdateNullEntityList() {
        try {
            testEntityRepoImpl.updateAll(null);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("I attempt to update empty entity list")
    public void iAttemptToUpdateEmptyEntityList() {
        try {
            testEntityRepoImpl.updateAll(List.of());
        } catch (Exception e) {
            lastException = e;
        }
    }

    @Then("a NullPointerException should be thrown with message containing {string}")
    public void aNullPointerExceptionShouldBeThrownWithMessageContaining(String expectedMessagePart) {
        assertNotNull(lastException, "Expected an exception to be thrown");
        assertTrue(lastException instanceof NullPointerException, "Expected NullPointerException but got: " + lastException.getClass().getSimpleName());
        assertTrue(lastException.getMessage().contains(expectedMessagePart), 
            "Expected message to contain '" + expectedMessagePart + "' but was: " + lastException.getMessage());
    }

    @Then("a CoreException should be thrown with message {string}")
    public void aCoreExceptionShouldBeThrownWithMessage(String expectedMessage) {
        assertNotNull(lastException, "Expected an exception to be thrown");
        assertTrue(lastException instanceof CoreException, "Expected CoreException but got: " + lastException.getClass().getSimpleName());
        assertEquals(expectedMessage, lastException.getMessage());
    }
}