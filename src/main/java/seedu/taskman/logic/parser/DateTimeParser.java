package seedu.taskman.logic.parser;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import seedu.taskman.commons.exceptions.IllegalValueException;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Generates machine readable datetime from natural language datetime
 */
public class DateTimeParser {
    // Examples: today 2359, tmr 0000, mon 0400, this tue 1600, next thu 2200
    // TODO: mention that time CANNOT come before date
    public static final String DESCRIPTION_DATE_TIME =
            "DATE & TIME (can use natural language, eg: 2nd Wed from now, 9pm)";
    // UG/DG: update changes in duration format
    public static final String SINGLE_DURATION =
            "(?:(?:[1-9]+[0-9]*) (?:(?:min)|(?:hour)|(?:day)|(?:week)|(?:month)|(?:year))s? ?)";
    // TODO: use in the future to allow "3 days 4 hours"
    public static final String MULTIPLE_DURATION =
            "^" + SINGLE_DURATION + "+$";
    public static final String DESCRIPTION_DURATION = "<number> <min/hour/day/week/month/year(s)>";
    private static final String GENERIC_ERROR_DATETIME = "Invalid date time";
    private static final String GENERIC_ERROR_DURATION = "Invalid duration";


    private static final Parser parser = new Parser();

    /**
     * Converts a date & time in natural language to unix time (seconds)
     */
    public static long getUnixTime(String naturalDateTime) throws IllegalDateTimeException {
        if (hasGroupOfFourDigits(naturalDateTime) && !fourDigitsAtStringTail(naturalDateTime)) {
            throw new IllegalDateTimeException("Time cannot come before date");
        }

        String timeZoneCorrected = naturalDateTime + " UTC";
        List<DateGroup> groups = parser.parse(timeZoneCorrected);

        // only use the first DateGroup & Date object in the group
        try {
            if (groups.isEmpty()) {
                throw new IllegalDateTimeException(GENERIC_ERROR_DATETIME);
            } else {
                DateGroup group = groups.get(0);
                Date date = getFirstDate(group.getDates());
                return date.toInstant().getEpochSecond();
            }
        } catch (IllegalDateTimeException e) {
            throw new IllegalDateTimeException(GENERIC_ERROR_DATETIME);
        }
    }

    private static boolean hasGroupOfFourDigits(String str) {
        return str.matches("\\d{4}.*");
    }

    private static boolean fourDigitsAtStringTail(String str) {
        if (str.length() < 4) {
            return false;
        } else {
            String lastFourChars = str.substring(str.length() - 4);
            return lastFourChars.matches("\\d{4}");
        }
    }

    private static Date getFirstDate(List<Date> dates) throws IllegalDateTimeException {
        if (dates.isEmpty()) {
            throw new IllegalDateTimeException("No first date");
        } else {
            return dates.get(0);
        }
    }

    /**
     * Converts a natural duration to an end time in unix time (seconds)
     */
    public static long durationToUnixTime(long startUnixTime, String naturalDuration) throws IllegalDateTimeException {
        if (!naturalDuration.matches(SINGLE_DURATION)) {
            throw new IllegalDateTimeException("failed to match regex");
        } else {
            long unixTimeNow = Instant.now().getEpochSecond();
            System.out.println("fake duration to unix: " + getUnixTime(naturalDuration));

            long actualDurationSeconds = getUnixTime(naturalDuration) - unixTimeNow;
            System.out.println("calculated duration " + actualDurationSeconds);
            long endUnixTime = startUnixTime + actualDurationSeconds;

            if (endUnixTime < startUnixTime) {
                throw new IllegalDateTimeException(GENERIC_ERROR_DURATION);
            } else {
                return endUnixTime;
            }
        }
    }


    public static class IllegalDateTimeException extends IllegalValueException {
        public IllegalDateTimeException(String message) {
            super(message);
        }
    }
}
