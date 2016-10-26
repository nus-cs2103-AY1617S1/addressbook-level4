package seedu.task.logic.commands;

import seedu.task.commons.events.ui.JumpToTaskListRequestEvent;

import seedu.task.model.item.*;
import seedu.taskcommons.core.EventsCenter;
import seedu.taskcommons.core.Messages;
import seedu.taskcommons.core.UnmodifiableObservableList;

/**
 * Selects an Event identified using it's last displayed index from the task
 * book.
 * @author Yee Heng
 */
public class SelectTaskCommand extends SelectCommand {

	public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

	public SelectTaskCommand(int targetIndex) {
		this.targetIndex = targetIndex;
	}

	@Override
	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

		if (lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(targetIndex - 1));
		return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

	}
}
