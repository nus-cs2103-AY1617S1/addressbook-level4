package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.PersonNotFoundException;
import java.util.Set;

/**
 * Deletes a person identified using it's last displayed index from the address
 * book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_TASK_FAILURE = "No such task was found.";
    public static final String MESSAGE_DELETE_IN_NEXT_STEP = "Multiple tasks were found containing the entered keywords. Please check below and delete by index.";

    public static final String STRING_UNUSED_DEFAULT_VAL = "";
    public static final int INDEX_UNUSED_DEFAULT_VAL = -1;

    public final int targetIndex;
    public final String taskName;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.taskName = STRING_UNUSED_DEFAULT_VAL;
    }

    public DeleteCommand(String taskName) {
        taskName = taskName.trim();
        this.taskName = taskName;
        this.targetIndex = INDEX_UNUSED_DEFAULT_VAL;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();
        ReadOnlyTask taskToDelete = lastShownList.get(0);
        boolean deleteTaskDirect = false;
        boolean noMatchingTasksFound = false;

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (targetIndexWasEntered()) {
            taskToDelete = lastShownList.get(targetIndex - 1);
            deleteTaskDirect = true;
        } 
        
        else {
        
            List<ReadOnlyTask> matchingTasks = model.getFilteredTaskListFromTaskName(taskName);
            if (matchingTasks.size() == 1) {
                taskToDelete = matchingTasks.get(0);
                deleteTaskDirect = true;
            } 
            else if (matchingTasks.size() > 1) {
                Set<String> keywords = new HashSet<String>();
                keywords.add(taskName);
                model.updateFilteredPersonList(keywords);
            }
            else {
                noMatchingTasksFound = true;
            }

        }
        if (deleteTaskDirect) {
            try {
                model.deleteTask(taskToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete.getName()));
        }
        else if(!noMatchingTasksFound) return new CommandResult(MESSAGE_DELETE_IN_NEXT_STEP);
        else return new CommandResult(MESSAGE_DELETE_TASK_FAILURE);
    }

    boolean targetIndexWasEntered() {
        return (targetIndex >= 0);
    }
}