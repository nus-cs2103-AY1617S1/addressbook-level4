package seedu.taskman.logic.parser;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import seedu.taskman.commons.exceptions.IllegalValueException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Generates machine readable datetime from natural language datetime
 */
public class DateTimeParser {
    // Examples: today 2359, tmr 0000, mon 0400, this tue 1600, next thu 2200
    // UG/DG: update changes in duration format
    // TODO: mention that time CANNOT come before date
    public static final String DESCRIPTION_DATE_TIME_FULL =
            "can use natural language, eg: 2nd Wed from now, 9pm";
    public static final String DESCRIPTION_DATE_TIME_SHORT = "DATE & TIME";
    public static final String SINGLE_DURATION =
            "(?:(?:[1-9]+[0-9]*) (?:(?:min)|(?:hour)|(?:day)|(?:week)|(?:month)|(?:year))s? ?)";
    // TODO: use in the future to allow "3 days 4 hours"
    public static final String MULTIPLE_DURATION =
            "^" + SINGLE_DURATION + "+$";
    public static final String DESCRIPTION_DURATION = "<number> <min/hour/day/week/month/year(s)>";
    public static final String TIME_BEFORE_DATE_ERROR = "Do not enter time before date";
    private static final String GENERIC_ERROR_DATETIME = "Invalid date time";
    private static final String GENERIC_ERROR_DURATION = "Invalid duration";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-YYYY HH:mm");
    private static final Parser parser = new Parser();

    /**
     * Converts a date & time in natural language to unix time (seconds)
     */
    public static long getUnixTime(String naturalDateTime, String errorMessage) throws IllegalDateTimeException {
        // assume 4 digits at tail of string == time at end
        boolean timeIsBeforeDate = naturalDateTime.matches(".* \\d{4}.*")  &&
                !naturalDateTime.matches(".* \\d{4}$");

        if (timeIsBeforeDate) {
            throw new IllegalDateTimeException(TIME_BEFORE_DATE_ERROR);
        }

        String timeZoneCorrected = naturalDateTime + " UTC";
        List<DateGroup> groups = parser.parse(timeZoneCorrected);

        // only use the first DateGroup & Date object in the group
        try {
            if (groups.isEmpty()) {
                throw new IllegalDateTimeException();
            } else {
                DateGroup group = groups.get(0);
                Date date = getFirstDate(group.getDates());
                return date.toInstant().getEpochSecond();
            }
        } catch (IllegalDateTimeException e) {
            throw new IllegalDateTimeException(errorMessage);
        }
    }

    public static long getUnixTime(String naturalDateTime) throws IllegalDateTimeException {
        return getUnixTime(naturalDateTime, GENERIC_ERROR_DATETIME);
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
            long actualDurationSeconds = getUnixTime(naturalDuration) - unixTimeNow;
            long endUnixTime = startUnixTime + actualDurationSeconds;

            if (endUnixTime < startUnixTime) {
                throw new IllegalDateTimeException(GENERIC_ERROR_DURATION);
            } else {
                return endUnixTime;
            }
        }
    }

    public static String epochSecondToDetailedDateTime(long epochSecond) {
        Instant instant = Instant.ofEpochSecond(epochSecond);
        return ZonedDateTime
        		.ofInstant(instant, ZoneId.systemDefault())
        		.format(formatter)
        		.toString();
    }

    public static String epochSecondToShortDateTime(long epochSecond) {
        Instant instant = Instant.ofEpochSecond(epochSecond);
        return LocalDateTime
        		.ofInstant(instant, ZoneId.systemDefault())
        		.format(formatter)
        		.toString();
    }

    public static class IllegalDateTimeException extends IllegalValueException {
        public IllegalDateTimeException() {
            super();
        }

        public IllegalDateTimeException(String message) {
            super(message);
        }
    }
}
