package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.DisplayTaskListEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0141019U-reused
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

    
    public DeleteCommand(int[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() {
    	doExecutionSetup();

        try {
        	List<ReadOnlyTask> tasksToDelete = getTasksToDelete();
            deleteTasks(tasksToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, tasksToDelete));
        } 
        catch (IllegalValueException e) {
        	indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(e.getMessage());
        }
        catch (TaskNotFoundException pnfe) {
        	undoModelSaveState();
        	return new CommandResult(Messages.MESSAGE_INDEX_NOT_IN_LIST);
        }
    }
    
    private void doExecutionSetup() {
    	EventsCenter.getInstance().post(new DisplayTaskListEvent(model.getFilteredTaskList()));

    	model.checkForOverdueTasks();
    	model.saveState();
    }
    
    
    private List<ReadOnlyTask> getTasksToDelete() throws IllegalValueException {
    	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        List<ReadOnlyTask> tasksToDelete = new ArrayList<>();
        
        for (int i=0; i<targetIndices.length; i++) {
        	if (lastShownList.size() < targetIndices[i]) {
        		throw new IllegalValueException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
            }

            tasksToDelete.add(lastShownList.get(targetIndices[i] - 1));
        }
        
        return tasksToDelete;
    }
    
    
    private void deleteTasks(List<ReadOnlyTask> tasksToDelete) throws TaskNotFoundException {
    	model.deleteTasks(tasksToDelete);
    }
    
    private void undoModelSaveState() {
    	model.undoSaveState();
    }
}
