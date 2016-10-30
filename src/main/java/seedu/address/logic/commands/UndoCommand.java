package seedu.address.logic.commands;

import java.util.EmptyStackException;

//@@author A0141019U
public class UndoCommand extends Command {
	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_UNDO_SUCCESS = "Undid last command successfully";
	public static final String MESSAGE_UNDO_FAIL = "No actions to undo";

	public UndoCommand() {}

	@Override
	public CommandResult execute() {
		model.checkForOverdueTasks();
		try {
			model.loadPreviousState();
			return new CommandResult(MESSAGE_UNDO_SUCCESS);
		}
		catch (EmptyStackException e) {
			return new CommandResult(MESSAGE_UNDO_FAIL);
		}
	}
}
