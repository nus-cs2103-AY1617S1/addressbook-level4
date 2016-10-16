package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.core.UnmodifiableObservableList;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.UniqueTaskList;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;
import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task
 * manager.
 */
public class DeleteCommand extends Command implements Undoable, Redoable {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                               + ": Deletes the task identified by the index number used in the last task listing.\n"
                                               + "Parameters: INDEX (must be a positive integer)\n"
                                               + "Example: "
                                               + COMMAND_WORD
                                               + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String COMMAND_SUMMARY = "Deleting a task:"
                                                 + "\n"
                                                 + COMMAND_WORD
                                                 + " INDEX";

    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Delete Command] Task added: %1$s";
    public static final String MESSAGE_REDO_SUCCESS = "[Redo Delete Command] Deleted Task: %1$s";

    public final int targetIndex;

    private ReadOnlyTask toDelete;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        try {
            executeDelete();

            model.pushToUndoHistory(this);

            model.clearRedoHistory();

        } catch (TaskNotFoundException | IndexOutOfBoundsException tnfe) {
            // by A0138862W: 
            // uncommented this line because it makes the DeleteCommandTest fail at line 33
            // should return invalid message for UI to display otherwise it'll display null
            //assert false : "The target task cannot be missing";
            
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, toDelete));
    }

    @Override
    /** action to perform when ModelManager requested to undo this command **/
    // @@author A0138862W
    public CommandResult undo() {
        try {
            // add back the task that's previously added.
            model.addTask((Task) toDelete);

            model.pushToRedoHistory(this);

            return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, toDelete));
        } catch (DuplicateTaskException e) {
            return new CommandResult(AddCommand.MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    // @@author A0138862W
    public CommandResult redo() {
        try {
            executeDelete();

            model.pushToUndoHistory(this);

        } catch (TaskNotFoundException | IndexOutOfBoundsException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_REDO_SUCCESS, toDelete));
    }

    private void executeDelete() throws TaskNotFoundException, IndexOutOfBoundsException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            throw new IndexOutOfBoundsException();
        }

        if (toDelete == null) {
            toDelete = lastShownList.get(targetIndex
                                         - 1);
        }

        model.deleteTask(toDelete);
    }

}
