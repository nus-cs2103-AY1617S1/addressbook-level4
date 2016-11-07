package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.DisplayTaskListEvent;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
//@@author A0139339W
/**
 * Set the tasks identified as done using it's last displayed index from the task manager.
 */
public class ChangeStatusCommand extends Command {

    public static final String COMMAND_WORD_DONE = "done";
    public static final String COMMAND_WORD_PENDING = "pending";

    public static final String MESSAGE_USAGE = COMMAND_WORD_DONE + " or " + COMMAND_WORD_PENDING
            + ": Set the task identified by the index number used in the last task listing as done or pending.\n"
            + "Parameters: INDEX [MORE_INDICES]... \n"
            + "Example: " + COMMAND_WORD_PENDING + " 1 3";
 
    public static final String MESSAGE_TASK_SUCCESS_DONE = "Task(s) marked as " + COMMAND_WORD_DONE + ": %1$s";
    public static final String MESSAGE_TASK_SUCCESS_PENDING = "Task(s) marked as " + COMMAND_WORD_PENDING + ": %1$s";
    
    private final int[] indices;
    private final String newStatus;

    
    public ChangeStatusCommand(int[] indices, String newStatus) {
        this.indices = indices;
        this.newStatus = newStatus;
    }


    @Override
	public CommandResult execute() {
    	EventsCenter.getInstance().post(new DisplayTaskListEvent(model.getFilteredTaskList()));

		model.saveState();
		ArrayList<ReadOnlyTask> tasksList = new ArrayList<ReadOnlyTask>();
		try {
			tasksList = getStatusChangedTasks();
			updateStatusChangedTasks(tasksList);
		} catch (IndexOutOfBoundsException iobe) {
			return new CommandResult(iobe.getMessage());
		}
		
		model.checkForOverdueTasks();
		
		if (newStatus.trim().toLowerCase().equals("done")) {
			return new CommandResult(String.format(MESSAGE_TASK_SUCCESS_DONE, tasksList));
		}
		else {
			return new CommandResult(String.format(MESSAGE_TASK_SUCCESS_PENDING, tasksList));
		}
	}
    
    /**
     * Get all the tasks which status will be changed based on int[] indices
     * @return the list of tasks with changed status
     * @throws IndexOutOfBoundsException
     */
    public ArrayList<ReadOnlyTask> getStatusChangedTasks () 
    		throws IndexOutOfBoundsException {
    	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
    	ArrayList<ReadOnlyTask> tasksList = new ArrayList<ReadOnlyTask>();
    	for (int i = 0; i < indices.length; i++) {
			if (lastShownList.size() < indices[i]) {
				model.undoSaveState();
				indicateAttemptToExecuteIncorrectCommand();
				throw new IndexOutOfBoundsException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
			}

			Task taskChanged = new Task(lastShownList.get(indices[i] - 1));
			tasksList.add(taskChanged);
		}
    	return tasksList;
    }
    
    /**
     * Updates the status of all the tasks in @param tasksList
     * @throws IndexOutOfBoundsException
     */
    public void updateStatusChangedTasks (ArrayList<ReadOnlyTask> tasksList) 
    		throws IndexOutOfBoundsException {
    	
    	UnmodifiableObservableList<ReadOnlyTask> fullList = model.getUnfilteredTaskList();
    	for (int i = 0; i < tasksList.size(); i++) {
			Task taskChanged = (Task) tasksList.get(i);
			int index = fullList.indexOf(taskChanged);
			taskChanged.setStatus(new Status(newStatus));
			try {
				model.editTask(index, taskChanged);
			} catch (TaskNotFoundException tnfe) {
				throw new IndexOutOfBoundsException ("The task with index " + indices[i] 
						+ " cannot be found. Please refresh the list.");
			}
		}
    }

}
