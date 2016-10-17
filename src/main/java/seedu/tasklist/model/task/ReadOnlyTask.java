package seedu.tasklist.model.task;

import seedu.tasklist.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    TaskDetails getTaskDetails();
    StartTime getStartTime();
    EndTime getEndTime();
    Priority getPriority();
    int getUniqueID();
    boolean isComplete();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTaskDetails().equals(this.getTaskDetails()) // state checks here onwards
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime())
                && other.getPriority().equals(this.getPriority())
                && (other.getUniqueID()==this.getUniqueID()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskDetails()+ "\n")
                .append("Start time: ")
                .append(getStartTime().toCardString() + "\t ")
                .append("End time:")
                .append(getEndTime().toCardString()+ "\n")
                .append("Priority: ")
                .append(getPriority()+ "\n")
                .append("Tags: ");
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
    

}