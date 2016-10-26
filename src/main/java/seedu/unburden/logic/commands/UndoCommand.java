package seedu.unburden.logic.commands;

import java.util.NoSuchElementException;

/**
 * Undo an undo action.
 * @@author A0139714B
 */
public class UndoCommand extends Command {
	
	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the previous command. \n "
			+ "Example: " + COMMAND_WORD;

	public static final String MESSAGE_SUCCESS = "Undo success!";
	public static final String MESSAGE_EMPTY_STACK = "No recent commands can be found.";
	
	// dummy constructor
	public UndoCommand() {}
	
	public CommandResult execute() {
		try {
			assert model != null;
			model.loadFromPrevLists();
			return new CommandResult(MESSAGE_SUCCESS);
		} catch (NoSuchElementException ee) {
			return new CommandResult(MESSAGE_EMPTY_STACK);
		}
	}
		
}
