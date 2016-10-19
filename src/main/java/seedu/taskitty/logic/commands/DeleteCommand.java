package seedu.taskitty.logic.commands;

import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    
    public static final String CATEGORY_CHARS = "t|d|e";
    
    public static final int DEFAULT_INDEX = 0;
    
    public static final String[] CATEGORIES = {"Todo", "Deadline", "Event"};

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the category character and index number used in the last task listing.\n"
            + "Parameters: CATEGORY(default to 't' if not given or incorrect) INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + CATEGORY_CHARS + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted" + " %1$s: %2$s";
    
    public final int categoryIndex;
    
    public final int targetIndex;
    
    public DeleteCommand(int targetIndex) {
        // default to Todo category if no given category
        this(targetIndex, DEFAULT_INDEX);
    }
    
    public DeleteCommand(int targetIndex, int categoryIndex) {
        this.targetIndex = targetIndex;
        this.categoryIndex = categoryIndex;
    }


    @Override
    public CommandResult execute() {
        assert categoryIndex >= 0 && categoryIndex < 3;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getTaskList();
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

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, CATEGORIES[categoryIndex], taskToDelete));
    }

    @Override
    public void saveStateIfNeeded(String commandText) {
        model.saveState(commandText);
    }

}
