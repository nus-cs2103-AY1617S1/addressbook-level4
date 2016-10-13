package seedu.address.logic.commands;


public class UndoCommand extends Command {
	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_UNDO_SUCCESS = "Undid last command successfully";

	public UndoCommand() {}

	@Override
	public CommandResult execute() {
//		model.undo();
		return new CommandResult(MESSAGE_UNDO_SUCCESS);
	}
	
	@Override
	public CommandResult undo() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Undo operation cannot be undone. To redo, enter the redo keyword.");
	}
}
