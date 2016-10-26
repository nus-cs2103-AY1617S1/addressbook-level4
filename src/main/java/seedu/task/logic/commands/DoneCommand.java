package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Done a task from the task manager.
 * @@author A0147335E
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Done the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX TASKNAME\n"
            + "Example: " + COMMAND_WORD
            + " 4";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Done Task: %1$s";

    public static final String MESSAGE_ALREADY_DONE = "Task has already been done!";

    public final int targetIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask currentTask = lastShownList.get(targetIndex - 1);
        boolean oldStatus = currentTask.getStatus().getDoneStatus();

        try {
            model.deleteTask(currentTask);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        Task newTask = new Task(currentTask);
        newTask.getStatus().setDoneStatus(true);

        try {
            model.addTask(targetIndex - 1, newTask);
        } catch (UniqueTaskList.DuplicateTaskException e) {}

        if (oldStatus == newTask.getStatus().getDoneStatus()) {
            return new CommandResult(MESSAGE_ALREADY_DONE);
        }

        if (isUndo == false) {
            history.getUndoList().add(new RollBackCommand(COMMAND_WORD, newTask, null));
        }
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, newTask));
    }

    @Override
    public CommandResult execute(int index) {
        return null;
    }
}