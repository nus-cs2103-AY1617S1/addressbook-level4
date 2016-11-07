package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

// @@author A0147335E
/**
 * Done a task from the task manager.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Done the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX TASKNAME\n" + "Example: " + COMMAND_WORD + " 4";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Done Task: %1$s";

    public static final String MESSAGE_ALREADY_DONE = "Task has already been done!";

    public int targetIndex;
    public int currentIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        currentIndex = targetIndex;
    }

    public DoneCommand(int targetIndex, int currentIndex) {
        this.targetIndex = targetIndex;
        this.currentIndex = currentIndex;
    }

    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < currentIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask currentTask = lastShownList.get(currentIndex - 1);
        boolean oldStatus = currentTask.getStatus().getDoneStatus();

        try {
            model.deleteTask(currentTask);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        Task taskToDone = new Task(currentTask);
        taskToDone.getStatus().setDoneStatus(true);

        try {
            model.addTask(targetIndex - 1, taskToDone);
        } catch (UniqueTaskList.DuplicateTaskException e) {
        }

        if (oldStatus == taskToDone.getStatus().getDoneStatus()) {
            return new CommandResult(MESSAGE_ALREADY_DONE);
        }
        // @@author A0147944U
        else {
            // Attempt to repeat the recurring task if done status successfully
            // changed
            model.repeatRecurringTask(new Task(currentTask));

        }

        // Sorts updated list of tasks
        model.autoSortBasedOnCurrentSortPreference();

        // @@author A0147335E
        int index = model.getTaskManager().getTaskList().indexOf(taskToDone);
        if (!isUndo) {
            history.getUndoList().add(new RollBackCommand(COMMAND_WORD, taskToDone, null, index));
        }
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToDone.getName()));
    }

}