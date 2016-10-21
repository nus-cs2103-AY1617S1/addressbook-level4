package seedu.taskitty.model.task;

import seedu.taskitty.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the taskmanager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    boolean getIsDone();
    TaskDate getStartDate();
    TaskDate getEndDate();
    TaskTime getStartTime();
    TaskTime getEndTime();
    int getNumArgs();
    boolean isTodo();
    boolean isDeadline();
    boolean isEvent();

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
                && TaskDate.isEquals(other.getStartDate(), this.getStartDate())
                && TaskTime.isEquals(other.getStartTime(), this.getStartTime())
                && TaskDate.isEquals(other.getEndDate(), this.getEndDate())
                && TaskTime.isEquals(other.getEndTime(), this.getEndTime())
                && other.getIsDone() == this.getIsDone());
    }

    /**
     * Formats the task as text, showing all contact details.
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
