package seedu.address.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
