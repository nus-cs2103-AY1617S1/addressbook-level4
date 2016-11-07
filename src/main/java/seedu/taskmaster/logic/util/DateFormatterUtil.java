package seedu.taskmaster.logic.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

//@@author A0135782Y
/**
 * Provides formatting for Date and LocalDate.
 */
public class DateFormatterUtil {
    private static final int END_DAY_OFFSET = 1;
    /**
     * Returns the end of day in Date format.
     * 
     * @param dateToFormat Date to be formatted, cannot be null.
     * @return Date limited to the end of day e.g. 00.00 of the next day
     */
    public static Date getEndOfDay(Date dateToFormat) {
        assert dateToFormat != null : "Date to be formatted should not be null";
        LocalDate date = dateToFormat.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        date = date.plusDays(END_DAY_OFFSET);
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    /**
     * Retuns the start of day in Date format.
     * 
     * @param dateToFormat Date to be formatted, cannot be null.
     * @return Date limited to the end of day e.g. 00.00 of the this day
     */
    public static Date getStartOfDay(Date dateToFormat) {
        assert dateToFormat != null : "Date to be formatted should not be null";
        LocalDate date = dateToFormat.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Returns the converted LocalDate object
     * 
     * @param toConvert Date to be formatted, cannot be null.
     * @return Date in LocalDate format.
     */
    public static LocalDate dateToLocalDate(Date toConvert) {
        assert toConvert != null : "Date to be converted should not be null";
        return toConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    /**
     * Returns the formatted date in String form
     * 
     * @param toFormat Cannot be null
     */
    public static String getFormattedDate(Date toFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d hh.mma", Locale.ENGLISH);
        return formatter.format(toFormat);
    }
}
