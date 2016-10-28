package seedu.flexitrack.logic.commands;

import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.commons.core.UnmodifiableObservableList;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.flexitrack.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Selects a task identified using it's last displayed index from the
 * FlexiTrack.
 */
public class MarkCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "mark";
    public static final String COMMAND_SHORTCUT = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ", Shortcut [" + COMMAND_SHORTCUT + "]"
            + ": Marks the task identified by the index number used in the task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s";
    
    private Task taskStore; 
    private Task markedTask;

    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    /** 
     * Constructor for undo command
     */
    public MarkCommand() {
        this.targetIndex = 0;
    }

    @Override
    public CommandResult execute(){

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        try {
            taskStore = lastShownList.get(targetIndex - 1).copy();             
            markedTask = model.markTask(lastShownList.get(targetIndex-1));
            markedTask = markedTask.copy();
            recordCommand(this); 
            return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, targetIndex));
        } catch (IndexOutOfBoundsException ioobe) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }
    }

    //@@author A0127686R
    @Override
    public void executeUndo() {
        Task toDelete = markedTask; 
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
