package seedu.task.logic.commands;

import java.util.EmptyStackException;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

public class UndoCommand extends Command {

	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo last executed command.\n" + "Example: "
			+ COMMAND_WORD;
	
	public static final String MESSAGE_NOTHING_TO_UNDO = COMMAND_WORD + ": There is nothing to undo.";
	

	@Override
	public CommandResult execute() {
		try {
			Command command = model.getCommandForUndo();
			return command.executeUndo();
		} catch (EmptyStackException e) {
			return new CommandResult(MESSAGE_NOTHING_TO_UNDO);
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
}
