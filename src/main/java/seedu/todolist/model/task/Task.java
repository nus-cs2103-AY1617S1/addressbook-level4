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
    private LocationParameter locationParameter;
    private RemarksParameter remarksParameter;

    /**
     * Only Name field must be present and not null. Other fields can be null.
     */
    public Task(Name name, Interval interval, LocationParameter location, RemarksParameter remarks) {
        assert !CollectionUtil.isAnyNull(name, interval, location, remarks);
        this.name = name;
        this.interval = interval;
        this.locationParameter = location;
        this.remarksParameter = remarks;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getInterval(), source.getLocationParameter(), source.getRemarksParameter());
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
    public LocationParameter getLocationParameter() {
        return locationParameter;
    }
    
    @Override
    public RemarksParameter getRemarksParameter() {
    	return remarksParameter;
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
        return Objects.hash(name, locationParameter, remarksParameter);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
