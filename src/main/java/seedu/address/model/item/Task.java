package seedu.address.model.item;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;

public class Task implements ReadOnlyTask, Comparable<Task> {

    private static final int FIELD_IS_ABSENT = -2;
    public static final String VARIABLE_CONNECTOR = ", Priority: ";

    protected Name taskName;
    private Date startDate;
    private Date endDate;
    private RecurrenceRate recurrenceRate;
    private Priority priority;

    public Task(Name taskName) {
        this.taskName = taskName;
        this.priority = Priority.MEDIUM;
    }

    public Task(Name taskName, Priority priorityValue) {
        this.taskName = taskName;
        this.priority = priorityValue;
    }

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

    //TODO: Comments - Zhi Yuan
    public void updateRecurringTask() {
        assert recurrenceRate != null && recurrenceRate.timePeriod != null && recurrenceRate.rate != null &&
                (startDate != null || endDate != null);

        if (startDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            DateTime.updateDateByRecurrenceRate(calendar, recurrenceRate);
            startDate = calendar.getTime();
        } else if (endDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            DateTime.updateDateByRecurrenceRate(calendar, recurrenceRate);
            endDate = calendar.getTime();
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        if (getName() != null) {
            builder.append(getName().name);
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

    @Override
    public boolean equals(Object other) {
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

        int startDateComparison = compareByStartDate(other);
        int endDateComparison = compareByEndDate(other);
        int priorityValueComparison = compareByPriorityValue(other);
        int taskNameComparison = compareByTaskName(other);

        if (startDateComparison != FIELD_IS_ABSENT) {
            return startDateComparison;
        } else if (endDateComparison != FIELD_IS_ABSENT) {
            return endDateComparison;
        } else if (priorityValueComparison != 0) {
            return priorityValueComparison;
        } else {
            return taskNameComparison;
        }
    }

    private int compareByStartDate(Task other) {
        if (this.startDate != null && other.startDate != null) {
            return this.startDate.compareTo(other.startDate);
        } else {
            return FIELD_IS_ABSENT;
        }
    }

    private int compareByEndDate(Task other) {
        if (this.endDate != null && other.endDate != null) {
            return this.endDate.compareTo(other.endDate);
        } else {
            return FIELD_IS_ABSENT;
        }
    }

    private int compareByPriorityValue(Task other) {
        return this.priority.compareTo(other.priority);
    }

    private int compareByTaskName(Task other) {
        return this.taskName.name.compareTo(other.taskName.name);
    }

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
