package seedu.cmdo.logic.commands;

import seedu.cmdo.commons.exceptions.CannotUndoException;

//@@author A0141006B
/**
 * Represents a redo command.
 */
public class RedoCommand extends Command {
	
	public static final String COMMAND_WORD = "redo";
	
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "redos previous undone action\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_REDO_SUCCESS = "Redone!";
	
	public RedoCommand() {}

	@Override
	public CommandResult execute() {		
		try {
			model.redo();
		} catch (CannotUndoException cue) {
			return new CommandResult(cue.getMessage());
		}
    	 
		return new CommandResult(String.format(MESSAGE_REDO_SUCCESS));
	}
}
