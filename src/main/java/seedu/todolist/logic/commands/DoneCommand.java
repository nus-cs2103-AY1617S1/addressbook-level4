package seedu.todolist.logic.commands;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.todolist.ui.MainWindow;

//@@author A0138601M
/**
 * Marks a task identified using it's last displayed index from the to do list.
 */
public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified using it's last displayed index from the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 \n";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Task marked!";
    public static final String MESSAGE_MARK_COMPLETED_TASK = "This task is already completed!";
    
    public static final int MULTIPLE_MARK_OFFSET = 1;

    private int[] targetIndexes;

    public DoneCommand(int[] targetIndexes) {
        this.targetIndexes = targetIndexes;
    }


    @Override
    public CommandResult execute() {
        if (model.getCurrentTab().equals(MainWindow.TAB_TASK_COMPLETE)) {
            return new CommandResult(MESSAGE_MARK_COMPLETED_TASK);
        }
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = getLastShownList();
        if (!isValidIndexes(lastShownList, targetIndexes)) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        ReadOnlyTask[] tasksToMark = getAllTaskToMark(lastShownList);              
        try {
            model.markTask(tasksToMark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(MESSAGE_MARK_TASK_SUCCESS);
    }
    
    /**
     * Get the last shown listing from the selected tab
     */
    private UnmodifiableObservableList<ReadOnlyTask> getLastShownList() {
        if (model.getCurrentTab().equals(MainWindow.TAB_TASK_INCOMPLETE)) {
            return model.getFilteredIncompleteTaskList();
        } else if (model.getCurrentTab().equals(MainWindow.TAB_TASK_OVERDUE)) {
            return model.getFilteredOverdueTaskList();
        } else {
            assert false : "Last shown list must come from either incomplete or overdue pane";
            return null;
        }
    }
    
    /**
     * Returns an array of ReadOnlyTask selected using the indexes in the last shown list
     */
    private ReadOnlyTask[] getAllTaskToMark(UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        ReadOnlyTask[] tasksToMark = new ReadOnlyTask[targetIndexes.length];
        for (int i = 0; i < targetIndexes.length; i++) {
            tasksToMark[i] = lastShownList.get(targetIndexes[i] - MULTIPLE_MARK_OFFSET);         
        }
        return tasksToMark;
    }
    //@@author
    
    /**
     * Check if a particular index can be marked as completed
     * @param targetIndex an array of indexes to be marked from lastShownList
     * @return true if all indexes are valid
     */
    private boolean isValidIndexes(UnmodifiableObservableList<ReadOnlyTask> lastShownList, int[] targetIndex) {
        for (int index : targetIndex) {
            if (lastShownList.size() < index) {
                indicateAttemptToExecuteIncorrectCommand();
                return false;
            }
        }
        return true;
    }
}
