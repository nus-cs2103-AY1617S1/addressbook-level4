package taskle.model.person;

import java.util.Objects;

import taskle.model.person.Name;
import taskle.model.person.ReadOnlyTask;
import taskle.commons.util.CollectionUtil;
import taskle.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public abstract class Task implements ReadOnlyTask, ModifiableTask {

    protected Name name;

    protected UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName());
    }
    
    /**
     * Copy constructor.
     */
    public Task(ModifiableTask source) {
        this(source.getName());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public void setName(Name name) {
        this.name = name;
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
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    public abstract DateTime getDateTime();
    
    public abstract String getDateTimeString();


}
