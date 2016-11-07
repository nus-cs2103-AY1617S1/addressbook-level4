package seedu.lifekeeper.logic.commands;

import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.model.activity.event.Event;
import seedu.lifekeeper.model.activity.event.ReadOnlyEvent;
import seedu.lifekeeper.model.activity.task.ReadOnlyTask;
import seedu.lifekeeper.model.activity.task.Task;

//@@author A0125097A
/** 
 * Carries information of previous command: Command word and task.
 */
public class PreviousCommand {

	public String commandWord;
	public Activity updatedActivity;
	public Activity oldActivity;
	public int index;

	
	public PreviousCommand(String command, Activity activity)
	{
		commandWord = command;
		updatedActivity = activity;
		oldActivity = null;
	}
	
	public PreviousCommand(String command, int index, ReadOnlyActivity activity)
	{
		commandWord = command;
		this.index = index;
		String type = activity.getClass().getSimpleName().toLowerCase();
			
		switch (type) {
        case "activity":
    		updatedActivity = new Activity(activity);
            break;
        
        case "task":
        	updatedActivity = new Task((ReadOnlyTask) activity);
            break;
        
        case "event":
        	updatedActivity = new Event((ReadOnlyEvent) activity);
            break;
        
        default:
            assert false : "Invalid class type";
        }
		
		
		oldActivity = null;
	}
		
	public PreviousCommand(String command, Activity originalActivity, Activity editedActivity) {
        commandWord = command;
        updatedActivity = editedActivity;
                
            oldActivity = originalActivity;

	}

	public String getCommand()
	{
		return commandWord;
	}
	
	public Activity getUpdatedTask()
	{
		return updatedActivity;
	}
	  
	public Activity getOldTask()
	{
	    return oldActivity;
    }
	   
	public int getIndex()
	{
	    return index;
    }
	
}
