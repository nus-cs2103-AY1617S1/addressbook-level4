package seedu.address.logic.commands;

import java.util.LinkedList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.models.DeleteCommandModel;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.TaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tasks identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final DeleteCommandModel commandModel;

    public DeleteCommand(DeleteCommandModel commandModel) {
        assert (commandModel != null);
        this.commandModel = commandModel;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        LinkedList<ReadOnlyTask> tasksToDelete = new LinkedList<ReadOnlyTask>();
        for(int targetIndex : commandModel.getTargetIndex()) {
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            tasksToDelete.add(lastShownList.get(targetIndex - 1));
        }

        StringBuilder resultSb = new StringBuilder();
        try {
            for(ReadOnlyTask taskToDelete : tasksToDelete) {
                model.deleteTask(taskToDelete);
                resultSb.append(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
            }
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(resultSb.toString());
    }
    
    @Override
    protected boolean canUndo() {
        return false;
    }

    /**
     * Redo the delete command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        // nothing required to be done
        return true;
    }

    /**
     * Undo the delete command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // nothing required to be done
        return true;
    }

}
