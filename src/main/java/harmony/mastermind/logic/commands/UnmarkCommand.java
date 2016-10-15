package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.core.UnmodifiableObservableList;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.UniqueTaskList;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;
import harmony.mastermind.model.task.ArchiveTaskList.TaskNotFoundException;
import javafx.collections.ObservableList;

//@@author A0124797R
public class UnmarkCommand extends Command implements Undoable{
    public static final String COMMAND_WORD = "unmark";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": undo marking of task as done"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String COMMAND_SUMMARY = "marks a task as not complete:"
            + "\n" + COMMAND_WORD + " INDEX";

    public static final String MESSAGE_SUCCESS = "%1$s has been unmarked";
    public static final String MESSAGE_UNMARKED_TASK = "%1$s has not been completed";
    public static final String MESSAGE_DUPLICATE_UNMARK_TASK = "%1$s already exist in not completed list";

    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Unmark Command] %1$s has been archived";

    public final int targetIndex;
    
    ReadOnlyTask taskToUnmark;

    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredArchiveList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToUnmark = lastShownList.get(targetIndex - 1);
        
        if (!taskToUnmark.isMarked()) {
            return new CommandResult(String.format(MESSAGE_UNMARKED_TASK, taskToUnmark));
        }

        try {
            model.unmarkTask((Task)taskToUnmark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (DuplicateTaskException e) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE_UNMARK_TASK, taskToUnmark));
        }
        
        model.pushToUndoHistory(this);
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToUnmark));
    }
    
    @Override
    // @@author A0138862W
    public CommandResult undo() {
        try {
            // remove the task that's previously added.
            model.markTask((Task)taskToUnmark);

            return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, taskToUnmark));
        } catch (UniqueTaskList.TaskNotFoundException e) {
            return new CommandResult(Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        }
    }
    
}
