package seedu.todolist.model.task;

import seedu.todolist.commons.exceptions.IllegalValueException;

public class Interval {
    
    public static final String MESSAGE_INTERVAL_CONSTRAINTS_DATE = "End date cannot be earlier than start date";
    public static final String MESSAGE_INTERVAL_CONSTRAINTS_TIME = "End time cannot be earlier than start time";
    
    private final TaskDate startDate;
    private final TaskTime startTime;
    private final TaskDate endDate;
    private final TaskTime endTime;

    /**
     * Validates given interval.
     *
     * @throws IllegalValueException if given interval string is invalid.
     */
    public Interval(String startDate, String startTime, String endDate, String endTime) throws IllegalValueException {
        //Every task type has at least an end date
        assert endDate != null;
        
        if (isDeadlineWithTime(startDate, startTime, endDate, endTime)) {
            this.startDate = null;
            this.startTime = null;
            this.endDate = new TaskDate(endDate);
            this.endTime = new TaskTime(endTime);
        }
        else if (isDeadlineWithoutTime(startDate, startTime, endDate, endTime)) {
            this.startDate = null;
            this.startTime = null;
            this.endDate = new TaskDate(endDate);
            this.endTime = null;
        }
        else {
            this.startDate = new TaskDate(startDate);
            this.startTime = new TaskTime(startTime);
            this.endDate = new TaskDate(endDate);
            this.endTime = new TaskTime(endTime);
            
            if (!isValidDateInterval(this.startDate, this.endDate)) {
                throw new IllegalValueException(MESSAGE_INTERVAL_CONSTRAINTS_DATE);
            }
            
            if (!isValidTimeInterval(this.startDate, this.startTime, this.endDate, this.endTime)) {
                throw new IllegalValueException(MESSAGE_INTERVAL_CONSTRAINTS_TIME);
            }
        } 
    }

    /**
     * Returns true if a given interval has a valid task date interval.
     */
    private static boolean isValidDateInterval(TaskDate startDate, TaskDate endDate) {
        
        if (endDate.isBefore(startDate)) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns true if a given interval has a valid task time interval.
     */
    private static boolean isValidTimeInterval(TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime) {
        if (startDate.isEquals(endDate)) {
            if (endTime.isBefore(startTime)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns true if a given interval is a deadline with time.
     */
    public static boolean isDeadlineWithTime(String startDate, String startTime, String endDate, String endTime) {
        if (startDate == null && startTime == null && endDate != null && endTime != null) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if a given interval is a deadline without time.
     */
    public static boolean isDeadlineWithoutTime(String startDate, String startTime, String endDate, String endTime) {
        if (startDate == null && startTime == null && endDate != null && endTime == null) {
            return true;
        }
        return false;
    }
    
    public TaskDate getStartDate() {
        return this.startDate;
    }
    
    public TaskTime getStartTime() {
        return this.startTime;
    }
    
    public TaskDate getEndDate() {
        return this.endDate;
    }
    
    public TaskTime getEndTime() {
        return this.endTime;
    }
    
    public String toString() {
        if (startDate == null && startTime == null && endDate != null && endTime != null) {
            return endDate + " " + endTime;
        }
        else if (startDate == null && startTime == null && endDate != null && endTime == null) {
            return endDate + "";
        }
        else {
            return startDate + " " + startTime + " to " + endDate + " " + endTime;
        }
    }

}
