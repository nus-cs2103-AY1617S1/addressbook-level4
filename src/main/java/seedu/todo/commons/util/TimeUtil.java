package seedu.todo.commons.util;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Utility methods that deals with time.
 */
public class TimeUtil {
    
    private static final String PREFIX_BEFORE_DEADLINE = " in";
    
    private static final String HOUR_SINGLE_UNIT = " hour";
    private static final String HOUR_MULTIPLE_UNIT = " hours";
    private static final String MINUTE_SINGLE_UNIT = " minute";
    private static final String MINUTE_MULTIPLE_UNIT = " minutes";
    
    /**
     * Gets the task deadline expression for the UI.
     * @param endTime ending time
     * @return a formatted deadline String
     */
    public static String getTaskDeadlineString(LocalDateTime endTime) {
        assert(endTime != null);
        
        LocalDateTime currentTime = LocalDateTime.now();
        Duration durationCurrentToEnd = Duration.between(currentTime, endTime);
        
        long hoursToDeadline = durationCurrentToEnd.toHours();
        
        if (hoursToDeadline == 1) {
            return "in an hour";
        }
        
        String formattedTime = "in " + String.valueOf(hoursToDeadline) + " hours";
        return formattedTime;
    }
    
}
