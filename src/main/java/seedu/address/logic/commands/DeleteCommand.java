package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (positive integer) [MORE_INDICES] ... \n"
            + "Example: " + COMMAND_WORD + " 1 3";
 
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted tasks: %1$s";
    public static final String MESSAGE_UNDO_DELETE_SUCCESS = "Undid deletion of tasks: %1$s";

    private final int[] targetIndices;
    private ArrayList<Task> recentDeletedTasks;

    public DeleteCommand(int[] targetIndices) {
        this.targetIndices = targetIndices;
    }


    @Override
    public CommandResult execute() {
    	model.checkForOverdueTasks();
    	model.saveState();
    	
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        ArrayList<ReadOnlyTask> tasksToDelete = new ArrayList<>();
        
        for (int i=0; i<targetIndices.length; i++) {
        	if (lastShownList.size() < targetIndices[i]) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            tasksToDelete.add(lastShownList.get(targetIndices[i] - 1));
        }
        
        try {
            model.deleteTasks(tasksToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        
        recentDeletedTasks = new ArrayList<>();
        for (ReadOnlyTask task : tasksToDelete) {
        	recentDeletedTasks.add(new Task(task));
        }
        
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, tasksToDelete));
    }


//	@Override
//	public CommandResult undo() {
//		AddCommand addCommand;
//		
//		for (Task task : recentDeletedTasks) {
//			addCommand = new AddCommand(task);
//			addCommand.setData(model);
//			addCommand.execute();
//		}
//		
//		return new CommandResult(String.format(MESSAGE_UNDO_DELETE_SUCCESS, recentDeletedTasks));
//	}

}
