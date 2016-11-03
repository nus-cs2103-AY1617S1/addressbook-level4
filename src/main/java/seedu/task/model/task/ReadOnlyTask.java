package seedu.task.model.task;

import seedu.task.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the Task Manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    //@@author A0144939R
    DateTime getOpenTime();
    DateTime getCloseTime();
    boolean getImportance();
    boolean getComplete();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the tasks's internal tags.
     */
    UniqueTagList getTags();
    
    //@@author A0141052Y
    /**
     * Equality based on what is shown to the user. Useful for tests.
     */
    default boolean isSameVisualStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                  
                // state checks here onwards
                && other.getName().equals(this.getName()) 
                && other.getOpenTime().toDisplayString().equals(this.getOpenTime().toDisplayString())
                && other.getCloseTime().toDisplayString().equals(this.getCloseTime().toDisplayString())
                && other.getImportance() == this.getImportance());
    }
    //@@author A0144939R

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                
                // state checks here onwards
                && other.getName().equals(this.getName()) 
                && other.getOpenTime().equals(this.getOpenTime())
                && other.getCloseTime().equals(this.getCloseTime())
                && other.getImportance() == this.getImportance());
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

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
