package seedu.todolist.model.task;

import seedu.todolist.commons.util.CollectionUtil;

import java.util.Objects;

/**
 * Represents a Task in the to do list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Interval interval;
    private Location location;
    private Remarks remarks;
    private Status status;

    /**
     * Only Name and Interval field must be present and not null. Other fields can be null.
     */
    public Task(Name name, Interval interval, Location location, Remarks remarks, Status status) {
        assert !CollectionUtil.isAnyNull(name, interval);
        this.name = name;
        this.interval = interval;
        this.location = location;
        this.remarks = remarks;
        this.status = status;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getInterval(), source.getLocation(), source.getRemarks(), source.getStatus());
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public Interval getInterval() {
        return interval;
    }

    @Override
    public Location getLocation() {
        return location;
    }
    
    @Override
    public Remarks getRemarks() {
    	return remarks;
    }
    
    @Override
    public Status getStatus() {
        return status;
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
        return Objects.hash(name, interval, location, remarks);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
