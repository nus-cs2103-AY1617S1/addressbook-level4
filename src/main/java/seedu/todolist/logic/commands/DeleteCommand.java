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

    public final int[] targetIndexes;

    public DeleteCommand(int[] targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

  //@@author A0138601M
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = null;
        
        if (model.getCurrentTab().equals(MainWindow.TAB_TASK_COMPLETE)) {
            lastShownList = model.getFilteredCompleteTaskList();
        }
        else {
            lastShownList = model.getFilteredIncompleteTaskList();
        }
        
        if (!isValidIndexes(lastShownList, targetIndexes)) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        for (int i = 0; i < targetIndexes.length; i++) {
            ReadOnlyTask taskToDelete = lastShownList.get(targetIndexes[i] - (i + MULTIPLE_DELETE_OFFSET));
            
            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
            
        }

        return new CommandResult(MESSAGE_DELETE_TASK_SUCCESS);
    }
    //@@author
    
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
