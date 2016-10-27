package seedu.task.model.item;

import java.util.Optional;

import seedu.task.model.item.Description;
import seedu.task.model.item.Name;

/**
 * A read-only immutable interface for an event in the task book.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    Name getEvent();
    Optional <Description> getDescription();
    EventDuration getDuration();
    boolean isEventCompleted();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getEvent().equals(this.getEvent()) // state checks here onwards
                && other.getDuration().equals(this.getDuration())
                && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the event as text, showing all event details and status.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEvent())
               .append(getDescriptionToString())
               .append(" Duration: ")
               .append(getDuration());
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
     * Formats the description as string.
     * If null, empty string is returned
     */
    default String getDescriptionValue() {
        return getDescription().isPresent()? getDescription().get().toString() : "";
    }	
    
    /**
     * Appends the name of a event with [PAST] if task is completed
     */
    default String getNameWithStatus() {
        return isEventCompleted() ? getEvent().toString() + " [PAST]" : getEvent().toString();
    }

}
