package seedu.address.model.activity.task;

import seedu.address.model.activity.ReadOnlyActivity;
//@@author A0125680H
public interface ReadOnlyTask extends ReadOnlyActivity {

    DueDate getDueDate();
    Priority getPriority();
    boolean getCompletionStatus();
    
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDueDate().equals(this.getDueDate())
                && other.getPriority().equals(this.getPriority())
                && other.getReminder().equals(this.getReminder()));
    }
    
    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Duedate: ")
                .append(getDueDate())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Reminder: ")
                .append(getReminder())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    String toStringCompletionStatus();
}
