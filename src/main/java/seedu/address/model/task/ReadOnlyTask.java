package seedu.address.model.task;

import java.time.LocalDateTime;
import java.util.Date;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the JYM program.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Description getDescription();
    LocalDateTime getDate();
    Location getLocation();
    Priority getPriority();
    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
//    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                && other.getLocation().equals(this.getLocation()));
    }

    /**
     * Formats the task/event as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getLocation())
                .append(" Description: ")
                .append(getDescription())
                .append(" Deadline: ")
                .append(getDate())
                .append(" Location: ")
                .append(getLocation());
//                .append(" Tags: ");
//        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this Person's tags
     */
//    default String tagsString() {
//        final StringBuffer buffer = new StringBuffer();
//        final String separator = ", ";
//        getTags().forEach(tag -> buffer.append(tag).append(separator));
//        if (buffer.length() == 0) {
//            return "";
//        } else {
//            return buffer.substring(0, buffer.length() - separator.length());
//        }
//    }

}
