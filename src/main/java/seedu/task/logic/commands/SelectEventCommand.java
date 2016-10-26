package seedu.task.logic.commands;

import seedu.task.commons.events.ui.JumpToEventListRequestEvent;
import seedu.task.model.item.*;
import seedu.taskcommons.core.EventsCenter;
import seedu.taskcommons.core.Messages;
import seedu.taskcommons.core.UnmodifiableObservableList;

/**
 * Selects an Event identified using it's last displayed index from the task
 * book.
 * @author Yee Heng
 */
public class SelectEventCommand extends SelectCommand {

	public static final String MESSAGE_SELECT_EVENT_SUCCESS = "Selected Event: %1$s";

	public SelectEventCommand(int targetIndex) {
		this.targetIndex = targetIndex;
	}

	@Override
	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();

		if (lastShownEventList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
		}

		EventsCenter.getInstance().post(new JumpToEventListRequestEvent(targetIndex - 1));
		return new CommandResult(String.format(MESSAGE_SELECT_EVENT_SUCCESS, targetIndex));

	}

}
