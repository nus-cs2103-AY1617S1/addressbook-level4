//@@author A0139772U
package seedu.whatnow.model.task;

import seedu.whatnow.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the whatnow.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    String getTaskDate();
    String getStartDate();
    String getEndDate();
    String getTaskTime();
    String getStartTime();
    String getEndTime();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();
    
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        if (isBothFloating(other)) {
            return other == this // short circuit if same object
                    || (other != null // this is first to avoid NPE below
                    && other.getName().equals(this.getName())
                    && other.getTags().equals(this.getTags()));
        } else if (isBothDeadline(other)) {
            if (other.getTaskTime() == null && this.getTaskTime() == null) {
                return other == this // short circuit if same object
                        || (other != null // this is first to avoid NPE below
                        && other.getName().equals(this.getName())
                        && other.getTaskDate().equals(this.getTaskDate())
                        && other.getTags().equals(this.getTags()));
            } else if (other.getTaskTime() != null && this.getTaskTime() != null) {
                return other == this // short circuit if same object
                        || (other != null // this is first to avoid NPE below
                        && other.getName().equals(this.getName())
                        && other.getTaskDate().equals(this.getTaskDate())
                        && other.getTags().equals(this.getTags())
                        && other.getTaskTime().equals(this.getTaskTime()));
            } else {
                return false;
            }
        } else if (isBothEvent(other)) {
            if (other.getStartTime() == null && this.getStartTime() == null) {
                return other == this // short circuit if same object
                        || (other != null // this is first to avoid NPE below
                        && other.getName().equals(this.getName())
                        && other.getStartDate().equals(this.getStartDate())
                        && other.getEndDate().equals(this.getEndDate())
                        && other.getTags().equals(this.getTags()));               
            } else if (other.getStartTime() != null && this.getStartTime() != null) {
                return other == this // short circuit if same object
                        || (other != null // this is first to avoid NPE below
                        && other.getName().equals(this.getName())
                        && other.getStartDate().equals(this.getStartDate())
                        && other.getEndDate().equals(this.getEndDate())
                        && other.getStartTime().equals(this.getStartTime())
                        && other.getEndTime().equals(this.getEndTime())
                        && other.getTags().equals(this.getTags()));             
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    default boolean isBothFloating(ReadOnlyTask task) {
        return this.getTaskDate() == null && task.getTaskDate() == null
                && this.getStartDate() == null && task.getStartDate() == null;
    }
    
    default boolean isBothDeadline(ReadOnlyTask task) {
        return this.getTaskDate() != null && task.getTaskDate() != null
                && this.getStartDate() == null && task.getStartDate() == null;
    }
    
    default boolean isBothEvent(ReadOnlyTask task) {
        return this.getTaskDate() == null && task.getTaskDate() == null
                && this.getStartDate() != null && task.getStartDate() != null;
    }
    
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
    
  //@@author A0126240W
    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (getTaskDate() != null)
            builder.append(" " + getTaskDate());
        		
        if (getStartDate() != null)
            builder.append(" " + getStartDate());
        
        if (getEndDate() != null)
            builder.append(" " + getEndDate());
        
        if (getTaskTime() != null)
            builder.append(" " + getTaskTime());
        
        if (getStartTime() != null)
            builder.append(" " + getStartTime());
        
        if (getEndTime() != null)
            builder.append(" " + getEndTime());
        
        if (getStatus() != null)
            builder.append(" " + getStatus());
        
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
