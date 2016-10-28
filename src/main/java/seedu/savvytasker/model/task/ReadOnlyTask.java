package seedu.savvytasker.model.task;

import java.util.Date;

//@@author A0139915W
/**
 * A read-only immutable interface for a Task in the TaskList.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {
    
    int getId();
    boolean isMarked();
    boolean isArchived();
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
     * Returns true if both tasks have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return getId() == other.getId();
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Id: ")
                .append(getId())
                .append(" Task Name: ")
                .append(getTaskName())
                .append(" Archived: ")
                .append(isArchived());
        if (getStartDateTime() != null) {
            builder.append(" Start: ")
                    .append(getStartDateTime());
        }
        if (getEndDateTime() != null) {
            builder.append(" End: ")
                    .append(getEndDateTime());
        }
        if (getLocation() != null && !getLocation().isEmpty()) {
            builder.append(" Location: ")
                    .append(getLocation());
        }
        builder.append(" Priority: ")
                .append(getPriority())
                .append(" Recurring Type: ")
                .append(getRecurringType())
                .append(" Nr. Recurrence: ")
                .append(getNumberOfRecurrence());
        if (getCategory() != null && !getCategory().isEmpty()) {
            builder.append(" Category: ")
                    .append(getCategory());
        }
        if (getDescription() != null && !getDescription().isEmpty()) {
            builder.append(" Description: ")
            .       append(getDescription());
        }
        return builder.toString();
    }

}
//@@author
