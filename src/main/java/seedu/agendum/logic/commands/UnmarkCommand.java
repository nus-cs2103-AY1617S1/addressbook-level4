package seedu.agendum.logic.commands;

import seedu.agendum.commons.core.Messages;
import seedu.agendum.commons.core.UnmodifiableObservableList;
import seedu.agendum.model.task.ReadOnlyTask;
import seedu.agendum.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Unmarks a task identified using its last displayed index from the to do list.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Unmarked Task: %1$s";

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
        }

        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
    }

}
