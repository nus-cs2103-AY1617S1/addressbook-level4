package seedu.todo.commons.util;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

/**
 * Utility methods that deals with time.
 */
public class TimeUtil {
    
    /* Constants */
    private static final String DEADLINE_PREFIX_IN = "in";
    private static final String DEADLINE_PREFIX_BY = "by";
    private static final String DEADLINE_PREFIX_SINCE = "since";
    private static final String DEADLINE_SUFFIX_AGO = "ago";

    private static final String UNIT_MINUTE = "minute";
    private static final String UNIT_MINUTES = "minutes";
    
    private static final String DUE_NOW = "due now";
    private static final String DUE_LESS_THAN_A_MINUTE = "in less than a minute";
    private static final String DUE_TOMORROW = "tomorrow,";
    private static final String DUE_YESTERDAY = "yesterday,";
    private static final String DUE_TODAY = "today,";
    private static final String DUE_TONIGHT = "tonight,";
    
    private static final String FORMAT_DATE_WITH_YEAR = "d MMMM yyyy";
    private static final String FORMAT_DATE_NO_YEAR = "d MMMM";
    private static final String FORMAT_TIME = "h:mm a";
    
    /* Variables */
    protected Clock clock = Clock.systemDefaultZone();
    
    /**
     * Gets the task deadline expression for the UI.
     * @param endTime ending time
     * @return a formatted deadline String
     */
    public String getTaskDeadlineText(LocalDateTime endTime) {
        assert(endTime != null);
        
        StringJoiner stringJoiner = new StringJoiner(" ");
        LocalDateTime currentTime = LocalDateTime.now(clock);
        Duration durationCurrentToEnd = Duration.between(currentTime, endTime);
        long minutesToDeadline = durationCurrentToEnd.toMinutes();
        long secondsToDeadline = durationCurrentToEnd.getSeconds();
        
        if (currentTime.isBefore(endTime)) {
            if (secondsToDeadline <= 59) {
                return DUE_LESS_THAN_A_MINUTE;
            } else if (minutesToDeadline == 1) {
                stringJoiner.add(DEADLINE_PREFIX_IN).add("1").add(UNIT_MINUTE);
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
                        .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_NO_YEAR + ", " + FORMAT_TIME)));
            } else {
                stringJoiner.add(DEADLINE_PREFIX_BY)
                        .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_WITH_YEAR + ", " + FORMAT_TIME)));
            }
        } else {
            if (secondsToDeadline >= -59) {
                return DUE_NOW;
            } else if (minutesToDeadline == -1) {
                stringJoiner.add("1").add(UNIT_MINUTE).add(DEADLINE_SUFFIX_AGO);
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
                        .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_NO_YEAR + ", " + FORMAT_TIME)));
            } else {
                stringJoiner.add(DEADLINE_PREFIX_SINCE)
                        .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_WITH_YEAR + ", " + FORMAT_TIME)));
            }
        }        
        return stringJoiner.toString();
    }
    
    public String getEventTimeText(LocalDateTime startTime, LocalDateTime endTime) {
        assert(startTime != null);
        assert(endTime != null);
        assert(startTime.isBefore(endTime));
        return "from " + startTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_WITH_YEAR + ", " + FORMAT_TIME)) + " to "
                + endTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_WITH_YEAR + ", " + FORMAT_TIME));
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
}
