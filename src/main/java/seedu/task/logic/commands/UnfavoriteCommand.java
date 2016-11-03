package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Unfavorite a task from the task manager.
 * @@author A0147335E
 */
public class UnfavoriteCommand extends Command {
    public static final String COMMAND_WORD = "unfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unfavorite the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX TASKNAME\n"
            + "Example: " + COMMAND_WORD
            + " 4";

    public static final String MESSAGE_UNFAVORITE_TASK_SUCCESS = "Unfavorite Task: %1$s";

    public static final String MESSAGE_ALREADY_UNFAVORITED = "Task has already been unfavorited!";

    public final int targetIndex;

    public UnfavoriteCommand(int targetIndex)
    {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(boolean isUndo) {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        assert model != null;
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask currentTask = lastShownList.get(targetIndex - 1);
        boolean oldStatus = currentTask.getStatus().getFavoriteStatus();

        try {
            model.deleteTask(currentTask);
        } catch (TaskNotFoundException e) {}

        Task newTask = new Task(currentTask);
        newTask.getStatus().setFavoriteStatus(false);
        try {
            model.addTask(targetIndex - 1, newTask);
        } catch (DuplicateTaskException e) {}

        if (oldStatus == newTask.getStatus().getFavoriteStatus()) {
            return new CommandResult(MESSAGE_ALREADY_UNFAVORITED);
        }
        if (isUndo == false) {
            history.getUndoList().add(new RollBackCommand(COMMAND_WORD, newTask, null));
        }
        return new CommandResult(String.format(MESSAGE_UNFAVORITE_TASK_SUCCESS, newTask.getName()));
    }

    @Override
    public CommandResult execute(int index) {
        return null;

    }
}
