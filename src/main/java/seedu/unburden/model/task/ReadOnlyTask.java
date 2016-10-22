package seedu.unburden.model.task;

import seedu.unburden.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    TaskDescription getTaskDescription();
    Date getDate();
    Time getStartTime();
    Time getEndTime();
    boolean getDone();
    String getDoneString();
    
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
                && other.getName().equals(this.getName())); // state checks here onwards
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        
        if(getDate().fullDate == "NIL" && getStartTime().fullTime == "NIL" && getEndTime().fullTime == "NIL"){
        	builder.append(getName());
        	getTags().forEach(builder::append);
        }
        
        else if(getDate().fullDate != "NIL" && getStartTime().fullTime == "NIL" && getEndTime().fullTime == "NIL"){
        	builder.append(getName());
            builder.append("   Deadline : ");        
            builder.append(getDate());
            getTags().forEach(builder::append);
        }
        
        else if(getTaskDescription().fullTaskDescriptions != "NIL" && getDate().fullDate != "NIL" && getStartTime().fullTime != "NIL" && getEndTime().fullTime != "NIL"){       
	        builder.append(getName());
	        builder.append("   Task Description : ");        
	        builder.append(getTaskDescription());
	        builder.append("   Deadline : ");        
	        builder.append(getDate());
	        builder.append("   Start Time - End time : ");  
	        builder.append(getStartTime() + " - ");
	        builder.append(getEndTime() + "   ");
	        getTags().forEach(builder::append);
        }
        
        
        else {       
	        builder.append(getName());
	        builder.append("   Deadline : ");        
	        builder.append(getDate());
	        builder.append("   Start Time - End time : ");  
	        builder.append(getStartTime() + " - ");
	        builder.append(getEndTime() + "   ");
	        getTags().forEach(builder::append);
        }
        
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
