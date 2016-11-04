package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;

// @@author A0147944U
/**
 * Favorite a task from the task manager.
 */
public class RepeatCommand extends Command {

    public static final String COMMAND_WORD = "repeat";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Repeat the task identified by the index number used in the last task listing with given interval.\n"
            + "Parameters: INDEX TASKNAME, INTERVAL\n"
            + "Example: " + COMMAND_WORD
            + " 4 weekly";

    public static final String MESSAGE_REPEAT_TASK_SUCCESS = "Task %1$s repeating: %2$s";
    
    public static final String MESSAGE_INVALID_INTERVAL = "Invalid interval provided: %1$s\n"
            + "Allowed intervals: daily, weekly, fortnightly, monthly, yearly\n"
            + "or d, w, f, m, y respectively.";

    public final int targetIndex;

    protected String interval;

    public RepeatCommand(int targetIndex, String interval) {
        this.targetIndex = targetIndex;
        this.interval = interval;
    }

    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask selectedTask = lastShownList.get(targetIndex - 1);
        if (this.interval.equals("daily") || this.interval.equals("d")) {
            this.interval = "daily";
        } else if (this.interval.equals("weekly") || this.interval.equals("w")) {
            this.interval = "weekly";
        } else if (this.interval.equals("fortnightly") || this.interval.equals("f")) {
            this.interval = "fortnightly";
        } else if (this.interval.equals("monthly") || this.interval.equals("m")) {
            this.interval = "monthly";
        } else if (this.interval.equals("yearly") || this.interval.equals("y")) {
            this.interval = "yearly";
        } else if (this.interval.equals("stop") || this.interval.equals("end")) {
            this.interval = "false";
        } else {
            return new CommandResult(String.format(MESSAGE_INVALID_INTERVAL, interval));
        }
        selectedTask.getRecurring().setRecurring(interval);
        return new CommandResult(String.format(MESSAGE_REPEAT_TASK_SUCCESS, selectedTask.getName(), interval));
    }

    @Override
    public CommandResult execute(int index) {
        return null;
    }
}
