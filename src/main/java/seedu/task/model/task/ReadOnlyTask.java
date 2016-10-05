package seedu.task.model.task;

/**
 * A read-only immutable interface for a task in the task book.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Description getDescription();
//    Deadline getDeadline();
    Boolean getTaskStatus();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
//                && other.getDeadline().equals(this.getDeadline())
                && other.getTaskStatus().equals(this.getTaskStatus())
                && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the task as text, showing all task details and status.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Desc: ")
                .append(getDescription())
                .append(" Status: ");
//              .append(" Deadline: ")
//              .append(getDeadline())
        if (getTaskStatus()) {
            builder.append("Completed");
        } else {
            builder.append("Not completed");
        }
        
        return builder.toString();
    }

/*    *//**
     * Returns a string representation of this Person's tags
     *//*
    default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getTags().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }*/

}
