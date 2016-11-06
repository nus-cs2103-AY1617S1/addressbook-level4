package seedu.address.model.item;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.TaskNotRecurringException;
import seedu.address.commons.util.CollectionUtil;

/**
 * Represents a Task in the task manager. Guarantees: field values are
 * validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private static final int THIS_CHRONOLOGICALLY_AFTER_OTHER = 1;
    private static final int THIS_CHRONOLOGICALLY_BEFORE_OTHER = -1;
    private static final int THIS_CHRONOLOGICALLY_EQUAL_TO_OTHER = 0;

    private static final String MESSAGE_RECURRING_TASK_CONSTRAINTS = "Unable to update recurring task as task "
            + "is not recurring or task does not have both start and end dates";

    private Name taskName;
    private Date startDate;
    private Date endDate;
    private RecurrenceRate recurrenceRate;
    private Priority priority;

    // @@author A0139498J
    public Task(Name taskName) {
        this.taskName = taskName;
        this.priority = Priority.MEDIUM;
    }

    // @@author A0139498J
    public Task(Name taskName, Priority priorityValue) {
        this.taskName = taskName;
        this.priority = priorityValue;
    }

    // @@author
    /**
     * Copy constructor.
     * 
     * @throws IllegalValueException
     */
    public Task(ReadOnlyTask source) {
        Date tempStartDate = null;
        Date tempEndDate = null;
        if (source.getStartDate().isPresent()) {
            tempStartDate = source.getStartDate().get();
        }
        if (source.getEndDate().isPresent()) {
            tempEndDate = source.getEndDate().get();
        }

        this.taskName = source.getName();
        this.startDate = tempStartDate;
        this.endDate = tempEndDate;
        if (source.getRecurrenceRate().isPresent())
            this.recurrenceRate = source.getRecurrenceRate().get();
        else
            this.recurrenceRate = null;
        this.priority = source.getPriorityValue();
    }

    // @@author A0139498J
    /**
     * Validates given value.
     *
     * @throws IllegalValueException if given value is invalid.
     */
    public Task(Name taskName, Date startDate, Date endDate, RecurrenceRate recurrenceRate, Priority priorityValue) {
        assert !CollectionUtil.isAnyNull(taskName);
        assert taskName != null;
        assert priorityValue != null;
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurrenceRate = recurrenceRate;
        this.priority = priorityValue;
    }

    // @@author A0139655U
    /**
     * Updates the startDate and/or endDate of the completed recurring task.
     * 
     * @throws TaskNotRecurringException if task does not have recurrence rate
     *             or does not have both start and end dates.
     */
    public void updateRecurringTask() throws TaskNotRecurringException {
        if (recurrenceRate == null || (startDate == null && endDate == null)) {
            throw new TaskNotRecurringException(MESSAGE_RECURRING_TASK_CONSTRAINTS);
        }

        if (startDate != null && endDate == null) {
            startDate = DateTime.updateDateByRecurrenceRate(startDate, recurrenceRate);
        } else if (startDate == null && endDate != null) {
            endDate = DateTime.updateDateByRecurrenceRate(endDate, recurrenceRate);
        } else if (startDate != null && endDate != null) {
            int timeDifference = (int) (endDate.getTime() - startDate.getTime());
            startDate = DateTime.updateDateByRecurrenceRate(startDate, recurrenceRate);
            endDate = updateEndDate(timeDifference);
        }
    }

    /**
     * Updates endDate using the timeDifference between startDate and endDate.
     * 
     * @param timeDifference    the difference in milliseconds between end date and start date
     * @return updated value of endDate
     */
    private Date updateEndDate(int timeDifference) {
        assert timeDifference >= 0;
        
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(startDate);
        endCalendar.add(Calendar.MILLISECOND, timeDifference);
        Date date = endCalendar.getTime();

        return date;
    }

    // @@author A0139498J
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        if (getName() != null) {
            builder.append(getName().getTaskName());
        }
        if (getPriorityValue() != null) {
            builder.append(", Priority: ").append(getPriorityValue());
        }
        if (getStartDate().isPresent()) {
            builder.append(", StartDate: ").append(getStartDate().get());
        }
        if (getEndDate().isPresent()) {
            builder.append(", EndDate: ").append(getEndDate().get());
        }
        return builder.toString();
    }

    // @@author A0093960X
    @Override
    public boolean equals(Object other) {

        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instance of handles null
                && this.isSameStateAs((ReadOnlyTask) other));

    }

    @Override
    public int compareTo(Task other) {

        int compareByDateValue = compareByDate(other);
        if (compareByDateValue != 0) {
            return compareByDateValue;
        }

        if (haveDifferentPriority(other)) {
            return compareByPriorityValue(other);
        } else {
            return compareByTaskName(other);
        }

    }

    //@@author
    @Override
    public Name getName() {
        return taskName;
    }

    @Override
    public Priority getPriorityValue() {
        return priority;
    }

    @Override
    public Optional<Date> getStartDate() {
        if (startDate != null) {
            return Optional.of(startDate);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Date> getEndDate() {
        if (endDate != null) {
            return Optional.of(endDate);
        }
        return Optional.empty();
    }

    @Override
    public Optional<RecurrenceRate> getRecurrenceRate() {
        if (recurrenceRate != null) {
            return Optional.of(recurrenceRate);
        }
        return Optional.empty();
    }   
    
    //@@author A0093960X
    /**
     * Compares this Task with other Task by date chronologically.
     * The result is a negative integer if this Task object chronologically precedes the argument Task.
     * The result is a positive integer if this Task object chronologically follows the argument Task.
     * The result is zero if the Dates of the Tasks are chronologically equal.
     *  
     * Tasks with at least one type of date (start date only, end date only, both start and end date)
     * will always chronologically precede Tasks without any dates.
     * 
     * If both Tasks have at least one type of date, but are chronologically equal 
     * Example: this Task has an end date that is equal to the start date and end date of another Task
     * Example 2: this Task has a start date that is equal to the end date of another Task
     * 
     * Tasks will be ordered in this manner:
     * 1. Tasks with start dates only
     * 2. Tasks with start dates and end dates
     * 3. Tasks with end dates only
     * 
     * @param other the other Task to compare by Date with
     * @return the value 0 if the argument Task is equal to this Task, a value less than 0 if this Task 
     *         chronologically precedes the argument Task and a value more than 0 
     *         if this Task chronologically follows the argument Task
     */
    private int compareByDate(Task other) {
        
        if (hasNoDateForThisTaskOrOtherTask(other)) {
            return compareTasksWhereOneHasNoDates(other);
            
        } else if (hasOnlyOneDateThisTaskAndOtherTask(other)) {
            return compareTasksWhereBothHaveOneDate(other);
            
        } else if (hasOnlyOneDateForOneTaskAndBothDatesForAnotherTask(other)) {
            return compareTasksWhereOneTaskHasOneDateAndAnotherTaskHasBothDates(other);
            
        } else {
            return compareTasksWithBothStartAndEndDates(other);
        }
    } 
    
    private boolean isStartDatePresent(Task task) {
        return task.startDate != null;
    }
    
    private boolean isEndDatePresent(Task task) {
        return task.endDate != null;
    }
    
    private int getNumDatesForTask(Task task) {
        int count = 0;
        
        if (isStartDatePresent(task)) {
            count++;
        }
        
        if (isEndDatePresent(task)) {
            count++;
        } 
        
        assert (count >= 0) && (count <= 2);
        return count;
    }
    
    private boolean hasNoDates(Task other) {
        return getNumDatesForTask(other) == 0;
    }
    
    private boolean hasOneDate(Task other) {
        return getNumDatesForTask(other) == 1;
    }

    private boolean hasTwoDates(Task other) {
        return getNumDatesForTask(other) == 2;
    }
    
    private boolean hasNoDateForThisTaskOrOtherTask(Task other) {
        return hasNoDates(this) 
                || hasNoDates(other);
    }
    
    private boolean hasNoDateForThisTaskAndOtherTask(Task other) {
        return hasNoDates(this) && hasNoDates(other);
    }
    
    private int compareTasksWhereOneHasNoDates(Task other) {
        if (hasNoDateForThisTaskAndOtherTask(other)) {
            return THIS_CHRONOLOGICALLY_EQUAL_TO_OTHER;
            
        } else if (hasNoDates(this)) {
            return THIS_CHRONOLOGICALLY_AFTER_OTHER;
            
        } else {
            return THIS_CHRONOLOGICALLY_BEFORE_OTHER;
        }
    }

    private boolean hasOnlyOneDateThisTaskAndOtherTask(Task other) {
        return hasOneDate(this) && hasOneDate(other);
    }
    
    private boolean hasStartDateOnlyInThisTaskAndStartDateOnlyInOtherTask(Task other) {
        return isStartDatePresent(this) 
                && !isEndDatePresent(this) 
                && isStartDatePresent(other)
                && !isEndDatePresent(other);
    }

    private boolean hasStartDateOnlyInThisTaskAndEndDateOnlyInOtherTask(Task other) {
        return isStartDatePresent(this) 
                && !isEndDatePresent(this) 
                && !isStartDatePresent(other)
                && isEndDatePresent(other);
    }

    private boolean hasEndDateOnlyInThisTaskAndStartDateOnlyInOtherTask(Task other) {
        return !isStartDatePresent(this) 
                && isEndDatePresent(this) 
                && isStartDatePresent(other)
                && !isEndDatePresent(other);
    }
    
    private int compareTasksWhereBothHaveOneDate(Task other) {
        if (hasStartDateOnlyInThisTaskAndStartDateOnlyInOtherTask(other)) {
            return compareThisTaskStartDateToOtherTaskStartDate(other);
            
        } else if (hasStartDateOnlyInThisTaskAndEndDateOnlyInOtherTask(other)) {
            return compareThisTaskStartDateToOtherTaskEndDate(other);
            
        } else if (hasEndDateOnlyInThisTaskAndStartDateOnlyInOtherTask(other)) {
            return compareThisTaskWithEndDateOnlyAndOtherTaskWithStartDateOnly(other);  
            
        } else {
            return compareThisTaskEndDateToOtherTaskEndDate(other);
        }
    }

    private int compareThisTaskStartDateToOtherTaskStartDate(Task other) {
        return startDate.compareTo(other.startDate);
    }
    
    private int compareThisTaskStartDateToOtherTaskEndDate(Task other) {
        return startDate.compareTo(other.endDate);
    }
    
    private int compareThisTaskEndDateToOtherTaskStartDate(Task other) {
        return endDate.compareTo(other.startDate);
    }

    private int compareThisTaskEndDateToOtherTaskEndDate(Task other) {
        return endDate.compareTo(other.endDate);
    }
    
    private int compareThisTaskWithEndDateOnlyAndOtherTaskWithStartDateOnly(Task other) {
        int compareByDateValue = compareThisTaskEndDateToOtherTaskStartDate(other);
        if (compareByDateValue == THIS_CHRONOLOGICALLY_EQUAL_TO_OTHER) {
            // if dates are equal, put the one with start date in front of one with end date
            return THIS_CHRONOLOGICALLY_AFTER_OTHER;
        }
        return compareByDateValue;
    }
    
    private boolean hasOnlyOneDateForOneTaskAndBothDatesForAnotherTask(Task other) {
        return (hasOneDate(this) && hasTwoDates(other)) || (hasTwoDates(this) && hasOneDate(other));
    }
    
    private boolean hasStartAndEndDateInThisTaskAndStartDateOnlyInOtherTask(Task other) {
        return isStartDatePresent(this) 
                && isEndDatePresent(this) 
                && isStartDatePresent(other)
                && !isEndDatePresent(other);
    }

    private boolean hasStartAndEndDateInThisTaskAndEndDateOnlyInOtherTask(Task other) {
        return isStartDatePresent(this) 
                && isEndDatePresent(this) 
                && !isStartDatePresent(other)
                && isEndDatePresent(other);
    }

    private boolean hasStartDateOnlyInThisTaskAndStartAndEndDateInOtherTask(Task other) {
        return isStartDatePresent(this) 
                && !isEndDatePresent(this) 
                && isStartDatePresent(other)
                && isEndDatePresent(other);
    }

    private int compareTasksWhereOneTaskHasOneDateAndAnotherTaskHasBothDates(Task other) {
        if (hasStartAndEndDateInThisTaskAndStartDateOnlyInOtherTask(other)) {
            return compareThisTaskWithStartAndEndDateAndOtherTaskWithStartDateOnly(other);
            
        } else if (hasStartAndEndDateInThisTaskAndEndDateOnlyInOtherTask(other)) {
            return compareThisTaskWithStartAndEndDateAndOtherTaskWithEndDateOnly(other);
            
        } else if (hasStartDateOnlyInThisTaskAndStartAndEndDateInOtherTask(other)) {
            return compareThisTaskWithStartDateOnlyAndOtherTaskWithStartAndEndDate(other);
            
        } else {
            return compareThisTaskWithEndDateOnlyAndOtherTaskWithStartAndEndDate(other);
        }
    }
    
    private int compareThisTaskWithStartAndEndDateAndOtherTaskWithStartDateOnly(Task other) {
        int compareByDateValue = compareThisTaskStartDateToOtherTaskStartDate(other);
        if (compareByDateValue != THIS_CHRONOLOGICALLY_EQUAL_TO_OTHER) {
            return compareByDateValue;
        }
        
        compareByDateValue = compareThisTaskEndDateToOtherTaskStartDate(other);
        if (compareByDateValue == THIS_CHRONOLOGICALLY_EQUAL_TO_OTHER) {
            // if dates are equal, put the one with start date only in front of one with start and end date
            return THIS_CHRONOLOGICALLY_AFTER_OTHER;
        }
        return compareByDateValue;
    }


    private int compareThisTaskWithStartAndEndDateAndOtherTaskWithEndDateOnly(Task other) {
        int compareByDateValue = compareThisTaskStartDateToOtherTaskEndDate(other);
        if (compareByDateValue != THIS_CHRONOLOGICALLY_EQUAL_TO_OTHER) {
            return compareByDateValue;
        }
        compareByDateValue = compareThisTaskEndDateToOtherTaskEndDate(other);
        if (compareByDateValue == THIS_CHRONOLOGICALLY_EQUAL_TO_OTHER) {
            // if dates are equal, put the one with start date and end date in front of the one with end date only
            return THIS_CHRONOLOGICALLY_BEFORE_OTHER;
        }
        return compareByDateValue;
    }
    
    private int compareThisTaskWithStartDateOnlyAndOtherTaskWithStartAndEndDate(Task other) {
        int compareByDateValue = compareThisTaskStartDateToOtherTaskStartDate(other);
        if (compareByDateValue != THIS_CHRONOLOGICALLY_EQUAL_TO_OTHER) {
            return compareByDateValue;
        }
        compareByDateValue = compareThisTaskStartDateToOtherTaskEndDate(other);
        if (compareByDateValue == THIS_CHRONOLOGICALLY_EQUAL_TO_OTHER) {
            // if dates are equal, put the one with start date only in front of the one with start and end date
            return THIS_CHRONOLOGICALLY_BEFORE_OTHER;
        }
        return compareByDateValue;
    }

    private int compareThisTaskWithEndDateOnlyAndOtherTaskWithStartAndEndDate(Task other) {
        int compareByDateValue = compareThisTaskEndDateToOtherTaskStartDate(other);
        if (compareByDateValue != THIS_CHRONOLOGICALLY_EQUAL_TO_OTHER) {
            return compareByDateValue;
        }

        compareByDateValue = compareThisTaskEndDateToOtherTaskEndDate(other);
        if (compareByDateValue == THIS_CHRONOLOGICALLY_EQUAL_TO_OTHER) {
            // if dates are equal, put the one with start date and end date in front of the one with end date only
            return THIS_CHRONOLOGICALLY_AFTER_OTHER;
        }
        return compareByDateValue;
    }


    private int compareTasksWithBothStartAndEndDates(Task other) {
        int compareByDateValue = compareThisTaskStartDateToOtherTaskStartDate(other);
        if (compareByDateValue != THIS_CHRONOLOGICALLY_EQUAL_TO_OTHER) {
            return compareByDateValue;
        }
        return compareThisTaskEndDateToOtherTaskEndDate(other);
    }

    // @@author
    private boolean haveDifferentPriority(Task other) {
        return !this.priority.equals(other.priority);
    }

    // @@author A0139498J
    private int compareByPriorityValue(Task other) {
        return this.priority.compareTo(other.priority);
    }

    // @@author A0139498J
    private int compareByTaskName(Task other) {
        return this.taskName.getTaskName().compareTo(other.taskName.getTaskName());
    }

    // @@author A0139498J
    public void setName(Name name) {
        this.taskName = name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPriority(Priority priorityValue) {
        this.priority = priorityValue;
    }

    public void setRecurrence(RecurrenceRate recurrenceRate) {
        this.recurrenceRate = recurrenceRate;
    }

}
