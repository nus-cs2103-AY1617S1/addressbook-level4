package seedu.taskitty.logic.commands;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.commons.util.AppUtil;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD + " [category] [index]";
    public static final String MESSAGE_USAGE = "This command deletes a task from TasKitty, Meow!";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted" + " %1$s: %2$s\n";
    
    public int categoryIndex;
    
    public int targetIndex;
    
    private final List<Pair<Integer, Integer>> listOfIndexes;
    
    public DeleteCommand(List<Pair<Integer, Integer>> listOfIndexes) {
        this.listOfIndexes = listOfIndexes;
    }

    @Override
    public CommandResult execute() {
        ArrayList<ReadOnlyTask> listOfTaskToDelete = new ArrayList<ReadOnlyTask>();
        StringBuilder listOfTaskToDeleteBuilder = new StringBuilder();
        for (Pair<Integer, Integer> indexPair: listOfIndexes) {
            categoryIndex = indexPair.getKey();
            targetIndex = indexPair.getValue();
            assert categoryIndex >= 0 && categoryIndex < 3;
            
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = AppUtil.getCorrectListBasedOnCategoryIndex(model, categoryIndex); 
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                model.removeUnchangedState();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            
            ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
            if (!listOfTaskToDelete.contains(taskToDelete)) {
                listOfTaskToDelete.add(taskToDelete);
                listOfTaskToDeleteBuilder.append(String.format(MESSAGE_DELETE_TASK_SUCCESS, 
                        Task.CATEGORIES[categoryIndex], taskToDelete.toString()));
            }                        
        }
        
        try {
            for (ReadOnlyTask taskToDelete: listOfTaskToDelete) {
                model.deleteTask(taskToDelete);
            }
        } catch (TaskNotFoundException pnfe) {
            model.removeUnchangedState();
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(listOfTaskToDeleteBuilder.toString());
    }

    @Override
    public void saveStateIfNeeded(String commandText) {
        model.saveState(commandText);
    }

}
