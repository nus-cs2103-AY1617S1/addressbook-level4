package seedu.task.logic.commands;

import java.util.EmptyStackException;

//@@author A0153411W
public class RedoCommand extends Command {

	public static final String COMMAND_WORD = "redo";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo last undone command.\n" + "Example: "
			+ COMMAND_WORD;
	
	public static final String MESSAGE_NOTHING_TO_REDO = COMMAND_WORD + ": There is nothing to redo.";
	

	@Override
	public CommandResult execute() {
		try {
			Command command = model.getCommandForRedo();
			CommandResult result=  command.execute();
			result.preAppendToResult("REDO COMMAND: ");
			return result;
		} catch (EmptyStackException e) {
			return new CommandResult(MESSAGE_NOTHING_TO_REDO);
		}
	}

	@Override
	public CommandResult executeUndo() {
		return null;
	}

	@Override
	public boolean isReversible() {
		return false;
	}
    //@@author
}
