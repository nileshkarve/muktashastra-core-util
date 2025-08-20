package in.muktashastra.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class providing date and time operations for Muktashastra application.
 * 
 * @author Nilesh
 */
public class MuktashastraUtil {

    /**
     * Gets current LocalDateTime formatted and parsed using standard ISO format.
     * 
     * @return current LocalDateTime
     */
    public LocalDateTime getCurrentLocalDateTimes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.LOCAL_DATE_TIME_FORMAT);
        String dateStr = LocalDateTime.now().format(formatter);
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * Gets current LocalDateTime as ISO formatted string.
     * 
     * @return current LocalDateTime as string
     */
    public String getCurrentLocalDateTimesString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.LOCAL_DATE_TIME_FORMAT);
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Gets current timestamp for ID generation.
     * 
     * @return current timestamp string
     */
    public String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.TIMESTAMP_FORMAT);
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Gets current LocalDate formatted and parsed using standard ISO format.
     * 
     * @return current LocalDate
     */
    public LocalDate getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.LOCAL_DATE_FORMAT);
        String dateStr = LocalDate.now().format(formatter);
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * Gets current LocalDate as ISO formatted string.
     * 
     * @return current LocalDate as string
     */
    public String getCurrentDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.LOCAL_DATE_FORMAT);
        return LocalDate.now().format(formatter);
    }
}
