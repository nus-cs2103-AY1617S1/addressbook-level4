package seedu.dailyplanner.logic.commands;

import seedu.dailyplanner.commons.core.Messages;
import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Selects a task identified using it's last displayed index from the daily planner.
 */
public class CompleteCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number shown in current list\n"
            + "Format: complete [INDEX] (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Completed Task: %1$s";

    public CompleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex - 1);

        try {
            model.getHistory().stackUncompleteInstruction(taskToComplete);
            model.markTaskAsComplete(taskToComplete);
            model.updatePinBoard();
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, taskToComplete));
    }

}
