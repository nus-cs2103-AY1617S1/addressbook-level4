package seedu.todolist.model.task;

import seedu.todolist.commons.exceptions.IllegalValueException;

//@@author A0138601M
public class Interval implements Comparable<Interval> {
    
    public static final String MESSAGE_INTERVAL_CONSTRAINTS_DATE = "End date cannot be earlier than start date";
    public static final String MESSAGE_INTERVAL_CONSTRAINTS_TIME = "End time cannot be earlier than start time";
    
    private TaskDate startDate;
    private TaskTime startTime;
    private TaskDate endDate;
    private TaskTime endTime;

    
    public Interval() {
        this.startDate = null; 
        this.startTime = null;
        this.endDate = null;
        this.endTime = null;
    }
    /**
     * Validates given interval.
     *
     * @throws IllegalValueException if given set of date is an invalid interval.
     */
    public Interval(String startDate, String startTime, String endDate, String endTime) throws IllegalValueException {
        this();
        if (!isFloat(startDate, startTime, endDate, endTime)) {
            if (isDeadlineWithTime(startDate, startTime, endDate, endTime)) {
                this.endDate = new TaskDate(endDate);
                this.endTime = new TaskTime(endTime);
            }
            else if (isDeadlineWithoutTime(startDate, startTime, endDate, endTime)) {
                this.endDate = new TaskDate(endDate);
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
    }

    /**
     * Returns true if a given interval has a valid task date interval.
     */
    private boolean isValidDateInterval(TaskDate startDate, TaskDate endDate) {
        if (endDate.isBefore(startDate)) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns true if a given interval has a valid task time interval.
     */
    private boolean isValidTimeInterval(TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime) {
        if (startDate.isEquals(endDate)) {
            if (endTime.isBefore(startTime)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns true if the interval object is a deadline with time.
     */
    public boolean isDeadlineWithTime() {
        if (this.startDate == null && this.startTime == null && this.endDate != null && this.endTime != null) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if a given set of datetime is a deadline with time.
     */
    private boolean isDeadlineWithTime(String startDate, String startTime, String endDate, String endTime) {
        if (startDate == null && startTime == null && endDate != null && endTime != null) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if the interval object is a deadline without time.
     */
    public boolean isDeadlineWithoutTime() {
        if (this.startDate == null && this.startTime == null && this.endDate != null && this.endTime == null) {
            return true;
        }
        return false;
    } 
    
    /**
     * Returns true if a given set of datetime is a deadline without time.
     */
    private boolean isDeadlineWithoutTime(String startDate, String startTime, String endDate, String endTime) {
        if (startDate == null && startTime == null && endDate != null && endTime == null) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if the interval object is a floating task
     */
    public boolean isFloat() {
        if (this.startDate == null && this.startTime == null && this.endDate == null && this.endTime == null) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if a given interval is a floating task
     */
    private boolean isFloat(String startDate, String startTime, String endDate, String endTime) {
        if (startDate == null && startTime == null && endDate == null && endTime == null) {
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
        if (!this.isFloat()) {
            if (this.isDeadlineWithTime()) {
                return endDate + " " + endTime;
            }
            else if (this.isDeadlineWithoutTime()) {
                return endDate + "";
            }
            else {
                return startDate + " " + startTime + " to " + endDate + " " + endTime;
            }
        }
        return null;
    }
    
    @Override
    public int compareTo(Interval interval) {
        TaskDate firstDate = this.getDateToCompare();
        TaskDate secondDate = interval.getDateToCompare();
        TaskTime firstTime = this.getTimeToCompare();
        TaskTime secondTime = interval.getTimeToCompare();
        
        if (firstDate == null) { //first task is float
            if (secondDate == null) { //both are floating task
                return 0;
            }
            else { //floating tasks always later than timed task
                return 1;
            }
        }
        else { //first task is timed
            if (secondDate == null) { //timed tasks always earlier than floating task
                return -1;
            }
            else { //both are timed tasks
                if (firstDate.isEquals(secondDate)) { //if date are the same, determine order with time
                    if (firstTime.isEquals(secondTime)) { //both datetime are equal
                        return 0;
                    }
                    else if (firstTime.isBefore(secondTime)) { //first task is earlier
                        return -1;
                    }
                    else { //first task is later
                        return 1;
                    }
                }
                else if (firstDate.isBefore(secondDate)) { //first task is earlier
                    return -1;
                }
                else { //first task is later
                    return 1;
                }
            }
        }
    }
    
    private TaskDate getDateToCompare() {
        if (this.startDate != null) {
            return this.startDate;
        }
        else if (this.endDate != null) {
            return this.endDate;
        }
        else {
            return null;
        }
    }
    
    private TaskTime getTimeToCompare() {
        if (this.startTime != null) {
            return this.startTime;
        }
        else if (this.endTime != null) {
            return this.endTime;
        }
        else {
            return null;
        }
    }

}
