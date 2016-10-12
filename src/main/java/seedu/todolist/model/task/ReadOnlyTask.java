package seedu.todolist.model.task;

/**
 * A read-only immutable interface for a Task in the to-do list.
 * Implementations should guarantee: name is present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Interval getInterval();
    LocationParameter getLocationParameter();  
    RemarksParameter getRemarksParameter();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName())); // state checks here onwards
    }

    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" from ").append(getInterval().startDate + " ").append(getInterval().startTime)
                .append(" to ").append(getInterval().endDate + " ").append(getInterval().endTime)
                .append(" at ").append(getLocationParameter())
                .append(" remarks ").append(getRemarksParameter());
        return builder.toString();
    }

}
