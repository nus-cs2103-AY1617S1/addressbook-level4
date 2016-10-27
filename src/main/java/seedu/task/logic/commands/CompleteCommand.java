//@@author A0153467Y
package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.Task;
import seedu.task.model.task.ReadOnlyTask;

public class CompleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as completed.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_ROLLBACK_SUCCESS = "Undo action on complete task was executed successfully!";

    public final int targetIndex;

    public CompleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(false, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask orginialTask = lastShownList.get(targetIndex - 1);
        try {
            Task completedTask = new Task(orginialTask);
            completedTask.setIsCompleted(true);
            model.completeTask(orginialTask, completedTask);
        } catch (IllegalValueException e) {
            assert false : "Impossible";
        }
        
        return new CommandResult(true, String.format(MESSAGE_COMPLETE_TASK_SUCCESS, orginialTask));
    }

    @Override
    public CommandResult rollback() {
        assert model != null;
        
        model.rollback();
        
        return new CommandResult(true, MESSAGE_ROLLBACK_SUCCESS);
    }
}
