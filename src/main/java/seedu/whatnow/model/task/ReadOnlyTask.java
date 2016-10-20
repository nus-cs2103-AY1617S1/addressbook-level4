package seedu.whatnow.model.task;

import seedu.whatnow.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the whatnow.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();
    
    TaskDate getTaskDate();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        if (other.getTaskDate() == null && this.getTaskDate() == null) {
            return other == this // short circuit if same object
                    || (other != null // this is first to avoid NPE below
                    && other.getName().equals(this.getName())
                    && other.getTags().equals(this.getTags())
                    );
        }
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName())
                && other.getTaskDate().equals(this.getTaskDate())
                && other.getTags().equals(this.getTags())
                );
    }
    
    /**
     * Return the status of the task.
     * @return
     */
    String getStatus();
    
    /**
     * Return the task type of the task.
     * @return
     */
    String getTaskType();
    
    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
        		.append(" " + getTaskDate())
                .append(" " + getStatus())
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
