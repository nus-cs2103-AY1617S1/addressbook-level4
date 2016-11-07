package seedu.task.logic.commands;

import java.util.ArrayList;

import seedu.task.commons.core.Messages;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

// @@author A0147335E
/**
 * Unfavorite a task from the task manager.
 */
public class UnfavoriteCommand extends Command {
    public static final String COMMAND_WORD = "unfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unfavorite the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX TASKNAME\n" + "Example: " + COMMAND_WORD + " 4";

    public static final String MESSAGE_UNFAVORITE_TASK_SUCCESS = "Unfavorite Task: %1$s";

    public static final String MESSAGE_ALREADY_UNFAVORITED = "Task has already been unfavorited!";

    public int targetIndex;
    public int currentIndex;

    public UnfavoriteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        currentIndex = targetIndex;
    }

    public UnfavoriteCommand(int targetIndex, int currentIndex)
    {
        this.targetIndex = targetIndex;
        this.currentIndex = currentIndex;
    }
    
    @Override
    public CommandResult execute(boolean isUndo) {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        assert model != null;
        if (lastShownList.size() < currentIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask currentTask = lastShownList.get(currentIndex - 1);
        boolean oldStatus = currentTask.getStatus().getFavoriteStatus();

        try {
            model.deleteTask(currentTask);
        } catch (TaskNotFoundException e) {

        }

        Task taskToUnfavorite = new Task(currentTask);
        taskToUnfavorite.getStatus().setFavoriteStatus(false);
        try {
            model.addTask(targetIndex - 1, taskToUnfavorite);
        } catch (DuplicateTaskException e) {
        }

        if (oldStatus == taskToUnfavorite.getStatus().getFavoriteStatus()) {
            return new CommandResult(MESSAGE_ALREADY_UNFAVORITED);
        }
        
        // @@author A0147944U
        // Sorts updated list of tasks
        model.autoSortBasedOnCurrentSortPreference();
        // @@author A0147335E
        int currentIndex = model.getTaskManager().getTaskList().indexOf(taskToUnfavorite);
        if (!isUndo) {
            getUndoList().add(new RollBackCommand(COMMAND_WORD, taskToUnfavorite, null, currentIndex));
        }
        return new CommandResult(String.format(MESSAGE_UNFAVORITE_TASK_SUCCESS, taskToUnfavorite.getName()));
    }

    private ArrayList<RollBackCommand> getUndoList() {
        return history.getUndoList();
    }

}
