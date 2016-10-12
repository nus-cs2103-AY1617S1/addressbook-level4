package seedu.todolist.model.task;

import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the to do list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Interval interval;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Interval interval, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, interval, tags);
        this.name = name;
        this.interval = interval;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getInterval(), source.getTags());
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
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
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

}
