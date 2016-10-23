package seedu.taskitty.logic.commands;

import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.commons.util.AppUtil;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.model.task.UniqueTaskList.DuplicateMarkAsDoneException;
import seedu.taskitty.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD + " [category] [index]";
    public static final String MESSAGE_USAGE = "This command marks a task in TasKitty as done, Meow!";

    public static final String MESSAGE_MARK_TASK_AS_DONE_SUCCESS = "Task done: %1$s";
    public static final String MESSAGE_DUPLICATE_MARK_AS_DONE_ERROR = "The task \"%1$s\" has already been marked as done.";

    public final int categoryIndex;
    
    public final int targetIndex;

    public DoneCommand(int targetIndex) {
        this(targetIndex, Task.DEFAULT_CATEGORY_INDEX);
    }
    
    public DoneCommand(int targetIndex, int categoryIndex) {
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

        ReadOnlyTask taskToBeMarkedDone = lastShownList.get(targetIndex - 1);

        try {
            model.doneTask(taskToBeMarkedDone);
        } catch (DuplicateMarkAsDoneException dmade) {
        	model.removeUnchangedState();
        	return new CommandResult(String.format(MESSAGE_DUPLICATE_MARK_AS_DONE_ERROR, taskToBeMarkedDone.getName()));
        } catch (TaskNotFoundException pnfe) {
            model.removeUnchangedState();
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_MARK_TASK_AS_DONE_SUCCESS, Task.CATEGORIES[categoryIndex], taskToBeMarkedDone));
    }
    
    @Override
    public void saveStateIfNeeded(String commandText) {
        model.saveState(commandText);
    }
}
