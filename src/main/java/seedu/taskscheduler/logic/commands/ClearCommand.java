package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.model.TaskScheduler;
import seedu.taskscheduler.model.Undo;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.Task;

/**
 * Clears the Task Scheduler.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task scheduler has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        Undo undo = new Undo(COMMAND_WORD);
        for (ReadOnlyTask task : model.getTaskScheduler().getTaskList()) {
            undo.addTask((Task) task);
        }
        model.resetData(TaskScheduler.getEmptyTaskScheduler());
        CommandHistory.addMutateCmd(undo);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
