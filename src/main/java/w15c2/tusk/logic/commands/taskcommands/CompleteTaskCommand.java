package w15c2.tusk.logic.commands.taskcommands;

import javafx.collections.ObservableList;
import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.core.Messages;
import w15c2.tusk.commons.events.ui.HideHelpRequestEvent;
import w15c2.tusk.logic.commands.Command;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.model.task.Task;
//@@author A0143107U
/**
 * Completes a task identified using it's last displayed index from TaskManager.
 */
public class CompleteTaskCommand extends Command {

	public static final String COMMAND_WORD = "complete";
    public static final String ALTERNATE_COMMAND_WORD = null;
    
    public static final String COMMAND_FORMAT = COMMAND_WORD + " <INDEX>";
    public static final String COMMAND_DESCRIPTION = "Complete a Task"; 

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed task: %1$s";
    public static final String MESSAGE_TASK_ALR_COMPLETED = "Task has already been completed";

    public static final String HELP_MESSAGE_USAGE = "Complete a task: \t" + COMMAND_WORD +" <index>";


    public final int targetIndex;

    /**
     * This CompleteTaskCommand constructor takes in a targetIndex and completes the task.
     *
     * @param targetIndex 	Index of the task to be completed.
     */
    public CompleteTaskCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Completes the task in Model
     * 
     * @return CommandResult Result of the execution of the complete command.
     */
    @Override
    public CommandResult execute() {

	    ObservableList<Task> lastShownList = model.getCurrentFilteredTasks();

        if (lastShownList.size() < targetIndex || targetIndex <= 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToComplete = lastShownList.get(targetIndex - 1);
        if(!taskToComplete.isCompleted()){
            closeHelpWindow();
        	model.completeTask(taskToComplete);
        	model.refreshTasksFilter();
        	return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
        }
        else{
        	return new CommandResult(MESSAGE_TASK_ALR_COMPLETED);
        }
    }

}
