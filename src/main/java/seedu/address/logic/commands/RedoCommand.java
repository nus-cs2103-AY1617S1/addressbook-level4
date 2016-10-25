package seedu.address.logic.commands;

import java.util.EmptyStackException;

//@@author A0141019U
public class RedoCommand extends Command {
	public static final String COMMAND_WORD = "redo";

	// TODO better message
	public static final String MESSAGE_REDO_SUCCESS = "Redid last undo successfully";
	public static final String MESSAGE_REDO_FAIL = "No more actions to redo";

	public RedoCommand() {}

	@Override
	public CommandResult execute() {
		try {
			model.loadNextState();
			return new CommandResult(MESSAGE_REDO_SUCCESS);
		}
		catch (EmptyStackException e) {
			return new CommandResult(MESSAGE_REDO_FAIL);
		}
	}
}
