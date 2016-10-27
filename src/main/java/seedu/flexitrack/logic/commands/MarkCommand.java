package seedu.flexitrack.logic.commands;

import java.util.Stack;

import seedu.flexitrack.commons.core.EventsCenter;
import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.commons.events.ui.JumpToListRequestEvent;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.flexitrack.commons.core.UnmodifiableObservableList;

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
    
    private static Stack<Integer> storeDataChanged = new Stack<Integer>(); 
    private static Stack<ReadOnlyTask> storeDataChangedTask = new Stack<ReadOnlyTask>(); 

    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    /** 
     * Constructor for undo command
     */
    public MarkCommand() {
        this.targetIndex = storeDataChanged.peek();
    }

    @Override
    public CommandResult execute(){

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        try {
            ReadOnlyTask taskMarked = lastShownList.get(targetIndex - 1);
            model.markTask(lastShownList.get(targetIndex-1));
            storeDataChanged.add(targetIndex);
            storeDataChangedTask.add(taskMarked);
            recordCommand("mark"); 
            return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, targetIndex));
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }
    }

    @Override
    //TODO: to be implemented 
    public void executeUndo() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            
        }
        try {
            model.unmarkTask(lastShownList.get(targetIndex - 1));
        } catch (IllegalValueException e) {
        }
        
        storeDataChanged.pop();
    }
}
