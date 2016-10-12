package seedu.todolist.model.task;

import seedu.todolist.commons.exceptions.IllegalValueException;

public class Interval {
    
    public static final String MESSAGE_INTERVAL_CONSTRAINTS_DATE = "End date cannot be earlier than start date";
    public static final String MESSAGE_INTERVAL_CONSTRAINTS_TIME = "End time cannot be earlier than start time";
    
    public final TaskDate startDate;
    public final TaskTime startTime;
    public final TaskDate endDate;
    public final TaskTime endTime;

    /**
     * Validates given interval.
     *
     * @throws IllegalValueException if given interval string is invalid.
     */
    public Interval(String startDate, String startTime, String endDate, String endTime) throws IllegalValueException {
        assert startDate != null;
        assert startTime != null;
        assert endDate != null;
        assert endTime != null;
        
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

    /**
     * Returns true if a given interval has a valid task date interval.
     */
    public static boolean isValidDateInterval(TaskDate startDate, TaskDate endDate) {
        if (endDate.isBefore(startDate)) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns true if a given interval has a valid task time interval.
     */
    public static boolean isValidTimeInterval(TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime) {
        if (startDate.isEquals(endDate)) {
            if (endTime.isBefore(startTime)) {
                return false;
            }
        }
        return true;
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

}
