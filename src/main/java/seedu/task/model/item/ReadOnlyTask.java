package seedu.task.model.item;

import java.util.Optional;

/**
 * A read-only immutable interface for a task in the task book.
 * Implementations should guarantee: 
 *      Details are present and not null, with the exception of Deadline field. 
 *      Field values are validated.
 */
public interface ReadOnlyTask {

    Name getTask();
    Optional<Description> getDescription();
    Optional<Deadline> getDeadline();
    Boolean getTaskStatus();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTask().equals(this.getTask()) // state checks here onwards
                && other.getDeadline().equals(this.getDeadline())
                && other.getTaskStatus().equals(this.getTaskStatus())
                && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the task as text, showing all task details and status.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTask())
                .append(getDescriptionToString())
                .append(getDeadlineToString())
                .append(getTaskStatusToString());
        
        return builder.toString();
    }
    
    /**
     * Formats the description as text.
     * If null, empty string is returned
     */
    default String getDescriptionToString() {
        return getDescription().isPresent()? " Desc: " + getDescription().get().toString() : "";
    }
    
    /**
     * Formats the deadline as text.
     * If null, empty string is returned
     */
    default String getDeadlineToString() {
        return getDeadline().isPresent()? " Deadline: " + getDeadline().get().toString() : "";
    }
    
    /**
     * Formats the deadline as string.
     * If null, empty string is returned
     */
    default String getDeadlineValue() {
        return getDeadline().isPresent()? getDeadline().get().toString() : "";
    }
    
    /**
     * Formats the description as string.
     * If null, empty string is returned
     */
    default String getDescriptionValue() {
        return getDescription().isPresent()? getDescription().get().toString() : "";
    }
    
    /**
     * Formats the task status as text
     */
    default String getTaskStatusToString() {
        return getTaskStatus() ? " Status: Completed" : " Status: Not completed";
    }
    
    /**
     * Appends the name of a task with [DONE] if task is completed
     */
    default String getNameWithStatus() {
        return getTaskStatus() ? getTask().toString() + " [DONE]" : getTask().toString();
    }


}
