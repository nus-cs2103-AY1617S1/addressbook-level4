package seedu.agendum.model.task;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * A read-only immutable interface for a Task in the ToDoList.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    boolean isCompleted();
    boolean isUpcoming();
    boolean isOverdue();
    boolean hasTime();
    Optional<LocalDateTime> getStartDateTime();
    Optional<LocalDateTime> getEndDateTime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && (other.isCompleted() == this.isCompleted())
                && other.getStartDateTime().equals(this.getStartDateTime())
                && other.getEndDateTime().equals(this.getEndDateTime()));
    }

    /**
     * Formats the task as text, showing task name only.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append("\n");
        return builder.toString();
    }

    /**
     * Format the tasks as text, showing all details including name, 
     * completion status, start and end time if any
     */
    default String getDetailedText() {
        String completionStatus = isCompleted() ? "Completed" : "Incomplete";
        String startTime = getStartDateTime().isPresent() ? getStartDateTime().get().toString() : "None";
        String endTime = getEndDateTime().isPresent() ? getEndDateTime().get().toString() : "None";

        final StringBuilder builder = new StringBuilder();
        builder.append("Task name: ")
               .append(getName())
               .append(" Status: ")
               .append(completionStatus)
               .append(" Start Time: ")
               .append(startTime)
               .append(" End Time: ")
               .append(endTime);
         return builder.toString();      
    }
}
