package in.muktashastra.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class providing date and time operations for Muktashastra application.
 * 
 * @author Nilesh
 */
public class CoreUtil {

    /**
     * Gets current LocalDateTime formatted and parsed using standard ISO format.
     * 
     * @return current LocalDateTime
     */
    public LocalDateTime getCurrentLocalDateTimes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CoreConstant.LOCAL_DATE_TIME_FORMAT);
        String dateStr = LocalDateTime.now().format(formatter);
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * Gets current LocalDateTime as ISO formatted string.
     * 
     * @return current LocalDateTime as string
     */
    public String getCurrentLocalDateTimesString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CoreConstant.LOCAL_DATE_TIME_FORMAT);
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Gets current timestamp for ID generation.
     * 
     * @return current timestamp string
     */
    public String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CoreConstant.TIMESTAMP_FORMAT);
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Gets current LocalDate formatted and parsed using standard ISO format.
     * 
     * @return current LocalDate
     */
    public LocalDate getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CoreConstant.LOCAL_DATE_FORMAT);
        String dateStr = LocalDate.now().format(formatter);
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * Gets current LocalDate as ISO formatted string.
     * 
     * @return current LocalDate as string
     */
    public String getCurrentDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CoreConstant.LOCAL_DATE_FORMAT);
        return LocalDate.now().format(formatter);
    }

    public LocalDate getLocalDateFromString(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CoreConstant.LOCAL_DATE_FORMAT);
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * Splits a list into smaller batches of specified size.
     * 
     * @param <T> the type of elements in the list
     * @param list the list to be split into batches
     * @param batchSize the maximum size of each batch
     * @return list of batches, each containing up to batchSize elements
     */
    public <T> List<List<T>> createBatches(List<T> list, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        for(int i = 0; i < list.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, list.size());
            batches.add(list.subList(i, endIndex));
        }
        return batches;
    }
}
