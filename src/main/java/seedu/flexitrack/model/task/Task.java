package seedu.flexitrack.model.task;

import java.util.Objects;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.commons.util.CollectionUtil;

/**
 * Represents a Person in the address book. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask{

    private Name name;
    private DateTimeInfo dueDate;
    private DateTimeInfo startTime;
    private DateTimeInfo endTime;
    private boolean isEvent;
    private boolean isTask;
    private boolean isDone = false;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, DateTimeInfo dueDate, DateTimeInfo startTime, DateTimeInfo endTime) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.dueDate = dueDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isTask = dueDate.isDateNull() ? false : true;
        this.isEvent = startTime.isDateNull() ? false : true;
        this.endTime.isEndTimeInferred();
        this.isDone = name.getIsDone();
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDueDate(), source.getStartTime(), source.getEndTime());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public boolean getIsTask() {
        return isTask;
    }

    @Override
    public boolean getIsEvent() {
        return isEvent;
    }

    @Override
    public boolean getIsDone() {
        return name.getIsDone();
    }

    @Override
    public DateTimeInfo getDueDate() {
        return dueDate;
    }

    @Override
    public DateTimeInfo getStartTime() {
        return startTime;
    }

    @Override
    public DateTimeInfo getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, dueDate, startTime, endTime, isTask, isEvent);
    }

    @Override
    public String toString() {
        return getAsText();
    }

  //@@author A0138455Y
    private void setIsDone(boolean isDone) {     
        if (isDone && !this.isDone) {
            name.setAsMark();
        } else if (!isDone && this.isDone) {
            name.setAsUnmark();
        }
    }

    public void markTask(boolean isDone) throws IllegalValueException {
        this.isDone = this.name.getIsDone(); 
        if(this.isDone && isDone) {
            throw new IllegalValueException("Task already marked!");
        } else if(!this.isDone && !isDone) {
            throw new IllegalValueException("Task already unmarked!");
        } else {
            setIsDone(isDone);
        }
    }
  //@@author

    public void setName(String name) {
        this.name.setName(name);
    }

    public void setDueDate(String dueDate) throws IllegalValueException {
        this.dueDate = new DateTimeInfo(dueDate);
    }

    public void setStartTime(String startTime) throws IllegalValueException {
        this.startTime = new DateTimeInfo(startTime);
    }

    public void setEndTime(String endTime) throws IllegalValueException {
        this.endTime = new DateTimeInfo(endTime);
    }

    public void setIsTask(Boolean bool) {
        this.isTask = bool;
    }

    public void setIsEvent(Boolean bool) {
        this.isEvent = bool;
    }

    
}
