package teamfour.tasc.model.task;

import java.util.Date;

import teamfour.tasc.model.tag.UniqueTagList;
import teamfour.tasc.model.task.status.EventStatus;

/**
 * A read-only immutable interface for a Task in the task list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {
    public static String MATCHSTRING_TYPE_ALL = "All Everything";
    public static String MATCHSTRING_TYPE_COMPLETED = "Completed";
    public static String MATCHSTRING_TYPE_UNCOMPLETED = "Uncompleted Incompleted";
    public static String MATCHSTRING_TYPE_RECURRING = "Recurring Repeating";
    public static String MATCHSTRING_TYPE_OVERDUE = "Overdue Past";
    public static String MATCHSTRING_TYPE_TASKS_WITH_TIMESLOT = "Tasks Allocated Timeslot Deadline Period";
    public static String MATCHSTRING_TYPE_NORMAL_TASKS = "Normal Tasks Deadline";
    public static String MATCHSTRING_TYPE_FLOATING_TASKS = "Floating Tasks";
    public static String MATCHSTRING_TYPE_EVENTS = "Events Period";
    
    Name getName();
    Complete getComplete();
    
    Deadline getDeadline();
    Period getPeriod();
    Recurrence getRecurrence();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && (other.getName().equals(this.getName()) // state checks here onwards
                   && other.getComplete().equals(this.getComplete())
                   && other.getDeadline().equals(this.getDeadline())
                   && other.getPeriod().equals(this.getPeriod())
                   && other.getRecurrence().equals(this.getRecurrence())));
    }
    
    /**
     * Is it a floating task?
     */
    default boolean isFloatingTask() {
        return !getDeadline().hasDeadline() && !getPeriod().hasPeriod();
    }
    
    /** 
     * Given the current time, determine whether the task is overdue.
     * (Only make sense if task has a deadline).
     */
    default boolean isOverdue(Date currentTime) {
        if (!getDeadline().hasDeadline()) {
            return false;
        }

        return currentTime.after(getDeadline().getDeadline());
    }
    
    /**
     * Get whether the event has started or not.
     */
    default EventStatus getEventStatus(Date currentTime) {
        if (!getPeriod().hasPeriod()) {
            return EventStatus.NOT_AN_EVENT;
        }
        
        if (currentTime.before(getPeriod().getStartTime())) {
            return EventStatus.NOT_STARTED;
        }
        else if (currentTime.after(getPeriod().getEndTime())) {
            return EventStatus.ENDED;
        }
        
        return EventStatus.IN_PROGRESS;
    }
    
    /**
     * Formats the task as keywords indicating its type from its attributes.
     */
    default String getAsTypeKeywords() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" ").append(MATCHSTRING_TYPE_ALL);
        
        if (getComplete().isCompleted()) {
            builder.append(" ").append(MATCHSTRING_TYPE_COMPLETED);
        } else {
            builder.append(" ").append(MATCHSTRING_TYPE_UNCOMPLETED);
        }
        
        if (getRecurrence().hasRecurrence()) {
            builder.append(" ").append(MATCHSTRING_TYPE_RECURRING);
        }
        
        if (isOverdue(new Date())) {
            builder.append(" ").append(MATCHSTRING_TYPE_OVERDUE);
        }
        
        if (getDeadline().hasDeadline() && getPeriod().hasPeriod()) {
            builder.append(" ").append(MATCHSTRING_TYPE_TASKS_WITH_TIMESLOT);
        } else if (getDeadline().hasDeadline()) {
            builder.append(" ").append(MATCHSTRING_TYPE_NORMAL_TASKS);
        } else if (getPeriod().hasPeriod()) {
            builder.append(" ").append(MATCHSTRING_TYPE_EVENTS);
        } else {
            builder.append(" ").append(MATCHSTRING_TYPE_FLOATING_TASKS);
        }
        
        return builder.toString();
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append("\nCompletion Status: ")
            .append(getComplete());
        
        if (getDeadline().hasDeadline()) {
            builder.append("\nDeadline: ")
                .append(getDeadline());
        }
        
        if (getPeriod().hasPeriod()) {
            builder.append("\nPeriod: ")
                .append(getPeriod());
        }

        if (getRecurrence().hasRecurrence()) {
            builder.append("\nRecurrence: ")
                .append(getRecurrence());
        }
        
        builder.append("\nTags: ");
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
