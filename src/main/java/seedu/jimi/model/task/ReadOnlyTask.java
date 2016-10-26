package seedu.jimi.model.task;

import seedu.jimi.model.tag.Priority;
import seedu.jimi.model.tag.UniqueTagList;

/**
 * @@author A0148040R
 * A read-only immutable interface for a FloatingTask in the task manager.
 * Implementations should guarantee: name is present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    boolean isCompleted();
    String getAsText();
    Priority getPriority();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();
    
    
    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement); 
    

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    boolean isSameStateAs(ReadOnlyTask other);

    /**
     * Returns a string representation of this Task's tags
     */
    default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getTags().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }
    
    

}