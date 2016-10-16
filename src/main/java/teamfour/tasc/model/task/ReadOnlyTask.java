package teamfour.tasc.model.task;

import teamfour.tasc.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Complete getComplete();
    
    Deadline getDeadline();
    Period getPeriod();
    Recurrence getRecurrence();

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
                && (other.getName().equals(this.getName()) // state checks here onwards
                   && other.getComplete().equals(this.getComplete())
                   && other.getDeadline().equals(this.getDeadline())
                   && other.getPeriod().equals(this.getPeriod())
                   && other.getRecurrence().equals(this.getRecurrence())));
    }
    
    /**
     * Formats the task as type from its attributes.
     */
    default String getAsType() {
        String typeResult = "";
        
        if (getComplete().isCompleted()) {
            typeResult = typeResult.concat("Completed ");
        } else {
            typeResult = typeResult.concat("Uncompleted ");
        }
        
        if (getDeadline().hasDeadline() && getPeriod().hasPeriod()) {
            typeResult = typeResult.concat("Task with Allocated Timeslot ");
        } else if (getDeadline().hasDeadline()) {
            typeResult = typeResult.concat("Normal Task ");
        } else if (getPeriod().hasPeriod()) {
            typeResult = typeResult.concat("Event ");
        } else {
            typeResult = typeResult.concat("Floating Task ");
        }
        
        return typeResult;
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append("\nCompletion Status: ")
            .append(getComplete());
        
        if (getDeadline().hasDeadline()) {
            builder.append("\nDeadline: ")
                .append(getDeadline());
        }
        
        if (getPeriod().hasPeriod()) {
            builder.append("\nPeriod: ")
                .append(getPeriod());
        }

        if (getRecurrence().hasRecurrence()) {
            builder.append("\nRecurrence: ")
                .append(getRecurrence());
        }
        
        builder.append("\nTags: ");
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
