package seedu.task.model.task;

import seedu.task.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the taskBook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    DateTime getStartDate();
    DateTime getEndDate();
    Venue getVenue();
    Priority getPriority();
    Status getStatus();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this; // short circuit if same object
                //|| (other != null); // this is first to avoid NPE below
//                && other.getStartDate().equals(this.getStartDate())
//                && other.getEndDate().equals(this.getEndDate()));
        //Need to check for clash of datetime
    }

    /**
     * Formats the Task showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" startDate: ")
                .append(getStartDate())
                .append(" endDate: ")
                .append(getEndDate())
                .append(" Venue: ")
                .append(getVenue())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Status: ")
                .append(getStatus())
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
