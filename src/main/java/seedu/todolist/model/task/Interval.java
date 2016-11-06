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
        
    }
    
    /**
     * Constructs and validates the Interval based on which parameters given is not null.
     * 
     * Constructs an event type interval if all parameters are not null.
     * Constructs an deadline with time type interval if only endDate and endTime are not null.
     * Constructs an deadline without time type interval if only endDate is not null.
     * Constructs an float type interval if all parameters are null. 
     *
     * @throws IllegalValueException if given date or time is invalid or set of date and time is an invalid interval.
     */
    public Interval(String startDate, String startTime, String endDate, String endTime) throws IllegalValueException {       
        if (isEvent(startDate, startTime, endDate, endTime)) {
            initEvent(startDate, startTime, endDate, endTime);
        } else if (isDeadlineWithTime(startDate, startTime, endDate, endTime)) {
            initDeadlineWithTime(endDate, endTime);
        } else if (isDeadlineWithoutTime(startDate, startTime, endDate, endTime)) {
            initDeadlineWithoutTime(endDate);
        } else if (isFloat(startDate, startTime, endDate, endTime)) {
            initFloat();
        } else {
            assert false : "A given interval must represent an event, deadline, or float";
        }     
    }
    
    /**
     * Initialises an event type interval.
     * 
     * @throws IllegalValueException if given date or time is invalid or set of date and time is an invalid event interval. 
     * For example, endDate earlier than startDate or endTime earlier than startTime.
     */
    private void initEvent(String startDate, String startTime, String endDate, String endTime) throws IllegalValueException {
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
     * Initialises a deadline with time type interval.
     * 
     * @throws IllegalValueException if given date or time is invalid
     */
    private void initDeadlineWithTime(String endDate, String endTime) throws IllegalValueException {
        this.endDate = new TaskDate(endDate);
        this.endTime = new TaskTime(endTime);
    }
    
    /**
     * Initialises a deadline without time type interval.
     * 
     * @throws IllegalValueException if given date is invalid
     */
    private void initDeadlineWithoutTime(String endDate) throws IllegalValueException {
        this.endDate = new TaskDate(endDate);
    }
    
    /**
     * Initialises a float type interval.
     */
    private void initFloat() {
        this.startDate = null;
        this.startTime = null;
        this.endDate = null;
        this.endTime = null;
    }

    /**
     * Returns true if a given interval has a valid task date interval. (i.e. startDate is earlier than endDate)
     */
    private boolean isValidDateInterval(TaskDate startDate, TaskDate endDate) {
        return !endDate.isBefore(startDate);
    }
    
    /**
     * Returns true if a given interval has a valid task time interval. 
     * if startDate and endDate are not equal, it is assume to be a valid interval. (i.e. startDate is earlier than endDate)
     */
    private boolean isValidTimeInterval(TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime) {
        return !startDate.equals(endDate) || !endTime.isBefore(startTime);
    }
    
    /**
     * Returns true if interval is earlier than current datetime
     */
    public boolean isOver() {
        Interval now = new Interval();
        now.endDate = TaskDate.now();
        now.endTime = TaskTime.now();
        return this.compareTo(now) < 0;
    }
    
    /**
     * Returns true if a given set of datetime is an event.
     */
    private boolean isEvent(String startDate, String startTime, String endDate, String endTime) {
        return startDate != null 
                && startTime != null 
                && endDate != null 
                && endTime != null;
    }
    
    /**
     * Returns true if the interval object is an event.
     */
    public boolean isEvent() {
        return this.startDate != null 
                && this.startTime != null 
                && this.endDate != null 
                && this.endTime != null;
    } 
    
    /**
     * Returns true if a given set of datetime is a deadline with time.
     */
    private boolean isDeadlineWithTime(String startDate, String startTime, String endDate, String endTime) {
        return startDate == null 
                && startTime == null 
                && endDate != null 
                && endTime != null;
    }
    
    /**
     * Returns true if the interval object is a deadline with time.
     */
    public boolean isDeadlineWithTime() {
        return this.startDate == null 
                && this.startTime == null 
                && this.endDate != null 
                && this.endTime != null;
    } 
    
    /**
     * Returns true if a given set of datetime is a deadline without time.
     */
    private boolean isDeadlineWithoutTime(String startDate, String startTime, String endDate, String endTime) {
        return startDate == null 
                && startTime == null
                && endDate != null 
                && endTime == null;
    }
    
    /**
     * Returns true if the interval object is a deadline without time.
     */
    public boolean isDeadlineWithoutTime() {
        return this.startDate == null 
                && this.startTime == null 
                && this.endDate != null 
                && this.endTime == null;
    }
    
    /**
     * Returns true if a given interval is a floating task
     */
    private boolean isFloat(String startDate, String startTime, String endDate, String endTime) {
        return startDate == null 
                && startTime == null 
                && endDate == null 
                && endTime == null;
    }  
    
    /**
     * Returns true if the interval object is a floating task
     */
    public boolean isFloat() {
        return this.startDate == null 
                && this.startTime == null 
                && this.endDate == null 
                && this.endTime == null;
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
    
    /**
     * Returns a formatted string based on the interval type.
     * 
     * Examples:
     * Event type - 20 Nov 2016, 3:00PM to 21 Nov 2016, 4:00PM
     * Deadline with time type - 20 Nov 2016, 3:00PM
     * Deadline without time type - 20 Nov 2016
     */
    public String toString() {
        if (this.isEvent()) {
            return formatStartDateTime() + " to " + formatEndDateTime();
        } else if (this.isDeadlineWithTime()) {
            return formatEndDateTime();
        } else if (this.isDeadlineWithoutTime()) {
            return formatEndDate();
        } else {
            return null;    
        }
    }
    
    public String formatStartDateTime() {
        return startDate + ", " + startTime;
    }
    
    public String formatEndDateTime() {
        return endDate + ", " + endTime;
    }
    
    public String formatEndDate() {
        return endDate + "";
    }
    
    @Override
    public int compareTo(Interval interval) {
        TaskDate firstDate = this.getDateToCompare();
        TaskDate secondDate = interval.getDateToCompare();
        TaskTime firstTime = this.getTimeToCompare();
        TaskTime secondTime = interval.getTimeToCompare();
        
        //If both are float task, they are equal
        if (isFloatTask(firstDate) && isFloatTask(secondDate)) {
            return 0;
        }
        
        return compareDateTime(firstDate, secondDate, firstTime, secondTime);
    }
    
    /**
     * Returns true if TaskDate is null
     */
    private boolean isFloatTask(TaskDate date) {
        return date == null;
    }
    
    /**
     * Returns true is TaskTime is not null
     */
    private boolean isTimedTask(TaskTime time) {
        return time != null;
    }
    
    /**
     * Compares the 2 date-times and determines the ordering
     * firstDate and secondDate cannot both be Float Tasks
     */
    private int compareDateTime(TaskDate firstDate, TaskDate secondDate, TaskTime firstTime, TaskTime secondTime) {
        assert !isFloatTask(firstDate) || !isFloatTask(secondDate);
        
        int compare;
        if (!isFloatTask(firstDate)) {
            compare = compareSecondDate(firstDate, secondDate, firstTime, secondTime);
        } else {
            compare = 1;
        }
        return compare;
    }
    
    /**
     * Compare the secondDate. firstDate must not be null
     * Returns 1 if first date-time is later than second date-time
     * Returns 0 if both date-times are equal
     * Returns -1 otherwise
     */
    private int compareSecondDate(TaskDate firstDate, TaskDate secondDate, TaskTime firstTime, TaskTime secondTime) {
        assert firstDate != null;
        
        if (isFloatTask(secondDate)) {
            return -1;
        } else {
            if (firstDate.equals(secondDate)) { //if date are the same, determine order with time
                return compareTime(firstTime, secondTime);
            }
            return firstDate.compareTo(secondDate);
        }
    }
    
    /**
     * Compares the firstTime and secondTime
     * Returns 1 if first time is later than second time
     * Returns 0 if both times are equal
     * Returns -1 otherwise
     */
    private int compareTime(TaskTime firstTime, TaskTime secondTime) {
        if (isTimedTask(firstTime)) {
            return firstTime.compareTo(secondTime);
        } else if (isTimedTask(secondTime)) {
            return -secondTime.compareTo(firstTime); //flip comparison
        } else {
            return 0;
        }
    }
    
    /**
     * Returns the date to be used for comparison.
     */
    private TaskDate getDateToCompare() {
        if (this.startDate != null) {
            return this.startDate;
        } else if (this.endDate != null) {
            return this.endDate;
        } else {
            return null;
        }
    }
    
    /**
     * Returns the time to be used for comparison.
     */
    private TaskTime getTimeToCompare() {
        if (this.startTime != null) {
            return this.startTime;
        } else if (this.endTime != null) {
            return this.endTime;
        } else {
            return null;
        }
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Interval // instanceof handles nulls
                && this.startDate.equals(((Interval) other).getStartDate())
                && this.startTime.equals(((Interval) other).getStartTime())
                && this.endDate.equals(((Interval) other).getEndDate())
                && this.endTime.equals(((Interval) other).getEndTime())); // state check
    }

}
