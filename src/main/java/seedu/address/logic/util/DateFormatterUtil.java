package seedu.address.logic.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

//@@author A0135782Y
/**
 * Formats TaskDate 
 */
public class DateFormatterUtil {
    public DateFormatterUtil(){}
    
    public static Date getEndOfDay(Date dateToFormat) {
        LocalDate date = dateToFormat.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        date = date.plusDays(1);
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    public static Date getStartOfDay(Date dateToFormat) {
        LocalDate date = dateToFormat.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        date = date.minusDays(1);
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
