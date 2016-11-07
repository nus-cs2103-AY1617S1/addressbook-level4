
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
 * Undone a task from the task manager.
 */
public class UndoneCommand extends Command {
    public static final String COMMAND_WORD = "undone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undone the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX TASKNAME\n" + "Example: " + COMMAND_WORD + " 4";

    public static final String MESSAGE_UNDONE_TASK_SUCCESS = "Undone Task: %1$s";

    public static final String MESSAGE_ALREADY_UNDONE = "Task has already been undone!";

    public int targetIndex;
    public int currentIndex;

    public UndoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        currentIndex = targetIndex;
    }

    public UndoneCommand(int targetIndex, int currentIndex) {
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
        boolean previousDoneStatus = currentTask.getStatus().getDoneStatus();

        try {
            model.deleteTask(currentTask);
        } catch (TaskNotFoundException e) {
        }

        Task taskToUndone = new Task(currentTask);
        taskToUndone.getStatus().setDoneStatus(false);
        try {
            model.addTask(targetIndex - 1, taskToUndone);
        } catch (DuplicateTaskException e) {
        }

        if (previousDoneStatus == taskToUndone.getStatus().getDoneStatus()) {
            return new CommandResult(MESSAGE_ALREADY_UNDONE);
        }

        // @@author A0147944U
        // Sorts updated list of tasks
        model.autoSortBasedOnCurrentSortPreference();
        // @@author A0147335E
        int index = model.getTaskManager().getTaskList().indexOf(taskToUndone);

        if (!isUndo) {
            getUndoList().add(new RollBackCommand(COMMAND_WORD, taskToUndone, null, index));
        }
        return new CommandResult(String.format(MESSAGE_UNDONE_TASK_SUCCESS, taskToUndone.getName()));
    }


    private ArrayList<RollBackCommand> getUndoList() {
        return history.getUndoList();
    }

}