package seedu.todo.commons.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
            DateGroup group = groups.get(0);
            Map<String, List<ParseLocation>> m = group.getParseLocations();
            if (!m.keySet().contains("date")) {
                return null;
            }
            Date date = group.getDates().get(0);
            
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            
            LocalDateTime ldt = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 
                    c.get(Calendar.DATE), c.get(Calendar.HOUR), c.get(Calendar.MINUTE));
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
