package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Deadline getDeadline();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDeadline().equals(this.getDeadline()));
    }

    /**
     * Temporary
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (!getDeadline().time.equals("")) {
            builder.append(" Deadline: ")
                   .append(getDeadline());
        }
        if (getTags().getNumber() > 0) {
            builder.append(" Tags: ");
            getTags().forEach(builder::append);
        }
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
