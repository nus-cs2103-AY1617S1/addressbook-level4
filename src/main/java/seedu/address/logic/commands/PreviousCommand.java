package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Priority;
/** 
 * Carries information of previous command: Command word and task.
 */
public class PreviousCommand {

	public String COMMAND_WORD;
	public Activity updatedTask;
	public Activity oldTask;

	
	public PreviousCommand(String command, Activity task)
	{
		COMMAND_WORD = command;
		updatedTask = task;
		oldTask = null;
	}
	
	public PreviousCommand(String command, ReadOnlyActivity task) {
		COMMAND_WORD = command;
		oldTask = null;
		try {
		updatedTask = new Activity(
                new Name(task.getName().toString()),
                new DueDate(task.getDueDate().getCalendarValue()),
                new Priority(task.getPriority().toString()),
                new Reminder(task.getReminder().getCalendarValue()),
                new UniqueTagList(task.getTags())
        );
		} catch (IllegalValueException ive) {
			assert false: "Strings have to be all valid to be added in the first place";
		}
	}
	
	public PreviousCommand(String command, ReadOnlyActivity originalTask, ReadOnlyActivity editedTask) {
        COMMAND_WORD = command;
        updatedTask = new Activity(editedTask);
        
        try {            
            oldTask = new Activity(
                new Name(originalTask.getName().toString()),
                new DueDate(originalTask.getDueDate().getCalendarValue()),
                new Priority(originalTask.getPriority().toString()),
                new Reminder(originalTask.getReminder().getCalendarValue()),
                new UniqueTagList(originalTask.getTags())
        );
        } catch (IllegalValueException ive) {
            assert false: "Strings have to be all valid to be added in the first place";
        }
	
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
	   
	
}
