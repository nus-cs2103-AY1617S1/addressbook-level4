package seedu.todo.commons.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;

import com.joestelmach.natty.*;

/**
 * Helper functions for anything with regards to date and time.
 */
public class DateTimeUtil {

    public static boolean isEmptyDateTimeString(String dateTimeString) {
        return (dateTimeString == null || dateTimeString.equals("") || dateTimeString.equals(" "));
    }

    public static LocalDateTime parseDateTimeString(String dateTimeString) {
        Parser nattyParser = new Parser();
        List<DateGroup> groups = nattyParser.parse(dateTimeString);

        if (groups.size() == 0) {
            return null;
        } else {
            Date date = groups.get(0).getDates().get(0);
            LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            return ldt;
        }
    }

    public static LocalDateTime combineLocalDateAndTime(LocalDate date, LocalTime time) {
        assert date != null;
        if (time == null) {
            return LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59);
        } else {
            return LocalDateTime.of(date, time);
        }
    }

    
    public static String prettyPrintDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    public static String prettyPrintTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

}
