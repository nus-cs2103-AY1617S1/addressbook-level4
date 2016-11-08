package seedu.dailyplanner.logic.commands;

import seedu.dailyplanner.commons.core.Messages;
import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.UniqueTaskList.TaskNotFoundException;


// @@author A0146749N

/**
 * Unpins a task identified using it's last displayed index from the daily
 * planner pinned task board.
 */
public class UnpinCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
	    + ": Unpins the task identified by the index number used in the last task listing on the pin board.\n"
	    + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PIN_TASK_SUCCESS = "Unpinned Task: %1$s";

    public UnpinCommand(int targetIndex) {
	this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

	UnmodifiableObservableList<ReadOnlyTask> pinnedList = model.getPinnedTaskList();

	if (pinnedList.size() < targetIndex) {
	    indicateAttemptToExecuteIncorrectCommand();
	    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	}

	ReadOnlyTask taskToUnpin = pinnedList.get(targetIndex - 1);

	try {
	    model.getHistory().stackPinInstruction(taskToUnpin);
	    model.unpinTask(targetIndex - 1);
	} catch (TaskNotFoundException pnfe) {
	    assert false : "The target task cannot be missing";
	}
	return new CommandResult(String.format(MESSAGE_PIN_TASK_SUCCESS, taskToUnpin));
    }

}
