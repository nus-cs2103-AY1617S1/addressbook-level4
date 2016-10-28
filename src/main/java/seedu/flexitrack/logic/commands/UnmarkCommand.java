package seedu.flexitrack.logic.commands;

//@@author A0138455Y
import java.util.Stack;

import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.commons.core.UnmodifiableObservableList;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.task.DateTimeInfo;
import seedu.flexitrack.model.task.Name;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.flexitrack.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Selects a task identified using it's last displayed index from the
 * FlexiTrack.
 */
public class UnmarkCommand extends Command {
    public final int targetIndex;

    public static final String COMMAND_WORD = "unmark";
    public static final String COMMAND_SHORTCUT = "u";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ", Shortcut [" + COMMAND_SHORTCUT + "]"
            + ": Unmarks the task identified by the index number used in the task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Unmark Task: %1$s";
    
    private Task taskStore; 
    private Task unMarkedTask;
    
    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    /** 
     * Constructor for undo command
     */
    public UnmarkCommand() {
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
            taskStore = lastShownList.get(targetIndex - 1).copy();             
            unMarkedTask = model.unmarkTask(lastShownList.get(targetIndex-1));
            unMarkedTask = unMarkedTask.copy();
            recordCommand(this); 
            return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, targetIndex));
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }

    }
    
    //@@author A0127686R
    @Override
    public void executeUndo() {
        Task toDelete = unMarkedTask; 
        Task toAddBack = taskStore;
        
        try {
            model.deleteTask(toDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        
        try {
            model.addTask(toAddBack);
        } catch (DuplicateTaskException e) {
            indicateAttemptToExecuteIncorrectCommand();
        }
    }
}
