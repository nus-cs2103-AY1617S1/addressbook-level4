package seedu.todo.commons.util;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility methods that deals with time.
 */
public class TimeUtil {
    
    /* Constants */
    private static final String DEADLINE_PREFIX_IN = "in ";
    private static final String DEADLINE_PREFIX_BY = "by ";
    private static final String DEADLINE_PREFIX_SINCE = "since ";
    private static final String DEADLINE_SUFFIX_AGO = " ago";
    
    private static final String HOUR_SINGLE_UNIT = " hour";
    private static final String HOURS_MULTIPLE_UNIT = " hours";
    private static final String MINUTE_SINGLE_UNIT = " minute";
    private static final String MINUTES_MULTIPLE_UNIT = " minutes";
    
    private static final String DUE_NOW = "due now";
    private static final String DUE_LESS_THAN_A_MINUTE = "in less than a minute";
    private static final String DUE_TOMORROW = "tomorrow, ";
    private static final String DUE_YESTERDAY = "yesterday, ";
    private static final String DUE_TODAY = "today, ";
    private static final String DUE_TONIGHT = "tonight, ";
    private static final String DUE_JUST_NOW = "moments ago at ";
    
    /* Variables */
    protected Clock clock = Clock.systemDefaultZone();
    
    /**
     * Gets the task deadline expression for the UI.
     * @param endTime ending time
     * @return a formatted deadline String
     */
    public String getTaskDeadlineText(LocalDateTime endTime) {
        assert(endTime != null);
        
        LocalDateTime currentTime = LocalDateTime.now(clock);
        Duration durationCurrentToEnd = Duration.between(currentTime, endTime);
        
        long daysToDeadline = durationCurrentToEnd.toDays();
        long hoursToDeadline = durationCurrentToEnd.toHours();
        long minutesToDeadline = durationCurrentToEnd.toMinutes();
        long secondsToDeadline = durationCurrentToEnd.getSeconds();
        
        if (currentTime.isBefore(endTime)) {
            if (secondsToDeadline <= 59) {
                return DUE_LESS_THAN_A_MINUTE;
            } else if (minutesToDeadline == 1) {
                return DEADLINE_PREFIX_IN + "1" + MINUTE_SINGLE_UNIT;
            } else if (minutesToDeadline > 1 && minutesToDeadline <= 59) {
                return DEADLINE_PREFIX_IN + minutesToDeadline + MINUTES_MULTIPLE_UNIT;
            } else if (currentTime.isBefore(endTime) && currentTime.toLocalDate().equals(endTime.toLocalDate())) {
                return ((endTime.toLocalTime().isBefore(LocalTime.of(18, 00))) ? DUE_TODAY : DUE_TONIGHT) 
                        + endTime.format(DateTimeFormatter.ofPattern("h:mm a"));  
            }
            
        } else {
            if (secondsToDeadline >= -59) {
                return DUE_NOW;
            } else if (minutesToDeadline == -1) {
                return "1" + MINUTE_SINGLE_UNIT + DEADLINE_SUFFIX_AGO;
            } else if (minutesToDeadline < -1 && minutesToDeadline >= -59) {
                return (-minutesToDeadline) + MINUTES_MULTIPLE_UNIT + DEADLINE_SUFFIX_AGO;
            } else if (currentTime.isAfter(endTime) && currentTime.toLocalDate().equals(endTime.toLocalDate())) {
                return DUE_JUST_NOW + endTime.format(DateTimeFormatter.ofPattern("h:mm a"));
            }
        }
        
             
        
        
        if (daysToDeadline >= 0 && daysToDeadline <= 2 && currentTime.plusDays(1).getDayOfWeek().equals(endTime.getDayOfWeek())) {
            return DEADLINE_PREFIX_BY + DUE_TOMORROW + endTime.format(DateTimeFormatter.ofPattern("h:mm a"));
        }
        if (daysToDeadline == 0) {
            return DEADLINE_PREFIX_IN + String.valueOf(hoursToDeadline) + HOURS_MULTIPLE_UNIT;
        }
        
        
        //Same year before deadline
        if (currentTime.getYear() == endTime.getYear()) {
            return DEADLINE_PREFIX_BY + endTime.format(DateTimeFormatter.ofPattern("d MMMM, h:mm a"));
        }
               
        
        //Different year before deadline
        if (currentTime.getYear() < endTime.getYear()) {
            return DEADLINE_PREFIX_BY + endTime.format(DateTimeFormatter.ofPattern("d MMMM yyyy, h:mm a"));
        }
        
        
        
        return null;
        
        
        
        
    }
    
    
    public String getEventTimeString(LocalDateTime startTime, LocalDateTime endTime) {
        assert(startTime != null);
        assert(endTime != null);
        assert(startTime.isBefore(endTime));
        
        return null;
    }
    
}
