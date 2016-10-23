package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Date getDate();
    Recurring getRecurring();
    boolean isEvent();
    boolean isDone();
    boolean isRecurring();
    void markAsDone();
    
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
                && other.getDate().equals(this.getDate())
                && other.isDone() == this.isDone()&&other.isRecurring()==this.isRecurring());
    }

    /**
     * Temporary
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (!getDate().isEmptyDate()) {
            if (isEvent()) {
                builder.append(" Event Date: ");
            } else {
                builder.append(" Deadline: ");
            }
            builder.append(getDate());
        }
        if (getTags().getNumber() > 0) {
            builder.append(" Tags: ");
            getTags().forEach(builder::append);
        }
        if(isDone()){
        	builder.append(" done ");
        }
        if(isRecurring()){
            builder.append(" recurring "+getRecurring().recurringFrequency);
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
