package seedu.whatnow.logic.commands;

import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

//@author A0139128A
public class RedoCommand extends Command{

	public static final String COMMAND_WORD = "redo";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo the previous action "
			+ "Parameters: No parameters"
			+ "Example: " + COMMAND_WORD;

	public static final String MESSAGE_SUCCESS = "Redo Successfully";
	public static final String MESSAGE_FAIL = "Redo failure due to unexisting undo commands";
	@Override
	public CommandResult execute() throws TaskNotFoundException {
		assert model != null;
		if(model.getRedoStack().isEmpty()) {
			return new CommandResult(MESSAGE_FAIL);
		}
		else {
			UndoAndRedo reqCommand = (UndoAndRedo) model.getRedoStack().pop();
			model.getUndoStack().push(reqCommand);
			return reqCommand.redo();
		}
	}
}
