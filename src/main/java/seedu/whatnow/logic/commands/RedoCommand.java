package seedu.whatnow.logic.commands;

public class RedoCommand extends Command{

	public static final String COMMAND_WORD = "redo";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo the previous action "
			+ "Parameters: No parameters"
			+ "Example: " + COMMAND_WORD;

	public static final String MESSAGE_SUCCESS = "Redo Successfully";
	public static final String MESSAGE_FAIL = "Redo failure due to unexisting undo commands";
	@Override
	public CommandResult execute() {
		assert model != null;
		if(model.getRedoStack().isEmpty()) {
			return new CommandResult(MESSAGE_FAIL);
		}
		else {
			if(model.getRedoStack().isEmpty()) {
				System.out.println("Entered first if condition");
				return new CommandResult(MESSAGE_FAIL);
			}
			UndoAndRedo reqCommand = (UndoAndRedo) model.getRedoStack().pop();
			if(model.getUndoStack().isEmpty()) {
				return new CommandResult(MESSAGE_FAIL);	
			}
			model.getUndoStack().push(reqCommand);
			return reqCommand.redo();
		}
	}
}
