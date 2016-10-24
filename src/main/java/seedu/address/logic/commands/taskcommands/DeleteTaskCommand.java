package seedu.address.logic.commands.taskcommands;

import javafx.collections.ObservableList;
import seedu.address.commons.collections.UniqueItemCollection.ItemNotFoundException;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.HideHelpRequestEvent;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.Task;

/**
 * Deletes a task identified using it's last displayed index from TaskManager.
 */
public class DeleteTaskCommand extends TaskCommand {

	public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = "Delete a task: \t" + "delete <index>";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    public final int targetIndex;

    public DeleteTaskCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

	        ObservableList<Task> lastShownList = model.getCurrentFilteredTasks();

        if (lastShownList.size() < targetIndex || targetIndex <= 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToDelete = lastShownList.get(targetIndex - 1);

	        try {
	            model.deleteTask(taskToDelete);
	            EventsCenter.getInstance().post(new HideHelpRequestEvent());
	            if(lastShownList.size() == 0) {
	                model.clearTasksFilter();
	            }
	        } catch (ItemNotFoundException tnfe) {
	            assert false : "The target item cannot be missing";
	        }


        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
