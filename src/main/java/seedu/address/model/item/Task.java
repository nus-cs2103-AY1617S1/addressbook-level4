package seedu.address.model.item;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.TaskNotRecurringException;
import seedu.address.commons.util.CollectionUtil;

/**
 * Represents a Task in the task manager.
 * Guarantees: field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private static final String MESSAGE_RECURRING_TASK_CONSTRAINTS = "Unable to update recurring task as task "
            + "is not recurring or task does not have both start and end dates";
    private Name taskName;
    private Date startDate;
    private Date endDate;
    private RecurrenceRate recurrenceRate;
    private Priority priority;

    //@@author A0139498J
    public Task(Name taskName) {
        this.taskName = taskName;
        this.priority = Priority.MEDIUM;
    }

    //@@author A0139498J
    public Task(Name taskName, Priority priorityValue) {
        this.taskName = taskName;
        this.priority = priorityValue;
    }
    //@@author
    /**
     * Copy constructor.
     * 
     * @throws IllegalValueException
     */
    public Task(ReadOnlyTask source) {
        Date tempStartDate = null, tempEndDate = null;
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

    //@@author A0139498J
    /**
     * Validates given value.
     *
     * @throws IllegalValueException
     *             if given value is invalid.
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

    //@@author A0139655U
    /**
     * Updates the startDate and/or endDate of the completed recurring task.
     * 
     * @throws TaskNotRecurringException    if task does not have recurrence rate or does not have both start and end dates.
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

    //@@author A0139498J
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

    //@@author
    @Override
    public boolean equals(Object other) {
        
        //TODO: extract method similar to compareto method
        // same object       
        if (other == this) {
            return true;
        }
        
        if (other instanceof Task) {
            Task otherTask = (Task) other;
            
            // check if same name
            if (!this.getName().equals(otherTask.getName())) {
                return false;
            }
            
            // check if both have same priority
            if (!this.getPriorityValue().equals(otherTask.getPriorityValue())) {
                return false;
            }
            
            // check if both have start date and are same start date, or both do not have a start date
            boolean bothHaveStartDate = this.getStartDate().isPresent() && otherTask.getStartDate().isPresent();
            boolean bothMissingStartDate = !this.getStartDate().isPresent() && !otherTask.getStartDate().isPresent();
            
            if ((bothHaveStartDate && this.getStartDate().get().equals(otherTask.getStartDate().get()))
                    || bothMissingStartDate) {}
            else {
                return false;
            }
            
            // check if both have end date and are same end date, or both do not have a end date
            boolean bothHaveEndDate = this.getEndDate().isPresent() && otherTask.getEndDate().isPresent();
            boolean bothMissingEndtDate = !this.getEndDate().isPresent() && !otherTask.getEndDate().isPresent();
            
            if ((bothHaveEndDate && this.getEndDate().get().equals(otherTask.getEndDate().get()))
                    || bothMissingEndtDate) {}
            else {
                return false;
            }
            
            // check if both have have recurrence rate and are same recurrence rate, or both do not have a recurrence rate
            boolean bothHaveRecurrenceRate = this.getRecurrenceRate().isPresent() && otherTask.getRecurrenceRate().isPresent();
            boolean bothMissingRecurrenceRate = !this.getRecurrenceRate().isPresent() && !otherTask.getRecurrenceRate().isPresent();
            
            if ((bothHaveRecurrenceRate && this.getRecurrenceRate().get().equals(otherTask.getRecurrenceRate().get()))
                    || bothMissingRecurrenceRate) {}
            else {
                return false;
            }
            
            return true;

            
        }
        
        return false;
    }

    @Override
    public int compareTo(Task other) {
        int comparedVal = 0;
                
        comparedVal = compareByDate(other);
        if (comparedVal != 0) {
            return comparedVal;
        }
        
        if (haveDifferentPriority(other)) {
            return compareByPriorityValue(other);
        }
        /* don't think recurrence rate should be considered in sorting 
        else if (bothHaveRecurrenceRate(other)) {
            return compareByReccurenceRate(other);
        }
        */
        else {
           return compareByTaskName(other);
        }


    }

    private int compareByDate(Task other) {
        // 1. those with start date
        // 2. those with start date and end date
        // 3. those with end date
        // 4. those with nothing
        
        boolean hasStart = this.startDate != null, hasEnd = this.endDate != null;
        boolean otherHasStart = other.startDate != null, otherHasEnd = other.endDate != null;
        int comparedVal = 0;
        
        boolean hasNoDate = !hasStart && !hasEnd;
        boolean otherNoDate = !otherHasStart && !otherHasEnd;
        
        if (hasNoDate && otherNoDate) {
            return 0;
        } else if (hasNoDate) {
            return 1;
        } else if (otherNoDate) {
            return -1;
        }
        
        // Easy case 1
        if (!hasStart && hasEnd && !otherHasStart && otherHasEnd) {
            comparedVal = this.endDate.compareTo(other.endDate);
            return comparedVal;
        }
        
        // Easy case 2
        if (!hasStart && hasEnd && otherHasStart && !otherHasEnd) {
            comparedVal = this.endDate.compareTo(other.startDate);
            if (comparedVal == 0) {
                return 1;
            }
            return comparedVal;
        }
        
        // Easy case 3
        if (hasStart && !hasEnd && !otherHasStart && otherHasEnd) {
            comparedVal = this.startDate.compareTo(other.endDate);
            return comparedVal;
        }
        
        // Easy case 4
        if (hasStart && !hasEnd && otherHasStart && !otherHasEnd) {
            comparedVal = this.startDate.compareTo(other.startDate);
            return comparedVal;
        }
        
        // Medium case 1
        if (!hasStart && hasEnd && otherHasStart && otherHasEnd) {
            comparedVal = this.endDate.compareTo(other.startDate);
            if (comparedVal != 0) {
                return comparedVal;
            }
            
            comparedVal = this.endDate.compareTo(other.endDate);
            if (comparedVal == 0) {
                return 1;
            }
            return comparedVal;
        }
        
        // Medium case 2
        if (hasStart && !hasEnd && otherHasStart && otherHasEnd) {
            comparedVal = this.startDate.compareTo(other.startDate);      
            if (comparedVal != 0) {
                return comparedVal;
            }
            
            comparedVal = this.startDate.compareTo(other.endDate);
            return comparedVal;
        }
        
        // Medium case 3
        if (hasStart && hasEnd && !otherHasStart && otherHasEnd) {
            comparedVal = this.startDate.compareTo(other.endDate);
            if (comparedVal != 0) {
                return comparedVal;
            }
            
            comparedVal = this.endDate.compareTo(other.endDate);
            return comparedVal;
        }
        
        // Medium case 4
        if (hasStart && hasEnd && otherHasStart && !otherHasEnd) {
            comparedVal = this.startDate.compareTo(other.startDate);
            if (comparedVal != 0) {
                return comparedVal;
            }
            
            comparedVal = this.endDate.compareTo(other.startDate);
            if (comparedVal == 0) {
                return 1;
            }
            return comparedVal;
        }
        
        // Final case
        // compare start first
        comparedVal = this.startDate.compareTo(other.startDate);
        if (comparedVal != 0) {
            return comparedVal;
        }

        comparedVal = this.startDate.compareTo(other.endDate);
        if (comparedVal != 0) {
            return comparedVal;
        }

        comparedVal = this.endDate.compareTo(other.startDate);
        if (comparedVal != 0) {
            return comparedVal;
        }

        comparedVal = this.endDate.compareTo(other.endDate);
        return comparedVal;

    }

    /*
    private boolean bothHaveRecurrenceRate(Task other) {
        return this.recurrenceRate != null && other.recurrenceRate != null;
    }
     */
    private boolean haveDifferentPriority(Task other) {
        return !this.priority.equals(other.priority);
    }
    //@@author A0139498J
    private int compareByPriorityValue(Task other) {
        return this.priority.compareTo(other.priority);
    }
    //@@author A0139498J
    private int compareByTaskName(Task other) {
        return this.taskName.getTaskName().compareTo(other.taskName.getTaskName());
    }
    
    //@@author A0139498J
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

}
