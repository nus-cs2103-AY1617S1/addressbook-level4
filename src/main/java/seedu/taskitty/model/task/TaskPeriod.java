//@@author A0139930B
package seedu.taskitty.model.task;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.commons.util.CollectionUtil;

/**
 * Represents a Task's startDate, startTime, endDate and endTime in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateFormat(String)}
 */
public class TaskPeriod {

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
        
        if (this.startDate != null && this.startTime != null) {
            numArgs = Task.EVENT_COMPONENT_COUNT;
        } else if (this.endDate != null && this.endTime != null) {
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
        if (startDate.isBefore(endDate)) {
            isValid = true;
        } else if (startDate.equals(endDate) && startTime.isBefore(endTime)) {
                isValid = true;
        }
        return isValid;
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

}
