package seedu.address.logic.commands;


public class UndoCommand extends Command {
	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_UNDO_SUCCESS = "Undid last command successfully";

	public UndoCommand() {}

	@Override
	public CommandResult execute() {
		// TODO
		return null;
	}
}
