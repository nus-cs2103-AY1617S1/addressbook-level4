package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the taskbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyUndatedTask {

    Name getName();
    Description getDescription();
    
    /**
     * Formats the task as text, showing all details.
     */
    String getAsText();
    
    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Checks if two ReadOnlyUndatedTask are equal
     */
    Boolean isEqualTo(ReadOnlyUndatedTask other);

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
