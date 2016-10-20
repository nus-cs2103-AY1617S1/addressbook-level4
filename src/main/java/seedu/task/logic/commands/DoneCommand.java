package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.task.model.task.Status;

/**
 * Mark a task as completed which is identified using it's last displayed index from the task manager.
 */
public class DoneCommand extends Command {
    
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark a task as completed which is identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Completed Task: %1$s";
    
    public final int targetIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex - 1);

        try {
            ReadOnlyTask completedTask = taskToComplete;
            completedTask.setStatus(new Status("Completed"));
            model.completeTask(taskToComplete, completedTask);
            
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        

        return new CommandResult(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, taskToComplete));
    }

}
