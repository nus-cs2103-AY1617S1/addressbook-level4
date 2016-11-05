package seedu.todolist.logic.commands;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.todolist.ui.MainWindow;

/**
 * Deletes a task identified using it's last displayed index from the to-do list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: TASK TYPE + INDEXES (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 \n"
            + "Example: " + COMMAND_WORD + " 1, 3, 4";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Task deleted!";
    public static final int MULTIPLE_DELETE_OFFSET = 1;

    private int[] targetIndexes;

    public DeleteCommand(int[] targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    //@@author A0138601M
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = getLastShownList();       
        if (!isValidIndexes(lastShownList, targetIndexes)) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }   
        
        ReadOnlyTask[] tasksToDelete = getAllTaskToDelete(lastShownList);        
        try {
            model.deleteTask(tasksToDelete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(MESSAGE_DELETE_TASK_SUCCESS);
    }
    
    /**
     * Get the last shown listing from the selected tab
     */
    private UnmodifiableObservableList<ReadOnlyTask> getLastShownList() {
        if (model.getCurrentTab().equals(MainWindow.TAB_TASK_COMPLETE)) {
            return model.getFilteredCompleteTaskList();
        } else {
            return model.getFilteredIncompleteTaskList();
        }
    }
    
    /**
     * Returns an array of ReadOnlyTask selected using the indexes in the last shown list
     */
    private ReadOnlyTask[] getAllTaskToDelete(UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        ReadOnlyTask[] tasksToDelete = new ReadOnlyTask[targetIndexes.length];
        for (int i = 0; i < targetIndexes.length; i++) {
            tasksToDelete[i] = lastShownList.get(targetIndexes[i] - MULTIPLE_DELETE_OFFSET);         
        }
        return tasksToDelete;
    }
    
    /**
     * Check if a particular index can be deleted
     * @param targetIndex an array of indexes to be delete from lastShownList
     * @return true if all indexes are valid
     */
    private boolean isValidIndexes(UnmodifiableObservableList<ReadOnlyTask> lastShownList, int[] targetIndex) {
        for (int index : targetIndexes) {
            if (lastShownList.size() < index) {
                indicateAttemptToExecuteIncorrectCommand();
                return false;
            }
        }
        return true;
    }
}
