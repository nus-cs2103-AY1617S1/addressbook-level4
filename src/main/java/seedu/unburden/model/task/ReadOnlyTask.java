package seedu.unburden.model.task;

import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 *  @@author A0143095H
 */

//@@Gauri Joshi A0143095H
public interface ReadOnlyTask {

    Name getName();
    TaskDescription getTaskDescription();
    Date getDate();
    Time getStartTime();
    Time getEndTime();
    boolean getDone();
    String getDoneString() throws IllegalValueException;
    
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
        
        // Floating task 
        if(getTaskDescription().getFullTaskDescription() == "" && getDate().getFullDate() == "" && getStartTime().getFullTime() == "" && getEndTime().getFullTime() == ""){
        	builder.append(getName());
        	builder.append("\n");
        	getTags().forEach(builder::append);
        }
        
        // Floating task with task description 
        else if(getTaskDescription().getFullTaskDescription() != "" && getDate().getFullDate() == "" && getStartTime().getFullTime() == ""&& getEndTime().getFullTime() == ""){       
	        builder.append(getName());
	        builder.append("\n");
	        builder.append("Task Description : ");        
	        builder.append(getTaskDescription());
	        builder.append("\n");
	        getTags().forEach(builder::append);
        }
        
        // Task with deadline 
        else if(getTaskDescription().getFullTaskDescription() == "" && getDate().getFullDate() !="" && getStartTime().getFullTime() == "" && getEndTime().getFullTime() == ""){       
	        builder.append(getName());
	        builder.append("\n");
	        builder.append("Deadline : ");        
	        builder.append(getDate());
	        builder.append("\n");
	        getTags().forEach(builder::append);
        }
        
        // Task with deadline and task description 
        else if(getTaskDescription().getFullTaskDescription() != "" && getDate().getFullDate() != "" && getStartTime().getFullTime() == "" && getEndTime().getFullTime() == ""){       
	        builder.append(getName());
	        builder.append("\n");
	        builder.append("Task Description : ");        
	        builder.append(getTaskDescription());
	        builder.append("\n");
	        builder.append("Deadline : ");        
	        builder.append(getDate());
	        builder.append("\n");
	        getTags().forEach(builder::append);
        }
        
        // Task with deadline and end date 
        else if(getTaskDescription().getFullTaskDescription() == "" && getDate().getFullDate() != "" && getStartTime().getFullTime() == "" && getEndTime().getFullTime() != ""){       
	        builder.append(getName());
	        builder.append("\n");
	        builder.append("Deadline : ");        
	        builder.append(getDate());
	        builder.append("\n");
	        builder.append("End time : ");  
	        builder.append(getEndTime() + "   ");
	        builder.append("\n");
	        getTags().forEach(builder::append);
        }
        
        // Task with deadline and end date and task description 
        else if(getTaskDescription().getFullTaskDescription() != "" && getDate().getFullDate() !="" && getStartTime().getFullTime() == "" && getEndTime().getFullTime() != ""){       
	        builder.append(getName());
	        builder.append("\n");
	        builder.append("Task Description : ");        
	        builder.append(getTaskDescription());
	        builder.append("\n");
	        builder.append("Deadline : ");        
	        builder.append(getDate());
	        builder.append("\n");
	        builder.append("End time : ");  
	        builder.append(getEndTime() + "   ");
	        builder.append("\n");
	        getTags().forEach(builder::append);
        }
        
        // Task with deadline, start time and end time 
        else if(getTaskDescription().getFullTaskDescription() == "" && getDate().getFullDate() !="" && getStartTime().getFullTime() != "" && getEndTime().getFullTime() != ""){       
	        builder.append(getName());
	        builder.append("\n");
	        builder.append("Deadline : ");        
	        builder.append(getDate());
	        builder.append("\n");
	        builder.append("Start Time - End time : ");  
	        builder.append(getStartTime() + " - ");
	        builder.append(getEndTime() + "   ");
	        builder.append("\n");
	        getTags().forEach(builder::append);
        }
        
        // Task with deadline, task description, start time and end time
        else {       
	        builder.append(getName());
	        builder.append("\n");
	        builder.append("Task Description : ");        
	        builder.append(getTaskDescription());
	        builder.append("\n");
	        builder.append("Deadline : ");        
	        builder.append(getDate());
	        builder.append("\n");
	        builder.append("Start Time - End time : ");  
	        builder.append(getStartTime() + " - ");
	        builder.append(getEndTime() + "   ");
	        builder.append("\n");
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
