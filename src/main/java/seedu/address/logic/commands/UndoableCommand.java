package seedu.address.logic.commands;

public interface UndoableCommand {
	
	/**
	 * Undoable commands must have a method that reverse their effect. 
	 * The program is taken to the state it was at before the command's execution.
	 */
	public CommandResult undo();

	public CommandResult execute();
}
