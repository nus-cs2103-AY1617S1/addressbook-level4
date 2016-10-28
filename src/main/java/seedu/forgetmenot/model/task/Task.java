package seedu.forgetmenot.model.task;

import java.util.Calendar;
import java.util.Objects;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.commons.util.CollectionUtil;

/**
 * Represents a Task in ForgetMeNot.
 * Guarantees: details are present and not null, field values are validated.
 * @@author A0147619W
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Done done;
    private Time start;
    private Time end;
    private Recurrence recurrence;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Done done, Time start, Time end, Recurrence recurrence) {
        assert !CollectionUtil.isAnyNull(name, done, start, end);
        this.name = name;
        this.done = done;
        this.start = start;
        this.end = end;
        this.recurrence = recurrence;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDone(), source.getStartTime(), source.getEndTime(), source.getRecurrence());
    }

    @Override
    public Name getName() {
        return name;
    }
    
    //@@author A0139198N
    @Override
    public Done getDone() {
        return done;
    }

    @Override
    public Time getStartTime() {
        return start;
    }

    @Override
    public Time getEndTime() {
        return end;
    }
    
    @Override
    public Recurrence getRecurrence() {
    	return recurrence;
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setStartTime(Time start) {
        this.start = start;
    }
    
    public void setEndTime(Time end) {
        this.end = end;
    }
    
    public void setDone(Done done) {
    	this.done = done;
    }
    
    public void setRecurrence(Recurrence recurrence) {
    	this.recurrence = recurrence;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, done, start, end);
    }
    
    //@@author A0139671X
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        
        builder.append(getName());
        builder.append(System.lineSeparator());
        
        if (!getStartTime().isMissing())
            builder.append("Start: " + getStartTime().easyReadDateFormatForUI());
        
        if (!getEndTime().isMissing())
            builder.append(" End: " + getEndTime().easyReadDateFormatForUI());
        
        if (getRecurrence().getValue())
            builder.append(" Recurrence: Every " + getRecurrence());
        
        return builder.toString();
    }
    
    /**
     * @return true if the tasks is past the current time
     * @throws IllegalValueException
     * @@author A0139671X
     */
    public boolean checkOverdue() {
        if (start.isMissing() && !end.isMissing())
            return end.time.compareTo(Calendar.getInstance()) < 0;
        
        if (!start.isMissing())
            return start.time.compareTo(Calendar.getInstance()) < 0;
        
        return false;
    }
    
    /**
     * Checks if a task is an event, i.e. a start time and an end time.
     * @return true if task has both start and end time.
     */
    public boolean isEventTask() {
        return !start.isMissing() && !end.isMissing();
    }
    /**
     * Checks if a task is a start time event, i.e. only has start time.
     * @return true if only start time is present for the task.
     */
    public boolean isStartTask() {
        return !start.isMissing() && end.isMissing();
    }
    
    /**
     * Checks if a task is a deadline, i.e. only has an end time.
     * @return true if only end time is present.
     */
    public boolean isDeadlineTask() {
        return start.isMissing() && !end.isMissing();
    }
    
    /**
     * Checks if a task is a floating task, i.e. no start time and no end time.
     * @return true if both start and end times are not present.
     */
    public boolean isFloatingTask() {
        return start.isMissing() && end.isMissing();
    }
    
}
