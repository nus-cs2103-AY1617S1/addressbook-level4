package seedu.cmdo.logic.commands;

import seedu.cmdo.commons.exceptions.CannotUndoException;

//@@author A0139661Y
/**
 * Represents an undo command.
 */
public class UndoCommand extends Command {
	
	public static final String COMMAND_WORD = "undo";
	
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "undos previous action\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Undone!";
	
	public UndoCommand() {}

	@Override
	public CommandResult execute() {
		try {
			model.undo();
		} catch (CannotUndoException cue) {
			return new CommandResult(cue.getMessage());
		}
    	 
		return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS));
	}
}
