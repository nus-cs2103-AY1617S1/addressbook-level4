package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.DisplayTaskListEvent;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using its displayed index in the last task listing .
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX [MORE_INDICES] ... \n"
            + "Example: " + COMMAND_WORD + " 1 3";
 
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted tasks: %1$s";

    private final int[] targetIndices;
    private ArrayList<Task> recentDeletedTasks;

    public DeleteCommand(int[] targetIndices) {
        this.targetIndices = targetIndices;
    }


    @Override
    public CommandResult execute() {
    	EventsCenter.getInstance().post(new DisplayTaskListEvent(model.getFilteredTaskList()));

    	model.checkForOverdueTasks();
    	model.saveState();
    	
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        ArrayList<ReadOnlyTask> tasksToDelete = new ArrayList<>();
        
        for (int i=0; i<targetIndices.length; i++) {
        	if (lastShownList.size() < targetIndices[i]) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
            }

            tasksToDelete.add(lastShownList.get(targetIndices[i] - 1));
        }
        
        try {
            model.deleteTasks(tasksToDelete);
        } catch (TaskNotFoundException pnfe) {
        	model.undoSaveState();
        	// TODO use variable instead
        	return new CommandResult("Task index does not exist in displayed list.");
        }
        
        recentDeletedTasks = new ArrayList<>();
        for (ReadOnlyTask task : tasksToDelete) {
        	recentDeletedTasks.add(new Task(task));
        }
        
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, tasksToDelete));
    }
}
