package seedu.dailyplanner.logic.commands;

import seedu.dailyplanner.commons.core.EventsCenter;
import seedu.dailyplanner.commons.core.Messages;
import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.commons.events.ui.JumpToListRequestEvent;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.dailyplanner.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Selects a task identified using it's last displayed index from the daily planner
 */
public class PinCommand extends Command {

	public final int targetIndex;

	public static final String COMMAND_WORD = "pin";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Pins the task identified by the index number used in the last task listing on the pin board.\n"
			+ "Format: pin [INDEX] (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

	public static final String MESSAGE_PINNED_TASK_SUCCESS = "Pinned Task: %1$s";
	public static final String MESSAGE_DUPLICATE_PINNED_TASK = "Task is already pinned.";

	public PinCommand(int targetIndex) {
		this.targetIndex = targetIndex;
	}

	@Override
	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

		if (lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}
		
		ReadOnlyTask taskToPin = lastShownList.get(targetIndex - 1);

		if (model.getPinnedTaskList().contains(taskToPin)) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(MESSAGE_DUPLICATE_PINNED_TASK);
		}
		
		try {
			model.getHistory().stackUnpinInstruction(taskToPin);
			model.pinTask(taskToPin);
			model.updatePinBoard();
		} catch (TaskNotFoundException pnfe) {
			assert false : "The target task cannot be missing";
		}
		return new CommandResult(String.format(MESSAGE_PINNED_TASK_SUCCESS, taskToPin));
	}

}
