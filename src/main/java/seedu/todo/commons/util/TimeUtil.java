package seedu.todo.commons.util;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Utility methods that deals with time.
 */
public class TimeUtil {
    
    /**
     * Gets the task deadline expression for the UI.
     * @param endTime ending time
     * @return a formatted deadline String
     */
    public static String getTaskDeadlineString(LocalDateTime endTime) {
        //Validate assumptions
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
