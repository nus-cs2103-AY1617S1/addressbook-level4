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
    public Task(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTags());
    }
    
    /**
     * Copy constructor.
     */
    public Task(ModifiableTask source) {
        this(source.getName(), source.getTags());
    }

    
    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    @Override
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    @Override
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
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
