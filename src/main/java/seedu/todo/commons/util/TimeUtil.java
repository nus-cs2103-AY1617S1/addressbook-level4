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

    private static final String WORD_IN = "in";
    private static final String WORD_BY = "by";
    private static final String WORD_SINCE = "since";
    private static final String WORD_AGO = "ago";
    private static final String WORD_FROM = "from";
    private static final String WORD_TO = "to";
    private static final String WORD_TOMORROW = "tomorrow";
    private static final String WORD_YESTERDAY = "yesterday";
    private static final String WORD_TODAY = "today";
    private static final String WORD_TONIGHT = "tonight";
    private static final String WORD_COMMA = ",";
    private static final String WORD_SPACE = " ";

    private static final String DUE_NOW = "due now";
    private static final String DUE_LESS_THAN_A_MINUTE = "in less than a minute";

    private static final String UNIT_MINUTES = "minutes";
    private static final String VALUE_ONE_MINUTE = "1 minute";

    private static final String FORMAT_DATE_WITH_YEAR = "d MMMM yyyy";
    private static final String FORMAT_DATE_NO_YEAR = "d MMMM";
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
            return getDeadlineNotOverdueText(currentTime, endTime);
        } else {
            return getDeadlineOverdueText(currentTime, endTime);
        }
    }

    /**
     * Helper method of {@link #getTaskDeadlineText(LocalDateTime)} to get deadline text
     * when it is still not overdue (currentTime < endTime).
     * @param currentTime the time now
     * @param endTime the due date and time
     * @return a formatted deadline string
     */
    private String getDeadlineNotOverdueText(LocalDateTime currentTime, LocalDateTime endTime) {
        Duration durationCurrentToEnd = Duration.between(currentTime, endTime);
        long minutesToDeadline = durationCurrentToEnd.toMinutes();
        long secondsToDeadline = durationCurrentToEnd.getSeconds();

        StringJoiner stringJoiner = new StringJoiner(WORD_SPACE);

        if (secondsToDeadline <= 59) {
            return DUE_LESS_THAN_A_MINUTE;
        } else if (minutesToDeadline <= 59) {
            stringJoiner.add(WORD_IN).add(getMinutesText(currentTime, endTime));
        } else {
            stringJoiner.add(WORD_BY).add(getDateText(currentTime, endTime) + WORD_COMMA)
                    .add(getTimeText(endTime));
        }
        return stringJoiner.toString();
    }

    /**
     * Helper method of {@link #getTaskDeadlineText(LocalDateTime)} to get deadline text
     * when it is overdue (currentTime > endTime).
     * @param currentTime the time now
     * @param endTime the due date and time
     * @return a formatted deadline string
     */
    private String getDeadlineOverdueText(LocalDateTime currentTime, LocalDateTime endTime) {
        Duration durationCurrentToEnd = Duration.between(currentTime, endTime);
        long minutesToDeadline = durationCurrentToEnd.toMinutes();
        long secondsToDeadline = durationCurrentToEnd.getSeconds();

        StringJoiner stringJoiner = new StringJoiner(WORD_SPACE);

        if (secondsToDeadline >= -59) {
            return DUE_NOW;
        } else if (minutesToDeadline >= -59) {
            stringJoiner.add(getMinutesText(currentTime, endTime)).add(WORD_AGO);
        } else {
            stringJoiner.add(WORD_SINCE).add(getDateText(currentTime, endTime) + WORD_COMMA)
                    .add(getTimeText(endTime));
        }
        return stringJoiner.toString();
    }

    /**
     * Gets the event date and time text for the UI
     * @param startTime of the event
     * @param endTime of the event
     * @return a formatted event duration string
     */
    public String getEventTimeText(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            logger.log(Level.WARNING, "Either startTime or endTime is missing in getEventTimeText(...)");
            return "";
        } else if (startTime.isAfter(endTime)) {
            logger.log(Level.WARNING, "Start time is after end time in getEventTimeText(...)");
            return "";
        }

        LocalDateTime currentTime = LocalDateTime.now(clock);
        StringJoiner joiner = new StringJoiner(WORD_SPACE);
        if (isSameDay(startTime, endTime)) {
            joiner.add(getDateText(currentTime, startTime) + WORD_COMMA)
                    .add(WORD_FROM).add(getTimeText(startTime))
                    .add(WORD_TO).add(getTimeText(endTime));
        } else {
            joiner.add(WORD_FROM).add(getDateText(currentTime, startTime) + WORD_COMMA).add(getTimeText(startTime))
                    .add(WORD_TO).add(getDateText(currentTime, endTime) + WORD_COMMA).add(getTimeText(endTime));
        }
        return joiner.toString();
    }


    /**
     * Gives a formatted text of the dateTime based on the current system time, and then returns one of the following:
     *      "Yesterday", "Today", "Tonight", "Tomorrow",
     *      full date without year if this year,
     *      full date with year if other year.
     *
     * @param dateTime to format the date with
     * @return a formatted date text described above
     */
    private String getDateText(LocalDateTime currentTime, LocalDateTime dateTime) {
        if (isYesterday(currentTime, dateTime)) {
            return WORD_YESTERDAY;
        } else if (isTonight(currentTime, dateTime)) {
            return WORD_TONIGHT;
        } else if (isToday(currentTime, dateTime)) {
            return WORD_TODAY;
        } else if (isTomorrow(currentTime, dateTime)) {
            return WORD_TOMORROW;
        } else if (isSameYear(currentTime, dateTime)) {
            return dateTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_NO_YEAR));
        } else {
            return dateTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_WITH_YEAR));
        }
    }

    /**
     * Returns a formatted string of the time component of dateTime
     * @param dateTime to format the time with
     * @return a formatted time text (HH:MM A/PM)
     */
    private String getTimeText(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(FORMAT_TIME));
    }

    /**
     * Counts the number of minutes between the two dateTimes and prints out either:
     *      "1 minute" or "X minutes", for X != 1, X >= 0.
     * @param dateTime1 the first time instance
     * @param dateTime2 the other time instance
     * @return a formatted string to tell number of minutes left (as above)
     */
    private String getMinutesText(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        Duration duration = Duration.between(dateTime1, dateTime2);
        long minutesToDeadline = Math.abs(duration.toMinutes());

        if (minutesToDeadline == 1){
            return VALUE_ONE_MINUTE;
        } else {
            return minutesToDeadline + WORD_SPACE + UNIT_MINUTES;
        }
    }

    public boolean isTomorrow(LocalDateTime dateTimeToday, LocalDateTime dateTimeTomorrow) {
        LocalDate dayBefore = dateTimeToday.toLocalDate();
        LocalDate dayAfter = dateTimeTomorrow.toLocalDate();
        return dayBefore.plusDays(1).equals(dayAfter);
    }
    
    public boolean isToday(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        LocalDate date1 = dateTime1.toLocalDate();
        LocalDate date2 = dateTime2.toLocalDate();
        return date1.equals(date2);
    }

    private boolean isTonight(LocalDateTime dateTimeToday, LocalDateTime dateTimeTonight) {
        return isToday(dateTimeToday, dateTimeTonight)
                && dateTimeTonight.toLocalTime().isAfter(LocalTime.of(17, 59, 59));
    }
    
    private boolean isYesterday(LocalDateTime dateTimeToday, LocalDateTime dateTimeYesterday) {
        return isTomorrow(dateTimeYesterday, dateTimeToday);
    }

    private boolean isSameDay(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.toLocalDate().equals(dateTime2.toLocalDate());
    }
    
    private boolean isSameYear(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.getYear() == dateTime2.getYear();
    }
    
    public boolean isOverdue(LocalDateTime endTime) {
        if (endTime == null) {
            logger.log(Level.WARNING, "endTime in isOverdue(...) is null.");
            return false;
        }
        return endTime.isBefore(LocalDateTime.now(clock));
    }
    
    public boolean isOngoing(LocalDateTime startTime, LocalDateTime endTime){
        if(endTime == null){
            logger.log(Level.WARNING, "endTime in isOngoing(...) is null.");
            return false;
        }
        else if(startTime == null){
            logger.log(Level.WARNING, "startTime in isOngoing(...) is null");
            return false;
        }
        return (LocalDateTime.now(clock).isAfter(startTime) && LocalDateTime.now(clock).isBefore(endTime));
    }

    //@@author A0135817B
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
