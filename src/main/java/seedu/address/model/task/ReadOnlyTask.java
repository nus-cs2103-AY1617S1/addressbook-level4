package seedu.address.model.task;

import java.util.Date;
import java.util.Optional;

import seedu.address.model.tag.UniqueTagList;


/**
 * A read-only immutable interface for a Task in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

	public enum TaskType {
        EVENT {
            @Override
            public String toString() {
                return "Event";
            }
        }, 
        
        DEADLINE {
            @Override
            public String toString() {
                return "Deadline";
            }
        }, 
        
        SOMEDAY {
        	@Override
        	public String toString() {
        		return "Someday";
        	}
        }
    }
    
    public Name getName();
    public TaskType getTaskType();
    public Status getStatus();
    public Optional<Date> getStartDate();
    public Optional<Date> getEndDate();
    
    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    public UniqueTagList getTags();

    
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(TMReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getEndDate().equals(this.getEndDate())
                && other.getTaskType().equals(this.getTaskType())
                && other.getStatus().equals(getStatus()));
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
    	final StringBuilder builder = new StringBuilder();
    	
    	builder.append(getName().toString());
    	builder.append(" Task type: " + getTaskType().toString());
    	getStartDate().ifPresent(startDate -> builder.append(" Start date: " + startDate.toString()));
    	getEndDate().ifPresent(endDate -> builder.append(" End date: " + endDate.toString()));
    	builder.append(" Status: " + getStatus().toString());
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
