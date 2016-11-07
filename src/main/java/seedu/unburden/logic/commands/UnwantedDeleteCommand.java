package seedu.unburden.logic.commands;

import java.util.ArrayList;

import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.commons.util.StringUtil;
import seedu.unburden.model.tag.UniqueTagList.DuplicateTagException;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.UniqueTaskList.TaskNotFoundException;

//@@Author A0147986H-unused
/**
 * Deletes a task or a set of tasks identified 
 * using it's last displayed index from the address book.
 */
public class UnwantedDeleteCommand extends Command {

	public static final String COMMAND_WORD = "delete";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Deletes the task identified by the index or a range of indexes\n"
			+ "(must be positive integer)used in the last task listing.\n"
			+ "Format1: delete index1-index2\n"
			+ "Example: " + COMMAND_WORD + " 1-3\n"
			+ "Format2: delete index1 index2 index3 index4 index5\n"
			+ "Example: " + COMMAND_WORD + " 1 2 3";

	public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task:\n%1$s";

	public final ArrayList<Integer> targetIndexes;

	public UnwantedDeleteCommand(ArrayList<Integer> targetIndexes) {
		this.targetIndexes = targetIndexes;
	}

	@Override
	public CommandResult execute() throws DuplicateTagException, IllegalValueException {

		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		ArrayList<ReadOnlyTask> deletedTaskList=new ArrayList<ReadOnlyTask>();

		if (lastShownList.size() < targetIndexes.get(targetIndexes.size()-1)) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		model.saveToPrevLists();
		
		for(int i = 0; i < targetIndexes.size(); i++){

			ReadOnlyTask taskToDelete = lastShownList.get(targetIndexes.get(i) - i - 1);
			deletedTaskList.add(taskToDelete);

			try {
				model.deleteTask(taskToDelete);
			} catch (TaskNotFoundException pnfe) {
				assert false : "The target task cannot be missing";
			}
		}
		return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTaskList)));         
	}    
	
	
}

