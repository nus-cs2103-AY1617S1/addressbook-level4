package seedu.unburden.logic.commands;
import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.model.task.ReadOnlyTask;


/**
 * Deletes a person identified using it's last displayed index from the address
 * book.
 * 
 * @@author A0143095H
 */

// @@Gauri Joshi A0143095H
public class DoneCommand extends Command {

	public static final String COMMAND_WORD = "done";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Marks the task identified by the index number used in the last task listing as done.\n"
			+ "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

	public static final String MESSAGE_DONE_TASK_SUCCESS = "Well done! Task Done!";

	public final int targetIndex;

	public DoneCommand() {
		this.targetIndex = Integer.MIN_VALUE; // Dummy variable for the index
	}

	public DoneCommand(int targetIndex) {
		this.targetIndex = targetIndex;
	}

	@Override
	public CommandResult execute() {
		assert model != null;
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		if (targetIndex == Integer.MIN_VALUE) {
			model.saveToPrevLists();
			while(lastShownList.size() != 0){
				ReadOnlyTask task = lastShownList.get(0);
				model.doneTask(task, true);
			}
			return new CommandResult(MESSAGE_DONE_TASK_SUCCESS);
		} else {
			ReadOnlyTask taskToDone = lastShownList.get(targetIndex - 1);
			if (lastShownList.size() < targetIndex) {
				indicateAttemptToExecuteIncorrectCommand();
				return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
			}
			if(taskToDone.getDone()){
				return new CommandResult(Messages.MESSAGE_TASK_IS_ALREADY_DONE);
			}
			model.saveToPrevLists();
			model.doneTask(taskToDone, true);
			return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToDone));
		}
	}
}
