package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.core.Messages;
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

    public DeleteCommand() {
        this(EMPTY_INDEX);
    } 
    
    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        try {
            taskToDelete = (Task) getTaskFromIndexOrLastModified(targetIndex);
        	model.deleteTask(taskToDelete);
        	CommandHistory.resetModifiedTask();
        	CommandHistory.addExecutedCommand(this);
        } catch (TaskNotFoundException tnfe) {
            return new CommandResult(tnfe.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }


    @Override
    public CommandResult revert() {
        try {
            model.addTask(taskToDelete);
            CommandHistory.setModifiedTask(taskToDelete);
            CommandHistory.addRevertedCommand(this);
        } catch (DuplicateTaskException e) {
            assert false : Messages.MESSAGE_TASK_CANNOT_BE_DUPLICATED;
        }
        return new CommandResult(String.format(MESSAGE_REVERT_COMMAND, COMMAND_WORD, "\n" + taskToDelete));
    }

}
