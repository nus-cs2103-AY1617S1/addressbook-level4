package seedu.address.model.item;

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
        try {
            this.recurrenceRate = new RecurrenceRate(new Integer(0));
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Task(Name taskName, Priority priorityValue) {
        this.taskName = taskName;
        this.priority = priorityValue;
        try {
            this.recurrenceRate = new RecurrenceRate(new Integer(0));
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public Task(ReadOnlyTask source) {
        Date tempStartDate = null, tempEndDate = null;
        if (source.getStartDate().isPresent()){
            tempStartDate = source.getStartDate().get();
        }
        if (source.getEndDate().isPresent()){
            tempEndDate = source.getEndDate().get();
        }
        
        this.taskName = source.getName();
        this.startDate = tempStartDate;
        this.endDate = tempEndDate;
        this.recurrenceRate = source.getRecurrenceRate();
        this.priority = source.getPriorityValue();
    }
    
    /**
     * Validates given value.
     *
     * @throws IllegalValueException if given value is invalid.
     */
    public Task(Name taskName, Date startDate, Date endDate, RecurrenceRate recurrenceRate, Priority priorityValue) {
        assert !CollectionUtil.isAnyNull(taskName);
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurrenceRate = recurrenceRate;
        this.priority = priorityValue;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName().name)
                .append(", Priority: ")
                .append(getPriorityValue());
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
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.compareTo((Task)other) == 0);
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
        if (this.startDate!=null && other.startDate!=null) {
            return this.startDate.compareTo(other.startDate);
        } else {
            return FIELD_IS_ABSENT;
        }
    }
    
    private int compareByEndDate(Task other) {
        if (this.endDate!=null && other.endDate!=null) {
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
        if (startDate!=null) {
            return Optional.of(startDate);
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<Date> getEndDate() {
        if (endDate!=null) {
            return Optional.of(endDate);
        }
        return Optional.empty();    
    }

    @Override
    public RecurrenceRate getRecurrenceRate() {
        assert recurrenceRate != null;
        return recurrenceRate;
    }
    
}
