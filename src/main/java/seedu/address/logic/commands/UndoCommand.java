package seedu.address.logic.commands;

import java.util.Stack;

import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Undo previous add, delete and edit commands.
 */
public class UndoCommand extends Command {

	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_USAGE = COMMAND_WORD;

	public static final String MESSAGE_SUCCESS = "Undo Command: %1$s";
	public static final String MESSAGE_END_OF_UNDO = "There is no more commands to undo";

	public static final String MESSAGE_UNDO_ADD_SUCCESS = "Undo: Adding of new task: %1$s";

	@Override
	public CommandResult execute() {

		if (!PreviousCommandsStack.empty()) {
			PreviousCommand toUndo = PreviousCommandsStack.pop();

			switch (toUndo.getCommand()) {

			case AddCommand.COMMAND_WORD:
				return undoAdd(toUndo);

			}

		} else {
			return new CommandResult(MESSAGE_END_OF_UNDO);
		}
		return null;
	}

	/**
	 * Deletes task which was previously added
	 */
	private CommandResult undoAdd(PreviousCommand toUndo) {
		Task taskToDelete = toUndo.getTask();

		try {
			model.deleteTask(taskToDelete);
		} catch (TaskNotFoundException tnfe) {
			assert false : "The target task cannot be missing";
		}

		return new CommandResult(String.format(MESSAGE_UNDO_ADD_SUCCESS, taskToDelete));
	}

}
