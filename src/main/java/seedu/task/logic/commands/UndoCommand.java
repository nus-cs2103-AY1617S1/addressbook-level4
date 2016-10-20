package seedu.task.logic.commands;

import java.util.logging.Logger;
import seedu.task.commons.exceptions.UndoableException;
import seedu.task.logic.LogicManager;
import seedu.taskcommons.core.LogsCenter;


/**
 * Undoes the most recent modification to the TaskBook
 * @author xuchen
 *
 */
public class UndoCommand extends Command{
	private final Logger logger = LogsCenter.getLogger(UndoCommand.class);
	
	public static final String MESSAGE_UNDO_FAILURE = "No more operations to undo";
	public static final String COMMAND_WORD = "undo";
	@Override
	public CommandResult execute() {
		try{
			UndoableCommand toBeUndone = commandList.pop();
			logger.info("-----------[SYSTEM UNDO COMMAND][" + toBeUndone.toString() + "]");
			return toBeUndone.undo();
		} catch (UndoableException e) {
			return new CommandResult(MESSAGE_UNDO_FAILURE);
		}
	}
}
