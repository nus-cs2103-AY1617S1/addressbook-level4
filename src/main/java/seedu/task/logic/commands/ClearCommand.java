package seedu.task.logic.commands;

import java.util.ArrayList;

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
    //@@author A0153411W
	public static final String MESSAGE_SUCCESS_UNDO = "Undo of clear command";
	private ArrayList<Task> savedTasksForUndo;
    //@@author 
	
	public ClearCommand() {
	}

	@Override
	public CommandResult execute() {
		assert model != null;
	    //@@author A0153411W
		saveModelForUndo();
	    //@@author
		model.resetData(TaskManager.getEmptyTaskManager());
		return new CommandResult(MESSAGE_SUCCESS);
	}

    //@@author A0153411W
	/**
	 * Before clear command is executed, save all tasks
	 * for undo command.
	 */
	private void saveModelForUndo() {
		savedTasksForUndo = new ArrayList<Task>();
		for (ReadOnlyTask task : model.getFilteredTaskList()) {
			savedTasksForUndo.add(new Task(task.getTitle(), task.getDescription(), task.getStartDate(),
					task.getDueDate(), task.getInterval(), task.getTimeInterval(), task.getStatus(), task.getTags()));
		}
	}

	/**
	 * Execute undo method for clear command - All all deleted tasks to restore 
	 * task manager to state before method was executed
	 * @throws DuplicateTaskException
	 * 				if task to add is already in manager
	 */
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
