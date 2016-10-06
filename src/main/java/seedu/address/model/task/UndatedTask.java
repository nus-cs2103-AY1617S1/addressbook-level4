package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * An abstract UndatedTask in the task book.
 */
public abstract class UndatedTask implements ReadOnlyUndatedTask {
    
    private Name name;
    private Description description;
    private Date date;
    private Time time;
    
    private UniqueTagList tags;
    
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
    
    @Override
    public Boolean isEqualTo(ReadOnlyUndatedTask other) {
        return this.equals(other);
    }
}
