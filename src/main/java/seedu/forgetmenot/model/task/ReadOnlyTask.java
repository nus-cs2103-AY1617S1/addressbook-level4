package seedu.forgetmenot.model.task;


/**
 * A read-only immutable interface for a Task in the taskmanager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

	Name getName();
    Time getStartTime();
    Time getEndTime();
    Recurrence getRecurrence();
    Done getDone();
    boolean checkOverdue();
    
    boolean isStartTask();
    boolean isDeadlineTask();
    boolean isEventTask();
    boolean isFloatingTask();
    
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     * @@author A0147619W
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDone().equals(this.getDone())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime()))
        		&& other.getRecurrence().equals(this.getRecurrence());
    }

    /**
     * Formats the task as text, showing all contact details.
     * @@author A0139671X
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        
        builder.append(getName());
        builder.append(System.lineSeparator());
        
        if (!getStartTime().isMissing())
            builder.append("Start: " + getStartTime().easyReadDateFormatForUI());
        
        if (!getEndTime().isMissing())
            builder.append(" End: " + getEndTime().easyReadDateFormatForUI());
        
        if (getRecurrence().getValue())
            builder.append(" Recurrence: " + getRecurrence());
        
        return builder.toString();
    }

}
