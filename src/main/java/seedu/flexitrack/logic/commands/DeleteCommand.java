package seedu.flexitrack.logic.commands;

import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.commons.core.UnmodifiableObservableList;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.flexitrack.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the
 * FlexiTrack.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_SHORTCUT = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ", Shortcut [" + COMMAND_SHORTCUT + "]"
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    public final int targetIndex;
    private ReadOnlyTask taskStore;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    /** 
     * Constructor for undo command
     */
    public DeleteCommand() {
        this.targetIndex = 0;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        try {
            taskStore = lastShownList.get(targetIndex - 1);
            model.deleteTask(taskStore);
            recordCommand(this); 
        } catch (IndexOutOfBoundsException ioobe) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskStore));
    }
    
    @Override
    public void executeUndo() {
        assert model != null;
        Task toAdd = (Task) taskStore;
        try {
            model.addTask(toAdd);
        } catch (DuplicateTaskException e) {
            e.printStackTrace();
        }
    }
}
