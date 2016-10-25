package seedu.flexitrack.logic.commands;

import java.util.Stack;

import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.commons.core.UnmodifiableObservableList;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.task.ReadOnlyTask;

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
    
    private static Stack<Integer> storeDataChanged = new Stack<Integer>(); 

    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        try {
            model.unmarkTask(lastShownList.get(targetIndex - 1));
            storeDataChanged.add(targetIndex);
            recordCommand("unmark"); 
            return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, targetIndex));
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }

    }
    
    @Override
    //TODO: to be implemented
    public void executeUndo() {
        int targetIndex = storeDataChanged.peek();
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
        }

        try {
            model.markTask(lastShownList.get(targetIndex-1));
        } catch (IllegalValueException e) {
        }
    }
}
