package seedu.address.logic.commands;

import seedu.address.commons.exceptions.FinishStateException;

/**
 * Undoes a command given by the user in the task manager.
 * @@author A0139516B
 */
public class UndoCommand extends Command {
	
	public static final String COMMAND_WORD = "undo";
	
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the previously done command.\n"
			+ "Example: " + COMMAND_WORD;
	
	public static final String MESSAGE_SUCCESS = "Undid most recent command";
	public static final String MESSAGE_FAILED = "Unable to undo commands";
	
	public UndoCommand() {
		
	}
	
	@Override
	public CommandResult execute() {
		try {
			model.getPreviousState();
			return new CommandResult(MESSAGE_SUCCESS);
		} catch (FinishStateException e) {
			return new CommandResult(MESSAGE_FAILED);
		}
		
	}
		
}
