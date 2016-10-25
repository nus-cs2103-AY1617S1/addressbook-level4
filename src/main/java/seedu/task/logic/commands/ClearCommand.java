package seedu.task.logic.commands;

import java.util.ArrayList;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.Model;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.TaskManager;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

	public static final String COMMAND_WORD = "clear";
	public static final String MESSAGE_SUCCESS = "Task Manager has been cleared!";
	public final String MESSAGE_DUPLICATE = "The edited task is a duplicate of an existing task.";
	public static final String MESSAGE_SUCCESS_UNDO = "Undo of clear command";
	private ArrayList<Task> savedTasksForUndo;

	public ClearCommand() {
	}

	@Override
	public CommandResult execute() {
		assert model != null;
		saveModelUndo();

		model.resetData(TaskManager.getEmptyTaskManager());
		return new CommandResult(MESSAGE_SUCCESS);
	}

	private void saveModelUndo() {
		savedTasksForUndo = new ArrayList<Task>();
		for (ReadOnlyTask task : model.getFilteredTaskList()) {
			savedTasksForUndo.add(new Task(task.getTitle(), task.getDescription(), task.getStartDate(),
					task.getDueDate(), task.getInterval(), task.getTimeInterval(), task.getStatus(), task.getTags()));
		}
	}

	@Override
	public CommandResult executeUndo() {
		for (Task task : savedTasksForUndo) {
			try {
				model.addTask(task);
			} catch (DuplicateTaskException e) {
				return new CommandResult(MESSAGE_DUPLICATE);
			}
		}
		return new CommandResult(MESSAGE_SUCCESS_UNDO);
	}

	@Override
	public boolean isReversible() {
		return true;
	}
}
