package seedu.flexitrack.logic.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.commons.core.UnmodifiableObservableList;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.task.DateTimeInfo;
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

    static Stack<ReadOnlyTask> storeDataChanged = new Stack<ReadOnlyTask>(); 

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

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        
        storeDataChanged.add(taskToDelete);
        recordCommand("delete"); 
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }
    
    @Override
    public void executeUndo() {
        Task toAdd = new Task (storeDataChanged.peek());
        assert model != null;
        try {
            model.addTask(toAdd);
        } catch (DuplicateTaskException e) {
            e.printStackTrace();
        }

        storeDataChanged.pop();
    }

}
