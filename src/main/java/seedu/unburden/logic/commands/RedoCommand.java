package seedu.unburden.logic.commands;

import java.util.NoSuchElementException;

/**
 * Redo an undo action.
 */
public class RedoCommand extends Command {
	
	public static final String COMMAND_WORD = "redo";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": redo the most recent undo command. \n "
			+ "Example: " + COMMAND_WORD;

	public static final String MESSAGE_SUCCESS = "Redo success!";
	public static final String MESSAGE_EMPTY_STACK = "No recent undo commands can be found.";
	

	
	public CommandResult execute() {
		try {
			assert model != null;
			model.loadFromUndoHistory();
			return new CommandResult(MESSAGE_SUCCESS);
		} catch (NoSuchElementException ee) {
			return new CommandResult(MESSAGE_EMPTY_STACK);
		}
		
		
	}
}
