package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * An abstract Task in the task book.
 * Guarantees: details are present and not null, field values are validated.
 */
public abstract class Task implements ReadOnlyTask {
    
    public abstract Name getName();

    @Override
    public abstract Description getDescription();

    @Override
    public abstract UniqueTagList getTags();

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public abstract void setTags(UniqueTagList replacement);

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
    
    @Override
    public abstract String getAsText();
    
}
