package seedu.tasklist.logic.commands;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.UniqueTaskList.TaskCompletionException;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Unmarks a task identified using it's last displayed index from the task list.
 */
public class UnmarkCommand extends Command {
    
    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmark a task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Task unmarked: %1$s";
    public static final String MESSAGE_UNMARKED_TASK = "This task is already unmarked in the task list.";

    public final int targetIndex;
    
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

        ReadOnlyTask taskToUnmark = lastShownList.get(targetIndex - 1);

        try {
            model.unmarkTask(taskToUnmark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (TaskCompletionException e) {
            return new CommandResult(MESSAGE_UNMARKED_TASK);
        }

        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
    }
    
    

}
