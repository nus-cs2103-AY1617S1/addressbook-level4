package seedu.todolist.model.task;

/**
 * A read-only immutable interface for a Task in the to-do list.
 * Implementations should guarantee: name is present and not null, field values are validated.
 */
public interface ReadOnlyTask extends Comparable<ReadOnlyTask> {

    Name getName();
    Interval getInterval();
    Location getLocation();  
    Remarks getRemarks();
    Status getStatus();
    Notification getNotification();

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
        builder.append(getNameAsText() + " ")
                .append(getIntervalAsText() + " ")
                .append(getLocationAsText() + " ")
                .append(getRemarksAsText() + " ");
        return builder.toString();
    }
    
    /**
     * Formats the name as text.
     */
    default String getNameAsText() {
        return getName().toString();
    }
    
    /**
     * Formats the interval as text.
     */
    default String getIntervalAsText() {
        Interval interval = getInterval();
        if (!interval.isFloat()) {
            return "from" + getInterval().toString();
        }
        return "";
    }
    
    /**
     * Formats the location as text.
     */
    default String getLocationAsText() {
        Location location = getLocation();
        if (location.location != null) {
            return "at" + getLocation().toString();
        }
        return "";
    }
    
    /**
     * Formats the remarks as text.
     */
    default String getRemarksAsText() {
        Remarks remarks = getRemarks();
        if (remarks.remarks != null) {
            return "remarks" + getRemarks().toString();
        }
        return "";
    } 
}
