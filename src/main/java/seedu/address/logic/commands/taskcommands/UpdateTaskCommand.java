package seedu.address.logic.commands.taskcommands;

import java.util.Date;

import javafx.collections.ObservableList;
import seedu.address.commons.collections.UniqueItemCollection.ItemNotFoundException;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.Description;
import seedu.address.model.task.Task;

/**
 * Updates a task identified using it's last displayed index from TaskManager.
 */
public class UpdateTaskCommand extends TaskCommand {

	public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the task identified by the index number used in the last task listing.\n"
            + "Depending on whether 'task', 'description' or 'date' is stated, the task will be updated accordingly.\n"
            + "1) Parameters: INDEX (must be a positive integer) task UPDATED_VALUE\n"
            + "Example: " + COMMAND_WORD + " 1 task Meeting from Oct 31 to Nov 1\n"
            + "2) Parameters: INDEX (must be a positive integer) task UPDATED_VALUE\n"
            + "Example: " + COMMAND_WORD + " 1 description Meeting in town\n"
            + "3) Parameters: INDEX (must be a positive integer) task UPDATED_VALUE\n"
            + "Example: " + COMMAND_WORD + " 1 date Oct 31 to Nov 1";

    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated task: %1$s";

    public final int targetIndex;
    public final Task updatedTask;

    public UpdateTaskCommand(int targetIndex, Task newTask) {
        this.targetIndex = targetIndex;
        updatedTask = newTask;
    }
    
    public UpdateTaskCommand(int targetIndex, Description newDescription) {
        this.targetIndex = targetIndex;
        updatedTask = null;
    }
    
    public UpdateTaskCommand(int targetIndex, Date newDeadline) {
        this.targetIndex = targetIndex;
        updatedTask = null;
    }
    
    public UpdateTaskCommand(int targetIndex, Date newStartDate, Date newEndDate) {
        this.targetIndex = targetIndex;
        updatedTask = null;
    }


    @Override
    public CommandResult execute() {

	    ObservableList<Task> lastShownList = model.getCurrentFilteredTasks();

        if (lastShownList.size() < targetIndex || targetIndex <= 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToUpdate = lastShownList.get(targetIndex - 1);

        try {
            model.updateTask(taskToUpdate, updatedTask);
        } catch (ItemNotFoundException tnfe) {
            assert false : "The target item cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
    }

}
