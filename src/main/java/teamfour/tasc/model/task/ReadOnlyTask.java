package teamfour.tasc.model.task;

import java.util.Date;

import teamfour.tasc.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

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
     * Formats the task as keywords indicating its type from its attributes.
     */
    default String getAsTypeKeywords() {
        Date now = new Date();
        final StringBuilder builder = new StringBuilder();
        builder.append("All");
        
        if (getComplete().isCompleted()) {
            builder.append(" Completed");
        } else {
            builder.append(" Uncompleted Incompleted");
        }
        
        if (getRecurrence().hasRecurrence()) {
            builder.append(" Recurring");
        }
        
        if (getDeadline().hasDeadline() && getPeriod().hasPeriod()) {
            if (getDeadline().getDeadline().before(now))
                builder.append(" Overdue");
            builder.append(" Tasks Allocated Timeslot");
            
        } else if (getDeadline().hasDeadline()) {
            if (getDeadline().getDeadline().before(now))
                builder.append(" Overdue");
            builder.append(" Normal Tasks");
            
        } else if (getPeriod().hasPeriod()) {
            if (getPeriod().getEndTime().before(now))
                builder.append(" Overdue");
            builder.append(" Events");
            
        } else {
            builder.append(" Floating Tasks");
        }
        System.out.println(builder.toString());
        
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
