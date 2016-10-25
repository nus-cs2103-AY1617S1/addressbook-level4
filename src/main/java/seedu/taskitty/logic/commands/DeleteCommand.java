package seedu.taskitty.logic.commands;

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

    public static final String MESSAGE_PARAMETER = COMMAND_WORD + " [index]";
    public static final String MESSAGE_USAGE = "This command deletes a task from TasKitty, Meow!"
            + "\n[index] is the index eg. t1, d1, e1.";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted" + " %1$s: %2$s";
    
    public final int categoryIndex;
    
    public final int targetIndex;
    
    public DeleteCommand(int targetIndex) {
        // default to Todo category if no given category
        this(targetIndex, Task.DEFAULT_CATEGORY_INDEX);
    }
    
    public DeleteCommand(int targetIndex, int categoryIndex) {
        this.targetIndex = targetIndex;
        this.categoryIndex = categoryIndex;
    }

    @Override
    public CommandResult execute() {
        assert categoryIndex >= 0 && categoryIndex < 3;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = AppUtil.getCorrectListBasedOnCategoryIndex(model, categoryIndex);   
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            model.removeUnchangedState();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            model.removeUnchangedState();
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, Task.CATEGORIES[categoryIndex], taskToDelete));
    }

    @Override
    public void saveStateIfNeeded(String commandText) {
        model.saveState(commandText);
    }

}
