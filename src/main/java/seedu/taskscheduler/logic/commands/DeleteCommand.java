package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.commons.core.UnmodifiableObservableList;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskscheduler.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0148145E

/**
 * Deletes a task identified using it's last displayed index from the Task Scheduler.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last tasks listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    private final int targetIndex;
    private Task taskToDelete;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToDelete = (Task) lastShownList.get(targetIndex - 1);

        try {
        	model.deleteTask(taskToDelete);
            CommandHistory.setModTask(null);
        	CommandHistory.addExecutedCommand(this);
        } catch (TaskNotFoundException pnfe) {
            assert false : Messages.MESSAGE_TASK_CANNOT_BE_MISSING;
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }


    @Override
    public CommandResult revert() {
        try {
            model.addTask(taskToDelete);
//            model.insertTask(targetIndex, taskToDelete);
            CommandHistory.setModTask(taskToDelete);
            CommandHistory.addRevertedCommand(this);
        } catch (DuplicateTaskException e) {
            assert false : Messages.MESSAGE_TASK_CANNOT_BE_DUPLICATED;
        }
        return new CommandResult(String.format(MESSAGE_REVERT_COMMAND, COMMAND_WORD, "\n" + taskToDelete));
    }

}
