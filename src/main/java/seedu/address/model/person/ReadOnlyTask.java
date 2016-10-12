package seedu.address.model.person;

import java.util.Date;

import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.RecurrenceType;

/**
 * A read-only immutable interface for a Task in the TaskList.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {
    
    String getTaskName();
    Date getStartDateTime();
    Date getEndDateTime();
    String getLocation();
    PriorityLevel getPriority();
    RecurrenceType getRecurringType();
    int getNumberOfRecurrence();
    String getCategory();
    String getDescription();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return false; // no constraints on duplicate tasks
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
