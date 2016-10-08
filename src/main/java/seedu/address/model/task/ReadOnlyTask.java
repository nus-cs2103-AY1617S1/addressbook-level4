package seedu.address.model.task;

import java.util.Date;

/**
 * A read-only immutable interface for a Task in the TaskList.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {
    
    String getTaskName();
    Date getStartDateTime();
    Date getEndDateTime();
    String getLocation();
    int getPriority();
    int getRecurringType();
    int getNumberOfRecurrence();
    int getCategory();
    String getDescription();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTaskName().equals(this.getTaskName())); // state checks here onwards
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName())
                .append(" Start: ")
                .append(getStartDateTime())
                .append(" End: ")
                .append(getEndDateTime())
                .append(" Location: ")
                .append(getLocation())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Recurring Type: ")
                .append(getRecurringType())
                .append(" Nr. Recurrence: ")
                .append(getNumberOfRecurrence())
                .append(" Category: ")
                .append(getCategory())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }

}
