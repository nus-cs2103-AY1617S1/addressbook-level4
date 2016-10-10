package seedu.todolist.model.task;

import seedu.todolist.commons.util.CollectionUtil;

import java.util.Objects;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    
    private LocationParameter locationParameter;

    /**
     * Only Name field must be present and not null. Other fields can be null.
     */
    public Task(Name name, LocationParameter location) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.locationParameter = location;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getLocationParameter());
    }

    @Override
    public Name getName() {
        return name;
    }


    @Override
    public LocationParameter getLocationParameter() {
        return locationParameter;
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
        return Objects.hash(name, locationParameter);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
