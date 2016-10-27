package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

public class PendingCommand extends Command {
	public static final String COMMAND_WORD = "pending";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Set the task identified by the index number used in the last task listing as pending.\n"
			+ "Parameters: INDEX [MORE_INDICES] ... \n" + "Example: " + COMMAND_WORD + " 1 3";

	public static final String MESSAGE_TASK_SUCCESS = "Pending tasks: %1$s";

	private final int[] pendingIndices;

	public PendingCommand(int[] pendingIndices) {
	        this.pendingIndices = pendingIndices;
	    }

	@Override
	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		UnmodifiableObservableList<ReadOnlyTask> fullList = model.getUnfilteredTaskList();

		String pendingMessage = changeStatus(lastShownList, fullList, pendingIndices);

		return new CommandResult(pendingMessage);
	}

	private String changeStatus(UnmodifiableObservableList<ReadOnlyTask> lastShownList,
			UnmodifiableObservableList<ReadOnlyTask> fullList, int[] indices) {

		model.saveState();

		ArrayList<ReadOnlyTask> tasksList = new ArrayList<>();
		Task taskChanged;

		for (int i = 0; i < indices.length; i++) {
			if (lastShownList.size() < indices[i]) {
				// TODO avoid save state/loadPrevious in case of incorrect command, 
				// since redo stack will have an element. possibly create model.undoSaveState()
				model.loadPreviousState();
				indicateAttemptToExecuteIncorrectCommand();
				return (Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX + " for pending command");
			}

			taskChanged = new Task(lastShownList.get(indices[i] - 1));
			int index = fullList.indexOf(taskChanged);
			taskChanged.setStatus(new Status("pending"));
			tasksList.add(taskChanged);

			try {
				model.editTask(index, taskChanged);
			} catch (TaskNotFoundException e) {
				model.loadPreviousState();
				return "The task with index " + indices[i] + " cannot be found. Please refresh the list.";
			}
		}

		model.checkForOverdueTasks();

		return String.format(MESSAGE_TASK_SUCCESS, tasksList);
	}

}
