package in.muktashastra.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Nilesh
 *
 */
public class MuktashastraUtil {

    public LocalDateTime getCurrentLocalDateTimes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.LOCAL_DATE_TIME_FORMAT);
        String dateStr = LocalDateTime.now().format(formatter);
        return LocalDateTime.parse(dateStr, formatter);
    }

    public String getCurrentLocalDateTimesString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.LOCAL_DATE_TIME_FORMAT);
        return LocalDateTime.now().format(formatter);
    }

    public String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.TIMESTAMP_FORMAT);
        return LocalDateTime.now().format(formatter);
    }

    public LocalDate getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.LOCAL_DATE_FORMAT);
        String dateStr = LocalDate.now().format(formatter);
        return LocalDate.parse(dateStr, formatter);
    }

    public String getCurrentDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.LOCAL_DATE_FORMAT);
        return LocalDate.now().format(formatter);
    }
}
