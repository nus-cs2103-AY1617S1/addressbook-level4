package seedu.todolist.logic.commands;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.task.Interval;
import seedu.todolist.model.task.Location;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.Remarks;
import seedu.todolist.model.task.Status;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.TaskDate;
import seedu.todolist.model.task.TaskTime;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.todolist.ui.MainWindow;

/**
 * Set notification for a task
 */
public class NotifyCommand extends Command{

	public static final String COMMAND_WORD = "notify";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets notification for a task in the list displayed. "
			+ "Parameters: [index] [number of hours beforehand] \n"
			+ "Example: " + COMMAND_WORD
			+ " 1 24";

	public static final String MESSAGE_SUCCESS = "Notification Set: %1$s\t\t\t\t%2$d hours beforehand.";

	private final int targetIndex;
	private final int bufferTime;


	public NotifyCommand(int targetIndex, int bufferTime) {
		this.targetIndex = targetIndex;
		this.bufferTime = bufferTime;
	}

	@Override
	public CommandResult execute() {
		
		UnmodifiableObservableList<ReadOnlyTask> lastShownList;
        
        if (model.getCurrentTab().equals(MainWindow.TAB_TASK_COMPLETE)) {
            lastShownList = model.getFilteredCompleteTaskList();
        }
        else {
            lastShownList = model.getFilteredIncompleteTaskList();
        }
        
		if (targetIndex < 1 || lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyTask taskToNotify = lastShownList.get(targetIndex - 1);
		
		try {
			model.notifyTask(taskToNotify, bufferTime);
		} catch (TaskNotFoundException pnfe) {
			assert false : "The target task cannot be missing";
		}

		return new CommandResult(String.format(MESSAGE_SUCCESS, taskToNotify, bufferTime));
	}

}
