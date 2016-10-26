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
        if (this.getName().equals(other.getName())) {  
            if (isSameNonFloatingTask(this, other)) {
                return true;
            }  
        }
        
        return false;
    }
    
    public static boolean isFloatingTask(ReadOnlyTask task) {
        if (task.getTaskDate() != null) {
            return false;
        }
        
        if (task.getStartDate() != null) {
            return false;
        }
        
        if (task.getEndDate() != null) {
            return false;
        }
        
        if (task.getTaskTime() != null) {
            return false;
        }
        
        if (task.getStartTime() != null) {
            return false;
        }
        
        if (task.getEndTime() != null) {
            return false;
        }
        
        return true;
    }
    
    public static boolean isSameNonFloatingTask(ReadOnlyTask task1, ReadOnlyTask task2) {
        if (!isFloatingTask(task1) || !isFloatingTask(task2)) {
            if (task1.getTaskDate() != null && !task1.getTaskDate().equals(task2.getTaskDate())) {
                return false;
            }
            
            if (task2.getTaskDate() != null && !task2.getTaskDate().equals(task1.getTaskDate())) {
                return false;
            }
            
            if (task1.getStartDate() != null && !task1.getStartDate().equals(task2.getStartDate())) {
                return false;
            }
            
            if (task2.getStartDate() != null && !task2.getStartDate().equals(task1.getStartDate())) {
                return false;
            }
            
            if (task1.getEndDate() != null && !task1.getEndDate().equals(task2.getEndDate())) {
                return false;
            }
            
            if (task2.getEndDate() != null && !task2.getEndDate().equals(task1.getEndDate())) {
                return false;
            }
            
            if (task1.getTaskTime() != null && !task1.getTaskTime().equals(task2.getTaskTime())) {
                return false;
            }
            
            if (task2.getTaskTime() != null && !task2.getTaskTime().equals(task1.getTaskTime())) {
                return false;
            }
            
            if (task1.getStartTime() != null && !task1.getStartTime().equals(task2.getStartTime())) {
                return false;
            }
            
            if (task2.getStartTime() != null && !task2.getStartTime().equals(task1.getStartTime())) {
                return false;
            }
            
            if (task1.getEndTime() != null && !task1.getEndTime().equals(task2.getEndTime())) {
                return false;
            }
            
            if (task2.getEndTime() != null && !task2.getEndTime().equals(task1.getEndTime())) {
                return false;
            }
        }        
        
        if (task1.getTags() != null && !task1.getTags().equals(task2.getTags())) {
            return false;
        }
        
        if (task2.getTags() != null && !task2.getTags().equals(task1.getTags())) {
            return false;
        }
        
        return true;
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
