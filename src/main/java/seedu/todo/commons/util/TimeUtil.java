package seedu.todo.commons.util;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Utility methods that deals with time.
 */
public class TimeUtil {
    
    /**
     * Gets the formatted time for the UI, where if the user provides:
     *     endTime only          - parsed as deadline format for tasks
     *     startTime and endTime - parsed as duration format for events
     *     any other combination - invalid input
     * 
     * @param startTime starting time (optional)
     * @param endTime ending time (compulsory)
     * @return a formatted time to be displayed in UI
     */
    public static String printFormattedTime(LocalDateTime startTime, LocalDateTime endTime) {
        //Validate assumptions
        assert(endTime != null);
        assert(startTime == null || startTime.isBefore(endTime));
        
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
