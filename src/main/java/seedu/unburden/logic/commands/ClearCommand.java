package seedu.unburden.logic.commands;

import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.model.ListOfTask;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

	public static final String COMMAND_WORD = "clear";
	public static final String MESSAGE_SUCCESS = "Unburden has been cleared!";

	public static String mode;

	public ClearCommand() {
		this.mode = null;
	}

	public ClearCommand(String all) {
		this.mode = all;
	}

	@Override
	public CommandResult execute() {
		assert model != null;
		model.saveToPrevLists();
		if (mode.equals("all")) {
			model.resetData(ListOfTask.getEmptyAddressBook());
			return new CommandResult(MESSAGE_SUCCESS);
		} else {
			UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
			if(lastShownList.size()==0){
				return new CommandResult("The list is currently empty. Try Clear all instead.");
			}
			try {
				for (ReadOnlyTask tasksToDelete : lastShownList) {
					model.deleteTask(tasksToDelete);
				}
			} catch (TaskNotFoundException pnfe) {
				assert false : "The target task cannot be missing";
			}
		}
		return null; //Should not go here
	}
}
