package seedu.todo.commons.util;

import seedu.todo.commons.core.LogsCenter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

//@@author A0315805H
/**
 * Utility methods that deals with time.
 */
public class TimeUtil {

    /* Constants */
    private static final Logger logger = LogsCenter.getLogger(TimeUtil.class);

    private static final String DEADLINE_PREFIX_IN = "in";
    private static final String DEADLINE_PREFIX_BY = "by";
    private static final String DEADLINE_PREFIX_SINCE = "since";
    private static final String DEADLINE_SUFFIX_AGO = "ago";

    private static final String DUE_NOW = "due now";
    private static final String DUE_LESS_THAN_A_MINUTE = "in less than a minute";
    private static final String DUE_TOMORROW = "tomorrow,";
    private static final String DUE_YESTERDAY = "yesterday,";
    private static final String DUE_TODAY = "today,";
    private static final String DUE_TONIGHT = "tonight,";

    private static final String UNIT_MINUTES = "minutes";
    private static final String VALUE_ONE_MINUTE = "1 minute";

    private static final String FORMAT_DATE_TIME_WITH_YEAR = "d MMMM yyyy, h:mm a";
    private static final String FORMAT_DATE_TIME_NO_YEAR = "d MMMM, h:mm a";
    private static final String FORMAT_TIME = "h:mm a";
    
    private static final Pattern DATE_REGEX = Pattern.compile("\\b([0123]?\\d)([/-])([01]?\\d)(?=\\2\\d{2,4}|\\s|$)");
    
    /* Variables */
    protected Clock clock = Clock.systemDefaultZone();
    
    /**
     * Gets the task deadline expression for the UI.
     * @param endTime ending time
     * @return a formatted deadline String
     */
    public String getTaskDeadlineText(LocalDateTime endTime) {
        if (endTime == null) {
            logger.log(Level.WARNING, "endTime in getTaskDeadlineText(...) is missing.");
            return "";
        }

        LocalDateTime currentTime = LocalDateTime.now(clock);
        if (endTime.isAfter(currentTime)) {
            return getDeadlineInFutureText(currentTime, endTime);
        } else {
            return getDeadlineInPastText(currentTime, endTime);
        }
    }

    private String getDeadlineInFutureText(LocalDateTime currentTime, LocalDateTime endTime) {
        Duration durationCurrentToEnd = Duration.between(currentTime, endTime);
        long minutesToDeadline = durationCurrentToEnd.toMinutes();
        long secondsToDeadline = durationCurrentToEnd.getSeconds();

        StringJoiner stringJoiner = new StringJoiner(" ");

        if (secondsToDeadline <= 59) {
            return DUE_LESS_THAN_A_MINUTE;
        } else if (minutesToDeadline == 1) {
            stringJoiner.add(DEADLINE_PREFIX_IN).add(VALUE_ONE_MINUTE);
        } else if (minutesToDeadline > 1 && minutesToDeadline <= 59) {
            stringJoiner.add(DEADLINE_PREFIX_IN).add(String.valueOf(minutesToDeadline))
                    .add(UNIT_MINUTES);
        } else if (isToday(currentTime, endTime)) {
            stringJoiner.add(DEADLINE_PREFIX_BY)
                    .add(((endTime.toLocalTime().isBefore(LocalTime.of(18, 00))) ? DUE_TODAY : DUE_TONIGHT))
                    .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_TIME)));
        } else if (isTomorrow(currentTime, endTime)) {
            stringJoiner.add(DEADLINE_PREFIX_BY).add(DUE_TOMORROW)
                    .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_TIME)));
        } else if (isSameYear(currentTime, endTime)) {
            stringJoiner.add(DEADLINE_PREFIX_BY)
                    .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_TIME_NO_YEAR)));
        } else {
            stringJoiner.add(DEADLINE_PREFIX_BY)
                    .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_TIME_WITH_YEAR)));
        }

        return stringJoiner.toString();
    }

    private String getDeadlineInPastText(LocalDateTime currentTime, LocalDateTime endTime) {
        Duration durationCurrentToEnd = Duration.between(currentTime, endTime);
        long minutesToDeadline = durationCurrentToEnd.toMinutes();
        long secondsToDeadline = durationCurrentToEnd.getSeconds();

        StringJoiner stringJoiner = new StringJoiner(" ");

        if (secondsToDeadline >= -59) {
            return DUE_NOW;
        } else if (minutesToDeadline == -1) {
            stringJoiner.add(VALUE_ONE_MINUTE).add(DEADLINE_SUFFIX_AGO);
        } else if (minutesToDeadline < -1 && minutesToDeadline >= -59) {
            stringJoiner.add(String.valueOf(-minutesToDeadline)).add(UNIT_MINUTES)
                    .add(DEADLINE_SUFFIX_AGO);
        } else if (isToday(currentTime, endTime)) {
            stringJoiner.add(DEADLINE_PREFIX_SINCE)
                    .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_TIME)));
        } else if (isYesterday(currentTime, endTime)) {
            stringJoiner.add(DEADLINE_PREFIX_SINCE).add(DUE_YESTERDAY)
                    .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_TIME)));
        } else if (isSameYear(currentTime, endTime)) {
            stringJoiner.add(DEADLINE_PREFIX_SINCE)
                    .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_TIME_NO_YEAR)));
        } else {
            stringJoiner.add(DEADLINE_PREFIX_SINCE)
                    .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_TIME_WITH_YEAR)));
        }
        return stringJoiner.toString();
    }
    
    public String getEventTimeText(LocalDateTime startTime, LocalDateTime endTime) {
        assert(startTime != null);
        assert(endTime != null);
        assert(startTime.isBefore(endTime));
        return "from " + startTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_TIME_NO_YEAR)) + " to "
                + endTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_TIME_WITH_YEAR));
    }
    
    /**
     * Gives a formatted text of the dateTime based on the current system time, and then returns one of the following:
     *      "Yesterday", "Today", "Tonight", "Tomorrow",
     *      full date without year if same year,
     *      full date with year if different year.
     *
     * @param dateTime to format the date with
     * @return a formatted date string described above
     */
    private String getDateText(LocalDateTime dateTime) {
        LocalDateTime currentTime = LocalDateTime.now(clock);
        if (isYesterday(currentTime, dateTime)) {
            return DUE_YESTERDAY;
        } else if (isTonight(currentTime, dateTime)) {
            return DUE_TONIGHT;
        } else if (isToday(currentTime, dateTime)) {
            return DUE_TODAY;
        } else if (isTomorrow(currentTime, dateTime)) {
            return DUE_TOMORROW;
        } else if (isSameYear(currentTime, dateTime)) {
            return dateTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_NO_YEAR);
        } else {
            return dateTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_WITH_YEAR));
        }
    }
    private boolean isTomorrow(LocalDateTime dateTimeToday, LocalDateTime dateTimeTomorrow) {
        LocalDate dayBefore = dateTimeToday.toLocalDate();
        LocalDate dayAfter = dateTimeTomorrow.toLocalDate();
        return dayBefore.plusDays(1).equals(dayAfter);
    }
    
    private boolean isToday(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        LocalDate date1 = dateTime1.toLocalDate();
        LocalDate date2 = dateTime2.toLocalDate();
        return date1.equals(date2);
    }
    
    private boolean isYesterday(LocalDateTime dateTimeToday, LocalDateTime dateTimeYesterday) {
        return isTomorrow(dateTimeYesterday, dateTimeToday);
    }
    
    private boolean isSameYear(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.getYear() == dateTime2.getYear();
    }
    
    public boolean isOverdue(LocalDateTime endTime) {
        assert (endTime != null);
        return endTime.isBefore(LocalDateTime.now(clock));
    }
    
    
    /**
     * Translates input string from International date format (DD/MM/YYYY) to American
     * date format (MM/DD/YYYY), because Natty only recognizes the later 
     */
    public static String toAmericanDateFormat(String input) {
        return DATE_REGEX.matcher(input).replaceAll("$3$2$1");
    }
    
    //@@author A0135817B-reuse
    // From http://stackoverflow.com/a/27378709/313758
    /**
     * Calls {@link #asLocalDate(Date, ZoneId)} with the system default time zone.
     */
    public static LocalDate asLocalDate(Date date) {
        return asLocalDate(date, ZoneId.systemDefault());
    }

    /**
     * Creates {@link LocalDate} from {@code java.util.Date} or it's subclasses. Null-safe.
     */
    public static LocalDate asLocalDate(Date date, ZoneId zone) {
        if (date == null)
            return null;

        if (date instanceof java.sql.Date)
            return ((java.sql.Date) date).toLocalDate();
        else
            return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDate();
    }

    /**
     * Calls {@link #asLocalDateTime(Date, ZoneId)} with the system default time zone.
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        return asLocalDateTime(date, ZoneId.systemDefault());
    }

    /**
     * Creates {@link LocalDateTime} from {@code java.util.Date} or it's subclasses. Null-safe.
     */
    public static LocalDateTime asLocalDateTime(Date date, ZoneId zone) {
        if (date == null)
            return null;

        if (date instanceof java.sql.Timestamp)
            return ((java.sql.Timestamp) date).toLocalDateTime();
        else
            return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDateTime();
    }

}
