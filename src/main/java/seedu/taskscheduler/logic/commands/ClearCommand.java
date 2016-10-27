package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.model.TaskScheduler;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.TaskArray;
import seedu.taskscheduler.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0148145E

/**
 * Clears the Task Scheduler.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task scheduler has been cleared!";
    private final TaskArray taskArray;
    
    /**
     * Instantiate taskArray which is use to store the cleared tasks for undo
     */
    public ClearCommand() {
        taskArray = new TaskArray();
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        for (ReadOnlyTask task : model.getTaskScheduler().getTaskList()) {
            taskArray.add((Task) task);
        }
        model.resetData(TaskScheduler.getEmptyTaskScheduler());
        CommandHistory.addExecutedCommand(this);
        return new CommandResult(MESSAGE_SUCCESS);
    }


    @Override
    public CommandResult revert() {
        assert model != null;
        try {
            model.addTask(taskArray.getArray());
        } catch (DuplicateTaskException e) {
            assert false : Messages.MESSAGE_TASK_CANNOT_BE_DUPLICATED;
        }
        CommandHistory.addRevertedCommand(this);
        return new CommandResult(String.format(MESSAGE_REVERT_COMMAND, COMMAND_WORD, taskArray.toString()));
        
    }
}
