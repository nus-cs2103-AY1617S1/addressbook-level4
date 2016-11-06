package seedu.unburden.logic.commands;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.tag.UniqueTagList.DuplicateTagException;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Clears the address book.
 */

//@@author A0139678J
public class ClearCommand extends Command {

	public static final String COMMAND_WORD = "clear";
	public static final String MESSAGE_SUCCESS = "Unburden has been cleared!";
	public static final String MESSAGE_EMPTY = "The list is currently empty. Try Clear on another list instead.";
	public static final String MESSAGE_USAGE = COMMAND_WORD + "Clears the current list. \n";

	public ClearCommand() {
	}

	@Override
	public CommandResult execute(){
		assert model != null;
		model.saveToPrevLists();
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		if (lastShownList.size() == 0) {
			return new CommandResult(MESSAGE_EMPTY);
		}
		try {
			while(lastShownList.size() != 0){
				ReadOnlyTask taskToDelete = lastShownList.get(0); 
				model.deleteTask(taskToDelete);
			}
		} catch (TaskNotFoundException pnfe) {
			assert false : "The target task cannot be missing";
		}
		return new CommandResult(MESSAGE_SUCCESS);
	}
	
}
