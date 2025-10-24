package in.muktashastra.core.test.bdd.steps;

import in.muktashastra.core.util.CoreUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class CoreUtilSteps {

    @Autowired
    private CoreUtil coreUtil;

    private LocalDateTime resultDateTime;
    private String resultDateTimeString;
    private String resultTimestamp;
    private LocalDate resultDate;
    private String resultDateString;
    private String inputDateString;
    private LocalDate parsedDate;
    private List<String> inputList;
    private List<List<String>> resultBatches;
    private int batchSize;

    @When("I call getCurrentLocalDateTimes method")
    public void iCallGetCurrentLocalDateTimesMethod() {
        resultDateTime = coreUtil.getCurrentLocalDateTimes();
    }

    @Then("the returned LocalDateTime should be in ISO format")
    public void theReturnedLocalDateTimeShouldBeInISOFormat() {
        assertNotNull(resultDateTime);
        String formatted = resultDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        assertNotNull(formatted);
    }

    @Then("the LocalDateTime should be close to current time")
    public void theLocalDateTimeShouldBeCloseToCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        long secondsDiff = Math.abs(ChronoUnit.SECONDS.between(resultDateTime, now));
        assertTrue(secondsDiff < 5, "DateTime should be within 5 seconds of current time");
    }

    @When("I call getCurrentLocalDateTimesString method")
    public void iCallGetCurrentLocalDateTimesStringMethod() {
        resultDateTimeString = coreUtil.getCurrentLocalDateTimesString();
    }

    @Then("the returned string should match ISO LocalDateTime format {string}")
    public void theReturnedStringShouldMatchISOLocalDateTimeFormat(String format) {
        assertNotNull(resultDateTimeString);
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}");
        assertTrue(pattern.matcher(resultDateTimeString).matches());
    }

    @Then("the string should represent current time")
    public void theStringShouldRepresentCurrentTime() {
        LocalDateTime parsed = LocalDateTime.parse(resultDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime now = LocalDateTime.now();
        long secondsDiff = Math.abs(ChronoUnit.SECONDS.between(parsed, now));
        assertTrue(secondsDiff < 5, "Parsed time should be within 5 seconds of current time");
    }

    @When("I call getCurrentTimestamp method")
    public void iCallGetCurrentTimestampMethod() {
        resultTimestamp = coreUtil.getCurrentTimestamp();
    }

    @Then("the returned string should match timestamp format {string}")
    public void theReturnedStringShouldMatchTimestampFormat(String format) {
        assertNotNull(resultTimestamp);
        Pattern pattern = Pattern.compile("\\d{17}");
        assertTrue(pattern.matcher(resultTimestamp).matches());
    }

    @Then("the timestamp should be {int} characters long")
    public void theTimestampShouldBeCharactersLong(int expectedLength) {
        assertEquals(expectedLength, resultTimestamp.length());
    }

    @When("I call getCurrentDate method")
    public void iCallGetCurrentDateMethod() {
        resultDate = coreUtil.getCurrentDate();
    }

    @Then("the returned LocalDate should be in ISO format")
    public void theReturnedLocalDateShouldBeInISOFormat() {
        assertNotNull(resultDate);
        String formatted = resultDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertNotNull(formatted);
    }

    @Then("the LocalDate should be today's date")
    public void theLocalDateShouldBeTodaysDate() {
        LocalDate today = LocalDate.now();
        assertEquals(today, resultDate);
    }

    @When("I call getCurrentDateString method")
    public void iCallGetCurrentDateStringMethod() {
        resultDateString = coreUtil.getCurrentDateString();
    }

    @Then("the returned string should match ISO LocalDate format {string}")
    public void theReturnedStringShouldMatchISOLocalDateFormat(String format) {
        assertNotNull(resultDateString);
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        assertTrue(pattern.matcher(resultDateString).matches());
    }

    @Then("the string should represent today's date")
    public void theStringShouldRepresentTodaysDate() {
        LocalDate parsed = LocalDate.parse(resultDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate today = LocalDate.now();
        assertEquals(today, parsed);
    }

    @Given("a valid date string {string}")
    public void aValidDateString(String dateString) {
        inputDateString = dateString;
    }

    @When("I call getLocalDateFromString method")
    public void iCallGetLocalDateFromStringMethod() {
        parsedDate = coreUtil.getLocalDateFromString(inputDateString);
    }

    @Then("the returned LocalDate should be {string}")
    public void theReturnedLocalDateShouldBe(String expectedDate) {
        LocalDate expected = LocalDate.parse(expectedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertEquals(expected, parsedDate);
    }

    @Given("a list with {int} element(s)")
    public void aListWithElements(int count) {
        inputList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            inputList.add("Element" + i);
        }
    }

    @Given("an empty list")
    public void anEmptyList() {
        inputList = new ArrayList<>();
    }

    @When("I call createBatches with batch size {int}")
    public void iCallCreateBatchesWithBatchSize(int size) {
        batchSize = size;
        resultBatches = coreUtil.createBatches(inputList, batchSize);
    }

    @Then("I should get {int} batch(es)")
    public void iShouldGetBatches(int expectedBatchCount) {
        assertEquals(expectedBatchCount, resultBatches.size());
    }

    @Then("the first {int} batches should have {int} elements each")
    public void theFirstBatchesShouldHaveElementsEach(int batchCount, int elementsPerBatch) {
        for (int i = 0; i < batchCount; i++) {
            assertEquals(elementsPerBatch, resultBatches.get(i).size());
        }
    }

    @Then("the last batch should have {int} element")
    public void theLastBatchShouldHaveElement(int elementCount) {
        List<String> lastBatch = resultBatches.get(resultBatches.size() - 1);
        assertEquals(elementCount, lastBatch.size());
    }

    @Then("all batches should have {int} elements each")
    public void allBatchesShouldHaveElementsEach(int elementsPerBatch) {
        for (List<String> batch : resultBatches) {
            assertEquals(elementsPerBatch, batch.size());
        }
    }

    @Then("the batch should have {int} element")
    public void theBatchShouldHaveElement(int elementCount) {
        assertEquals(1, resultBatches.size());
        assertEquals(elementCount, resultBatches.get(0).size());
    }
}