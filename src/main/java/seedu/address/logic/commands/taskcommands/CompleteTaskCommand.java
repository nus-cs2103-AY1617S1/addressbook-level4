package seedu.address.logic.commands.taskcommands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.Task;

/**
 * Completes a task identified using it's last displayed index from TaskManager.
 */
public class CompleteTaskCommand extends TaskCommand {

	public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed task: %1$s";
    public static final String MESSAGE_TASK_ALR_COMPLETED = "Task has already been completed";

    public static final String HELP_MESSAGE_USAGE = "Complete a task: \t" + COMMAND_WORD +" <index>";


    public final int targetIndex;

    public CompleteTaskCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

	    ObservableList<Task> lastShownList = model.getCurrentFilteredTasks();

        if (lastShownList.size() < targetIndex || targetIndex <= 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToComplete = lastShownList.get(targetIndex - 1);
        if(!taskToComplete.isComplete()){
        	model.completeTask(taskToComplete);
        	model.refreshTasksFilter();
        	return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
        }
        else{
        	return new CommandResult(MESSAGE_TASK_ALR_COMPLETED);
        }
    }

}
