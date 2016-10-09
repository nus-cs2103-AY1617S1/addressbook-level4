package seedu.jimi.model.task;

import seedu.jimi.commons.util.CollectionUtil;
import seedu.jimi.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a task in the task book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FloatingTask implements ReadOnlyTask {

    private Name name;
    private UniqueTagList tags;
    private boolean isCompleted;

    /**
     * Every field must be present and not null.
     */
    public FloatingTask(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.isCompleted = false;
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public FloatingTask(ReadOnlyTask source) {
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

    /**
     * Replaces this floating task's tags with the tags in the argument tag list.
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

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

}