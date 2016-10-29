package seedu.address.logic.commands.taskcommands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.HideHelpRequestEvent;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.Task;

//@@author A0138978E
/**
 * Unpins a task identified using it's last displayed index from TaskManager.
 */
public class UnpinTaskCommand extends TaskCommand {

	public static final String[] COMMAND_WORD = {"unpin"};
	
    public static final String MESSAGE_USAGE = COMMAND_WORD[0]
            + ": Unpins the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD[0] + " 1";
    
    public static final String HELP_MESSAGE_USAGE = "Unpin a task: \t" + COMMAND_WORD[0] + " <index>";

    public static final String MESSAGE_UNPIN_TASK_SUCCESS = "Unpinned task: %1$s";
    public static final String MESSAGE_TASK_ALR_UNPINNED = "Task is already an unpinned task";


    public final int targetIndex;

    public UnpinTaskCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

	    ObservableList<Task> lastShownList = model.getCurrentFilteredTasks();

        if (lastShownList.size() < targetIndex || targetIndex <= 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToUnpin = lastShownList.get(targetIndex - 1);
        if(taskToUnpin.isPinned()){
            
        	model.unpinTask(taskToUnpin);
        	EventsCenter.getInstance().post(new HideHelpRequestEvent());
        	return new CommandResult(String.format(MESSAGE_UNPIN_TASK_SUCCESS, taskToUnpin));
        }
        else{
        	return new CommandResult(MESSAGE_TASK_ALR_UNPINNED);
        }
    }
    
  //@@author
    /*
     * Retrieves index for testing purposes
     */
    public String getIndex(){
    	return Integer.toString(targetIndex);
    }

}
