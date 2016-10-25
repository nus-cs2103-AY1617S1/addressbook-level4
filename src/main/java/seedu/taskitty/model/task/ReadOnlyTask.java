package seedu.taskitty.model.task;

import seedu.taskitty.model.tag.UniqueTagList;

//@@author A0139930B
/**
 * A read-only immutable interface for a Task in the taskmanager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    TaskPeriod getPeriod();
    //@@author A0130853L
    boolean getIsDone();
    boolean isTodo();
    boolean isDeadline();
    boolean isEvent();
    boolean isOverdue();
	boolean isOver();
	
	//@@author A0139930B
    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPeriod().equals(this.getPeriod())
                && other.getIsDone() == this.getIsDone());
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(getPeriod());
        builder.append("\nTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    //@@author
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
