package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;

public class UndoCommand extends Command {
	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_UNDO_SUCCESS = "Undid last command successfully";

	public UndoCommand() {}

	@Override
	public CommandResult execute() {
//		model.undo();
		return new CommandResult(MESSAGE_UNDO_SUCCESS);
	}
}
