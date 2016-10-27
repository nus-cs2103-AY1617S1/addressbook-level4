package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.activity.event.EndTime;
import seedu.address.model.activity.event.Event;
import seedu.address.model.activity.event.ReadOnlyEvent;
import seedu.address.model.activity.event.StartTime;
import seedu.address.model.activity.task.DueDate;
import seedu.address.model.activity.task.Priority;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.model.activity.task.Task;
import seedu.address.model.tag.UniqueTagList;

//@@author A0125097A
/** 
 * Carries information of previous command: Command word and task.
 */
public class PreviousCommand {

	public String COMMAND_WORD;
	public Activity updatedTask;
	public Activity oldTask;
	public int index;

	
	public PreviousCommand(String command, Activity task)
	{
		COMMAND_WORD = command;
		updatedTask = task;
		oldTask = null;
	}
	
	public PreviousCommand(String command, int index, ReadOnlyActivity task)
	{
		COMMAND_WORD = command;
		this.index = index;
		String type = task.getClass().getSimpleName().toLowerCase();
			
		switch (type) {
        case "activity":
    		updatedTask = new Activity(task);
            break;
        
        case "task":
        	updatedTask = new Task((ReadOnlyTask) task);
            break;
        
        case "event":
        	updatedTask = new Event((ReadOnlyEvent) task);
            break;
        
        default:
            assert false : "Invalid class type";
        }
		
		
		oldTask = null;
	}
		
	public PreviousCommand(String command, Activity originalActivity, Activity editedActivity) {
        COMMAND_WORD = command;
        updatedTask = editedActivity;
                
            oldTask = originalActivity;

	}

	public String getCommand()
	{
		return COMMAND_WORD;
	}
	
	public Activity getUpdatedTask()
	{
		return updatedTask;
	}
	  
	public Activity getOldTask()
	{
	    return oldTask;
    }
	   
	public int getIndex()
	{
	    return index;
    }
	
}
