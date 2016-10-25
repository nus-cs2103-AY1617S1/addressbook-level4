package seedu.address.logic.commands.taskcommands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.HideHelpRequestEvent;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.Task;

//@@author A0138978E
/**
 * Unfavorites a task identified using it's last displayed index from TaskManager.
 */
public class UnfavoriteTaskCommand extends TaskCommand {

	public static final String COMMAND_WORD = "unfavorite";
	
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unfavorites the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String HELP_MESSAGE_USAGE = "Unfavorite a task: \t" + COMMAND_WORD + " <index>";

    public static final String MESSAGE_FAVORITE_TASK_SUCCESS = "Unavorited task: %1$s";
    public static final String MESSAGE_TASK_ALR_UNFAVORITED = "Task is already an unfavorited task";


    public final int targetIndex;

    public UnfavoriteTaskCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

	    ObservableList<Task> lastShownList = model.getCurrentFilteredTasks();

        if (lastShownList.size() < targetIndex || targetIndex <= 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToUnfavorite = lastShownList.get(targetIndex - 1);
        if(taskToUnfavorite.isFavorite()){
            
        	model.unfavoriteTask(taskToUnfavorite);
        	EventsCenter.getInstance().post(new HideHelpRequestEvent());
        	return new CommandResult(String.format(MESSAGE_FAVORITE_TASK_SUCCESS, taskToUnfavorite));
        }
        else{
        	return new CommandResult(MESSAGE_TASK_ALR_UNFAVORITED);
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
