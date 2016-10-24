package seedu.whatnow.logic.commands;

public class UndoCommand extends Command{

	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo previous task in WhatNow "
			+ "Parameters: No parameters"
			+ "Example: " + COMMAND_WORD;

	public static final String MESSAGE_SUCCESS = "Undo Successfully";
	public static final String MESSAGE_FAIL = "Undo failure due to unexisting previous commands";

	@Override
	public CommandResult execute() {
		assert model != null;
		if(model.getUndoStack().isEmpty()) {
			return new CommandResult(MESSAGE_FAIL);
		}
		else {
			UndoAndRedo reqCommand = (UndoAndRedo) model.getUndoStack().pop();
			model.getRedoStack().push(reqCommand);
			return reqCommand.undo();
		}
	}
}
