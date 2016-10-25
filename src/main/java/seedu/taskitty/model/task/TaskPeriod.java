package seedu.taskitty.model.task;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.commons.util.CollectionUtil;

//@@author A0139930B
/**
 * Represents a Task's startDate, startTime, endDate and endTime in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateFormat(String)}
 */
public class TaskPeriod implements Comparable<TaskPeriod>{

    public static final String MESSAGE_PERIOD_INVALID =
            "Task start datetime cannot be after end datetime!";

    private TaskDate startDate;
    private TaskTime startTime;
    private TaskDate endDate;
    private TaskTime endTime;
    private int numArgs;

    /**
     * Constructor for a "todo" TaskPeriod.
     * "todo" is a TaskPeriod no date and time.
     */
    public TaskPeriod() {
        numArgs = Task.TASK_COMPONENT_COUNT;
    }
    
    /**
     * Constructor for a "deadline" TaskPeriod.
     * "deadline" is a TaskPeriod with endDate and endTime.
     */
    public TaskPeriod(TaskDate endDate, TaskTime endTime) {
        assert !CollectionUtil.isAnyNull(endDate, endTime);
        
        this.endDate = endDate;
        this.endTime = endTime;
        numArgs = Task.DEADLINE_COMPONENT_COUNT;
    }
    
    /**
     * Constructor for a "event" TaskPeriod.
     * "event" is a TaskPeriod with all fields.
     * This constructor allows nulls and can be used when unsure which values are null
     *
     * @throws IllegalValueException if given start date/time is after end date/time.
     */
    public TaskPeriod(TaskDate startDate, TaskTime startTime,
                TaskDate endDate, TaskTime endTime) throws IllegalValueException {
        if (!isValidPeriod(startDate, startTime, endDate, endTime)) {
            throw new IllegalValueException(MESSAGE_PERIOD_INVALID);
        }
        
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        
        if (this.startDate != null) {
            assert this.startTime != null;
            numArgs = Task.EVENT_COMPONENT_COUNT;
        } else if (this.endDate != null) {
            assert this.endTime != null;
            numArgs = Task.DEADLINE_COMPONENT_COUNT;
        } else {
            numArgs = Task.TASK_COMPONENT_COUNT;
        }
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidPeriod(TaskDate startDate, TaskTime startTime,
            TaskDate endDate, TaskTime endTime) {
        
        boolean isValid = false;
        if (startDate == null || endDate == null) {
            isValid = true;
        }else if (startDate.isBefore(endDate)) {
            isValid = true;
        } else if (startDate.equals(endDate) && startTime.isBefore(endTime)) {
                isValid = true;
        }
        return isValid;
    }
    
    public int getNumArgs() {
        return numArgs;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (startDate != null) {
            builder.append(" from: ")
                    .append(startDate + " ")
                    .append(startTime)
                    .append(" to ")
                    .append(endDate + " ")
                    .append(endTime);
        } else if (endDate != null) {
            builder.append(" by ")
                .append(endDate + " ")
                .append(endTime);
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskPeriod // instanceof handles nulls
                && TaskDate.isEquals(((TaskPeriod)other).getStartDate(), this.getStartDate())
                && TaskTime.isEquals(((TaskPeriod)other).getStartTime(), this.getStartTime())
                && TaskDate.isEquals(((TaskPeriod)other).getEndDate(), this.getEndDate())
                && TaskTime.isEquals(((TaskPeriod)other).getEndTime(), this.getEndTime()));
    }
    
    public TaskDate getStartDate() {
        return startDate;
    }
    
    public TaskTime getStartTime() {
        return startTime;
    }
    
    public TaskDate getEndDate() {
        return endDate;
    }
    
    public TaskTime getEndTime() {
        return endTime;
    }
    
    public boolean isTodo() {
        return numArgs == Task.TASK_COMPONENT_COUNT;
    }
    
    public boolean isDeadline() {
        return numArgs == Task.DEADLINE_COMPONENT_COUNT;
    }
    
    public boolean isEvent() {
        return numArgs == Task.EVENT_COMPONENT_COUNT;
    }

    //@@author A0139052L
    @Override
    public int compareTo(TaskPeriod periodToCompare) {
        if (this.getNumArgs() == periodToCompare.getNumArgs()) {
            // sort events according to their start time and end time
            if (this.isEvent()) {
                if (!this.getStartDate().equals(periodToCompare.getStartDate())) {
                    return this.getStartDate().getDate().compareTo(periodToCompare.getStartDate().getDate());
                } else if (!this.getStartTime().equals(periodToCompare.getStartTime())) {
                    return this.getStartTime().getTime().compareTo(periodToCompare.getStartTime().getTime());                    
                }
            }
            // if event has same start date and start time, sort it by its end date or end time like deadline
            if (this.isEvent() || this.isDeadline()) {
                if (!this.getEndDate().equals(periodToCompare.getEndDate())) {
                    return this.getEndDate().getDate().compareTo(periodToCompare.getEndDate().getDate());
                } else if (!this.getEndTime().equals(periodToCompare.getEndTime())) {
                    return this.getEndTime().getTime().compareTo(periodToCompare.getEndTime().getTime());                    
                } 
            }
            return 0; //no difference found
        } else {
            return this.getNumArgs() - periodToCompare.getNumArgs();
        } 
    }

}
