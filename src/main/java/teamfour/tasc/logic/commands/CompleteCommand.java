//@@author A0140011L
package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.core.Messages;
import teamfour.tasc.commons.core.UnmodifiableObservableList;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.commons.util.DateUtil;
import teamfour.tasc.model.keyword.CompleteCommandKeyword;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.UniqueTaskList.DuplicateTaskException;
import teamfour.tasc.model.task.UniqueTaskList.TaskNotFoundException;
import teamfour.tasc.model.task.exceptions.TaskAlreadyCompletedException;
import teamfour.tasc.model.task.util.TaskCompleteConverter;

/**
 * Marks a task as complete using the last displayed index from the task list.
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = CompleteCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark as complete the task identified by the index number used "
            + "in the last task listing.\n" + "Parameters: INDEX (must be a positive integer)\n" + "Example: "
            + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_COMPLETE_TASK_UNDO_SUCCESS = "Marked task as uncompleted: %1$s";
    public static final String MESSAGE_COMPLETE_TASK_ALREADY_COMPLETED = "Task is already completed: %1$s";

    public final int targetIndex;

    private ReadOnlyTask oldReadOnlyTask;
    private TaskCompleteConverter newTasks;

    public CompleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        oldReadOnlyTask = lastShownList.get(targetIndex - 1);

        try {
            newTasks = new TaskCompleteConverter(oldReadOnlyTask, DateUtil.getCurrentTime());
        } catch (TaskAlreadyCompletedException | IllegalValueException e) {
            return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_ALREADY_COMPLETED,
                    oldReadOnlyTask));
        }

        try {
            model.updateTask(oldReadOnlyTask, newTasks.getCompletedTask());
            
            if (newTasks.getUncompletedRemainingRecurringTask() != null) {
                model.addTask(newTasks.getUncompletedRemainingRecurringTask());
            }
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (DuplicateTaskException dte) {
            assert false : "Not possible";
        }

        return new CommandResult(
                String.format(MESSAGE_COMPLETE_TASK_SUCCESS, newTasks.getCompletedTask()));
    }

    @Override
    public boolean canUndo() {
        return true;
    }

}
