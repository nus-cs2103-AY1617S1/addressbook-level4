package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a task in Simply.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Date getDate();
    Start getStart();
    End getEnd();
    int getOverdue();
    int getTaskCategory();
    boolean getIsCompleted();
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
                && other.getDate().equals(this.getDate())
                && other.getStart().equals(this.getStart())
                && other.getEnd().equals(this.getEnd())
                && other.getOverdue() == this.getOverdue()
                && (other.getTaskCategory() == this.getTaskCategory()));
    }

    /**
     * Formats the task as text, showing all date, time, tag details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" Start: ")
                .append(getStart())
                .append(" End: ")
                .append(getEnd())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this task's tags
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
