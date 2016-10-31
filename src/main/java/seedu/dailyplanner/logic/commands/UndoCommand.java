package seedu.dailyplanner.logic.commands;

import seedu.dailyplanner.history.HistoryManager;

public class UndoCommand extends Command {

	public static final String COMMAND_WORD = "undo";
	public static final String MESSAGE_SUCCESS = "Undone";
	
	@Override
	public CommandResult execute() {
		Command undoCommand = model.getHistory().undoLastCommand();
		undoCommand.setData(model);
		undoCommand.execute();
		return new CommandResult(String.format(MESSAGE_SUCCESS));
	}

}
