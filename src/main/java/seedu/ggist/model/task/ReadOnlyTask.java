package seedu.ggist.model.task;

import seedu.ggist.model.tag.UniqueTagList;

/**
 * A read-only immutable (except for setting task done) interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    TaskName getTaskName();
    TaskDate getStartDate();
    TaskTime getStartTime();
    TaskDate getEndDate();
    TaskTime getEndTime();
    boolean getDone();
    void setDone();
    
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
                && other.getTaskName().equals(this.getTaskName()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndDate().equals(this.getEndDate())
                && other.getEndTime().equals(this.getEndTime())
         );
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName())
                .append(" Task Name: ")
                .append(getTaskName())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" End Date: ")
                .append(getEndDate())
                .append(" End Time: ")
                .append(getEndTime())
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
