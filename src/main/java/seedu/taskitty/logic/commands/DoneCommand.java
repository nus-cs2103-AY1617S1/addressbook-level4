package seedu.taskitty.logic.commands;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.commons.util.AppUtil;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.model.task.UniqueTaskList.DuplicateMarkAsDoneException;
import seedu.taskitty.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0130853L
/**
 * Marks a task as done identified using it's last displayed index from the task manager.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD + " [category] [index]";
    public static final String MESSAGE_USAGE = "This command marks a task in TasKitty as done, Meow!";

    public static final String MESSAGE_MARK_TASK_AS_DONE_SUCCESS = "Task done: %1$s";
    public static final String MESSAGE_DUPLICATE_MARK_AS_DONE_ERROR = "The task \"%1$s\" has already been marked as done.";

    public int categoryIndex;
    
    public int targetIndex;
    
    private final List<Pair<Integer, Integer>> listOfIndexes;
    
    public DoneCommand(List<Pair<Integer, Integer>> listOfIndexes) {
        this.listOfIndexes = listOfIndexes;
    }

    @Override
    public CommandResult execute() {
        
        ArrayList<ReadOnlyTask> listOfTaskToMarkDone = new ArrayList<ReadOnlyTask>();
        StringBuilder resultMessageBuilder = new StringBuilder();
        
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
            
            ReadOnlyTask taskToBeMarkedDone = lastShownList.get(targetIndex - 1);
            if (taskToBeMarkedDone.getIsDone()) {
                model.removeUnchangedState();
                return new CommandResult(String.format(MESSAGE_DUPLICATE_MARK_AS_DONE_ERROR, taskToBeMarkedDone.getName()));
            }
            
            if (!listOfTaskToMarkDone.contains(taskToBeMarkedDone)) {
                listOfTaskToMarkDone.add(taskToBeMarkedDone);
                resultMessageBuilder.append(String.format(MESSAGE_MARK_TASK_AS_DONE_SUCCESS, 
                        Task.CATEGORIES[categoryIndex], taskToBeMarkedDone));
            }                        
        }

        try {
             model.markTasksAsDone(listOfTaskToMarkDone);            
        } catch (TaskNotFoundException pnfe) {
            model.removeUnchangedState();
            assert false : "The target task cannot be missing";
        } catch (DuplicateMarkAsDoneException e) {
            model.removeUnchangedState();
            assert false: "The target task should not be marked done";
        }

        return new CommandResult(resultMessageBuilder.toString());
    }
    
    @Override
    public void saveStateIfNeeded(String commandText) {
        model.saveState(commandText);
    }
}
