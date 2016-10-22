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

import seedu.todo.model.task.TaskDate;

/**
 * Helper functions for anything with regards to date and time.
 */
public class DateTimeUtil {

    public static boolean isEmptyDateTimeString(String dateTimeString) {
        return (dateTimeString == null || dateTimeString.equals("") || dateTimeString.equals(" "));
    }

    public static LocalDateTime parseDateTimeString(String dateTimeString, String onOrBy) {
        Parser nattyParser = new Parser();
        List<DateGroup> groups = nattyParser.parse(dateTimeString);

        if (groups.size() == 0) {
            return null;
        } else {
            DateGroup group = groups.get(0);
            Map<String, List<ParseLocation>> m = group.getParseLocations();

            Date date = group.getDates().get(0);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            
            LocalDateTime ldt;
            if (!m.keySet().contains("date")) {
                ldt = LocalDateTime.now();
                ldt = LocalDateTime.of(ldt.getYear(), ldt.getMonth(), ldt.getDayOfMonth(), 
                        c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
            } else {
                if (!m.keySet().contains("explicit_time")) {
                    if (onOrBy.equals(TaskDate.TASK_DATE_BY)) {
                        ldt = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 
                                c.get(Calendar.DATE), 23, 59);
                    } else {
                        ldt = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 
                                c.get(Calendar.DATE), 00, 00);
                    }
                } else {
                    ldt = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 
                            c.get(Calendar.DATE), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
                }
                
            }
            
            return ldt;
        }
    }

    public static boolean containsDateField(String dateTimeString) {
        Parser nattyParser = new Parser();
        List<DateGroup> groups = nattyParser.parse(dateTimeString);
        
        if (groups.size() == 0) {
            return false;
        } else {
            DateGroup group = groups.get(0);
            Map<String, List<ParseLocation>> m = group.getParseLocations();
            return m.keySet().contains("date");
        }
    }
    
    public static boolean containsTimeField(String dateTimeString) {
        Parser nattyParser = new Parser();
        List<DateGroup> groups = nattyParser.parse(dateTimeString);
        
        if (groups.size() == 0) {
            return false;
        } else {
            DateGroup group = groups.get(0);
            Map<String, List<ParseLocation>> m = group.getParseLocations();
            return m.keySet().contains("explicit_time");
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
