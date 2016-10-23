package seedu.todo.logic.arguments;

import java.time.LocalDateTime;

//@@author A0135817B
/**
 * Utility container class for the output from DateRangeArgument
 */
public class DateRange {
    private final LocalDateTime endTime; 
    private LocalDateTime startTime;
    
    public DateRange(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public DateRange(LocalDateTime startTime, LocalDateTime endTime) {
        this(endTime);
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public boolean isRange() {
        return startTime != null;
    }
}
