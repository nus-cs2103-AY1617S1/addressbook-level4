package seedu.todo.commons.util;

import java.time.Clock;
import java.time.Duration;
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

    private static final String MINUTE_SINGLE_UNIT = "minute";
    private static final String MINUTES_MULTIPLE_UNIT = "minutes";
    
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
                stringJoiner.add(DEADLINE_PREFIX_IN).add("1").add(MINUTE_SINGLE_UNIT);
            } else if (minutesToDeadline > 1 && minutesToDeadline <= 59) {
                stringJoiner.add(DEADLINE_PREFIX_IN).add(String.valueOf(minutesToDeadline))
                        .add(MINUTES_MULTIPLE_UNIT);
            } else if (currentTime.toLocalDate().equals(endTime.toLocalDate())) {
                stringJoiner.add(DEADLINE_PREFIX_BY)
                        .add(((endTime.toLocalTime().isBefore(LocalTime.of(18, 00))) ? DUE_TODAY : DUE_TONIGHT))
                        .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_TIME)));
            } else if (currentTime.toLocalDate().plusDays(1).equals(endTime.toLocalDate())) {
                stringJoiner.add(DEADLINE_PREFIX_BY).add(DUE_TOMORROW)
                        .add(endTime.format(DateTimeFormatter.ofPattern(FORMAT_TIME)));
            } else if (currentTime.getYear() == endTime.getYear()) {
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
                stringJoiner.add("1").add(MINUTE_SINGLE_UNIT).add(DEADLINE_SUFFIX_AGO);
            } else if (minutesToDeadline < -1 && minutesToDeadline >= -59) {
                stringJoiner.add(String.valueOf(-minutesToDeadline)).add(MINUTES_MULTIPLE_UNIT)
                        .add(DEADLINE_SUFFIX_AGO);
            } else if (currentTime.toLocalDate().equals(endTime.toLocalDate())) {
                stringJoiner.add(DEADLINE_PREFIX_SINCE)
                        .add(endTime.format(DateTimeFormatter.ofPattern("h:mm a")));
            } else if (currentTime.toLocalDate().minusDays(1).equals(endTime.toLocalDate())) {
                stringJoiner.add(DEADLINE_PREFIX_SINCE).add(DUE_YESTERDAY)
                        .add(endTime.format(DateTimeFormatter.ofPattern("h:mm a")));
            } else if (currentTime.getYear() == endTime.getYear()) {
                stringJoiner.add(DEADLINE_PREFIX_SINCE)
                        .add(endTime.format(DateTimeFormatter.ofPattern("d MMMM, h:mm a")));
            } else {
                stringJoiner.add(DEADLINE_PREFIX_SINCE)
                        .add(endTime.format(DateTimeFormatter.ofPattern("d MMMM yyyy, h:mm a")));
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
}
