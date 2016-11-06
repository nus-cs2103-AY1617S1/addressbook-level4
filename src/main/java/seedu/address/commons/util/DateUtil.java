package seedu.address.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.joestelmach.natty.DateGroup;

//@@author A0146123R
/**
 * Utility methods related to Dates
 */
public class DateUtil {

    private static final String DATE_VALIDATION_REGEX = "^[0-3][0-9].[0-1][0-9].([0-9]{4})(-[0-2]?[0-9]?)?";
    // EXAMPLE = "15.10.2016-14"

    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy-HH");
    private static final DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            .withLocale(Locale.GERMAN);
    private static final String EMPTY = "";
    private static final String TIME_SEPERATOR = "-";

    /**
     * Returns true if the given string is in a valid date format.
     */
    public static boolean isValidDateFormat(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string is an empty deadline.
     */
    public static boolean isEmptyDate(String test) {
        return test.equals(EMPTY);
    }

    /**
     * Parse the given date.
     * 
     * @return a String that represents a valid date (i.e. if the user input
     *         31.04.2016, it will be corrected to 01.05.2016)
     * @throws IndexOutOfBoundsException
     *             if the given date cannot be parsed
     */
    public static String parseDate(String date) throws IndexOutOfBoundsException {
        String[] parts = date.split(TIME_SEPERATOR);
        if (parts.length == 1) {
            try {
                return dateFormat.format(dateFormat.parse(date));
            } catch (ParseException e) {
                return parseNotFormattedDate(date);
            }
        }
        try {
            return dateTimeFormat.format(dateTimeFormat.parse(date));
        } catch (ParseException e) {
            return parseNotFormattedDate(date);
        }
    }

    // @@author A0142325R

    /**
     * get the current date and time in LocalDate format
     * 
     * @return current LocalDate
     */
    public static LocalDate getCurrentLocalDate() {
        Calendar currentDateTime = Calendar.getInstance();
        LocalDate currentDate = LocalDate.parse(dateFormat.format(currentDateTime.getTime()).toString(),
                germanFormatter);
        return currentDate;
    }

    public static DateTimeFormatter getGermanFormatter() {
        return germanFormatter;
    }

    /**
     * format the LocalDate in "dd.MM.yyyy-XX" format
     * 
     * @param LocalDate
     *            date to be formatted
     * @return a string representing time in "dd.MM.yyyy-XX" format
     */
    public static String getFormattedDateString(LocalDate date) {
        return germanFormatter.format(date).toString();
    }

    /**
     * checks if the date string conforms to "dd.MM.yyyy" format
     * 
     * @param String
     *            date
     * @return true if the date string is "dd.MM.yyyy" format
     */
    public static boolean isDateFormat(String date) {
        return date.split(TIME_SEPERATOR).length == 1;

    }

    /**
     * checks if the date string conforms to "dd.MM.yyyy-XX" format
     * 
     * @param String
     *            date
     * @return true if the date string is "dd.MM.yyyy" format
     */
    public static boolean isDateTimeFormat(String date) {
        return date.split(TIME_SEPERATOR).length == 2;
    }

    /**
     * get ElapsedDays between current date and input date
     * 
     * @param LocalDate
     *            date
     * @return number of days
     */
    public static long getElapsedDaysFromCurrentDate(LocalDate date) {
        return ChronoUnit.DAYS.between(date, getCurrentLocalDate());
    }

    /**
     * Parse the given date if it does not adhere to the given date format
     * 
     * @param a
     *            string that represents a date ( i.e. yesterday, today, today
     *            at 4pm, etc)
     * @return a string that adhere to the given date format
     * @throws IndexOutOfBoundsException
     *             If the given date cannot be parsed
     */
    private static String parseNotFormattedDate(String date) throws IndexOutOfBoundsException {
        DateGroup dateGroup = new com.joestelmach.natty.Parser().parse(date).get(0);
        List<java.util.Date> parsedDate = dateGroup.getDates();
        if (dateGroup.isTimeInferred()) {
            return dateFormat.format(parsedDate.get(0));
        } else {
            return dateTimeFormat.format(parsedDate.get(0));
        }
    }

}
