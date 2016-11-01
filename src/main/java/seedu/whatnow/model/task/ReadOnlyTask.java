//@@author A0139772U
package seedu.whatnow.model.task;

import seedu.whatnow.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the whatnow. Implementations
 * should guarantee: details are present and not null, field values are
 * validated.
 */
public interface ReadOnlyTask {

    Name getName();

    String getTaskDate();

    String getStartDate();

    String getEndDate();

    String getTaskTime();

    String getStartTime();

    String getEndTime();
    
    String getPeriod();
    
    String getEndPeriod();

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Return the status of the task.
     * @return
     */
    String getStatus();
    
    /**
     * Return the task type of the task.
     * @return
     */
    String getTaskType();
    
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        if (isBothNoDateNoTime(other)) {
            return other == this // short circuit if same object
                    || (other != null // this is first to avoid NPE below
                            && other.getName().equals(this.getName()) && other.getTags().equals(this.getTags()));
        } else if (isBothOneDateNoTime(other)) {
            return other == this // short circuit if same object
                    || (other != null // this is first to avoid NPE below
                            && other.getName().equals(this.getName()) && other.getTags().equals(this.getTags()))
                            && other.getTaskDate().equals(this.getTaskDate());
        } else if (isBothOneDateOneTime(other)) {
            return other == this // short circuit if same object
                    || (other != null // this is first to avoid NPE below
                            && other.getName().equals(this.getName()) && other.getTags().equals(this.getTags()))
                            && other.getTaskDate().equals(this.getTaskDate())
                            && other.getTaskTime().equals(this.getTaskTime());
        } else if (isBothOneDateTwoTime(other)) {
            return other == this // short circuit if same object
                    || (other != null // this is first to avoid NPE below
                            && other.getName().equals(this.getName()) && other.getTags().equals(this.getTags()))
                            && other.getTaskDate().equals(this.getTaskDate())
                            && other.getStartTime().equals(this.getStartTime())
                            && other.getEndTime().equals(this.getEndTime());
        } else if (isBothTwoDateNoTime(other)) {
            return other == this // short circuit if same object
                    || (other != null // this is first to avoid NPE below
                            && other.getName().equals(this.getName()) && other.getTags().equals(this.getTags()))
                            && other.getStartDate().equals(this.getStartDate())
                            && other.getEndDate().equals(this.getEndDate());
        } else if (isBothTwoDateTwoTime(other)) {
            return other == this // short circuit if same object
                    || (other != null // this is first to avoid NPE below
                            && other.getName().equals(this.getName()) && other.getTags().equals(this.getTags()))
                            && other.getStartDate().equals(this.getStartDate())
                            && other.getEndDate().equals(this.getEndDate())
                            && other.getStartTime().equals(this.getStartTime())
                            && other.getEndTime().equals(this.getEndTime());
        } else {
            return false;
        }
    }

    default boolean isBothNoDateNoTime(ReadOnlyTask task) {
        return this.getTaskDate() == null && task.getTaskDate() == null && this.getStartDate() == null
                && task.getStartDate() == null && this.getEndDate() == null && task.getEndDate() == null
                && this.getStartTime() == null && task.getStartTime() == null && this.getEndDate() == null
                && task.getEndTime() == null;
    }

    default boolean isBothOneDateNoTime(ReadOnlyTask task) {
        return this.getTaskDate() != null && task.getTaskDate() != null && this.getTaskTime() == null
                && task.getTaskTime() == null && this.getStartTime() == null && task.getStartTime() == null;
    }

    default boolean isBothOneDateOneTime(ReadOnlyTask task) {
        return this.getTaskDate() != null && task.getTaskDate() != null && this.getTaskTime() != null
                && task.getTaskTime() != null;
    }

    default boolean isBothOneDateTwoTime(ReadOnlyTask task) {
        return this.getTaskDate() != null && task.getTaskDate() != null && this.getStartTime() != null
                && task.getStartTime() != null;
    }

    default boolean isBothTwoDateNoTime(ReadOnlyTask task) {
        return this.getStartDate() != null && task.getStartDate() != null && this.getStartTime() == null
                && task.getStartTime() == null;
    }

    default boolean isBothTwoDateTwoTime(ReadOnlyTask task) {
        return this.getStartDate() != null && task.getStartDate() != null && this.getStartTime() != null
                && task.getStartDate() != null;
    }
    
    //@@author A0126240W
    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (getTaskDate() != null) {
            builder.append(" on " + getTaskDate());
        }
        if (getStartDate() != null) {
            builder.append(" from " + getStartDate());
        }
        if (getEndDate() != null) {
            builder.append(" to " + getEndDate());
        }
        if (getTaskTime() != null) {
            builder.append(" at " + getTaskTime());
        }
        if (getStartTime() != null) {
            builder.append(" from " + getStartTime());
        }
        if (getEndTime() != null) {
            builder.append(" to " + getEndTime());
        }
        if (getPeriod() != null) {
            builder.append(" every " + getPeriod());
        }
        if (getEndPeriod() != null) {
            builder.append(" till " + getEndPeriod());
        }
        if (getStatus() != null) {
            builder.append(" " + getStatus());
        }
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    //@@author A0139772U
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
