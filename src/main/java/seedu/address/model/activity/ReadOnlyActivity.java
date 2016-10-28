package seedu.address.model.activity;

import seedu.address.model.activity.task.DueDate;
import seedu.address.model.activity.task.Priority;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the Lifekeeper.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyActivity {

    Name getName();
    Reminder getReminder();

    String toStringCompletionStatus();
    
    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if Activity is completed or over
     */
    boolean getCompletionStatus();
    
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyActivity other) {
        if (this.getClass() != other.getClass()) {
            return false;
        } else if (!(this.getClass().getSimpleName().equalsIgnoreCase("activity") && other.getClass().getSimpleName().equalsIgnoreCase("activity"))) {
            return this.equals(other);
        } else {
            return other == this // short circuit if same object
                    || (other != null // this is first to avoid NPE below
                    && other.getName().equals(this.getName()) // state checks here onwards
                    && other.getReminder().equals(this.getReminder()));
        }
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Reminder: ")
                .append(getReminder())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this Person's tags
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
    
    boolean hasPassedDueDate();
    
 
    

}
