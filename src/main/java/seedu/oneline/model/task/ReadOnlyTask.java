package seedu.oneline.model.task;

import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the taskbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    public TaskName getName();
    public TaskTime getStartTime();
    public TaskTime getEndTime();
    public TaskTime getDeadline();
    public TaskRecurrence getRecurrence();
    public boolean isCompleted();

    /**
     * Returns the tag of the current task
     */
    Tag getTag();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime())
                && other.getDeadline().equals(this.getDeadline())
                && other.getRecurrence().equals(this.getRecurrence())
                && other.isCompleted() == this.isCompleted());
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Time: ")
                .append(getStartTime())
                .append(" to ")
                .append(getEndTime())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Recurrence: ")
                .append(getRecurrence())
                .append(" Tag: ")
                .append(getTag());
        return builder.toString();
    }

}
