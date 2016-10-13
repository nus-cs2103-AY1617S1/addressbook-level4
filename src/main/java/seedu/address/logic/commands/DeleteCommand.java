package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.history.ReversibleEffect;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the last item listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String TOOL_TIP = "delete INDEX";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted Item: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredFloatingTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target person cannot be missing";
        }
        
        List<Task> affectedTasks = new ArrayList<Task>();
        Task affectedTaskToDelete = new Task(taskToDelete);
        affectedTasks.add(affectedTaskToDelete);
        history.update(new ReversibleEffect(this.COMMAND_WORD, affectedTasks));
        return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, taskToDelete));
    }

}
