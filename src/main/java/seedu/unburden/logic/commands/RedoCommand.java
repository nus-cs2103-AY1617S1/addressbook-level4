package seedu.unburden.logic.commands;

import java.util.List;
import java.util.NoSuchElementException;

import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.tag.UniqueTagList.DuplicateTagException;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.Task;

/**
 * Redo an undo action.
 * @@author A0139714B
 */
public class RedoCommand extends Command {
	
	public static final String COMMAND_WORD = "redo";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": redo the most recent undo command. \n "
			+ "Example: " + COMMAND_WORD;

	public static final String MESSAGE_SUCCESS = "Redo success!";
	public static final String MESSAGE_EMPTY_STACK = "No recent undo commands can be found.";
	

	
	public CommandResult execute() throws DuplicateTagException, IllegalValueException {
		try {
			assert model != null;
			model.loadFromUndoHistory();
			overdueOrNot();
			return new CommandResult(MESSAGE_SUCCESS);
		} catch (NoSuchElementException ee) {
			return new CommandResult(MESSAGE_EMPTY_STACK);
		}
	}
	 //This method checks the entire list to check for overdue tasks
		private void overdueOrNot() throws IllegalValueException, DuplicateTagException {
			List<ReadOnlyTask> currentTaskList= model.getListOfTask().getTaskList();
			for(ReadOnlyTask task : currentTaskList){
				if(((Task) task).checkOverDue()){
					((Task) task).setOverdue();
				}
				else{
					((Task) task).setNotOverdue();
				}
			}
		}
}
